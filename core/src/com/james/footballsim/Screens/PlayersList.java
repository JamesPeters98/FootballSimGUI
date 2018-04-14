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

import static com.james.footballsim.FootballSim.skin;
import static com.james.footballsim.FootballSim.team;

/**
 * Created by James on 04/04/2018.
 */
public class PlayersList extends CustomGameScreen {


    //Labels
    private Label title;

    //Table
    private Table table;

    private ScrollPane scrollPane;
    private Dialog dialog;

    public PlayersList(FootballSim aGame) {
        super(aGame);

        title = new Label("Players", skin,"title");
        title.setAlignment(Align.center);

        stage.addActor(title);

        table = new Table();
//        table.setDebug(true);

        for(Player player : team.players.getList()){
            TextButton name = new TextButton(player.getFullName(), skin, "small");
            name.pad(0,15,0,15);
            name.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Clicked! "+team.name);
                };
            });
            TextButton rating = new TextButton(String.valueOf(player.getRating()), skin, "noClick_small");
            rating.pad(0,15,0,15);
            table.add(name).fillX();
            table.add(rating);
            table.row().spaceTop(20);
        }
        table.padBottom(10f);


        scrollPane = new ScrollPane(table,skin);
        scrollPane.setDebug(true);

        stage.addActor(scrollPane);

        Label label = new Label("You chose "+team.name+". Take a look at your team!", FootballSim.skin, "content");
        label.setWrap(true);
        label.setFontScale(.8f);
        label.setAlignment(Align.center);

        showBackButton(true);

        dialog =
                new Dialog("", FootballSim.skin) {
                    protected void result (Object object) {
                        System.out.println("Chosen: " + object);
                    }
                };

        dialog.padTop(50).padBottom(50);
        System.out.println(vWidth);
        dialog.getContentTable().add(label).width(vWidth*0.7f).row();
        dialog.getButtonTable().padTop(50);

        TextButton dbutton = new TextButton("Okay", FootballSim.skin);
        dialog.button(dbutton, true);

        dialog.key(Input.Keys.ENTER, true);
        dialog.invalidateHierarchy();
        dialog.invalidate();
        dialog.layout();
        dialog.show(stage);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
        title.setY(height-90);
        title.setWidth(width);

        table.setY(height-100);
//        table.setWidth(Gdx.graphics.getWidth()/2);
        scrollPane.setHeight(height-100);
        scrollPane.setWidth(width);

        dialog.getContentTable().setWidth(width*0.7f);
        dialog.setWidth(width*0.7f);
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
