package com.james.footballsim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.james.footballsim.FootballSim;
import com.james.footballsim.Screens.Components.BottomBar;
import com.james.footballsim.Screens.Components.TopBar;
import com.james.footballsim.Simulator.League;
import com.james.footballsim.Simulator.LeagueStats;
import com.james.footballsim.Simulator.Player;
import com.james.footballsim.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.james.footballsim.FootballSim.info;
import static com.james.footballsim.FootballSim.skin;

/**
 * Created by James on 04/04/2018.
 */
public class LeagueTable extends CustomGameScreen {

    //TopBar
    TopBar topBar;
    BottomBar bottomBar;

    //Buttons
    TextButton menu;

    //Table
    private Table table;
    private ScrollPane scrollPane;

    FootballSim aGame;

    public LeagueTable(FootballSim aGame) {
        super(aGame);
        this.aGame = aGame;

    }

    @Override
    public void show() {
        super.show();
        stage = new Stage(viewport);

        topBar = new TopBar(stage, "Table").addToStage();
        bottomBar = new BottomBar(stage).addToStage();
        showBackButton(true);

        table = new Table();
        table.padTop(25f);
        table.padBottom(10f);
        for(League league: FootballSim.info.leagues.values()) {
            addToTable(league);
        }

        scrollPane = new ScrollPane(table,skin);
        stage.addActor(scrollPane);
        menu = ScreenUtils.addScreenSwitchTextButton("Menu", aGame,this,FootballSim.SCREENS.MAIN_MENU,FootballSim.IN);
        stage.addActor(menu);

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
        topBar.update(width,height);
        bottomBar.update(width,height);
        menu.setPosition(width-menu.getWidth()-10, 0);

        int pad_top = 95;
        int pad_bottom = 85;
        scrollPane.setHeight(height-(pad_bottom+pad_top));
        scrollPane.setY(pad_bottom);
        scrollPane.setWidth(width);
        }

//        public TextButton buttonLeague(String text, int teamID){
//            TextButton button = button(text);
//            if(teamID == FootballSim.info.teamId){
//                button.set
//            }
//        }


    public void addToTable(League league){
        table.add(button(league.leagueType.getName())).colspan(7).fillX();
        table.row().spaceTop(10);
        table.add(button("Team")).colspan(2).fillX();
        table.add(button("W")).fillX();
        table.add(button("D")).fillX();
        table.add(button("L")).fillX();
        table.add(button("GD")).fillX();
        table.add(button("PTS")).fillX();

        table.row().spaceTop(10);

        ArrayList<LeagueStats> leagueStatsArray = new ArrayList<>(league.getLeagueStats().values());
		Utils.sortArray(leagueStatsArray);
		for(int i = 0; i <leagueStatsArray.size();i++) {
			LeagueStats stats = leagueStatsArray.get(i);
			System.out.format("%-3s%-25s%-10s%-15s%-18s%-8s%-8s%-8s%-8s\n", new String[]{""+(i+1),stats.team.name,""+stats.points,""+stats.goals,""+stats.goalsConceeded,""+stats.wins,""+stats.draws,""+stats.losses,""+stats.team.getRating()});

            TextButton points = new TextButton(String.valueOf(stats.points), skin, "noClick_small");
            points.pad(0,15,0,15);

            if(i == leagueStatsArray.size()-1) table.row().spaceTop(3).padBottom(10);
            table.add(button(String.valueOf(i+1))).fillX();
            table.add(button(stats.team.shortName)).fillX();
            int played = stats.wins+stats.draws+stats.losses;
            table.add(button(String.valueOf(stats.wins))).fillX();
            table.add(button(String.valueOf(stats.draws))).fillX();
            table.add(button(String.valueOf(stats.losses))).fillX();
            table.add(button(String.valueOf(stats.goals-stats.goalsConceeded))).fillX();
            table.add(button(String.valueOf(stats.points))).fillX();
            table.row().spaceTop(3);
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
