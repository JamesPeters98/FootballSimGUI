package com.james.footballsim.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.james.footballsim.Team;
import com.james.footballsim.FootballSim;

import static com.james.footballsim.FootballSim.skin;

/**
 * Created by James on 04/04/2018.
 */
public class TeamSelection extends CustomGameScreen {


    //Labels
    private Label title;

    //Table
    private Table table;

    private ScrollPane scrollPane;

    public TeamSelection(Game aGame) {
        super(aGame);

        title = new Label("Team Selection", skin,"title");
        title.setAlignment(Align.center);

        stage.addActor(title);

        table = new Table();
//        table.setDebug(true);

        for(Team team : FootballSim.teams){
            TextButton name = new TextButton(team.name, skin);
            name.pad(0,15,0,15);
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
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }


    @Override
    public void updateUI(float width, float height) {
        title.setY(height-90);
        title.setWidth(width);

        table.setY(height-100);
//        table.setWidth(Gdx.graphics.getWidth()/2);
        scrollPane.setHeight(height-100);
        scrollPane.setWidth(width);
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
