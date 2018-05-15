package com.james.footballsim.Simulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.james.footballsim.FootballSim;
import com.james.footballsim.Screens.MatchScreen;

import java.util.concurrent.TimeUnit;

import static com.james.footballsim.FootballSim.info;
import static com.james.footballsim.FootballSim.skin;

public class MatchSim {

	int openPlayGoals[] = new int[2];
	private int penGoals[] = new int[2];
	private int freeKickGoals[] = new int[2];
	private int goals[] = {0,0};
	private int goalsSecondLeg[] = new int[2];
	private int goalsFirstLeg[] = new int[2];
	private int penaltyShootOut[] = new int[2];

	private VerticalGroup table;
	private MatchScreen screen;

	private Team home;
	private Team away;
	private League league;

	private Result resultHome;
	private Result resultAway;
	private MatchResult matchResult;
	private MatchResult firstLegResult;

	private int matchLength = 90;
	private int minute = 0;
	private float totalDelta = 0;
	private float interval = 0.15f; //Interval between ingame minutes.
	private float delay = 1.5f; //Delay after an event happens. e.g a Goal.
    private int fixtureID = 0;

	private boolean setup = false;
	private boolean finished = false;
	private boolean skip = false;
	private boolean knockoutTwoLegs = false;
	private boolean knockoutOneLeg = false;
	private boolean inExtraTime = false;
	private boolean penShootout = false;

	public boolean isPlaceholder = false;

	//Defines a knockout match for second leg, using matchResult of first leg.
    public MatchSim(Team home, Team away, League league, MatchResult result, int fixtureID){
        this(home,away,league);
        this.knockoutTwoLegs = true;
        this.firstLegResult = result;
        this.fixtureID = fixtureID;
        goalsFirstLeg[0] = result.getHomeGoals();
        goalsFirstLeg[1] = result.getAwayGoals();
        goals[0] += result.getAwayGoals();
        goals[1] += result.getHomeGoals();
    }

    //Use for first leg of match
	public MatchSim(Team home, Team away, League league, int fixtureID){
		this(home,away,league);
		this.fixtureID = fixtureID;
	}

    public MatchSim(Team home, Team away, League league, boolean knockoutOneLeg, int fixtureID){
        this(home,away,league);
        this.knockoutOneLeg = knockoutOneLeg;
        this.fixtureID = fixtureID;
    }

	public MatchSim(Team home, Team away, League league){
		this.home = home;
		this.away = away;
		if(home==null || away==null) Gdx.app.error("MatchSim", "Can't have null team");
		this.league = league;
	}

	public MatchSim(){
    	isPlaceholder = true;
    	Gdx.app.log("MatchSim", "Empty match sim");
	}

	public void setupUI(MatchScreen screen, VerticalGroup table){
		this.table = table;
		this.screen = screen;
		this.setup = true;
	}

	public boolean render(float delta){
		if(setup&&!finished) {



			if(minute >= matchLength) {
				if(!finished){
					displayOutput();

					Gdx.app.log("MatchSim", "Game finished!");
					postMatchUpdates();
					seasonChecks();
					finished = true;
					screen.menu.setDisabled(false);
					return true;
				}
			}
			if(minute < matchLength) {
				totalDelta += delta;
				if ((totalDelta >= interval)||(skip)) {
					displayOutput();
					minute++;
					totalDelta = 0;
					loop();
				}
			}
		}
		return false;
	}

	public MatchResult runMatchBackground() {
			for(int i = 1; i<= matchLength; i++){
				minute++;
				loop();
			}
			postMatchUpdates();
		return matchResult;
	}

	private void loop(){
		if(minute == 90){
			Gdx.app.log("MatchSim", "End of 90 minutes.");
			if(knockoutTwoLegs){
				if(goals[1]==goals[0]) {
					if (firstLegResult.getAwayGoals() == goalsSecondLeg[1]) {
						setExtraTime();
						Gdx.app.log("MatchSim", "EXTRA TIME!");
					}
				}
			}
			if(knockoutOneLeg){
				Gdx.app.log("MatchSim", "Knockout Game");
				if(goals[1]==goals[0]) {
					setExtraTime();
					Gdx.app.log("MatchSim", "EXTRA TIME!");
				} else {
					Gdx.app.log("MatchSim", "No extra time");
				}
			}
		}
		else if(minute == 120){
			Gdx.app.log("MatchSim", "End of 120 minutes.");
			if(knockoutTwoLegs) {
				if(goals[1]==goals[0]) {
					if (firstLegResult.getAwayGoals() == goalsSecondLeg[1]) {
						penShootout = true;
						penaltyShootOut();
					}
				}
			}
			if(knockoutOneLeg){
				if (goals[0] == goals[1]) {
					penShootout = true;
					penaltyShootOut();
				}
			}
		}

		resultHome = teamLoop(away,home);
		resultAway = teamLoop(home,away);
		update();
	}

	private void postMatchUpdates(){
		home.update();
		away.update();
		if(knockoutTwoLegs){
			Gdx.app.log("MatchSim", "two legged knockout!");
		    int team1Goals = goalsSecondLeg[0]+firstLegResult.getAwayGoals();
		    int team2Goals = goalsSecondLeg[1]+firstLegResult.getHomeGoals();

            if(team1Goals>team2Goals){ matchResult = new MatchResult(home,away,team1Goals,team2Goals,true); Gdx.app.log("MatchSim", home.name+" won!"); }
			if(team2Goals>team1Goals){ matchResult = new MatchResult(home,away,team1Goals,team2Goals,false); Gdx.app.log("MatchSim", away.name+" won!"); }
			if(team1Goals == team2Goals){
				if(firstLegResult.getAwayGoals() > goalsSecondLeg[1]){
					Gdx.app.log("MatchSim", home.name+" won on away Goals!");
                    matchResult = new MatchResult(home,away,team1Goals,team2Goals,true);
				} else if(goalsSecondLeg[1] > firstLegResult.getAwayGoals()){
					Gdx.app.log("MatchSim", away.name+" won on away Goals!");
                    matchResult = new MatchResult(home,away,team1Goals,team2Goals,false);
				}
			}
        }
        else if(knockoutOneLeg){
		    if(goals[0]>goals[1]) matchResult = new MatchResult(home,away,goals[0],goals[1],true);
		    if(goals[1]>goals[0]) matchResult = new MatchResult(home,away,goals[0],goals[1],false);
        }
        else {
		    matchResult = new MatchResult(home, away, goals[0], goals[1]);
        }
	}

	private void seasonChecks(){
		league.addStat(matchResult,fixtureID);
		FootballSim.info.round++;
	}

	private void update(){
		if(resultHome.goalScored()){ goals[0]++; goalsSecondLeg[0]++; }
		if(resultHome.freekickScored()) freeKickGoals[0]++;
		if(resultHome.penaltyScored()) penGoals[0]++;
		if(resultAway.goalScored()) { goals[1]++; goalsSecondLeg[1]++; }
		if(resultAway.freekickScored()) freeKickGoals[1]++;
		if(resultAway.penaltyScored()) penGoals[1]++;
	}

	public void displayOutput() {
		int i = minute;
		Gdx.app.log("MatchSim", i+"");

		Player homeScorer = null;
		Player awayScorer = null;
		TextButton homeButton = null;
		TextButton awayButton = null;

		if(i>0) {

			if (resultHome.goalScored()) {
				homeScorer = home.getGoalscorer();
			}

			if (resultAway.goalScored()) {
				awayScorer = away.getGoalscorer();
			}

			if (resultHome.openPlayGoal())
				homeButton = new TextButton(i + "' GOAL! " + homeScorer.getMatchName() + " | " + home.name + " " + goals[0] + "-" + goals[1], skin, "noClick_small");
			if (resultHome.penaltyScored())
				homeButton = new TextButton(i + "' GOAL! Penalty! " + home.name + " " + goals[0] + "-" + goals[1], skin, "noClick_small");
			if (resultHome.freekickScored())
				homeButton = new TextButton(i + "' GOAL! Freekick! " + home.name + " " + goals[0] + "-" + goals[1], skin, "noClick_small");
			if (resultHome.resultType == ResultType.MISS_PENALTY)
				homeButton = new TextButton(i + "' MISSED PENALTY! " + home.name, skin, "noClick_small");

			if (resultAway.openPlayGoal())
				awayButton = new TextButton(i + "' GOAL! " + awayScorer.getMatchName() + " | " + away.name + " " + goals[0] + "-" + goals[1], skin, "noClick_small");
			if (resultAway.penaltyScored())
				awayButton = new TextButton(i + "' GOAL! Penalty! " + away.name + " " + goals[0] + "-" + goals[1], skin, "noClick_small");
			if (resultAway.freekickScored())
				awayButton = new TextButton(i + "' GOAL! Freekick! " + away.name + " " + goals[0] + "-" + goals[1], skin, "noClick_small");
			if (resultAway.resultType == ResultType.MISS_PENALTY)
				awayButton = new TextButton(i + "' MISSED PENALTY! " + away.name, skin, "noClick_small");

			if (resultHome.openPlayGoal())
				System.out.println(i + "' GOAL! " + homeScorer.getMatchName() + " | " + home.name + " " + goals[0] + "-" + goals[1]);
			if (resultHome.penaltyScored())
				System.out.println(i + "' GOAL! Penalty! " + home.name + " " + goals[0] + "-" + goals[1]);
			if (resultHome.freekickScored())
				System.out.println(i + "' GOAL! Freekick! " + home.name + " " + goals[0] + "-" + goals[1]);
			if (resultHome.resultType == ResultType.MISS_PENALTY)
				System.out.println(i + "' MISSED PENALTY! " + home.name);

			if (resultAway.openPlayGoal())
				System.out.println(i + "' GOAL! " + awayScorer.getMatchName() + " | " + away.name + " " + goals[0] + "-" + goals[1]);
			if (resultAway.penaltyScored())
				System.out.println(i + "' GOAL! Penalty! " + away.name + " " + goals[0] + "-" + goals[1]);
			if (resultAway.freekickScored())
				System.out.println(i + "' GOAL! Freekick! " + away.name + " " + goals[0] + "-" + goals[1]);
			if (resultAway.resultType == ResultType.MISS_PENALTY)
				System.out.println(i + "' MISSED PENALTY! " + away.name);


			if(resultHome.goalScored()||resultAway.goalScored()) totalDelta = -delay;
		}

		screen.minutes.setText(""+minute);

		if(minute==0){
			TextButton kickOff = new TextButton("KICK OFF!",skin,"noClick_small");
			Container container = new Container(kickOff).fillX().width(screen.getvWidth()*0.8f);
			table.addActorAt(0, container);
			if(knockoutTwoLegs){
				TextButton aggregate = new TextButton("Aggregate: "+home.shortName+" "+goals[0]+"-"+goals[1]+" "+away.shortName,skin,"noClick_small");
				Container container2 = new Container(kickOff).fillX().width(screen.getvWidth()*0.8f);
				table.addActorAt(0, container2);
			}
		}

		if (homeButton != null) {
			homeButton.getLabel().setAlignment(Align.left);
			homeButton.getLabelCell().padLeft(10f);
			Container container = new Container(homeButton).fillX().width(screen.getvWidth()*0.8f);
			table.addActorAt(0, container);
		}

		if (awayButton != null) {
			awayButton.getLabel().setAlignment(Align.left);
			awayButton.getLabelCell().padLeft(10f);
			Container container = new Container(awayButton).fillX().width(screen.getvWidth()*0.8f);
			table.addActorAt(0, container);
		}

		if(minute==45) {
			TextButton button = new TextButton("HALF TIME!",skin,"noClick_small");
			Container container = new Container(button).fillX().width(screen.getvWidth()*0.8f);
			table.addActorAt(0, container);
			totalDelta = -delay;
		}
		if(minute==90 && !inExtraTime){
			TextButton button = new TextButton("GAME OVER!",skin,"noClick_small");
			Container container = new Container(button).fillX().width(screen.getvWidth()*0.8f);
			table.addActorAt(0, container);
		}

        if(minute==90 && inExtraTime){
            TextButton button = new TextButton("EXTRA TIME!",skin,"noClick_small");
            Container container = new Container(button).fillX().width(screen.getvWidth()*0.8f);
            table.addActorAt(0, container);
            totalDelta = -delay;
        }

        if(minute==105 && inExtraTime){
            TextButton button = new TextButton("HALF TIME!",skin,"noClick_small");
            Container container = new Container(button).fillX().width(screen.getvWidth()*0.8f);
            table.addActorAt(0, container);
            totalDelta = -delay;
        }

        if(minute==120 && !penShootout){
            TextButton button = new TextButton("GAME OVER!",skin,"noClick_small");
            Container container = new Container(button).fillX().width(screen.getvWidth()*0.8f);
            table.addActorAt(0, container);
            totalDelta = -delay;
        }

        if(minute==120 && penShootout){
            TextButton button = new TextButton("PENALTIES!",skin,"noClick_small");
            Container container = new Container(button).fillX().width(screen.getvWidth()*0.8f);
            table.addActorAt(0, container);

            TextButton button2 = new TextButton(home.shortName+" "+penaltyShootOut[0]+"-"+penaltyShootOut[1]+" "+away.shortName,skin,"noClick_small");
            Container container2 = new Container(button2).fillX().width(screen.getvWidth()*0.8f);
            table.addActorAt(0, container2);
            totalDelta = -delay;
        }

		if(i == 90 && !inExtraTime){
		    System.out.println(home.shortName+" "+goals[0]+"-"+goals[1]+" "+away.shortName);
            TextButton button = new TextButton(home.shortName+" "+goals[0]+"-"+goals[1]+" "+away.shortName,skin,"noClick_small");
            Container container = new Container(button).fillX().width(screen.getvWidth()*0.8f);
            table.addActorAt(0, container);
        }

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

	public void penaltyShootOut(){
    	Gdx.app.log("MatchSim", "Penalty shoot out!!");
        double rand = Math.random();
        int homePens = 0;
        int awayPens = 0;

        for(int i = 0; i < 5; i++){
            rand = Math.random();
            if(rand <= home.penalites) homePens++;
            rand = Math.random();
            if(rand <= away.penalites) awayPens++;
        }
        while(homePens==awayPens){
            rand = Math.random();
            if(rand <= home.penalites) homePens++;
            rand = Math.random();
            if(rand <= away.penalites) awayPens++;
		}
        penaltyShootOut[0] = homePens;
        penaltyShootOut[1] = awayPens;

        if(homePens>awayPens) matchResult = new MatchResult(home,away,goals[0],goals[1],homePens,awayPens,true);
        else matchResult = new MatchResult(home,away,goals[0],goals[1],homePens,awayPens,false);
    }

	public void skip(){
		skip = true;
		interval = 0;
		delay = 0;
	}

    public void setExtraTime(){
        matchLength += 30;
        inExtraTime = true;
    }

}
