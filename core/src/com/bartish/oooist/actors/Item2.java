package com.bartish.oooist.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Item2 extends Group {
    private Image back;
    private Image stroke;
    private Image icon;

    public int index;

    public Item2(int index, int x, int y){
        this.index = index;

        back = new Image(new Texture(Gdx.files.internal("itemFill.png")));
        stroke = new Image(new Texture(Gdx.files.internal("itemStroke.png")));
        icon = new Image(new Texture(Gdx.files.internal(indexToChar(index) + ".png")));

        addActor(back);
        addActor(stroke);
        addActor(icon);

        setBounds(x, y, back.getWidth(), back.getHeight());
        icon.setPosition((getWidth() - icon.getWidth()) / 2, (getHeight() - icon.getHeight()) / 2);
    }
    public Item2(int index){
        this(index, 0, 0);
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
            default:
                ch = 'X';
                break;
        }
        return ch;
    }
}
