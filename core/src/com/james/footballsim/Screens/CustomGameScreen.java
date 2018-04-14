package com.james.footballsim.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.james.footballsim.FootballSim;

public class CustomGameScreen implements Screen {

    //Retina zoom factor
    float zoom = 1f;

    Stage stage;
    FootballSim game;
    ScreenViewport viewport;
    float vWidth;
    float vHeight;

    Button backButton;
    boolean hasBackButton;

    private CustomGameScreen prevScreen;

    public CustomGameScreen(FootballSim game) {
        this.game = game;

        if(Gdx.graphics.getDensity() >= 1.25){
            zoom = 1f;
        } else {
            zoom = 2f;
        }

        vHeight = Gdx.graphics.getHeight()*zoom;
        vWidth = Gdx.graphics.getWidth()*zoom;

        viewport = new ScreenViewport();
        viewport.setUnitsPerPixel(zoom);
        stage = new Stage(viewport);

        backButton = ScreenUtils.backButton(game,this, null);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public final void resize(int width, int height) {

        stage.getViewport().update(width, height,true);

        //Game width/height - use this for UI stuff.
        vWidth = width*zoom;
        vHeight = height*zoom;

        stage.getCamera().viewportWidth = vWidth;
        stage.getCamera().viewportHeight = vHeight;

        updateUI(vWidth,vHeight);
    }

    public void updateUI(float width, float height){

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

    }

    public void showBackButton(boolean bool){
        hasBackButton = bool;
        if(hasBackButton)
            stage
                    .addActor(backButton);
        if(!hasBackButton){
            backButton.remove();
        }
    }

    public void setPrevScreen(CustomGameScreen screen){
        System.out.println("Set prev screen");
        prevScreen = screen;
        if(hasBackButton) {
            backButton.remove();
            backButton = ScreenUtils.backButton(game, this, prevScreen);
            stage.addActor(backButton);
        }
    }

    public CustomGameScreen getPrevScreen(){
        return prevScreen;
    }

}
