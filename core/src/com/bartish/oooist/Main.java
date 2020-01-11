package com.bartish.oooist;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.bartish.oooist.stages.GameStage;
import com.bartish.oooist.utils.GameColor;

public class Main extends ApplicationAdapter {
	public static int WIDTH = 360, HEIGHT = 600;
	public static Preferences save;
	public static GameColor mainColor = GameColor.BACK, minorColor = GameColor.BACK;

	public static GameStage gameStage;
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
		if(!mainColor.equals(minorColor)){
			mainColor.r -= (mainColor.r - minorColor.r)/10f;
			mainColor.g -= (mainColor.g - minorColor.g)/10f;
			mainColor.b -= (mainColor.b - minorColor.b)/10f;
		}

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(mainColor.r, mainColor.g, mainColor.b, 1);
		gameStage.act(Gdx.graphics.getDeltaTime());
		gameStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, false);
		gameStage.resize(viewport.getWorldWidth(), viewport.getWorldHeight());
	}

	public static void changeColor(GameColor color){
		minorColor = color;
	}
}