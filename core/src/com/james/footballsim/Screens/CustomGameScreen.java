package com.james.footballsim.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CustomGameScreen implements Screen {

    //Retina zoom factor
    float zoom = 1f;

    Stage stage;
    Game game;
    ScreenViewport viewport;
    float vWidth;
    float vHeight;

    public CustomGameScreen(Game aGame) {
        game = aGame;
        viewport = new ScreenViewport();
        viewport.setUnitsPerPixel(zoom);
        stage = new Stage(viewport);
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

}
