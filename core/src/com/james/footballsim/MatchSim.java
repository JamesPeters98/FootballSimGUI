package com.james.footballsim;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MatchSim implements KeyListener{
	
	double trials = 1;
	int totalGoals = 0;
	
	int openPlayGoals[] = new int[2];
	int penGoals[] = new int[2];
	int freeKickGoals[] = new int[2];
	int goals[] = new int[2];
	
	int pens = 0;
	int freekicks = 0;

	boolean fastMode = true;


	public MatchResult runMatch(Scanner scanner, Team home, Team away, int teamId) throws InterruptedException {
		
		if((teamId==home.id)||(teamId==away.id)){
			System.out.println(home.name+" vs "+away.name);
			System.out.println("-----------------------");
			System.out.println("Team Sheet:");
			System.out.println("");
			System.out.println(home.name+" | R:"+home.getRating()+" A:"+home.getAttackRating()+" D:"+home.getDefenceRating());
			System.out.println("-----------------------");
			home.listBestSquad();
			Utils.promptEnterKey(scanner);
			System.out.println("");
			System.out.println(away.name+" | R:"+away.getRating()+" A:"+away.getAttackRating()+" D:"+away.getDefenceRating());
			System.out.println("-----------------------");
			away.listBestSquad();
			Utils.promptEnterKey(scanner);
			System.out.println("Kick Off!");
			System.out.println("----------");
		}
				
		//for(int z = 1; z<=trials; z++){
			for(int i = 1; i<= 90; i++){
				Result result1 = teamLoop(away,home);
				Result result2 = teamLoop(home,away);

				displayOutput(result1,0,1,i,home,away,teamId);
				displayOutput(result2,1,0,i,away,home,teamId);

				if((teamId==home.id)||(teamId==away.id)) {
					if(!fastMode) System.out.print("Min " + i + " |");

					if(!fastMode) {
						if ((result1.resultType == ResultType.NOTHING) && (result2.resultType == ResultType.NOTHING)) {
							System.out.println("");
						} else {
							System.out.println("");
							TimeUnit.MILLISECONDS.sleep(1000);
						}
					}

					if (i == 45) {
						System.out.println("----------");
						System.out.println("Half Time!" + home.name + " " + goals[0] + "-" + goals[1] + " " + away.name);
						System.out.println("----------");
						if(!fastMode) TimeUnit.SECONDS.sleep(2);
					}
					if (i == 90) {
						System.out.println("----------");
						System.out.println("Game Over!");
						System.out.println(home.name + " " + goals[0] + "-" + goals[1] + " " + away.name);
						System.out.println("");
						Utils.promptEnterKey(scanner);
						System.out.println("Other Results");
						System.out.println("---------------");

					}
				}

			}

		
			if((teamId!=home.id)&&(teamId!=away.id))System.out.println(home.name+" "+goals[0]+"-"+goals[1]+" "+away.name);
		return new MatchResult(home, away, goals[0], goals[1]);

	}

	public void displayOutput(Result result, int sideA, int sideB, int minute, Team team, Team opposition, int teamId) throws InterruptedException {
		int i = minute;
		if(result.goalScored()) goals[sideA]++;
		if(result.freekickScored()) freeKickGoals[sideA]++;
		if(result.penaltyScored()) penGoals[sideA]++;

		if((teamId==team.id)||(teamId==opposition.id)){

			Player teamScorer = null;
			if(result.goalScored()){
				teamScorer = team.getGoalscorer();
			}

			if(result.openPlayGoal()) System.out.println(i+"' GOAL! "+teamScorer.getMatchName()+" | "+team.name+" "+goals[0]+"-"+goals[1]);
			if(result.penaltyScored()) System.out.println(i+"' GOAL! Penalty! "+team.name+" "+goals[0]+"-"+goals[1]);
			if(result.freekickScored()) System.out.println(i+"' GOAL! Freekick! "+team.name+" "+goals[0]+"-"+goals[1]);
			if(result.resultType == ResultType.MISS_PENALTY) System.out.println(i+"' MISSED PENALTY! "+team.name);

			if(!fastMode) TimeUnit.MILLISECONDS.sleep(150);

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

	@Override
	public void keyPressed(KeyEvent arg0) {
		System.out.println("Key Pressed!");
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
