package com.bartish.oooist.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.bartish.oooist.utils.GameColors;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class Item extends Group {
    public int index;
    public float startX;
    public float startY;

    private Image back;
    private Image stroke;
    private Image icon;

    private InputListener dragAndDrop = new InputListener() {
        float deltaX = 0;
        float deltaY = 0;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            deltaX = x;
            deltaY = y;
            addAction(parallel(
                    scaleTo(1.25f, 1.25f,0.3f, Interpolation.pow5Out)
            ));
            setZIndex(100);
            touch = true;
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if(!active){addAction(parallel(
                    scaleTo(1, 1,0.6f, Interpolation.pow3In),
                    moveTo( startX, startY, 0.8f, Interpolation.fade)
            ));
            }
            touch = false;
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            moveBy(x - deltaX,y - deltaY);
        }
    };
    private boolean touch = false;
    private boolean active = false;

    private Color endColor;

    public Item(int index, float x, float y){
        this.index = index;
        startX = x;
        startY = y;

        back = new Image(new Texture(Gdx.files.internal("itemFill.png")));
        stroke = new Image(new Texture(Gdx.files.internal("itemStroke.png")));
        icon = new Image(new Texture(Gdx.files.internal(indexToChar(index) + ".png")));

        addActor(back);
        addActor(stroke);
        addActor(icon);

        setBounds(x, y, back.getWidth(), back.getHeight());
        setOrigin(getWidth() / 2,getHeight() / 2);
        setTouchable(Touchable.enabled);
        setColor(GameColors.getColor(index));
        addListener(dragAndDrop);

        back.setColor(getColor());
        back.setOrigin(back.getWidth()/2, back.getHeight()/2);
        stroke.setOrigin(stroke.getWidth()/2, stroke.getHeight()/2);
        icon.setPosition((int)((getWidth() - icon.getWidth() + 1) / 2), (int)((getHeight() - icon.getHeight() + 1) / 2));
        icon.setOrigin(icon.getWidth()/2, icon.getHeight()/2);


        endColor = getColor();
    }
    public Item(int index){
        this(index, 0, 0);
    }

    @Override
    public void act(float delta) {
        back.setColor(getColor());
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void changeIndex(int index){
        this.index = index;
        endColor = GameColors.getColor(index);
        addAction(color(endColor, 0.5f, Interpolation.fade));



        icon.addAction(sequence(
                scaleTo(0, 0,0.3f, Interpolation.pow3In),
                run(new Runnable() {
                    @Override
                    public void run() {
                        Texture t = new Texture(Gdx.files.internal(indexToChar(((Item)icon.getParent()).index) + ".png"));
                        icon.setDrawable(new SpriteDrawable(new Sprite(t)));
                        icon.setSize(t.getWidth(), t.getHeight());
                        icon.setPosition((int)((getWidth() - icon.getWidth() + 1) / 2), (int)((getHeight() - icon.getHeight() + 1) / 2));
                        icon.setOrigin(icon.getWidth()/2, icon.getHeight()/2);
                    }
                }),
                scaleTo(1, 1, 0.3f, Interpolation.pow3Out)
                ));


    }

    public Color getEndColor() {
        return endColor;
    }

    public boolean isTouch() {
        return touch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static char indexToChar(int index){
        char ch;
        switch (index){
            case 1:
                ch = '1';
                break;
            case 2:
                ch = 'A';
                break;
            case 3:
                ch = '2';
                break;
            case 4:
                ch = 'B';
                break;
            case 5:
                ch = '3';
                break;
            case 6:
                ch = 'C';
                break;
            case 7:
                ch = '4';
                break;
            case 8:
                ch = 'D';
                break;
            case 9:
                ch = '5';
                break;
            case 10:
                ch = 'E';
                break;
            case 11:
                ch = '6';
                break;
            case 12:
                ch = 'F';
                break;
            case 100:
                ch = 'R';
                break;
            default:
                ch = 'X';
                break;
        }
        return ch;
    }
}
