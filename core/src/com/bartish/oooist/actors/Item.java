package com.bartish.oooist.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.bartish.oooist.utils.GameColor;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class Item extends Actor {
    private Texture number, back;
    private GameColor color;

    public  float startX, startY;
    public int index;
    public boolean isTouch = false;
    public boolean isActive = false;

    public Item(int index, float x, float y){
        number = new Texture(Gdx.files.internal("item" + index + ".png"));
        back = new Texture(Gdx.files.internal("itemFill.png"));

        color = GameColor.getColor(index);

        startX = x - number.getWidth()/2;
        startY = y - number.getHeight()/2;
        this.index = index;

        setBounds(startX, startY, number.getWidth(), number.getHeight());
        setOrigin(getWidth()/2, getHeight()/2);
        setTouchable(Touchable.enabled);

        addListener(new InputListener() {
            float deltaX, deltaY;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                deltaX = x;
                deltaY = y;
                addAction(parallel(
                        scaleTo(1.25f, 1.25f,0.3f, Interpolation.pow5Out)
                ));
                setZIndex(100);
                isTouch = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(!isActive){addAction(parallel(
                        scaleTo(1, 1,0.6f, Interpolation.pow3In),
                        moveTo( startX, startY, 0.8f, Interpolation.fade)
                ));
                }
                isTouch = false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                moveBy(x - deltaX,y - deltaY);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(getColor().a == 1)
            batch.setColor(color.r, color.g, color.b, getParent().getColor().a);
        else
            batch.setColor(color.r, color.g, color.b, getColor().a);

        batch.draw(back,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation(),
                0, 0,
                back.getWidth(), back.getHeight(),
                false, false);

        if(getColor().a == 1)
            batch.setColor(1, 1, 1, getParent().getColor().a);
        else
            batch.setColor(1, 1, 1, getColor().a);

        batch.draw(number,
                getX(), getY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(), getScaleY(),
                getRotation(),
                0, 0,
                number.getWidth(), number.getHeight(),
                false, false);
    }

    public GameColor getGameColor(){
        return color;
    }

    public void changeIndex(int index){
        this.index = index;
        this.color = GameColor.getColor(index);
        number = new Texture(Gdx.files.internal("item" + index + ".png"));
    }
}