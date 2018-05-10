package com.james.footballsim.Simulator;

import java.io.Serializable;

public class LeagueStats implements Serializable {

	public int points;
	public int goals;
	public int goalsConceeded;
	public int wins;
	public int draws;
	public int losses;
	public Team team;
	
	public void reset(){
		points = 0;
		goals = 0;
		goalsConceeded = 0;
		wins = 0;
		draws = 0;
		losses = 0;
	}

}
