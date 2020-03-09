package com.bartish.oooist.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.bartish.oooist.Main;
import com.bartish.oooist.utils.GameColors;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class LogoStage extends MyStage {
    private Image logo;
    private Image forColor;

    public LogoStage(ExtendViewport viewport) {
        super(viewport);
        forColor = new Image(new Texture(Gdx.files.internal("logo.png")));
        logo = new Image(new Texture(Gdx.files.internal("logo.png")));
        logo.setOrigin(logo.getWidth()/2, logo.getHeight()/2);
        logo.setScale(0, 0);

        logo.addAction(sequence(
                scaleTo(1, 1, 0.5f, Interpolation.pow3Out),
                delay(0.3f, scaleTo(0, 0, 0.5f, Interpolation.pow3In)),
                run(new Runnable() {
                    @Override
                    public void run() {
                        Main.changeStages();
                    }
                })
        ));
        forColor.setColor(Color.BLACK);
        forColor.addAction(color(GameColors.BACK, 0.5f));
        addActor(logo);
        addActor(forColor);
        forColor.setScale(0);
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(forColor.getColor().r, forColor.getColor().g, forColor.getColor().b, 1);
        super.draw();
    }

    @Override
    public void resize(float worldWidth, float worldHeight){
        logo.setPosition((worldWidth - logo.getWidth()) / 2, (worldHeight - logo.getHeight()) / 2);
    }

}
