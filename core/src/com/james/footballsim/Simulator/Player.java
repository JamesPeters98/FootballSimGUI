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

    protected int defense;
    protected int attack;

    int type;

    int rating;
    int growth;

    boolean injured = false;
    int injuryLength;

    int id;

    public Player(){

    }

    public Player(int type, float averageAttackRating, float averageDefenceRating) {
        this.type = type;
        this.id = totalPlayers;
        totalPlayers++;
        NameGenerator generator = NameGenerator.getInstance();
        firstName = generator.getFirstName();
        lastName = generator.getLastName();
        Random rand = new Random();

        switch (type) {
            case GOALKEEPER:
                defense = generateRating(rand,averageDefenceRating,1);
                attack = generateRating(rand,averageAttackRating/5,5f);
                rating = defense;
                break;
            case DEFENDER:
                defense = generateRating(rand,averageDefenceRating,1);
                attack = generateRating(rand,averageAttackRating*0.75f,5f);
                rating = defense;
                break;
            case MIDFIELDER:
                attack = generateRating(rand,averageAttackRating*0.9f,5f);
                defense = generateRating(rand,averageDefenceRating*0.9f,5f);
                rating = (attack+defense)/2;
                break;
            case FORWARD:
                attack = generateRating(rand,averageAttackRating,1);
                defense = generateRating(rand,averageDefenceRating/2,10f);
                rating = attack;
                break;
        }

    }

    private int generateRating(Random rand, float averageTeamRating, float spread){
        int r = (int) Math.round(averageTeamRating + (rand.nextGaussian())*spread);
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

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
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

    public float getWeightedRating(){
        switch (type) {
            case GOALKEEPER:
                return attack*Team.GK_ATTACK+defense*Team.GK_DEF;
            case DEFENDER:
                return attack*Team.CB_ATTACK+defense*Team.CB_DEF;
            case MIDFIELDER:
                return attack*Team.MID_ATTACK+defense*Team.MID_DEF;
            case FORWARD:
                return attack*Team.FORWARD_ATTACK+defense*Team.FORWARD_DEF;
            default:
                return 0;
        }
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
