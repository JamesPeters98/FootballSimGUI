package com.james.footballsim.Screens;

import com.james.footballsim.FootballSim;

public class Screens {

    public CustomGameScreen TITLE_SCREEN;
    public CustomGameScreen TEAM_SELECTION;
    public CustomGameScreen PLAYER_SELECTION;
    public CustomGameScreen MAIN_MENU;
    public CustomGameScreen TOTAL_FIXTURES;
    public CustomGameScreen WEEKLY_FIXTURES;
    public CustomGameScreen MATCH_SCREEN;
    public CustomGameScreen UPDATES_SCREEN;

    public Screens(FootballSim footballSim){
        TITLE_SCREEN = new TitleScreen(footballSim);
        TEAM_SELECTION = new TeamSelection(footballSim);
        PLAYER_SELECTION = new PlayersList(footballSim);
        MAIN_MENU = new MainMenu(footballSim);
        TOTAL_FIXTURES = new TotalFixtures(footballSim);
        WEEKLY_FIXTURES = new WeeklyFixtures(footballSim);
        MATCH_SCREEN = new MatchScreen(footballSim);
        UPDATES_SCREEN = new UpdatesScreen(footballSim);
    }
}
