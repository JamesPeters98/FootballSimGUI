package com.james.footballsim.Screens.Components;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import static com.james.footballsim.FootballSim.skin;

public class BottomBar {

    private Image bottom_bar;
    private Stage stage;

    public Image getBottom_bar() {
        return bottom_bar;
    }

    public BottomBar(Stage stage){
        bottom_bar = new Image(skin.getPatch("bottom_bar"));
        this.stage = stage;
    }

    public BottomBar addToStage(){
        stage.addActor(bottom_bar);
        return this;
    }

    public void update(float width, float height){
        bottom_bar.setWidth(width);
        bottom_bar.setHeight(108);
    }
}
