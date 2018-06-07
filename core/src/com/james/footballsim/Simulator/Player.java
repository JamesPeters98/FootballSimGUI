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
                defense = generateRating(rand,averageDefenceRating,3f);
                attack = generateRating(rand,averageAttackRating/5,2f);
                rating = defense;
                break;
            case DEFENDER:
                defense = generateRating(rand,averageDefenceRating,2.5f);
                attack = generateRating(rand,averageAttackRating*0.4f,2f);
                rating = defense;
                break;
            case MIDFIELDER:
                attack = generateRating(rand,averageAttackRating,2f);
                defense = generateRating(rand,averageDefenceRating,2f);
                rating = (attack+defense)/2;
                break;
            case FORWARD:
                attack = generateRating(rand,averageAttackRating,2.5f);
                defense = generateRating(rand,averageDefenceRating/5,5f);
                rating = attack;
                break;
        }

        generatePotential(rand,rating);

    }

    private int generateRating(Random rand, float averageTeamRating, float spread){
        int r = (int) Math.round(averageTeamRating + (rand.nextGaussian())*spread);
        return r;
    }

    private void generatePotential(Random rand, float r){
        growth = (int) (((90-r)/2)+(rand.nextGaussian()*((99-r)/6)));
    }

    public void grow(){
        switch(type) {
            case GOALKEEPER:
                defense++;
                growth--;
                break;
            case DEFENDER:
                defense++;
                growth--;
                break;
            case MIDFIELDER:
                attack++;
                defense++;
                growth--;
                break;
            case FORWARD:
                attack++;
                growth--;
                break;
        }
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

    public int getWeightedRating(){
        switch (type) {
            case GOALKEEPER:
                return defense;
            case DEFENDER:
                return defense;
            case MIDFIELDER:
                return (attack+defense)/2;
            case FORWARD:
                return attack;
            default:
                return 0;
        }
    }

}
