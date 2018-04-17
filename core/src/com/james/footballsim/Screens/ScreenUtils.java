package com.james.footballsim.Screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ixeption.libgdx.transitions.FadingGame;
import com.ixeption.libgdx.transitions.ScreenTransition;
import com.james.footballsim.FootballSim;

import static com.james.footballsim.FootballSim.skin;

public class ScreenUtils {

    public static Button backButton(FootballSim aGame, CustomGameScreen currentGameScreen, CustomGameScreen previousGameScreen){
        if(previousGameScreen == null) System.out.println("Prev screen null");
        Button backButton = new Button(skin,"back_button");
        backButton.setPosition(5,5);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                aGame.setScreen(currentGameScreen,previousGameScreen, FootballSim.OUT,0.5f,true);
            }
        });
        return backButton;
    }

    public static TextButton addScreenSwitchTextButton(String text, FootballSim sim, CustomGameScreen currentGameScreen, CustomGameScreen nextGameScreen, ScreenTransition transition){
        TextButton button = new TextButton(text,FootballSim.skin);
        button.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                sim.setScreen(currentGameScreen, nextGameScreen,transition,1f);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return button;
    }
}
