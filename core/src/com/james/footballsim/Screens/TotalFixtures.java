package com.james.footballsim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.james.footballsim.FootballSim;
import com.james.footballsim.Screens.Components.BottomBar;
import com.james.footballsim.Screens.Components.TopBar;
import com.james.footballsim.Simulator.Team;
import com.james.footballsim.Utils;
import uk.co.codeecho.fixture.generator.Fixture;

import java.util.List;

import static com.james.footballsim.FootballSim.info;
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
        for(int i = 0; i<FootballSim.info.leagues.get(info.division).rounds.size(); i++){
            List<Fixture<Integer>> round = FootballSim.info.leagues.get(info.division).rounds.get(i);
            for(Fixture<Integer> fixture: round){
                if((fixture.getHomeTeam() == FootballSim.info.teamId)||(fixture.getAwayTeam()==FootballSim.info.teamId)){
                    System.out.println("Week "+(i+1)+" "+FootballSim.info.leagues.get(info.division).getTeam(fixture.getHomeTeam()).name + " vs " + FootballSim.info.leagues.get(info.division).getTeam(fixture.getAwayTeam()).name);
                    Team homeTeam = FootballSim.info.leagues.get(info.division).getTeam(fixture.getHomeTeam());
                    Team awayTeam = FootballSim.info.leagues.get(info.division).getTeam(fixture.getAwayTeam());

                    TextButton vs = new TextButton("VS", skin, "noClick_small");
                    TextButton home = button(homeTeam.shortName);
                    TextButton away = button(awayTeam.shortName);

                    table.add(home).minWidth(Utils.getMax(home.getWidth(),away.getWidth())).fillX();
                    table.add(vs).center();
                    table.add(away).minWidth(Utils.getMax(home.getWidth(),away.getWidth())).fillX();
                    table.row().spaceTop(5);
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
