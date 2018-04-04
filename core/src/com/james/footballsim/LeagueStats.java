package com.james.footballsim;

public class LeagueStats {
	
	int points;
	int goals;
	int goalsConceeded;
	int wins;
	int draws;
	int losses;
	Team team;
	
	public void reset(){
		points = 0;
		goals = 0;
		goalsConceeded = 0;
		wins = 0;
		draws = 0;
		losses = 0;
	}

}
