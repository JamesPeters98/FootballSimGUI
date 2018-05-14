package com.james.footballsim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;
import com.james.footballsim.FootballSim;
import com.james.footballsim.Screens.Components.BottomBar;
import com.james.footballsim.Screens.Components.TopBar;
import com.james.footballsim.Utils;

import static com.james.footballsim.FootballSim.fileSave;
import static com.james.footballsim.FootballSim.info;
import static com.james.footballsim.FootballSim.skin;

/**
 * Created by James on 04/04/2018.
 */
public class MainMenu extends CustomGameScreen {

    //Buttons
    private TextButton nextGame;
    private TextButton playersList;
    private TextButton leagueTable;

    private ScrollPane scrollPane;

    Table buttons;

    //TopBar
    TopBar topBar;
    BottomBar bottomBar;

    FootballSim aGame;

    public MainMenu(FootballSim aGame) {
        super(aGame);
        this.aGame = aGame;

        nextGame = new TextButton("Next Game",FootballSim.skin);
        playersList = new TextButton("Players", FootballSim.skin);

    }

    public void seasonChecks(){
        if(info.seasonRunning) {
            if (FootballSim.info.round >= FootballSim.info.leagues.get(info.division).rounds.size()) {
                Gdx.app.log("MatchSim", "Resetting season");
                FootballSim.info.round = 0;
                FootballSim.info.seasonRunning = false;
                FootballSim.info.leagues.get(info.division).newSeason();
                fileSave.saveInfo();
            }
        }
    }

    @Override
    public void show() {
        super.show();
        stage = new Stage(viewport);
        seasonChecks();

        topBar = new TopBar(stage, "Football Sim").addToStage();
        bottomBar = new BottomBar(stage).addToStage();

        buttons = new Table();
        //buttons.setDebug(true);

        playersList = ScreenUtils.addScreenSwitchTextButton("Players",aGame,this, FootballSim.SCREENS.PLAYER_SELECTION, FootballSim.IN);
        leagueTable = ScreenUtils.addScreenSwitchTextButton("Table",aGame,this, FootballSim.SCREENS.LEAGUE_TABLE, FootballSim.IN);

        String nextString = "";
        if(!FootballSim.info.seasonRunning) nextString = "Start Season";
        else nextString = "Next Game";
        nextGame = new TextButton(nextString,FootballSim.skin);
        nextGame.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(!FootballSim.info.seasonRunning) {
                    aGame.startSeason();
                    aGame.setScreen(MainMenu.this, FootballSim.SCREENS.TOTAL_FIXTURES, FootballSim.IN, 1f);
                } else {
                    aGame.setScreen(MainMenu.this, FootballSim.SCREENS.WEEKLY_FIXTURES, FootballSim.IN, 1f);
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        TextButton teamName = new TextButton(FootballSim.getTeam().name, FootballSim.skin);

        //Team Name
        buttons.add(teamName).fillX().colspan(2).padTop(130f);
        buttons.row().align(Align.top).expandY();

        //Stats
        Table statsTable = new Table();
        //statsTable.debug();
        statsTable.add(button("DEFENCE")).fillX();
        statsTable.add(button("ATTACK")).fillX();
        statsTable.add(button("OVERALL")).fillX();
        statsTable.row().padBottom(10f);

        statsTable.add(buttonBig(String.valueOf(FootballSim.getTeam().getDefenceRating())));
        statsTable.add(buttonBig(String.valueOf(FootballSim.getTeam().getAttackRating())));
        statsTable.add(buttonBig(String.valueOf(FootballSim.getTeam().getRating())));

        statsTable.row();

        Table infoTable = new Table();
        TextButton week = button("WEEK");
        TextButton position = button("POSITION");

        infoTable.add(week).fillX().minWidth(Utils.getMax(week.getWidth(),position.getWidth()));
        infoTable.add(position).fillX();
        infoTable.row();
        infoTable.add(buttonBig(String.valueOf(FootballSim.info.round)));
        infoTable.add(buttonBig(String.valueOf(FootballSim.info.getTeamPosition())));

        statsTable.add(infoTable).colspan(3);
        statsTable.row();



        buttons.add(statsTable).expandX().colspan(2).spaceTop(20);
        buttons.row().align(Align.top);

        //Buttons
        Gdx.app.log("MainMenu", ""+buttons.getWidth());
        buttons.add(leagueTable).fillX();
        buttons.add(playersList).fillX();
        buttons.row().spaceTop(15f);
        buttons.add(nextGame).colspan(2).fillX();
        buttons.row();
        buttons.padBottom(130f);

        //buttons.setFillParent(true);

//        scrollPane = new ScrollPane(buttons,skin);
//        scrollPane.setDebug(true);
        stage.addActor(buttons);


        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act();
        stage.draw();
    }


    @Override
    public void updateUI(float width, float height) {
//        int pad_top = 40;
//        int pad_bottom = 85;
//        scrollPane.setHeight(height-(pad_bottom+pad_top));
//        scrollPane.setY(pad_bottom);
//        scrollPane.setWidth(width);
        buttons.setHeight(height);
        buttons.setX(width/2-buttons.getWidth()/2);
        topBar.update(width,height);
        bottomBar.update(width,height);

//        float pad = 50;
//        float totalWidth = nextGame.getWidth()+playersList.getWidth()+pad;
//        nextGame.pad(0,30,0,30);
//        nextGame.setPosition(width/2-totalWidth/2, height/2-nextGame.getHeight()/2);
//
//        playersList.pad(0,30,0,30);
//        playersList.setPosition(nextGame.getX()+nextGame.getWidth()+pad, height/2-nextGame.getHeight()/2);
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
