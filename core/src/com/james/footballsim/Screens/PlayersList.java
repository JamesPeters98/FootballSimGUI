package com.james.footballsim.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.james.footballsim.FootballSim;
import com.james.footballsim.Player;
import com.james.footballsim.Screens.Components.BottomBar;
import com.james.footballsim.Screens.Components.TopBar;

import static com.james.footballsim.FootballSim.skin;
import static com.james.footballsim.FootballSim.team;

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

        table = new Table();

        table.padTop(25f);
        for(Player player : team.players.getList()){
            TextButton name = new TextButton(player.getFullName(), skin);
            name.pad(0,15,0,15);
            name.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Clicked! "+team.name);
                };
            });
            TextButton rating = new TextButton(String.valueOf(player.getRating()), skin, "noClick");
            rating.pad(0,15,0,15);
            table.add(name).fillX();
            table.add(rating);
            table.row().spaceTop(20);
        }
        table.padBottom(10f);

        scrollPane = new ScrollPane(table,skin);
        stage.addActor(scrollPane);

        topBar = new TopBar(stage, "Players").addToStage();
        bottomBar = new BottomBar(stage).addToStage();

        showBackButton(true);

        dialogCreator = new ScreenUtils.DialogCreator("You chose "+team.name+". Take a look at your team!", "Okay",vWidth);
        dialogCreator.getDialog().show(stage);

        menu = new TextButton("Menu", FootballSim.skin);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        menu = ScreenUtils.addScreenSwitchTextButton("Menu", aGame,this,FootballSim.SCREENS.MAIN_MENU,FootballSim.IN);
        stage.addActor(menu);
        updateUI(vWidth,vHeight);
        System.out.println("Shown");
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
        topBar.update(width,height);
        bottomBar.update(width,height);

        menu.setPosition(width-menu.getWidth()-10, 0);


        int pad_top = 95;
        int pad_bottom = 85;
        scrollPane.setHeight(height-(pad_bottom+pad_top));
        scrollPane.setY(pad_bottom);
        scrollPane.setWidth(width);

        dialogCreator.updateDialogUI(width, height);

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
