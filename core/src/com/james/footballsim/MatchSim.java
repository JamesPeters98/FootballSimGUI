package com.james.footballsim;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.james.footballsim.Screens.CustomGameScreen;
import com.james.footballsim.Screens.MatchScreen;
import uk.co.codeecho.fixture.generator.Fixture;

import java.util.List;

import static com.james.footballsim.FootballSim.league;
import static com.james.footballsim.FootballSim.skin;

public class MatchSim {

	int openPlayGoals[] = new int[2];
	private int penGoals[] = new int[2];
	private int freeKickGoals[] = new int[2];
	private int goals[] = new int[2];

	private VerticalGroup table;
	private MatchScreen screen;

	private Team home;
	private Team away;

	private Result resultHome;
	private Result resultAway;

	private int minute = 0;
	private float totalDelta = 0;
	private float interval = 0.3f; //Interval between ingame minutes.
	private float delay = 2.5f; //Delay after an event happens. e.g a Goal.

	private boolean setup = false;
	private boolean finished = false;
	private boolean skip = false;

	public MatchSim(Team home, Team away){
		this.home = home;
		this.away = away;
	}

	public void setupUI(MatchScreen screen, VerticalGroup table){
		this.table = table;
		this.screen = screen;
		this.setup = true;
	}

	public boolean render(float delta){
		if(setup) {
            if(minute < 90) {
                totalDelta += delta;
                if ((totalDelta >= interval)||(skip)) {
                    minute++;
                    totalDelta = 0;
                    loop();
                    displayOutput();
                }
            } else {
            	if(!finished){
            		postMatchUpdates();
            		FootballSim.round++;
            		finished = true;
            		System.out.println("Updates:");
            		for(TeamUpdate update : FootballSim.getTeam().updates){
            			System.out.println(update.getUpdate());
					}
				}
            	return true;
			}
		}
		return false;
	}

	public MatchResult runMatchBackground() {
			for(int i = 1; i<= 90; i++){
				loop();
			}
			postMatchUpdates();
		return new MatchResult(home, away, goals[0], goals[1]);

	}

	private void loop(){
		resultHome = teamLoop(away,home);
		resultAway = teamLoop(home,away);
		update();
	}

	private void postMatchUpdates(){
		league.getTeam(home.id).update();
		league.getTeam(away.id).update();
	}

	private void update(){
		if(resultHome.goalScored()) goals[0]++;
		if(resultHome.freekickScored()) freeKickGoals[0]++;
		if(resultHome.penaltyScored()) penGoals[0]++;
		if(resultAway.goalScored()) goals[1]++;
		if(resultAway.freekickScored()) freeKickGoals[1]++;
		if(resultAway.penaltyScored()) penGoals[1]++;
	}

	public void displayOutput() {
		int i = minute;

			Player homeScorer = null;
			if(resultHome.goalScored()){
				homeScorer = home.getGoalscorer();
			}

			Player awayScorer = null;
			if(resultAway.goalScored()){
				awayScorer = away.getGoalscorer();
			}

			//TextButton button = new TextButton(i+"'",skin,"noClick_small");
			TextButton button = null;


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


		screen.minutes.setText(""+minute);

		if(minute==1)	table.addActorAt(0, new TextButton("KICK OFF!",skin,"noClick_small"));

		if (button != null) {
			table.addActorAt(0,button);
		}

		if(resultHome.goalScored()||resultAway.goalScored()) totalDelta = -delay;
		if(minute==45) {table.addActorAt(0, new TextButton("HALF TIME! "+home.name+" "+goals[0]+"-"+goals[1]+" "+away.name,skin,"noClick_small")); totalDelta = -delay;}
		if(minute==90) table.addActorAt(0, new TextButton("GAME OVER! "+home.name+" "+goals[0]+"-"+goals[1]+" "+away.name,skin,"noClick_small"));

		if(i == 90) System.out.println(home.name+" "+goals[0]+"-"+goals[1]+" "+away.name);

	}
	
	public Result teamLoop(Team teamA, Team teamB){
		double rand = Math.random();
		//Probability of defensive error
		if(rand <= 1-teamA.defence){
			rand = Math.random();
			if(rand <= 0.02){
				rand = Math.random();
				if(rand <= teamB.penalites){
					return new Result(ResultType.GOAL_PENALTY);
				} else {
					return new Result(ResultType.MISS_PENALTY);
				}
			}
			else if(rand <= 0.08){
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

	public void skip(){
		skip = true;
		interval = 0;
		delay = 0;
	}


}
