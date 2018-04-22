package com.james.footballsim;

import com.badlogic.gdx.utils.Timer;

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

	public MatchResult runMatch(Team home, Team away, boolean showUI) {

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
				, 1
				, 90
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
		if(resultHome.goalScored()){
			awayScorer = away.getGoalscorer();
		}

			if(resultHome.openPlayGoal()) System.out.println(i+"' GOAL! "+homeScorer.getMatchName()+" | "+home.name+" "+goals[0]+"-"+goals[1]);
			else if(resultHome.penaltyScored()) System.out.println(i+"' GOAL! Penalty! "+home.name+" "+goals[0]+"-"+goals[1]);
			else if(resultHome.freekickScored()) System.out.println(i+"' GOAL! Freekick! "+home.name+" "+goals[0]+"-"+goals[1]);
			else if(resultHome.resultType == ResultType.MISS_PENALTY) System.out.println(i+"' MISSED PENALTY! "+home.name);
			else {
				System.out.println(i+"'");
			}

			if(resultAway.openPlayGoal()) System.out.println(i+"' GOAL! "+awayScorer.getMatchName()+" | "+away.name+" "+goals[0]+"-"+goals[1]);
			else if(resultAway.penaltyScored()) System.out.println(i+"' GOAL! Penalty! "+away.name+" "+goals[0]+"-"+goals[1]);
			else if(resultAway.freekickScored()) System.out.println(i+"' GOAL! Freekick! "+away.name+" "+goals[0]+"-"+goals[1]);
			else if(resultAway.resultType == ResultType.MISS_PENALTY) System.out.println(i+"' MISSED PENALTY! "+away.name);
			else {
				System.out.println(i+"'");
			}


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
