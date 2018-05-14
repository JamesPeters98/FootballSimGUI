package com.james.footballsim.Simulator;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by James on 07/02/2018.
 */
public class Player implements Serializable {

    static int totalPlayers = 0;

    protected String firstName;
    protected String lastName;

    public static final int GOALKEEPER = 0;
    public static final int DEFENDER = 1;
    public static final int MIDFIELDER = 2;
    public static final int FORWARD = 3;

    protected int goalkeeping;
    protected int defense;
    protected int midfield;
    protected int attack;

    int type;

    int rating;
    int growth;

    boolean injured = false;
    int injuryLength;

    int id;

    public Player(){

    }

    public Player(int type, float averageTeamRating) {
        this.type = type;
        this.id = totalPlayers;
        totalPlayers++;
        NameGenerator generator = NameGenerator.getInstance();
        firstName = generator.getFirstName();
        lastName = generator.getLastName();
        Random rand = new Random();

        switch (type) {
            case GOALKEEPER:
                goalkeeping = generateRating(rand,averageTeamRating);
                rating = goalkeeping;
                break;
            case DEFENDER:
                defense = generateRating(rand,averageTeamRating);
                rating = defense;
                break;
            case MIDFIELDER:
                midfield = generateRating(rand,averageTeamRating);
                rating = midfield;
                break;
            case FORWARD:
                attack = generateRating(rand,averageTeamRating);
                rating = attack;
                break;
        }

    }

    private int generateRating(Random rand, float averageTeamRating){
        int r = (int) Math.round(averageTeamRating + (rand.nextGaussian())*1);
        growth = (int) (((95-r)/2)+(rand.nextGaussian()*((99-r)/6)));
        return r;
    }

    public String getPosition(){
        switch (type) {
            case GOALKEEPER:
                return "Goalkeeper";
            case DEFENDER:
                return "Defender";
            case MIDFIELDER:
                return "Midfielder";
            case FORWARD:
                return "Forward";
            default:
                return "None";
        }
    }

    public String getShortPosition(){
        switch (type) {
            case GOALKEEPER:
                return "GK";
            case DEFENDER:
                return "D";

            case MIDFIELDER:
                return "M";
            case FORWARD:
                return "F";
            default:
                return "None";
        }
    }

    public String getMatchName(){
        return (firstName.substring(0,1)+". "+lastName);
    }

    public String getFullName(){
        return (firstName+" "+lastName);
    }

    public int getRating(){
        return rating;
    }

    public int getGrowth(){
        return growth;
    }

    public String getRatingAndGrowth(){
        return rating+" (+"+growth+")";
    }

    public boolean isInjured() {
        return injured;
    }

    public int getInjuryLength() {
        return injuryLength;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Player)) {
            return false;
        }
        Player player = (Player) o;
        return player.id == id;
    }

    public int hashCode() {
        return id;
    }

//    @Override
//    public void write(Kryo kryo, Output output) {
//        output.writeInt(totalPlayers);
//        output.writeString(firstName);
//        output.writeString(lastName);
//    }
//
//    @Override
//    public void read(Kryo kryo, Input input) {
//        totalPlayers = input.readInt();
//        firstName = input.readString();
//        lastName = input.readString();
//    }
}
