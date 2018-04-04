package com.james.footballsim.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.james.footballsim.Team;
import com.james.footballsim.FootballSim;

/**
 * Created by James on 04/04/2018.
 */
public class TeamSelection implements Screen {



    private Stage stage;
    private Game game;

    //Labels
    private Label title;

    //Table
    private Table table;

    public TeamSelection(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        title = new Label("Team Selection", FootballSim.skin,"title");
        title.setAlignment(Align.center);

        stage.addActor(title);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);

        for(Team team : FootballSim.teams){
            Label name = new Label(team.name,FootballSim.skin);
            name.setFontScale(2);
            table.add(name);
            table.row();
        }

        stage.addActor(table);
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
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);

        title.setY(Gdx.graphics.getHeight()-40);
        title.setWidth(Gdx.graphics.getWidth());


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
