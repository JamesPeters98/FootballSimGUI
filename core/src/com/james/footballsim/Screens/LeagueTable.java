package com.james.footballsim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.james.footballsim.FootballSim;
import com.james.footballsim.Screens.Components.BottomBar;
import com.james.footballsim.Screens.Components.TopBar;
import com.james.footballsim.Simulator.LeagueStats;
import com.james.footballsim.Simulator.Player;
import com.james.footballsim.Utils;

import java.util.ArrayList;
import java.util.List;

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
        addToTable(FootballSim.getTeam().getBestSquad());

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

    public void addToTable(List<Player> players){
        TextButton titleButton = new TextButton("Team", skin);
        titleButton.pad(0,15,0,15);
        table.add(titleButton).colspan(3).fillX();
        table.row().spaceTop(20);

        ArrayList<LeagueStats> leagueStatsArray = new ArrayList<>(FootballSim.info.league.getLeagueStats().values());
		Utils.sortArray(leagueStatsArray);
		for(int i = 0; i <leagueStatsArray.size();i++) {
			LeagueStats stats = leagueStatsArray.get(i);
			System.out.format("%-3s%-25s%-10s%-15s%-18s%-8s%-8s%-8s%-8s\n", new String[]{""+(i+1),stats.team.name,""+stats.points,""+stats.goals,""+stats.goalsConceeded,""+stats.wins,""+stats.draws,""+stats.losses,""+stats.team.getRating()});

			TextButton position = new TextButton(String.valueOf(i+1), skin, "noClick");
            position.pad(0,15,0,15);
            TextButton name = new TextButton(stats.team.name, skin);
            name.pad(0,15,0,15);
            name.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Clicked! "+FootballSim.getTeam().name);
                };
            });
            TextButton points = new TextButton(String.valueOf(stats.points), skin, "noClick");
            points.pad(0,15,0,15);
            table.add(position).fillX();
            table.add(name).fillX();
            table.add(points).fillX();
            table.row().spaceTop(20);
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
