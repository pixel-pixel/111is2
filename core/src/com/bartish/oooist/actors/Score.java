package com.bartish.oooist.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Score extends Actor {
    private static final Texture numbers[] = {
            new Texture(Gdx.files.internal("0.png")),
            new Texture(Gdx.files.internal("1.png")),
            new Texture(Gdx.files.internal("2.png")),
            new Texture(Gdx.files.internal("3.png")),
            new Texture(Gdx.files.internal("4.png")),
            new Texture(Gdx.files.internal("5.png")),
            new Texture(Gdx.files.internal("6.png")),
            new Texture(Gdx.files.internal("7.png")),
            new Texture(Gdx.files.internal("8.png")),
            new Texture(Gdx.files.internal("9.png")),
    };
    public int count;
    protected int dynamicWidth = 0;
    protected int backdrop = 0;
    private int dec = 1000000;
    private int number = 0;

    public Score(int s){
        count = s;
        setSize(0, numbers[0].getHeight());
        setPosition(0, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateDynamicWidth();

        dec = 1000000;
        while (dec > count) dec /= 10;
        if(count == 0) dec = 1;
        batch.setColor(getColor());
        while(dec != 0){
            number = (count / dec) - (count / (dec*10)) * 10;
            batch.draw(numbers[number],
                    getX() - dynamicWidth / 2 + backdrop,
                    getY() - (numbers[number].getHeight() / 2),
                    numbers[number].getWidth()*getScaleX(),
                    numbers[number].getHeight()*getScaleY());
            backdrop += numbers[number].getWidth()*getScaleX() + 3;
            dec /= 10;
        }
        batch.setColor(1, 1, 1, 1);
            //setX((int)(getX() - (dynamicWidth - getWidth()) / 2));
        setWidth(dynamicWidth);
        backdrop = 0;
    }

    protected int updateDynamicWidth(){
        dec = 1000000;
        dynamicWidth = 0;

        while (dec > count) dec /= 10;
        if(count == 0) dec = 1;
        while(dec != 0){
            number = (count / dec) - (count / (dec*10)) * 10;
            dynamicWidth += numbers[number].getWidth()*getScaleX() + 3;
            dec /= 10;
        }
        dynamicWidth -= 3;
        return dynamicWidth;
    }
}
