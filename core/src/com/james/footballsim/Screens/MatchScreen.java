package com.james.footballsim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.james.footballsim.FootballSim;
import com.james.footballsim.Simulator.MatchResult;
import com.james.footballsim.Simulator.MatchSim;
import com.james.footballsim.Screens.Components.BottomBar;
import com.james.footballsim.Screens.Components.TopBar;
import uk.co.codeecho.fixture.generator.Fixture;

import static com.james.footballsim.FootballSim.skin;

/**
 * Created by James on 04/04/2018.
 */
public class MatchScreen extends CustomGameScreen {

    //TopBar
    private TopBar topBar;
    private BottomBar bottomBar;

    public Label minutes;

    //Table
    private VerticalGroup table;
    private Table mainTable;

    public TextButton menu;
    private TextButton skip;

    private ScrollPane scrollPane;
    private FootballSim aGame;
    private MatchSim matchSim;


    public MatchScreen(FootballSim aGame) {
        super(aGame);
        this.aGame = aGame;
    }

    @Override
    public void show() {
        super.show();
        stage = new Stage(viewport);

        topBar = new TopBar(stage, "Game").addToStage();
        bottomBar = new BottomBar(stage).addToStage();

        table = new VerticalGroup();
        //table.debug();
        table.padTop(25f);
        table.padBottom(40f);

        mainTable = new Table();

        scrollPane = new ScrollPane(table,skin);
        scrollPane.setDebug(true);
        stage.addActor(scrollPane);

        menu = ScreenUtils.addScreenSwitchTextButton("Next", aGame,this,FootballSim.SCREENS.UPDATES_SCREEN,FootballSim.IN);
        menu.setDisabled(true);
        stage.addActor(menu);

        skip = new TextButton("Skip",FootballSim.skin);
        skip.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                matchSim.skip();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        for(Fixture<Integer> fixture: FootballSim.info.rounds.get(FootballSim.info.round)){
            if((fixture.getHomeTeam()==FootballSim.info.teamId)||(fixture.getAwayTeam()==FootballSim.info.teamId)){
                matchSim = new MatchSim(FootballSim.info.league.getTeam(fixture.getHomeTeam()),FootballSim.info.league.getTeam(fixture.getAwayTeam()));
            }else {
                MatchResult result = new MatchSim(FootballSim.info.league.getTeam(fixture.getHomeTeam()),FootballSim.info.league.getTeam(fixture.getAwayTeam())).runMatchBackground();
                FootballSim.info.league.addStat(result);
            }

        }
        stage.addActor(skip);

        matchSim.setupUI(this,table);

        minutes = new Label("0",skin);
        minutes.setAlignment(Align.right);

        mainTable.add(minutes).width(getvWidth()).padRight(50f).spaceTop(140f);
        mainTable.row();
        stage.addActor(mainTable);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        matchSim.render(delta);
        scrollPane.layout();
        //scrollPane.scrollTo(0,0,0,0);
        stage.act();
        stage.draw();
    }


    @Override
    public void updateUI(float width, float height) {
        int pad_top = 95;
        int pad_bottom = 85;
        scrollPane.setHeight(height-(pad_bottom+pad_top)-200);
        scrollPane.setY(pad_bottom);
        scrollPane.setWidth(width);

        topBar.update(width,height);
        bottomBar.update(width,height);
        mainTable.setY(height-pad_top-200);
        mainTable.setHeight(pad_top+200);
        mainTable.setWidth(width);
        table.setWidth((float) (0.8*vWidth));
//        minutes.invalidate();
//        minutes.layout();
//        System.out.println(minutes.getWidth());

        menu.setPosition(width-menu.getWidth()-10, 0);
        skip.setPosition(menu.getX()-skip.getWidth()-10,0);
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
