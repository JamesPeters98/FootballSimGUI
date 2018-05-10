package uk.co.codeecho.fixture.generator;

import java.io.Serializable;

public class Fixture<T extends Object> implements Serializable {

    T homeTeam;
    T awayTeam;

    public Fixture(){

    }

    public Fixture(T homeTeam, T awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public T getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(T homeTeam) {
        this.homeTeam = homeTeam;
    }

    public T getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(T awayTeam) {
        this.awayTeam = awayTeam;
    }

}
