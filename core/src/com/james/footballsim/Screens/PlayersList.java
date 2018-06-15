package com.james.footballsim.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.james.footballsim.FootballSim;
import com.james.footballsim.Simulator.Player;
import com.james.footballsim.Screens.Components.BottomBar;
import com.james.footballsim.Screens.Components.TopBar;

import java.util.List;

import static com.james.footballsim.FootballSim.Bottom_Padding;
import static com.james.footballsim.FootballSim.Top_Padding;
import static com.james.footballsim.FootballSim.skin;

/**
 * Created by James on 04/04/2018.
 */
public class PlayersList extends CustomGameScreen {

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
    private ScreenUtils.DialogCreator dialogCreator;

    FootballSim aGame;

    public PlayersList(FootballSim aGame) {
        super(aGame);
        this.aGame = aGame;

    }

    @Override
    public void show() {
        super.show();
        stage = new Stage(viewport);

        topBar = new TopBar(stage, "Players").addToStage();
        bottomBar = new BottomBar(stage).addToStage();

        showBackButton(true);

        table = new Table();

        table.padTop(25f);
        table.padBottom(10f);


        addToTable("First Team", FootballSim.getTeam().getBestSquad());
        addToTable("Reserves", FootballSim.getTeam().getReserves());
        if(FootballSim.getTeam().injured.getList().size() > 0) addToTable("Injured", FootballSim.getTeam().injured.getList());

        scrollPane = new ScrollPane(table,skin);
        stage.addActor(scrollPane);

        menu = ScreenUtils.addScreenSwitchTextButton("Menu", aGame,this,FootballSim.SCREENS.MAIN_MENU,FootballSim.IN);
        stage.addActor(menu);

        if(FootballSim.info.firstRun) {
            dialogCreator = new ScreenUtils.DialogCreator("You chose " + FootballSim.getTeam().name + ". Take a look at your team!", "Okay");
            dialogCreator.getDialog().show();
            FootballSim.info.firstRun = false;
        }

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

        int pad_top = (int) (95+Top_Padding);
        int pad_bottom = (int) (85+Bottom_Padding);
        scrollPane.setHeight(height-(pad_bottom+pad_top));
        scrollPane.setY(pad_bottom);
        scrollPane.setWidth(width);

        menu.setPosition(width-menu.getWidth()-20, 0+Bottom_Padding);
    }

    public void addToTable(String title, List<Player> players){

        TextButton titleButton = new TextButton(title, skin);
        titleButton.pad(0,15,0,15);
        table.add(titleButton).colspan(4).fillX();
        table.row().spaceTop(20);

        for(Player player : players){
            String playerName = "";
            if(player.isInjured()) playerName = player.getMatchName()+" ["+player.getInjuryLength()+"]";
            else playerName = player.getMatchName();

            TextButton position = new TextButton(String.valueOf(player.getShortPosition()), skin, "noClick");
            position.pad(0,15,0,15);
            TextButton name = new TextButton(playerName, skin);
            name.pad(0,15,0,15);
            name.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Clicked! "+FootballSim.getTeam().name);
                };
            });
            TextButton ratingAttack = new TextButton(String.valueOf(player.getWeightedRating()), skin, "noClick");
            ratingAttack.pad(0,15,0,15);
            TextButton ratingDefence = new TextButton(String.valueOf(player.getWeightedRating()+player.getGrowth()), skin, "noClick");
            ratingDefence.pad(0,15,0,15);
            table.add(position).fillX();
            table.add(name).fillX();
            table.add(ratingAttack);
            table.add(ratingDefence);
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
