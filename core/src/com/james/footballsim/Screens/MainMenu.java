package com.james.footballsim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.james.footballsim.FootballSim;
import com.james.footballsim.Screens.Components.BottomBar;
import com.james.footballsim.Screens.Components.TopBar;

/**
 * Created by James on 04/04/2018.
 */
public class MainMenu extends CustomGameScreen {

    //Buttons
    private TextButton nextGame;
    private TextButton playersList;

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

    @Override
    public void show() {
        super.show();
        stage = new Stage(viewport);

        topBar = new TopBar(stage, "Football Sim").addToStage();
        bottomBar = new BottomBar(stage).addToStage();

        buttons = new Table();
        //buttons.setDebug(true);

        playersList = ScreenUtils.addScreenSwitchTextButton("Players",aGame,this, FootballSim.SCREENS.PLAYER_SELECTION, FootballSim.IN);
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

        buttons.add(teamName).expandX().colspan(2).padTop(130f).minWidth(nextGame.getWidth()+playersList.getWidth()+50f);
        buttons.row().align(Align.top).expandY();

        buttons.add(nextGame).spaceRight(25f).expandX().align(Align.right);
        buttons.add(playersList).spaceLeft(25f).expandX().align(Align.left);

        buttons.setFillParent(true);
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
