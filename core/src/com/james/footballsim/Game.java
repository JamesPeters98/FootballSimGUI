//package com.james.footballsim;
//
//import java.util.*;
//
//import com.james.footballsim.Simulator.League;
//import com.james.footballsim.Simulator.Records;
//import com.james.footballsim.Simulator.Team;
//import uk.co.codeecho.fixture.generator.Fixture;
//import uk.co.codeecho.fixture.generator.FixtureGenerator;
//
//public class Game {
//
//	static FixtureGenerator fixtureGenerator;
//	static League leagues;
//	static List<List<Fixture<Integer>>> leagueGames;
//	static Scanner reader;
//
//	public static int teamId;
//	static Team team;
//
//	public static void main(String[] args){
//        reader = new Scanner(System.in);
//        fixtureGenerator = new FixtureGenerator();
//        leagues = new League();
//
//        List<Team> teams = new ArrayList<Team>(leagues.getTeams().values());
//        Collections.sort(teams,League.sortTeams);
//
//		System.out.println("Choose your team");
//
//        System.out.format("%-3s%-24s%-9s%-9s%-8s\n", new String[]{"|","Team", "| Rating", "| Attack", "| Defence"});
//        System.out.println("----------------------------------------------------------------------------------------------------");
//		for(Team team : teams){
//            System.out.format("%-3s%-25s%-10s%-10s%-8s\n", new String[]{""+team.id,""+team.name, ""+team.getRating(), ""+team.getAttackRating(), ""+team.getDefenceRating()});
//		}
//
//		teamId = Utils.readNumber(reader,"Enter the number of the desired team.");
//		team = leagues.getTeam(teamId);
//		team.chosenTeam = true;
//		System.out.println("Players:");
//		team.listPlayers();
//		Utils.promptEnterKey(reader);
//
//		while(true){
//			teamId = -1;
//			for(int i=0;i<=0;i++){ runSeason();}
//			Utils.promptEnterKey2(reader);
//		}
//		//runSeason();
//	}
//
//	public static void runSeason(){
//		leagueGames = fixtureGenerator.getFixtures(leagues.getTeams(), true, teamId);
//
//		System.out.println("Fixture List for "+team.name);
//		System.out.println("----------------------------");
//		for(int i=0; i<leagueGames.size(); i++){
//			List<Fixture<Integer>> round = leagueGames.get(i);
//			for(Fixture<Integer> fixture: round){
//				if((fixture.getHomeTeam() == teamId)||(fixture.getAwayTeam()==teamId)){
//					System.out.println("Week "+(i+1)+" "+leagues.getTeam(fixture.getHomeTeam()).name + " vs " + leagues.getTeam(fixture.getAwayTeam()).name);
//				}
//		    }
//		}
//		System.out.println("");
//		Utils.promptEnterKey(reader);
//
//		for(int i=0; i<leagueGames.size(); i++){
//		    System.out.println("Week " + (i+1)+" | Games remaining: "+(leagueGames.size()-i));
//		    System.out.println("---------");
//		    List<Fixture<Integer>> round = leagueGames.get(i);
//		    for(Fixture<Integer> fixture: round){
//		    	System.out.println(leagues.getTeam(fixture.getHomeTeam()).name + " vs " + leagues.getTeam(fixture.getAwayTeam()).name);
//		    }
//		    System.out.println("");
//		    Utils.promptEnterKey(reader);
////		    for(Fixture<Integer> fixture: round){
////				try {
////					//MatchResult result = new MatchSim().runMatch(reader,leagues.getTeam(fixture.getHomeTeam()),leagues.getTeam(fixture.getAwayTeam()),teamId);
////					//leagues.addStat(result);
////				} catch (InterruptedException e) {
////					e.printStackTrace();
////				}
////
////		    }
//            Utils.promptEnterKey(reader);
//            System.out.println("--------------");
//            System.out.println("Updates");
//		    System.out.println("--------------");
////			for(Fixture<Integer> fixture: round){
////				leagues.getTeam(fixture.getHomeTeam()).update();
////				leagues.getTeam(fixture.getAwayTeam()).update();
////			}
//		    System.out.println("");
//		    Utils.promptEnterKey(reader);
//		    leagues.printTable();
//		    Utils.promptEnterKey(reader);
////		    leagues.printAttackTable();
////		    Utils.promptEnterKey(reader);
////		    leagues.printDefenceTable();
////		    Utils.promptEnterKey(reader);
//		}
//		leagues.printTable();
//		leagues.endSeason();
//		Records.MOST_POINTS.printRecordTable();
//		Records.MOST_GOALS_IN_SEASON.printRecordTable();
//		Utils.promptEnterKey2(reader);
//		runSeason();
//	}
//
//}
