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
                sim.setScreen(currentGameScreen, nextGameScreen,transition,1f);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        return button;
    }




    public static class DialogCreator {

        Dialog dialog;
        Cell labelCell;

        public DialogCreator(String message, String acceptMessage, float width){
            Label label = new Label(message, FootballSim.skin, "content");
            label.setWrap(true);
            label.setAlignment(Align.center);

            dialog =
                    new Dialog("", FootballSim.skin) {
                        protected void result (Object object) {
                            System.out.println("Chosen: " + object);
                        }
                    };

            dialog.padTop(50).padBottom(50);
            labelCell = dialog.getContentTable().add(label);
            labelCell.width(width*0.7f).row();
            dialog.getButtonTable().padTop(50);

            TextButton dbutton = new TextButton("Okay", FootballSim.skin);
            dialog.button(dbutton, true);

            dialog.key(Input.Keys.ENTER, true);
            dialog.invalidateHierarchy();
            dialog.invalidate();
            dialog.layout();
        }

        public Dialog getDialog(){
            return dialog;
        }

        //Call in resize/updateUI to ensure dialog auto fits to new size
        public void updateDialogUI(float width, float height){
            dialog.getContentTable().setWidth(width*0.7f);
            labelCell.width(width*0.7f);
            dialog.setWidth(width*0.7f);
            dialog.setPosition(width/2-dialog.getWidth()/2,height/2-dialog.getHeight()/2);

            dialog.invalidateHierarchy();
            dialog.invalidate();
            dialog.layout();
        }

    }
}
