package com.james.footballsim.Simulator;

import com.james.footballsim.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class League implements Serializable {
	
	private HashMap<Integer,Team> teams;
	private HashMap<Team,LeagueStats> leagueStats;
	
	public League(){

	}

	public League init(){
		teams = new HashMap<>();
		leagueStats = new HashMap<>();
		addTeam(Teams.CHELSEA);
		addTeam(Teams.LIVERPOOL);
		addTeam(Teams.MAN_CITY);
		addTeam(Teams.TOTTENHAM);
		addTeam(Teams.ARSENAL);
		addTeam(Teams.MAN_UNITED);
		addTeam(Teams.EVERTON);
		addTeam(Teams.SOUTHAMPTON);
		addTeam(Teams.CRYS_PALACE);
		addTeam(Teams.BOURNEMOUTH);
		addTeam(Teams.LEICESTER);
		addTeam(Teams.SWANSEA);
		addTeam(Teams.WEST_BROM);
		addTeam(Teams.WEST_HAM);
		addTeam(Teams.BURNLEY);
		addTeam(Teams.HULL);
		addTeam(Teams.MIDDLESBROUGH);
		addTeam(Teams.WATFORD);
		addTeam(Teams.STOKE);
		addTeam(Teams.SUNDERLAND);
		return this;
	}
	
	private void addTeam(Team team){
		teams.put(team.id,team);
		leagueStats.put(team, new LeagueStats());
	}
	
	public HashMap<Integer,Team> getTeams(){
		return teams;
	}

	public HashMap<Team, LeagueStats> getLeagueStats() {
		return leagueStats;
	}

	public Team getTeam(int id){
		return teams.get(id);
	}
	
	public void addStat(MatchResult result){
		LeagueStats statsHome = leagueStats.get(result.getHomeTeam());
		if(statsHome.team == null) statsHome.team = result.getHomeTeam();
		statsHome.goals += result.getHomeGoals();
		statsHome.goalsConceeded += result.getAwayGoals();
		statsHome.points += result.getHomePoints();
		if(result.getHomePoints() == 3) statsHome.wins++;
		if(result.getHomePoints() == 1) statsHome.draws++;
		if(result.getHomePoints() == 0) statsHome.losses++;

		LeagueStats statsAway = leagueStats.get(result.getAwayTeam());
		if(statsAway.team == null) statsAway.team = result.getAwayTeam();
		statsAway.goals += result.getAwayGoals();
		statsAway.goalsConceeded += result.getHomeGoals();
		statsAway.points += result.getAwayPoints();
		if(result.getAwayPoints() == 3) statsAway.wins++;
		if(result.getAwayPoints() == 1) statsAway.draws++;
		if(result.getAwayPoints() == 0) statsAway.losses++;
	}
	
//	public void printWinner(){
//		ArrayList<LeagueStats> leagueStatsArray = new ArrayList<LeagueStats>(leagueStats.values());
//		Utils.sortArray(leagueStatsArray);
//		LeagueStats stats = leagueStatsArray.get(0);
//		System.out.println(stats.team.name+" won the Premier League! Total times won: "+stats.team.trophiesWon);
//	}
//
//	public void printTable(){
//		System.out.format("%-3s%-25s%-10s%-15s%-18s%-8s%-8s%-8s%-8s\n", new String[]{"","Team","Points","Goals Scored","Goals Conceeded","Wins","Draws","Losses", "Rating"});
//		System.out.println("----------------------------------------------------------------------------------------------------");
//		ArrayList<LeagueStats> leagueStatsArray = new ArrayList<LeagueStats>(leagueStats.values());
//		Utils.sortArray(leagueStatsArray);
//		for(int i = 0; i <leagueStatsArray.size();i++) {
//			LeagueStats stats = leagueStatsArray.get(i);
//		    System.out.format("%-3s%-25s%-10s%-15s%-18s%-8s%-8s%-8s%-8s\n", new String[]{""+(i+1),stats.team.name,""+stats.points,""+stats.goals,""+stats.goalsConceeded,""+stats.wins,""+stats.draws,""+stats.losses,""+stats.team.getRating()});
//		}
//		System.out.println("");
//	}
//
//	public void printAttackTable(){
////		System.out.format("%-2s%-25s%-10s\n", new String[]{"","Team","Attack"});
////		System.out.println("----------------------------------------------------------------------------------");
////		ArrayList<LeagueStats> leagueStatsArray = new ArrayList<LeagueStats>(leagueStats.values());
////		Utils.sortArrayByAttack(leagueStatsArray);
////		for(int i = 0; i <leagueStatsArray.size();i++) {
////			LeagueStats stats = leagueStatsArray.get(i);
////		    System.out.format("%-2s%-25s%-10s\n", new String[]{""+(i+1),stats.team.name,""+stats.team.attack});
////		}
////		System.out.println("");
//	}
//
//	public void printDefenceTable(){
////		System.out.format("%-2s%-25s%-10s\n", new String[]{"","Team","Defence"});
////		System.out.println("----------------------------------------------------------------------------------");
////		ArrayList<LeagueStats> leagueStatsArray = new ArrayList<LeagueStats>(leagueStats.values());
////		Utils.sortArrayByDefence(leagueStatsArray);
////		for(int i = 0; i <leagueStatsArray.size();i++) {
////			LeagueStats stats = leagueStatsArray.get(i);
////		    System.out.format("%-2s%-25s%-10s\n", new String[]{""+(i+1),stats.team.name,""+stats.team.defence});
////		}
////		System.out.println("");
//	}
//
//	public void newSeason(){
////		ArrayList<LeagueStats> leagueStatsArray = new ArrayList<LeagueStats>(leagueStats.values());
////		Utils.sortArray(leagueStatsArray);
////		Team winners = leagueStatsArray.get(0).team;
////		winners.trophiesWon++;
////		Utils.sortArrayByTrophies(leagueStatsArray);
////		System.out.format("%-2s%-25s%-10s\n", new String[]{"","Team","Trophies"});
////		System.out.println("----------------------------------------------------------------------------------");
////		for(int i = 0; i <leagueStatsArray.size();i++) {
////			LeagueStats stats = leagueStatsArray.get(i);
////		    System.out.format("%-2s%-25s%-10s\n", new String[]{""+(i+1),stats.team.name,""+stats.team.trophiesWon});
////		}
////		System.out.println("");
////
////		printWinner();
////
////
////		Utils.sortArray(leagueStatsArray);
////		for(int i = 0; i < leagueStats.size();i++) {
////			LeagueStats stats = leagueStatsArray.get(i);
////			Team team = stats.team;
////			team.totalPositions+=(i+1);
////			team.leaguesPlayed++;
////			team.averagePosition = (team.totalPositions/team.leaguesPlayed);
////			Records.MOST_POINTS.checkRecord(stats.points, team);
////			Records.MOST_GOALS_IN_SEASON.checkRecord(stats.goals, team);
////			//System.out.println(Team.attackRatio(stats.goals/100.0));
////			//team.attack = (Team.attackRatio(stats.goals/60.0));
////			//team.defence = (team.defence+(Team.defenceRatio(1-(stats.goalsConceeded/100.0))))/2;
////			//System.out.println(team.name+"| Attack Difference: "+(team.attack-oldAttack)+"| Defence Difference: "+(team.defence-oldDefence));
////			stats.reset();
////
////		}
////
////		Utils.sortArrayByAvgPos(leagueStatsArray);
////		System.out.format("%-2s%-25s%-10s\n", new String[]{"","Team","Avg Pos"});
////		System.out.println("----------------------------------------------------------------------------------");
////		for(int i = 0; i <leagueStatsArray.size();i++) {
////			LeagueStats stats = leagueStatsArray.get(i);
////		    System.out.format("%-2s%-25s%-10s\n", new String[]{""+(i+1),stats.team.name,""+stats.team.averagePosition});
////		}
////		System.out.println("");
//	}


	public static Comparator<Team> sortTeams = (p1, p2) -> {
		if(p1.getRating()>p2.getRating()) return -1;
		else if(p1.getRating()<p2.getRating()) return 1;
		else return 0;
	};


}
