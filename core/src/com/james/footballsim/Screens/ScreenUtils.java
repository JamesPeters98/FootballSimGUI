package com.james.footballsim.Screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ixeption.libgdx.transitions.FadingGame;
import com.ixeption.libgdx.transitions.ScreenTransition;
import com.james.footballsim.FootballSim;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

import static com.james.footballsim.FootballSim.skin;

public class ScreenUtils {

    public static Button backButton(FootballSim aGame, CustomGameScreen currentGameScreen, CustomGameScreen previousGameScreen){
        //if(previousGameScreen == null) System.out.println("Prev screen null");
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
                sim.setScreen(currentGameScreen, nextGameScreen,transition,CustomGameScreen.transitionDuration);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return button;
    }




    public static class DialogCreator {

        GDXButtonDialog bDialog;

        public DialogCreator(String message, String acceptMessage){
            bDialog = FootballSim.dialogs.newDialog(GDXButtonDialog.class);
            bDialog.setMessage(message);

            bDialog.setClickListener(new ButtonClickListener() {

                @Override
                public void click(int button) {
                    // handle button click here
                }
            });

            bDialog.addButton(acceptMessage);
            bDialog.build();
        }

        public GDXButtonDialog getDialog(){
            return bDialog;
        }

    }
}
