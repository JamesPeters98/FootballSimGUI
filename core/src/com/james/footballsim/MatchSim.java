package com.james.footballsim;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.james.footballsim.Screens.CustomGameScreen;
import uk.co.codeecho.fixture.generator.Fixture;

import java.util.List;

import static com.james.footballsim.FootballSim.league;
import static com.james.footballsim.FootballSim.skin;

public class MatchSim {
	
	double trials = 1;
	int totalGoals = 0;
	
	int openPlayGoals[] = new int[2];
	int penGoals[] = new int[2];
	int freeKickGoals[] = new int[2];
	int goals[] = new int[2];
	
	int pens = 0;
	int freekicks = 0;

	boolean fastMode = true;
	int i = 0;

	Table table;
	CustomGameScreen screen;

	public MatchResult runMatch(Team home, Team away, boolean showUI, Table table, CustomGameScreen screen) {
		this.table = table;
		this.screen = screen;
//		List<Fixture<Integer>> round = FootballSim.rounds.get(FootballSim.round);
//		for(Fixture<Integer> fixture: round){
//			System.out.println(league.getTeam(fixture.getHomeTeam()).name + " vs " + league.getTeam(fixture.getAwayTeam()).name);
//			Team homeTeam = league.getTeam(fixture.getHomeTeam());
//			Team awayTeam = league.getTeam(fixture.getAwayTeam());
//
//			TextButton home = new TextButton(homeTeam.name, skin, "noClick_small");
//			home.pad(0,15,0,15);
//			TextButton away = new TextButton(awayTeam.name, skin, "noClick_small");
//			away.pad(0,15,0,15);
//			TextButton vs = new TextButton("VS", skin, "noClick_small");
//			table.add(home).fillX();
//			table.add(vs).center();
//			table.add(away).fillX();
//			table.row().spaceTop(10);
//		}

		Timer.Task loop = Timer.schedule(new Timer.Task(){
						   @Override
						   public void run() {
						   	i++;
							   Result resultHome = teamLoop(away,home);
							   Result resultAway = teamLoop(home,away);

							   if(resultHome.goalScored()) goals[0]++;
							   if(resultHome.freekickScored()) freeKickGoals[0]++;
							   if(resultHome.penaltyScored()) penGoals[0]++;
							   if(resultAway.goalScored()) goals[1]++;
							   if(resultAway.freekickScored()) freeKickGoals[1]++;
							   if(resultAway.penaltyScored()) penGoals[1]++;

							   if(showUI) displayOutput(resultHome,resultAway,i,home,away);
							   //displayOutput(result2,i,away,home);
						   }
					   }
				, 0
				, 0.2f
				, 89
		);

			for(int i = 1; i<= 90; i++){

//				if((teamId==home.id)||(teamId==away.id)) {
//					if(!fastMode) System.out.print("Min " + i + " |");
//
//					if(!fastMode) {
//						if ((result1.resultType == ResultType.NOTHING) && (result2.resultType == ResultType.NOTHING)) {
//							System.out.println("");
//						} else {
//							System.out.println("");
//							TimeUnit.MILLISECONDS.sleep(1000);
//						}
//					}
//
//					if (i == 45) {
//						System.out.println("----------");
//						System.out.println("Half Time!" + home.name + " " + goals[0] + "-" + goals[1] + " " + away.name);
//						System.out.println("----------");
//						if(!fastMode) TimeUnit.SECONDS.sleep(2);
//					}
//					if (i == 90) {
//						System.out.println("----------");
//						System.out.println("Game Over!");
//						System.out.println(home.name + " " + goals[0] + "-" + goals[1] + " " + away.name);
//						System.out.println("");
//						Utils.promptEnterKey(scanner);
//						System.out.println("Other Results");
//						System.out.println("---------------");
//
//					}
//				}

			}

		
			//if((teamId!=home.id)&&(teamId!=away.id))System.out.println(home.name+" "+goals[0]+"-"+goals[1]+" "+away.name);
		return new MatchResult(home, away, goals[0], goals[1]);

	}

	public void displayOutput(Result resultHome, Result resultAway, int minute, Team home, Team away) {
		int i = minute;

			Player homeScorer = null;
			if(resultHome.goalScored()){
				homeScorer = home.getGoalscorer();
			}

			Player awayScorer = null;
			if(resultAway.goalScored()){
				awayScorer = away.getGoalscorer();
			}

			TextButton button = new TextButton(i+"' ", skin, "noClick_small");

		if(resultHome.openPlayGoal()) button = new TextButton(i+"' GOAL! "+homeScorer.getMatchName()+" | "+home.name+" "+goals[0]+"-"+goals[1], skin,  "noClick_small");
		if(resultHome.penaltyScored()) button = new TextButton(i+"' GOAL! Penalty! "+home.name+" "+goals[0]+"-"+goals[1], skin,  "noClick_small");
		if(resultHome.freekickScored()) button = new TextButton(i+"' GOAL! Freekick! "+home.name+" "+goals[0]+"-"+goals[1], skin,  "noClick_small");
		if(resultHome.resultType == ResultType.MISS_PENALTY) button = new TextButton(i+"' MISSED PENALTY! "+home.name, skin,  "noClick_small");

		if(resultAway.openPlayGoal()) button = new TextButton(i+"' GOAL! "+awayScorer.getMatchName()+" | "+away.name+" "+goals[0]+"-"+goals[1], skin,  "noClick_small");
		if(resultAway.penaltyScored()) button = new TextButton(i+"' GOAL! Penalty! "+away.name+" "+goals[0]+"-"+goals[1], skin,  "noClick_small");
		if(resultAway.freekickScored()) button = new TextButton(i+"' GOAL! Freekick! "+away.name+" "+goals[0]+"-"+goals[1], skin,  "noClick_small");
		if(resultAway.resultType == ResultType.MISS_PENALTY) button = new TextButton(i+"' MISSED PENALTY! "+away.name, skin,  "noClick_small");

			if(resultHome.openPlayGoal()) System.out.println(i+"' GOAL! "+homeScorer.getMatchName()+" | "+home.name+" "+goals[0]+"-"+goals[1]);
			if(resultHome.penaltyScored()) System.out.println(i+"' GOAL! Penalty! "+home.name+" "+goals[0]+"-"+goals[1]);
			if(resultHome.freekickScored()) System.out.println(i+"' GOAL! Freekick! "+home.name+" "+goals[0]+"-"+goals[1]);
			if(resultHome.resultType == ResultType.MISS_PENALTY) System.out.println(i+"' MISSED PENALTY! "+home.name);

			if(resultAway.openPlayGoal()) System.out.println(i+"' GOAL! "+awayScorer.getMatchName()+" | "+away.name+" "+goals[0]+"-"+goals[1]);
			if(resultAway.penaltyScored()) System.out.println(i+"' GOAL! Penalty! "+away.name+" "+goals[0]+"-"+goals[1]);
			if(resultAway.freekickScored()) System.out.println(i+"' GOAL! Freekick! "+away.name+" "+goals[0]+"-"+goals[1]);
			if(resultAway.resultType == ResultType.MISS_PENALTY) System.out.println(i+"' MISSED PENALTY! "+away.name);

//			if((resultHome.resultType == ResultType.NOTHING)||(resultAway.resultType == ResultType.NOTHING)) {
//				System.out.println(i+"'");
//			}

		table.add(button).expandX();
		table.row();
		screen.updateUI(screen.getvWidth(),screen.getvHeight());

			if(i == 90) System.out.println(home.name+" "+goals[0]+"-"+goals[1]+" "+away.name);

	}
	
	public Result teamLoop(Team teamA, Team teamB){
		double rand = Math.random();
		//Probability of defensive error
		if(rand <= 1-teamA.defence){
			rand = Math.random();
			if(rand <= 0.02){
				pens++;
				rand = Math.random();
				if(rand <= teamB.penalites){
					return new Result(ResultType.GOAL_PENALTY);
				} else {
					return new Result(ResultType.MISS_PENALTY);
				}
			}
			else if(rand <= 0.08){
				freekicks++;
				rand = Math.random();
				if(rand <= teamB.freekicks){
					return new Result(ResultType.GOAL_FREE_KICK);
				}
			}
			//prob of attack
			else if(rand <= teamB.attack){
				return new Result(ResultType.GOAL_OPEN_PLAY);
			}

		}
		return new Result(ResultType.NOTHING);
	}


}
