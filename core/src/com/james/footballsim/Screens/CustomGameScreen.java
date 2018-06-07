package com.james.footballsim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.james.footballsim.FootballSim;

import static com.james.footballsim.FootballSim.skin;

public class CustomGameScreen implements Screen {

    //Retina zoom factor
    float zoom = 1f;

    Stage stage;
    FootballSim game;
    ScreenViewport viewport;

    float vWidth;
    float vHeight;

    public final static float transitionDuration = 0.5f;

    Button backButton;
    Label FPSCounter;
    boolean hasBackButton;

    private CustomGameScreen prevScreen;

    public CustomGameScreen(FootballSim game) {
        this.game = game;

        int scaleFactor = 1;

//        // find the display device of interest
//        final GraphicsDevice defaultScreenDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//
//        // on OS X, it would be CGraphicsDevice
//        if (defaultScreenDevice instanceof CGraphicsDevice) {
//            final CGraphicsDevice device = (CGraphicsDevice) defaultScreenDevice;
//
//            // this is the missing correction factor, it's equal to 2 on HiDPI a.k.a. Retina displays
//            scaleFactor = device.getScaleFactor();
//
//            // now we can compute the real DPI of the screen
//            //final double realDPI = scaleFactor * (device.getXResolution() + device.getYResolution()) / 2;
//
//            //System.out.println(scaleFactor);
//            //System.out.println(realDPI);
//        }

        //System.out.println(Gdx.graphics.getDensity());
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
        FPSCounter = new Label("FPS: "+Gdx.graphics.getFramesPerSecond(),skin);
    }


    @Override
    public void show() {
        FootballSim.fileSave.saveInfo();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.6f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        FPSCounter.setText("FPS: "+Gdx.graphics.getFramesPerSecond());
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
        FPSCounter.setPosition(3,3);
    }

    public void updateUI(float width, float height){

    }

    @Override
    public void pause() {
        FootballSim.fileSave.saveInfo();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        FootballSim.fileSave.saveInfo();
    }

    @Override
    public void dispose() {
        FootballSim.fileSave.saveInfo();
    }

    public void showBackButton(boolean bool){
        hasBackButton = bool;
        if(hasBackButton)
            stage.addActor(backButton);
        if(!hasBackButton){
            backButton.remove();
        }
    }

    public void showFPSCounter(){
        stage.addActor(FPSCounter);
    }

    public void setPrevScreen(CustomGameScreen screen){
        //System.out.println("Set prev screen");
        prevScreen = screen;
        if(hasBackButton) {
            backButton.remove();
            backButton = ScreenUtils.backButton(game, this, prevScreen);
            stage.addActor(backButton);
        }
    }

    protected TextButton button(String text){
        TextButton button = new TextButton(text, skin, "noClick_small");
        button.pad(0,15,0,15);
        return button;
    }

    protected TextButton buttonBig(String text){
        TextButton button = new TextButton(text, skin, "noClick");
        button.pad(0,15,0,15);
        return button;
    }

    public CustomGameScreen getPrevScreen(){
        return prevScreen;
    }

    public float getvWidth() {
        return vWidth;
    }

    public float getvHeight() {
        return vHeight;
    }

}
