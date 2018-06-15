package com.james.footballsim.Screens.Components;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import static com.james.footballsim.FootballSim.Top_Padding;
import static com.james.footballsim.FootballSim.skin;

public class TopBar {

    private Image top_bar;
    private Label titleLabel;
    private Stage stage;

    public Image getTop_bar() {
        return top_bar;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public TopBar(Stage stage, String title){
        this.stage = stage;
        top_bar = new Image(skin.getPatch("top_bar"));
        titleLabel = new Label(title, skin,"title");
        titleLabel.setAlignment(Align.center);
    }

    public TopBar addToStage(){
        stage.addActor(top_bar);
        stage.addActor(titleLabel);
        return this;
    }

    public void update(float width, float height){
        titleLabel.setY(height-95-Top_Padding);
        titleLabel.setWidth(width);

        top_bar.setWidth(width);
        top_bar.setHeight(118+Top_Padding);
        top_bar.setY(height-top_bar.getHeight());
    }
}
