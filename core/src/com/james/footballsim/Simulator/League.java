package com.james.footballsim.Simulator;

import com.badlogic.gdx.Gdx;
import com.james.footballsim.FootballSim;
import com.james.footballsim.Utils;
import uk.co.codeecho.fixture.generator.Fixture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class League implements Serializable {
	
	private HashMap<Integer,Team> teams;
	private HashMap<Integer,LeagueStats> leagueStats;
	public List<List<Fixture<Integer>>> leagueGames;
	public LeagueType leagueType;

	//PlayOff Vars
	private List<List<Fixture<Integer>>> playOffs;
	private int playOffRound = 0;
	private List<Team> finalTeams;
	private Team playOffWinner;

	//Round -> Team -> Result
	private HashMap<Integer,List<MatchResult>> matchResults;
	private HashMap<Integer,HashMap<Integer,MatchResult>> playOffResults;

	public League(){

	}

	public League init(LeagueType leagueType){
		teams = new HashMap<>();
		leagueStats = new HashMap<>();
		matchResults = new HashMap<>();
		playOffResults = new HashMap<>();
		finalTeams = new ArrayList<>();
		this.leagueType = leagueType;
		leagueType.addTeams(this);
		return this;
	}
	
	void addTeam(Team team){
		teams.put(team.id,team);
		leagueStats.put(team.id, new LeagueStats());
	}
	
	public HashMap<Integer,Team> getTeams(){
		return teams;
	}

	public HashMap<Integer, LeagueStats> getLeagueStats() {
		return leagueStats;
	}

	public HashMap<Integer, List<MatchResult>> getMatchResults() {
		return  matchResults;
	}

	public Team getTeam(int id){
		return teams.get(id);
	}

	public List<Fixture<Integer>> getFixtures(int week){
		if(week < leagueGames.size()){
			return  leagueGames.get(week);
		} else {
			if(playOffs != null)
			return playOffs.get(week-leagueGames.size());
			else return new ArrayList<>();
		}
	}
	
	public void addStat(MatchResult result, int fixtureID){

		if(leagueType.hasPlayOffs() && playOffRound != 0) {
			playOffs(result,fixtureID);
		} else {

			if((result.getAwayTeam().id==FootballSim.info.teamId)||(result.getHomeTeam().id==FootballSim.info.teamId)) Gdx.app.log("League", "adding stats");
			List<MatchResult> weekResults = matchResults.get(FootballSim.info.round);
			if (weekResults == null) {
				matchResults.put(FootballSim.info.round, weekResults = new ArrayList<>());
			}
			weekResults.add(result);

			LeagueStats statsHome = leagueStats.get(result.getHomeTeam().id);
			if (statsHome.team == null) statsHome.team = result.getHomeTeam();
			statsHome.goals += result.getHomeGoals();
			statsHome.goalsConceeded += result.getAwayGoals();
			statsHome.points += result.getHomePoints();
			if (result.getHomePoints() == 3) statsHome.wins++;
			if (result.getHomePoints() == 1) statsHome.draws++;
			if (result.getHomePoints() == 0) statsHome.losses++;

			LeagueStats statsAway = leagueStats.get(result.getAwayTeam().id);
			if (statsAway.team == null) statsAway.team = result.getAwayTeam();
			statsAway.goals += result.getAwayGoals();
			statsAway.goalsConceeded += result.getHomeGoals();
			statsAway.points += result.getAwayPoints();
			if (result.getAwayPoints() == 3) statsAway.wins++;
			if (result.getAwayPoints() == 1) statsAway.draws++;
			if (result.getAwayPoints() == 0) statsAway.losses++;
		}
	}

	public void playOffs(MatchResult result, int fixtureID){
		if(playOffRound == 1){
			HashMap<Integer,MatchResult> firstLeg = playOffResults.get(1);
			if(firstLeg == null) playOffResults.put(playOffRound, firstLeg = new HashMap<>());
			firstLeg.put(fixtureID,result);
			playOffResults.put(playOffRound,firstLeg);
			matchResults.put(FootballSim.info.round,new ArrayList<>(firstLeg.values()));
		}
		else if(playOffRound == 2){
			HashMap<Integer,MatchResult> secondLeg = playOffResults.get(2);
			if(secondLeg == null) playOffResults.put(playOffRound, secondLeg = new HashMap<>());
			secondLeg.put(fixtureID,result);
			playOffResults.put(playOffRound,secondLeg);
			matchResults.put(FootballSim.info.round,new ArrayList<>(secondLeg.values()));

			MatchResult fixture2 = secondLeg.get(fixtureID);

			if(fixture2.hasHomeTeamWon()) finalTeams.add(fixture2.getHomeTeam());
			else finalTeams.add(fixture2.getAwayTeam());


//			int team1Goals = firstLeg.get(fixtureID).getHomeGoals()+secondLeg.get(fixtureID).getAwayGoals();
//			int team2Goals = firstLeg.get(fixtureID).getAwayGoals()+secondLeg.get(fixtureID).getHomeGoals();
//
//			if(finalTeams == null) finalTeams = new ArrayList<>();
//			if(team1Goals>team2Goals) finalTeams.add(fixture1.getHomeTeam());
//			if(team2Goals>team1Goals) finalTeams.add(fixture1.getAwayTeam());
//			if(team1Goals == team2Goals){
//				if(fixture1.getAwayGoals() > fixture2.getAwayGoals()){
//					finalTeams.add(fixture1.getAwayTeam());
//				} else if(fixture1.getAwayGoals() > fixture2.getAwayGoals()){
//					finalTeams.add(fixture1.getHomeTeam());
//				} else {
//					//Placeholder just choose random team for now
//					finalTeams.add(fixture1.getHomeTeam());
//				}
//			}

		}
		else if(playOffRound == 3){
			HashMap<Integer,MatchResult> finalResult = playOffResults.get(3);
			if(finalResult == null) playOffResults.put(playOffRound, finalResult = new HashMap<>());
			finalResult.put(fixtureID,result);
			playOffResults.put(playOffRound,finalResult);
			matchResults.put(FootballSim.info.round,new ArrayList<>(finalResult.values()));

			if(result.hasHomeTeamWon()) playOffWinner = result.getHomeTeam();
			else playOffWinner = result.getAwayTeam();
		}
	}

	public MatchSim runMatches(int round){
		MatchSim matchSim = null;
		for (Fixture<Integer> fixture : getFixtures(round)) {
			if ((fixture.getHomeTeam() == FootballSim.info.teamId) || (fixture.getAwayTeam() == FootballSim.info.teamId)) {
				Gdx.app.log("League "+leagueType.getName(), "Found chosen team fixture.");
				if(playOffRound == 2) matchSim = new MatchSim(getTeam(fixture.getHomeTeam()),getTeam(fixture.getAwayTeam()),this, playOffResults.get(2).get(fixture.id), fixture.id);
				else if(playOffRound == 3) matchSim = new MatchSim(getTeam(fixture.getHomeTeam()),getTeam(fixture.getAwayTeam()),this, true, fixture.id);
				else matchSim = new MatchSim(getTeam(fixture.getHomeTeam()), getTeam(fixture.getAwayTeam()),this);
			} else {
				MatchResult result = new MatchSim(getTeam(fixture.getHomeTeam()), getTeam(fixture.getAwayTeam()),this).runMatchBackground();
				addStat(result,fixture.id);
			}
		}
		return matchSim;
	}

	public void postEndSeason(){
		Gdx.app.log("League", "Post Season");
		if(FootballSim.info.round < leagueGames.size()){
			for(int r = FootballSim.info.round; r < leagueGames.size(); r++){
				runMatches(r);
				Gdx.app.log("League: "+leagueType.getName(), "Running matches for week: "+r);
			}
		}
		if(leagueType.hasPlayOffs()){
			ArrayList<LeagueStats> leagueStatsArray = new ArrayList<LeagueStats>(leagueStats.values());
			if(FootballSim.info.round == leagueGames.size() && playOffRound == 0){
				Utils.sortArray(leagueStatsArray);
				playOffs = new ArrayList<>();
				List<Fixture<Integer>> round = new ArrayList<>();
				Fixture<Integer> fixture1 = new Fixture<>(leagueStatsArray.get(2).team.id,leagueStatsArray.get(5).team.id);
				fixture1.id = 1;
				Fixture<Integer> fixture2 = new Fixture<>(leagueStatsArray.get(3).team.id,leagueStatsArray.get(4).team.id);
				fixture2.id = 2;
				round.add(fixture1);
				round.add(fixture2);
				playOffs.add(round);
				playOffRound++;
			}
			else if(FootballSim.info.round == leagueGames.size()+1 && playOffRound == 1){
				List<Fixture<Integer>> round = new ArrayList<>();
				HashMap<Integer, MatchResult> result = playOffResults.get(playOffRound);
				MatchResult matchResult1 = result.get(1);
				MatchResult matchResult2 = result.get(2);
				Fixture<Integer> fixture1 = new Fixture<>(matchResult1.getAwayTeam().id,matchResult1.getHomeTeam().id);
				fixture1.id = 1;
				Fixture<Integer> fixture2 = new Fixture<>(matchResult2.getAwayTeam().id,matchResult2.getHomeTeam().id);
				fixture2.id = 2;
				round.add(fixture1);
				round.add(fixture2);
				playOffs.add(round);
				playOffRound++;
			}
			else if(FootballSim.info.round == leagueGames.size()+2 && playOffRound == 2){
				List<Fixture<Integer>> round = new ArrayList<>();
				Fixture<Integer> fixture1 = new Fixture<>(finalTeams.get(0).id,finalTeams.get(1).id);
				fixture1.id = 1;
				round.add(fixture1);
				playOffs.add(round);
				playOffRound++;
			}
		}
	}

	public void endSeason() {
		ArrayList<LeagueStats> leagueStatsArray = new ArrayList<LeagueStats>(leagueStats.values());
		Utils.sortArray(leagueStatsArray);
		Team winners = leagueStatsArray.get(0).team;
		winners.trophiesWon++;
		Utils.sortArrayByTrophies(leagueStatsArray);
		System.out.format("%-2s%-25s%-10s\n", new String[]{"", "Team", "Trophies"});
		System.out.println("----------------------------------------------------------------------------------");
		for (int i = 0; i < leagueStatsArray.size(); i++) {
			LeagueStats stats = leagueStatsArray.get(i);
			System.out.format("%-2s%-25s%-10s\n", new String[]{"" + (i + 1), stats.team.name, "" + stats.team.trophiesWon});
		}
		System.out.println("");


		Utils.sortArray(leagueStatsArray);
		for (int i = 0; i < leagueStats.size(); i++) {
			LeagueStats stats = leagueStatsArray.get(i);
			Team team = stats.team;
			team.totalPositions += (i + 1);
			team.leaguesPlayed++;
			team.averagePosition = (team.totalPositions / team.leaguesPlayed);
			Records.MOST_POINTS.checkRecord(stats.points, team);
			Records.MOST_GOALS_IN_SEASON.checkRecord(stats.goals, team);
			//System.out.println(Team.attackRatio(stats.goals/100.0));
			//team.attack = (Team.attackRatio(stats.goals/60.0));
			//team.defence = (team.defence+(Team.defenceRatio(1-(stats.goalsConceeded/100.0))))/2;
			//System.out.println(team.name+"| Attack Difference: "+(team.attack-oldAttack)+"| Defence Difference: "+(team.defence-oldDefence));
			stats.reset();

		}

		matchResults = new HashMap<>();

	}
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
