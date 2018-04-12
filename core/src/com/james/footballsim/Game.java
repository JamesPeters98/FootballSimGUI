package com.james.footballsim;

import java.util.*;

import uk.co.codeecho.fixture.generator.Fixture;
import uk.co.codeecho.fixture.generator.FixtureGenerator;

public class Game {
	
	static FixtureGenerator fixtureGenerator;
	static League league;
	static List<List<Fixture<Integer>>> rounds;
	static Scanner reader;
	
	public static int teamId;
	static Team team;
	
	public static void main(String[] args){
        reader = new Scanner(System.in);
        fixtureGenerator = new FixtureGenerator();
        league = new League();

        List<Team> teams = new ArrayList<Team>(league.getTeams().values());
        Collections.sort(teams,League.sortTeams);

		System.out.println("Choose your team");

        System.out.format("%-3s%-24s%-9s%-9s%-8s\n", new String[]{"|","Team", "| Rating", "| Attack", "| Defence"});
        System.out.println("----------------------------------------------------------------------------------------------------");
		for(Team team : teams){
            System.out.format("%-3s%-25s%-10s%-10s%-8s\n", new String[]{""+team.id,""+team.name, ""+team.getRating(), ""+team.getAttackRating(), ""+team.getDefenceRating()});
		}

		teamId = Utils.readNumber(reader,"Enter the number of the desired team.");
		team = league.getTeam(teamId);
		team.chosenTeam = true;
		System.out.println("Players:");
		team.listPlayers();
		Utils.promptEnterKey(reader);
		
		while(true){
			teamId = -1;
			for(int i=0;i<=0;i++){ runSeason();}
			Utils.promptEnterKey2(reader);
		}
		//runSeason();
	}
	
	public static void runSeason(){
		rounds = fixtureGenerator.getFixtures(league.getTeams(), true, teamId);
		
		System.out.println("Fixture List for "+team.name);
		System.out.println("----------------------------");
		for(int i=0; i<rounds.size(); i++){
			List<Fixture<Integer>> round = rounds.get(i);
			for(Fixture<Integer> fixture: round){
				if((fixture.getHomeTeam() == teamId)||(fixture.getAwayTeam()==teamId)){
					System.out.println("Week "+(i+1)+" "+league.getTeam(fixture.getHomeTeam()).name + " vs " + league.getTeam(fixture.getAwayTeam()).name);
				}
		    }
		}
		System.out.println("");
		Utils.promptEnterKey(reader);
		
		for(int i=0; i<rounds.size(); i++){
		    System.out.println("Week " + (i+1)+" | Games remaining: "+(rounds.size()-i));
		    System.out.println("---------");
		    List<Fixture<Integer>> round = rounds.get(i);
		    for(Fixture<Integer> fixture: round){
		    	System.out.println(league.getTeam(fixture.getHomeTeam()).name + " vs " + league.getTeam(fixture.getAwayTeam()).name);
		    }
		    System.out.println("");
		    Utils.promptEnterKey(reader);
		    for(Fixture<Integer> fixture: round){
				try {
					MatchResult result = new MatchSim().runMatch(reader,league.getTeam(fixture.getHomeTeam()),league.getTeam(fixture.getAwayTeam()),teamId);
					league.addStat(result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

		    }
            Utils.promptEnterKey(reader);
            System.out.println("--------------");
            System.out.println("Updates");
		    System.out.println("--------------");
			for(Fixture<Integer> fixture: round){
				league.getTeam(fixture.getHomeTeam()).update();
				league.getTeam(fixture.getAwayTeam()).update();
			}
		    System.out.println("");
		    Utils.promptEnterKey(reader);
		    league.printTable();
		    Utils.promptEnterKey(reader);
//		    league.printAttackTable();
//		    Utils.promptEnterKey(reader);
//		    league.printDefenceTable();
//		    Utils.promptEnterKey(reader);
		}
		league.printTable();
		league.newSeason();
		Records.MOST_POINTS.printRecordTable();
		Records.MOST_GOALS_IN_SEASON.printRecordTable();
		Utils.promptEnterKey2(reader);
		runSeason();
	}

}
