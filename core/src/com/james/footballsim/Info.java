package com.james.footballsim;

import com.james.footballsim.Simulator.League;
import com.james.footballsim.Simulator.Team;
import uk.co.codeecho.fixture.generator.Fixture;

import java.io.Serializable;
import java.util.List;

public class Info implements Serializable {

    //Everything in this class gets stored.
    public Info(){

    }

    public League league;
    public List<List<Fixture<Integer>>> rounds;
    public List<Team> teams;
    public int teamId = -1;
    public int round;
    public boolean seasonRunning = false;

}
