package com.bartish.oooist.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelegateAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.bartish.oooist.Main;
import com.bartish.oooist.actors.Item;
import com.bartish.oooist.utils.GameColors;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class LogoStage extends MyStage {
    private Image forColor;
    private Item items[] = {
            new Item(1),
            new Item(2),
            new Item(3),
            new Item(4),
            new Item(5),
            new Item(6)
    };

    public LogoStage(ExtendViewport viewport) {
        super(viewport);

        for(int i = 0; i < items.length; i++){
            addActor(items[i]);
            items[i].setPosition(i * 60 + 2 + (viewport.getWorldWidth() - Main.WIDTH) / 2, viewport.getWorldHeight());
            items[i].addAction(delay(i * 0.08f, sequence(
                    moveTo(items[i].getX(), (viewport.getWorldHeight() - items[i].getHeight()) / 2,
                            1.25f, Interpolation.exp10Out),
                    moveTo(items[i].getX(), -items[i].getHeight(), 1.25f, Interpolation.exp10In)
            )));
        }
        forColor = new Image(new Texture(Gdx.files.internal("logo.png")));

        forColor.setColor(Color.BLACK);
        forColor.addAction(sequence(color(GameColors.BACK, 3f, Interpolation.exp5Out), delay(0f,run(new Runnable() {
            @Override
            public void run() {
                Main.changeStages();
            }
        }))));

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
        //TODO
        for(int i = 0; i < items.length; i++){
            Action a= ((DelegateAction)items[i].getActions().get(0)).getAction();
            if(((SequenceAction)a).getActions().isEmpty()){
                ((MoveToAction)a).setPosition(i * 60 + 2 + (worldWidth - Main.WIDTH) / 2, -items[i].getHeight());
            }else{
                ((MoveToAction)((SequenceAction)a).getActions().get(0))
                        .setPosition(i * 60 + 2 + (worldWidth - Main.WIDTH) / 2, (worldHeight - items[0].getHeight()) / 2);
                ((MoveToAction)((SequenceAction)a).getActions().get(1))
                        .setPosition(i * 60 + 2 + (worldWidth - Main.WIDTH) / 2, -items[i].getHeight());
            }

        }

    }

}
