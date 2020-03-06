package com.bartish.oooist;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.bartish.oooist.stages.GameStage;

public class Main extends ApplicationAdapter {
	public static int WIDTH = 360, HEIGHT = 600;
	public static Preferences save;

	private static GameStage gameStage;
	public static ExtendViewport viewport;

	@Override
	public void create () {
		newGame();
	}
	public static void newGame(){
		save = Gdx.app.getPreferences("111is2!");

		viewport = new ExtendViewport(WIDTH, HEIGHT);
		gameStage = new GameStage(viewport);
		Gdx.input.setInputProcessor(gameStage);
		gameStage.setKeyboardFocus(gameStage.getActors().get(0));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameStage.act(Gdx.graphics.getDeltaTime());
		gameStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		gameStage.resize(viewport.getWorldWidth(), viewport.getWorldHeight());
	}
}