package com.james.footballsim;

import java.util.*;

import com.james.footballsim.Simulator.LeagueStats;
import com.james.footballsim.Simulator.RecordDetails;
import uk.co.codeecho.fixture.generator.Fixture;

public class Utils {

	public static void promptEnterKey(Scanner scanner){
		   System.out.println("Press \"ENTER\" to continue...");
		   //scanner.nextLine();
		}
	
	public static void promptEnterKey2(Scanner scanner){
		   System.out.println("Press \"ENTER\" to continue...");
		   scanner.nextLine();
		}
	
	public static int readNumber(Scanner scanner, String message){
		System.out.println(message);
		int n = scanner.nextInt(); // Scans the next token of the input as an int.
		scanner.nextLine();
		return n;	
	}
	public static void sortArray(ArrayList<LeagueStats> collection){
		Comparator<LeagueStats> comparator = new Comparator<LeagueStats>() {
		    @Override
		    public int compare(LeagueStats left, LeagueStats right) {
		        if(left.points > right.points) return -1;
		        else if(left.points < right.points) return 1;
		        else {
		        	if((left.goals-left.goalsConceeded)>(right.goals-right.goalsConceeded)) return -1;
		        	else if ((left.goals-left.goalsConceeded)>(right.goals-right.goalsConceeded)) return 1;
		        	else if ((left.goals-left.goalsConceeded)==(right.goals-right.goalsConceeded)){
		        		if(left.goals>right.goals) return -1;
		        		else return 1;
		        	}
		        }
				return 0;
		    }
		};

		Collections.sort(collection, comparator);
	}
	
	public static void sortArrayByAttack(ArrayList<LeagueStats> collection){
		Comparator<LeagueStats> comparator = new Comparator<LeagueStats>() {
		    @Override
		    public int compare(LeagueStats left, LeagueStats right) {
		        if(left.team.attack > right.team.attack) return -1;
		        if(left.team.attack < right.team.attack) return 1;
		        return 0;
		    }
		};

		Collections.sort(collection, comparator);
	}
	
	public static void sortArrayByDefence(ArrayList<LeagueStats> collection){
		Comparator<LeagueStats> comparator = new Comparator<LeagueStats>() {
		    @Override
		    public int compare(LeagueStats left, LeagueStats right) {
		        if(left.team.defence > right.team.defence) return -1;
		        if(left.team.defence < right.team.defence) return 1;
		        return 0;
		    }
		};

		Collections.sort(collection, comparator);
	}
	
	public static void sortArrayByTrophies(ArrayList<LeagueStats> collection){
		Comparator<LeagueStats> comparator = new Comparator<LeagueStats>() {
		    @Override
		    public int compare(LeagueStats left, LeagueStats right) {
		        if(left.team.trophiesWon > right.team.trophiesWon) return -1;
		        if(left.team.trophiesWon < right.team.trophiesWon) return 1;
		        return 0;
		    }
		};

		Collections.sort(collection, comparator);
	}
	
	public static void sortArrayByAvgPos(ArrayList<LeagueStats> collection){
		Comparator<LeagueStats> comparator = new Comparator<LeagueStats>() {
		    @Override
		    public int compare(LeagueStats left, LeagueStats right) {
		        if(left.team.averagePosition > right.team.averagePosition) return 1;
		        if(left.team.averagePosition < right.team.averagePosition) return -1;
		        return 0;
		    }
		};

		Collections.sort(collection, comparator);
	}
	
	public static void randomiseArray(List<?> collection){
		Comparator<Object> comparator = new Comparator<Object>() {
		    @Override
		    public int compare(Object left, Object right) {
		        if(Math.round(Math.random())==0) return -1;
		        else return 1;
		    }
		};

		Collections.sort(collection, comparator);
	}
	
	public static void sortArrayByBestRecord(ArrayList<RecordDetails> collection){
		Comparator<RecordDetails> comparator = new Comparator<RecordDetails>() {
		    @Override
		    public int compare(RecordDetails left, RecordDetails right) {
		        if(left.record > right.record) return -1;
		        if(left.record < right.record) return 1;
		        return 0;
		    }
		};

		Collections.sort(collection, comparator);
	}
	
//	public static void sortAlphabetical(List<Team> collection){
//		Comparator<Team> comparator = new Comparator<Team>() {
//		    @Override
//		    public int compare(Team left, Team right) {
//		        if(Math.round(Math.random())==0) return -1;
//		        else return 0;
//		    }
//		};
//
//		Collections.sort(collection, comparator);
//	}
	
	public static void putChosenTeamAtTopOfArray(LinkedList<Fixture<Integer>> fixtures){
		Comparator<Fixture<Integer>> comparator = new Comparator<Fixture<Integer>>() {
		    @Override
		    public int compare(Fixture<Integer> left, Fixture<Integer> right) {
		        if((left.getHomeTeam()==FootballSim.info.teamId)||(left.getAwayTeam()==FootballSim.info.teamId)) return -1;
		        else return 1;
		    }
		};

		Collections.sort(fixtures, comparator);
	}

	public static int getMax(int a, int b) {
		return (a>b?a:b);
	}

	public static float getMax(float a, float b) {
		return (a>b?a:b);
	}


	public static int poisson(double a) {
		Random random = new Random();
		double limit = Math.exp(-a), prod = random.nextDouble();
		int n;
		for (n = 0; prod >= limit; n++)
			prod *= random.nextDouble();
		return n;
	}

}
