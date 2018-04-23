package com.james.footballsim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.james.footballsim.FootballSim;
import com.james.footballsim.MatchResult;
import com.james.footballsim.MatchSim;
import com.james.footballsim.Screens.Components.BottomBar;
import com.james.footballsim.Screens.Components.TopBar;
import uk.co.codeecho.fixture.generator.Fixture;

import static com.james.footballsim.FootballSim.league;
import static com.james.footballsim.FootballSim.skin;
import static com.james.footballsim.FootballSim.teamId;

/**
 * Created by James on 04/04/2018.
 */
public class MatchScreen extends CustomGameScreen {

    //TopBar
    private TopBar topBar;
    private BottomBar bottomBar;

    //Table
    private Table table;

    private TextButton menu;

    private ScrollPane scrollPane;
    private FootballSim aGame;

    public MatchScreen(FootballSim aGame) {
        super(aGame);
        this.aGame = aGame;
        showBackButton(true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage = new Stage(viewport);

        topBar = new TopBar(stage, "Game").addToStage();
        bottomBar = new BottomBar(stage).addToStage();

        table = new Table();
        table.padTop(25f);
        table.padBottom(10f);

        scrollPane = new ScrollPane(table,skin);
        scrollPane.setDebug(true);
        stage.addActor(scrollPane);

        menu = ScreenUtils.addScreenSwitchTextButton("Next", aGame,this,FootballSim.SCREENS.MAIN_MENU,FootballSim.IN);
        stage.addActor(menu);

        for(Fixture<Integer> fixture: FootballSim.rounds.get(FootballSim.round)){
            if((fixture.getHomeTeam()==FootballSim.teamId)||(fixture.getAwayTeam()==teamId)) return;
            MatchResult result = new MatchSim().runMatch(league.getTeam(fixture.getHomeTeam()),league.getTeam(fixture.getAwayTeam()),false,table,this);
            league.addStat(result);
        }
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
