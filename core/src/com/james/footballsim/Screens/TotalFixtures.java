package com.james.footballsim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.james.footballsim.FootballSim;
import com.james.footballsim.Screens.Components.BottomBar;
import com.james.footballsim.Screens.Components.TopBar;
import com.james.footballsim.Simulator.Team;
import uk.co.codeecho.fixture.generator.Fixture;

import java.util.List;

import static com.james.footballsim.FootballSim.skin;

/**
 * Created by James on 04/04/2018.
 */
public class TotalFixtures extends CustomGameScreen {

    //TopBar
    private TopBar topBar;
    private BottomBar bottomBar;

    //Table
    private Table table;

    private TextButton menu;

    private ScrollPane scrollPane;
    private FootballSim aGame;

    public TotalFixtures(FootballSim aGame) {
        super(aGame);
        this.aGame = aGame;

        topBar = new TopBar(stage, "Fixture List").addToStage();
        bottomBar = new BottomBar(stage).addToStage();

        showBackButton(true);
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.padTop(25f);
        for(int i=0; i<FootballSim.info.rounds.size(); i++){
            List<Fixture<Integer>> round = FootballSim.info.rounds.get(i);
            for(Fixture<Integer> fixture: round){
                if((fixture.getHomeTeam() == FootballSim.info.teamId)||(fixture.getAwayTeam()==FootballSim.info.teamId)){
                    System.out.println("Week "+(i+1)+" "+FootballSim.info.league.getTeam(fixture.getHomeTeam()).name + " vs " + FootballSim.info.league.getTeam(fixture.getAwayTeam()).name);
                    Team homeTeam = FootballSim.info.league.getTeam(fixture.getHomeTeam());
                    Team awayTeam = FootballSim.info.league.getTeam(fixture.getAwayTeam());

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
            }
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
