package com.james.footballsim.Simulator;

public interface LeagueType  {

    //Leagues Name
    public String getName();

    //Add teams to league using league.addTeam()
    public void addTeams(League league);

    //Get number of fixtures per season including playoffs;
    public int getFixtureLength();

    public boolean hasPlayOffs();

    public int divisionPosition();

    public boolean lastDivision();

}
