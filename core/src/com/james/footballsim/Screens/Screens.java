package com.james.footballsim.Screens;

import com.james.footballsim.FootballSim;

public class Screens {

    public CustomGameScreen TITLE_SCREEN;
    public CustomGameScreen TEAM_SELECTION;
    public CustomGameScreen PLAYER_SELECTION;
    public CustomGameScreen MAIN_MENU;

    public Screens(FootballSim footballSim){
        TITLE_SCREEN = new TitleScreen(footballSim);
        TEAM_SELECTION = new TeamSelection(footballSim);
        PLAYER_SELECTION = new PlayersList(footballSim);
        MAIN_MENU = new MainMenu(footballSim);
    }
}
