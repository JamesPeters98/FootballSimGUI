package com.james.footballsim.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.james.footballsim.Screens.Components.BottomBar;
import com.james.footballsim.Screens.Components.TopBar;
import com.james.footballsim.Team;
import com.james.footballsim.FootballSim;

import static com.james.footballsim.FootballSim.skin;

/**
 * Created by James on 04/04/2018.
 */
public class TeamSelection extends CustomGameScreen {

    //TopBar
    TopBar topBar;
    BottomBar bottomBar;

    //Table
    private Table table;

    private ScrollPane scrollPane;

    public TeamSelection(FootballSim aGame) {
        super(aGame);
        table = new Table();

        table.padTop(25f);
        for(Team team : FootballSim.teams){
            TextButton name = new TextButton(team.name, skin);
            name.pad(0,15,0,15);
            name.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Clicked! "+team.name);
                    aGame.setTeam(team.id);
                    aGame.setScreen(TeamSelection.this, FootballSim.SCREENS.PLAYER_SELECTION, FootballSim.IN,0.5f);
                };
            });
            TextButton rating = new TextButton(String.valueOf(team.getRating()), skin, "noClick");
            rating.pad(0,15,0,15);
            table.add(name).fillX();
            table.add(rating);
            table.row().spaceTop(20);
        }
        table.padBottom(10f);


        scrollPane = new ScrollPane(table,skin);
        scrollPane.setDebug(true);
        stage.addActor(scrollPane);

        topBar = new TopBar(stage, "Team Selection").addToStage();
        bottomBar = new BottomBar(stage).addToStage();

        showBackButton(true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act();
        stage.draw();
    }


    @Override
    public void updateUI(float width, float height) {
        int pad_top = 95;
        int pad_bottom = 85;
        scrollPane.setHeight(height-(pad_bottom+pad_top));
        scrollPane.setY(pad_bottom);
        scrollPane.setWidth(width);

        topBar.update(width,height);
        bottomBar.update(width,height);
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
