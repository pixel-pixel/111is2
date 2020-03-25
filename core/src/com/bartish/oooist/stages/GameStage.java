package com.bartish.oooist.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bartish.oooist.Main;
import com.bartish.oooist.actors.BtnRestart;
import com.bartish.oooist.actors.Field;
import com.bartish.oooist.actors.Item;
import com.bartish.oooist.actors.Record;
import com.bartish.oooist.actors.Score;
import com.bartish.oooist.utils.Executer;
import com.bartish.oooist.utils.GameColors;

import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class GameStage extends MyStage {
    private static final float[] START_X = new float[]{Main.WIDTH/4, Main.WIDTH/2, Main.WIDTH/4*3};
    private static int START_DOWN;

    private Random random;
    private Actor background;
    private Field field;
    private Score score;
    private Record record;
    private Item[] items;
    private Image gameOver, curtain;
    private BtnRestart restart;
    private Executer stopGame, resumeGame, restartGame;
    private float worldWidth = 0, worldHeight = 0;

    private boolean forGO;
    private float probY;

    public GameStage(Viewport viewport) {
        super(viewport);
        init();
        resize(viewport.getWorldWidth(), viewport.getWorldHeight());

        if(Main.save.getBoolean("isSave", false)) field.load();
        else field.create();

        addActor(background);

        addActor(field);
        field.setColor(1,1,1,0);
        field.setScale(2);
        field.addAction(parallel(
                alpha(1, 1.5f, Interpolation.pow5Out),
                scaleTo(1, 1, 1.5f, Interpolation.pow5Out)
        ));

        addActor(score);
        score.setPosition(viewport.getWorldWidth() / 2, viewport.getWorldHeight() + 100);

        addActor(record);
        record.setY(START_DOWN);
        record.setColor(1, 1, 1, 0.4f);

        for(Item temp : items) addActor(temp);

        addActor(gameOver);
        gameOver.setPosition((worldWidth - gameOver.getWidth()) / 2, (worldHeight - gameOver.getHeight()) / 2);
        gameOver.setOrigin(Align.center);
        gameOver.setScale(2, 2);
        gameOver.setColor(1, 1, 1, 0);

        addActor(curtain);
        curtain.setColor(GameColors.X.r, GameColors.X.g, GameColors.X.b, 0);
        curtain.setTouchable(Touchable.disabled);

        addActor(restart);
        restart.addAction(moveTo(0, restart.getY(), 0.4f, Interpolation.pow2));
        restart.addExecuter(stopGame, resumeGame, restartGame);
    }

    private void init(){
        START_DOWN = -100;
        random = new Random();
        background = new Actor();
        background.setColor(GameColors.BACK);
        score = new Score(Main.save.getInteger("score", 0));
        field = new Field(score);
        record = new Record(Main.save.getInteger("record", 0));
        items = new Item[]{
                new Item(Main.save.getInteger("item0",random.nextInt(4) + 1), START_X[0], START_DOWN),
                new Item(Main.save.getInteger("item1",random.nextInt(4) + 1), START_X[1], START_DOWN),
                new Item(Main.save.getInteger("item2",random.nextInt(4) + 1), START_X[2], START_DOWN)
        };
        gameOver = new Image(new Texture(Gdx.files.internal("gameOver.png")));
        curtain = new Image(new Texture(Gdx.files.internal("fieldShadow.png")));
        restart = new BtnRestart(Main.WIDTH, Main.HEIGHT);
        stopGame = new Executer() {
            @Override
            public void execute() {
                field.clearActions();
                field.addAction(parallel(
                        alpha(0.4f, 0.4f, Interpolation.fade),
                        scaleTo(1, 1, 1f, Interpolation.pow3Out)
                ));
                score.addAction(alpha(0.4f, 0.4f, Interpolation.fade));
                for(int i = 0; i < items.length; i++){
                    items[i].setTouchable(Touchable.disabled);
                    items[i].clearActions();
                    items[i].addAction(parallel(alpha(0.4f, 0.4f, Interpolation.fade) , delay(i * 0.1f,
                            moveTo(items[i].getX(), START_DOWN, 0.3f, Interpolation.pow3In)
                    )));
                }
                record.addAction(moveTo(record.getX(), (worldHeight - field.getHeight()) / 4, 0.5f, Interpolation.pow3Out));
                //record.addAction(moveTo(record.getX(),score.getY(), 0.5f, Interpolation.pow3Out));
            }
        };
        resumeGame = new Executer() {
            @Override
            public void execute() {
                field.addAction(alpha(1, 0.4f, Interpolation.fade));
                score.addAction(alpha(1, 0.4f, Interpolation.fade));
                for(int i = 0; i < items.length; i++){
                    items[i].startY = (worldHeight - field.getHeight()) / 4 - items[i].getHeight() / 2;
                    items[i].startX = worldWidth / (items.length + 1) * (i + 1) - items[i].getWidth() / 2;
                    START_X[i] = items[i].startX + items[i].getWidth()/2;
                    items[i].setTouchable(Touchable.enabled);
                    items[i].addAction(parallel(alpha(1, 0.4f, Interpolation.fade), delay(i * 0.08f,
                            moveTo(items[i].startX, items[i].startY, 0.5f, Interpolation.pow3Out)
                    )));
                }
                record.addAction(moveTo(record.getX(), START_DOWN, 0.5f, Interpolation.pow3Out));
            }
        };
        restartGame = new Executer() {
            @Override
            public void execute() {
                RunnableAction run = new RunnableAction();
                run.setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Main.save.clear();
                        Main.save.putInteger("record", record.count);
                        Main.save.flush();
                        Main.newGame();
                    }
                });

                changeColor(GameColors.X);
                curtain.addAction(alpha(1, 0.3f, Interpolation.fade));
                restart.addAction(sequence(
                        moveTo(68, restart.getY(), 0.3f, Interpolation.pow2),
                        run
                ));
            }
        };

        forGO = true;
        probY = 0;
    }

    @Override
    public void act(float delta){
        super.act(delta);

        //When GameOver
        if(field.gameOver() && forGO){
            forGO = false;
            Main.save.clear();
            Main.save.flush();

            gameOver.addAction(parallel(
                    alpha(1, 1, Interpolation.pow5Out),
                    scaleTo(1, 1, 1, Interpolation.pow5Out)
            ));

            restart.setTouchable(Touchable.disabled);
            restart.show();
        }

        //Collisions
        for(int i = 0; i < items.length; i++){
            if(items[i].getX() + items[i].getOriginX() > field.getX() &&
            items[i].getX() + items[i].getOriginX() < field.getX() + field.getWidth() &&
            items[i].getY() + items[i].getOriginY() > field.getY() &&
            items[i].getY() + items[i].getOriginY() < field.getY() + field.getHeight()){
                if(items[i].isTouch()) items[i].setActive(true);
                //якщо додали об'єкт на field, забираємо звідси
                probY = items[i].startY;
                if(field.addItem(items[i])){
                    changeColor(items[i].getEndColor());

                    items[i] = new Item(random.nextInt(4) + 1, START_X[i] - items[i].getWidth() / 2, START_DOWN);
                    addActor(items[i]);
                    items[i].startY = probY;
                    items[i].addAction(moveTo(items[i].getX(), items[i].startY, 0.8f, Interpolation.fade));
                }
            }else if(items[i].isActive()){
                items[i].setActive(false);
                field.unfocus();
                field.oldMatrixPositionX = -1;
                field.oldMatrixPositionY = -1;

                if(!items[i].isTouch())
                    items[i].addAction(parallel(
                            scaleTo(1, 1,0.6f, Interpolation.pow3In),
                            moveTo( items[i].startX, items[i].startY, 0.8f, Interpolation.fade)
                    ));
            }
        }

        if(score.count > record.count) record.count = score.count;
        save();
    }
    //TODO
    private void save(){
        Main.save.putInteger("item0", items[0].index);
        Main.save.putInteger("item1", items[1].index);
        Main.save.putInteger("item2", items[2].index);
        Main.save.putInteger("score", score.count);
        Main.save.putInteger("record", record.count);
        Main.save.flush();
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(background.getColor().r, background.getColor().g, background.getColor().b, 1);
        super.draw();
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void resize(float worldWidth, float worldHeight){
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        restart.setPosition(worldWidth, worldHeight);
        field.setPosition((int)((worldWidth - field.getWidth()) / 2), (int)((worldHeight - field.getHeight()) / 2));
        field.edges(worldWidth == Main.WIDTH);
        score.addAction(delay(0.16f ,moveTo((int)(worldWidth / 2), (int)(worldHeight - (worldHeight - field.getHeight()) / 4),
                0.8f, Interpolation.pow3Out)));
        if(!restart.isActive()) {
            for (int i = 0; i < items.length; i++) {
                items[i].startY = (int)((worldHeight - field.getHeight()) / 4 - items[i].getHeight() / 2);
                items[i].startX = (int)(worldWidth / (items.length + 1) * (i + 1) - items[i].getWidth() / 2);
                START_X[i] = items[i].startX + items[i].getWidth()/2;
                items[i].clearActions();
                items[i].addAction(delay((i + 1) * 0.08f, moveTo(items[i].startX, items[i].startY, 0.8f, Interpolation.pow3Out)));
            }
            record.setX((int)(worldWidth / 2));
        }else{
            record.addAction(delay(0.16f, moveTo((int)(worldWidth/2), (int)((worldHeight - field.getHeight()) / 4),
                    0.8f, Interpolation.pow3Out)));
        }
        curtain.setBounds(0, 0, worldWidth, worldHeight);
        gameOver.setPosition((int)((worldWidth - gameOver.getWidth()) / 2), (int)((worldHeight - gameOver.getHeight()) / 2));
    }

    private void changeColor(Color c){
        background.addAction(color(c, 0.5f, Interpolation.fade));
    }

    @Override
    public boolean keyDown (int keycode) {
        if(keycode == Input.Keys.BACK && restart.isTouchable()) {
            if(restart.isActive()) restart.hide();
            else restart.show();
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer == 0){
            return super.touchDown(screenX, screenY, pointer, button);
        }
        return false;
    }
}
