package com.james.footballsim.Simulator;

public interface LeagueType  {

    //Leagues Name
    String getName();

    //Add teams to league using league.addTeam()
    void addTeams(League league);

    //Get number of fixtures per season including playoffs;
    int getFixtureLength();

    boolean hasPlayOffs();

    int divisionPosition();

    boolean lastDivision();

}
