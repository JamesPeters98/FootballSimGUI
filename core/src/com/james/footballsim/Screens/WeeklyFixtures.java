package com.james.footballsim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.james.footballsim.FootballSim;
import com.james.footballsim.Screens.Components.BottomBar;
import com.james.footballsim.Screens.Components.TopBar;
import com.james.footballsim.Team;
import uk.co.codeecho.fixture.generator.Fixture;

import java.util.List;

import static com.james.footballsim.FootballSim.league;
import static com.james.footballsim.FootballSim.skin;

/**
 * Created by James on 04/04/2018.
 */
public class WeeklyFixtures extends CustomGameScreen {

    //TopBar
    private TopBar topBar;
    private BottomBar bottomBar;

    //Table
    private Table table;

    private TextButton menu;

    private ScrollPane scrollPane;
    private FootballSim aGame;

    public WeeklyFixtures(FootballSim aGame) {
        super(aGame);
        this.aGame = aGame;

        bottomBar = new BottomBar(stage).addToStage();

        showBackButton(true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        topBar = new TopBar(stage, "Week "+(FootballSim.round+1)).addToStage();

        table = new Table();
        table.padTop(25f);

        List<Fixture<Integer>> round = FootballSim.rounds.get(FootballSim.round);
        for(Fixture<Integer> fixture: round){
            System.out.println(league.getTeam(fixture.getHomeTeam()).name + " vs " + league.getTeam(fixture.getAwayTeam()).name);
            Team homeTeam = league.getTeam(fixture.getHomeTeam());
            Team awayTeam = league.getTeam(fixture.getAwayTeam());

            TextButton home = new TextButton(homeTeam.name, skin, "noClick_small");
            home.pad(0,15,0,15);
            TextButton away = new TextButton(awayTeam.name, skin, "noClick_small");
            away.pad(0,15,0,15);
            TextButton vs = new TextButton("VS", skin, "noClick_small");
            table.add(home).fillX();
            table.add(vs).center();
            table.add(away).fillX();
            table.row().spaceTop(10);
        }

        table.padBottom(10f);

        scrollPane = new ScrollPane(table,skin);
        scrollPane.setDebug(true);
        stage.addActor(scrollPane);

        menu = ScreenUtils.addScreenSwitchTextButton("Next", aGame,this,FootballSim.SCREENS.MAIN_MENU,FootballSim.IN);
        stage.addActor(menu);
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
        int pad_top = 95;
        int pad_bottom = 85;
        scrollPane.setHeight(height-(pad_bottom+pad_top));
        scrollPane.setY(pad_bottom);
        scrollPane.setWidth(width);

        topBar.update(width,height);
        bottomBar.update(width,height);

        menu.setPosition(width-menu.getWidth()-10, 0);
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
