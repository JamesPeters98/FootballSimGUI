package com.james.footballsim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.james.footballsim.FootballSim;
import com.james.footballsim.Screens.Components.PaddedScreenViewport;

import static com.james.footballsim.FootballSim.Bottom_Padding;
import static com.james.footballsim.FootballSim.Top_Padding;
import static com.james.footballsim.FootballSim.skin;

public class CustomGameScreen implements Screen {

    //Retina zoom factor
    float zoom = 1f;

    Stage stage;
    FootballSim game;
    ScreenViewport viewport;

    float vWidth;
    float vHeight;

    public final static float transitionDuration = 0.3f;

    Button backButton;
    Label FPSCounter;
    Label Ram;
    boolean hasBackButton;

    private CustomGameScreen prevScreen;

    public CustomGameScreen(FootballSim game) {
        this.game = game;

        zoom = (float) (2/FootballSim.SCALE);

        vHeight = (Gdx.graphics.getHeight())*zoom;
        vWidth = Gdx.graphics.getWidth()*zoom;

        viewport = new ScreenViewport();
        viewport.setUnitsPerPixel(zoom);
        stage = new Stage(viewport);

        backButton = ScreenUtils.backButton(game,this, null);
        FPSCounter = new Label("",skin,"content");
        Ram = new Label("",skin,"content");
    }


    @Override
    public void show() {
        FootballSim.fileSave.saveInfo();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((53f/255f), (104f/255f), (28f/255f), 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        FPSCounter.setText("FPS: "+Gdx.graphics.getFramesPerSecond());
        int javaHeap = (int) (Gdx.app.getJavaHeap()/1000000);
        int nativeHeap = (int) (Gdx.app.getNativeHeap()/1000000);
        Ram.setText("Java Heap: "+javaHeap+"MB Native Heap: "+nativeHeap+"MB");
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
        FPSCounter.setPosition(3,80+Bottom_Padding);
        Ram.setPosition(3,25+Bottom_Padding);
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
        stage.addActor(Ram);
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
