package com.james.footballsim;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.james.footballsim.Screens.TitleScreen;
import uk.co.codeecho.fixture.generator.Fixture;
import uk.co.codeecho.fixture.generator.FixtureGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FootballSim extends Game {
	public static Skin skin;

	static FixtureGenerator fixtureGenerator;
	static League league;
	static List<List<Fixture<Integer>>> rounds;

	public static List<Team> teams;
	
	@Override
	public void create () {
		skin = new Skin(Gdx.files.internal("skin/cloud-form-ui.json"));

		fixtureGenerator = new FixtureGenerator();
		league = new League();

		teams = new ArrayList<>(league.getTeams().values());
		Collections.sort(teams,League.sortTeams);


		this.setScreen(new TitleScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		skin.dispose();
	}
}
