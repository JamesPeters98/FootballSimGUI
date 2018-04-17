package com.james.footballsim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

    //TopBar
    TopBar topBar;
    BottomBar bottomBar;

    FootballSim aGame;

    public MainMenu(FootballSim aGame) {
        super(aGame);
        this.aGame = aGame;

        nextGame = new TextButton("Next Game",FootballSim.skin);
        playersList = new TextButton("Players", FootballSim.skin);

        topBar = new TopBar(stage, "Football Sim").addToStage();
        bottomBar = new BottomBar(stage).addToStage();
    }

    @Override
    public void show() {
        Table buttons = new Table();
        playersList = ScreenUtils.addScreenSwitchTextButton("Players",aGame,this, FootballSim.SCREENS.PLAYER_SELECTION, FootballSim.IN);
        nextGame = ScreenUtils.addScreenSwitchTextButton("Next Game",aGame,this, this,FootballSim.IN);
        buttons.add(nextGame).spaceRight(25f).expandX().align(Align.right);
        buttons.add(playersList).spaceLeft(25f).expandX().align(Align.left);
        buttons.setFillParent(true);
        stage.addActor(buttons);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
