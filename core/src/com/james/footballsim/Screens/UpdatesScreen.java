package com.james.footballsim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.james.footballsim.FootballSim;
import com.james.footballsim.Screens.Components.BottomBar;
import com.james.footballsim.Screens.Components.TopBar;
import com.james.footballsim.Simulator.TeamUpdate;

import java.util.List;

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
        stage = new Stage(viewport);

        topBar = new TopBar(stage, "Updates").addToStage();
        bottomBar = new BottomBar(stage).addToStage();

        table = new Table();

        table.padTop(25f);
        table.padBottom(10f);

        addToTable(FootballSim.getTeam().getUpdates());

        scrollPane = new ScrollPane(table,skin);
        stage.addActor(scrollPane);

        menu = ScreenUtils.addScreenSwitchTextButton("Next", aGame,this,FootballSim.SCREENS.MAIN_MENU,FootballSim.IN);
        stage.addActor(menu);

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

        for(TeamUpdate update : teamUpdates){
            TextButton updateText = new TextButton(update.getUpdate(), skin);
            updateText.pad(0,15,0,15);
            table.add(updateText).fillX();
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
