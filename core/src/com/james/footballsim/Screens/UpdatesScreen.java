package com.james.footballsim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.james.footballsim.FootballSim;
import com.james.footballsim.Screens.Components.BottomBar;
import com.james.footballsim.Screens.Components.TopBar;
import com.james.footballsim.Simulator.MatchResult;
import com.james.footballsim.Simulator.Team;
import com.james.footballsim.Simulator.TeamUpdate;
import com.james.footballsim.Utils;

import java.util.List;

import static com.james.footballsim.FootballSim.info;
import static com.james.footballsim.FootballSim.skin;

/**
 * Created by James on 04/04/2018.
 */
public class UpdatesScreen extends CustomGameScreen {

    //TopBar
    TopBar topBar;
    BottomBar bottomBar;

    //Buttons
    TextButton menu;

    //Labels
    private Label label;

    //Table
    private Table table;
    private Cell<Label> labelCell;

    private ScrollPane scrollPane;

    FootballSim aGame;

    public UpdatesScreen(FootballSim aGame) {
        super(aGame);
        this.aGame = aGame;

    }

    @Override
    public void show() {
        super.show();
        stage = new Stage(viewport);

        topBar = new TopBar(stage, "Updates").addToStage();
        bottomBar = new BottomBar(stage).addToStage();

        table = new Table();

        table.padTop(25f);
        table.padBottom(10f);

        addToTable(FootballSim.getTeam().getUpdates());

        scrollPane = new ScrollPane(table,skin);
        stage.addActor(scrollPane);

        menu = ScreenUtils.addScreenSwitchTextButton("Next", aGame,this,FootballSim.SCREENS.LEAGUE_TABLE,FootballSim.IN);
        stage.addActor(menu);

        showFPSCounter();
        //updateUI(vWidth,vHeight);
        Gdx.input.setInputProcessor(stage);
        //System.out.println("Shown");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act();
        stage.draw();
    }


    @Override
    public void updateUI(float width, float height) {
        topBar.update(width,height);
        bottomBar.update(width,height);

        menu.setPosition(width-menu.getWidth()-10, 0);


        int pad_top = 95;
        int pad_bottom = 85;
        scrollPane.setHeight(height-(pad_bottom+pad_top));
        scrollPane.setY(pad_bottom);
        scrollPane.setWidth(width);

    }

    public void addToTable(List<TeamUpdate> teamUpdates){
        int roundShow;
        if(info.round == 0) roundShow = 38;
        else roundShow = info.round-1;
        if(teamUpdates.size()!=0){
            TextButton updateText = new TextButton("Team Updates", skin, "noClick_small");
            updateText.pad(0,15,0,15);
            table.add(updateText).fillX().colspan(4).spaceBottom(10);
            table.row();
        }
        for(TeamUpdate update : teamUpdates){
            TextButton updateText = new TextButton(update.getUpdate(), skin, "noClick_small");
            updateText.pad(0,15,0,15);
            table.add(updateText).fillX().colspan(4);
            table.row().spaceTop(3);
        }
        if(FootballSim.info.leagues.get(info.division).getMatchResults().size()!=0){
            TextButton updateText = new TextButton("Other Results", skin, "noClick_small");
            updateText.pad(0,15,0,15);
            table.add(updateText).fillX().colspan(4).spaceTop(20).spaceBottom(10);
            table.row();
        }
        for(MatchResult result : FootballSim.info.leagues.get(info.division).getMatchResults().get(roundShow)){
            Team homeTeam = result.getHomeTeam();
            Team awayTeam = result.getAwayTeam();


            TextButton home = button(homeTeam.shortName);
            TextButton away = button(awayTeam.shortName);
            TextButton homeScore = button(String.valueOf(result.getHomeGoals()));
            TextButton awayScore = button(String.valueOf(result.getAwayGoals()));

            table.add(home).minWidth(Utils.getMax(home.getWidth(),away.getWidth())).fillX();
            table.add(homeScore).center();
            table.add(awayScore).center();
            table.add(away).minWidth(Utils.getMax(home.getWidth(),away.getWidth())).fillX();
            table.row().spaceTop(3);

            if(result.isCupGame()&&(result.getHomePens()!=0 && result.getAwayPens()!=0)){
                home = button("Penalties");
                away = button("Penalties");
                homeScore = button(String.valueOf(result.getHomePens()));
                awayScore = button(String.valueOf(result.getAwayPens()));

                table.add(home).minWidth(Utils.getMax(home.getWidth(),away.getWidth())).fillX();
                table.add(homeScore).center();
                table.add(awayScore).center();
                table.add(away).minWidth(Utils.getMax(home.getWidth(),away.getWidth())).fillX();
                table.row().spaceTop(3);
            }
        }

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
