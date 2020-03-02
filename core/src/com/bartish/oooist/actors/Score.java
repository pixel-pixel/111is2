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
    public int score;
    private int dec = 1000000;
    private int number = 0;
    private int backdown = 0;

    public Score(int s){
        score = s;
        setSize(0, numbers[0].getHeight());
        setPosition(0, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        dec = 1000000;
        backdown = 0;
        while (dec > score) dec /= 10;

        if(score == 0) dec = 1;

        while(dec != 0){
            number = (score / dec) - (score / (dec*10)) * 10;
            batch.draw(numbers[number],
                    getX() + backdown - 8,
                    getY() - (numbers[number].getHeight() / 2),
                    numbers[number].getWidth()*getScaleX(),
                    numbers[number].getHeight()*getScaleY());
            backdown += numbers[number].getWidth()*getScaleX();
            dec /= 10;
        }
            setX((int)(getX() - (backdown - getWidth()) / 2));
            setWidth(backdown);
    }
}
