package com.bartish.oooist.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.bartish.oooist.Main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public class Field extends Group {
    public static final int MATRIX_WIDTH = 360;
    public static final int MATRIX_SIZE = 6;

    Image shadow = new Image(new Texture(Gdx.files.internal("fieldShadow.png")));
    Image field = new Image(new Texture(Gdx.files.internal("field.png")));
    Image edges = new Image(new Texture(Gdx.files.internal("edges.png")));

    Item[][] matrix = new Item[MATRIX_SIZE][MATRIX_SIZE];

    Vector2 vector = new Vector2();
    int matrixPositionX, matrixPositionY;
    public int oldMatrixPositionX = -1, oldMatrixPositionY = -1;
    public Field() {
        super();
        setBounds((Main.WIDTH - MATRIX_WIDTH) / 2,
                (Main.HEIGHT - MATRIX_WIDTH) / 2,
                MATRIX_WIDTH, MATRIX_WIDTH);
        setOrigin(Align.center);

        addActor(shadow);
        shadow.setBounds(-10000,-7, 20000, MATRIX_WIDTH + 14);
        shadow.setColor(0,0,0,0.25f);

        addActor(field);
        field.setPosition((MATRIX_WIDTH - field.getWidth()) / 2,
                (MATRIX_WIDTH - field.getHeight()) / 2);
        addActor(edges);
        edges.setPosition(field.getX(), field.getY());
    }

    public void create(){
        Random random = new Random();
        int countOfX = random.nextInt(2) + 2;
        int x, y;

        Main.save.putBoolean("isSave", true);

        while (countOfX > 0){
            x = random.nextInt(MATRIX_SIZE);
            y = random.nextInt(MATRIX_SIZE);

            if(matrix[x][y] == null){
                matrix[x][y] = new Item(0, x * 60, y * 60);
                matrix[x][y].setPosition(x * 60 + 2, y * 60 + 2);
                matrix[x][y].startX = x * 60 + 2;
                matrix[x][y].startY = y * 60 + 2;
                matrix[x][y].setTouchable(Touchable.disabled);
                addActor(matrix[x][y]);

                Main.save.putInteger(x+"_"+y, 0);

                countOfX--;
            }
        }
        Main.save.flush();
    }

    public void load(){
        int index;

        for(int i = 0; i < MATRIX_SIZE; i++){
            for(int j = 0; j < MATRIX_SIZE; j++){
                index = Main.save.getInteger(i+"_"+j, -1);
                if(index >= 0){
                    matrix[i][j] = new Item(index, i * 60, j * 60);
                    matrix[i][j].setPosition(i * 60 + 2, j * 60 + 2);
                    matrix[i][j].startX = i * 60 + 2;
                    matrix[i][j].startY = j * 60 + 2;
                    matrix[i][j].setTouchable(Touchable.disabled);
                    addActor(matrix[i][j]);
                }
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
    }

    public boolean addItem(Item item){
        //Знаходимо індекси матриці для item
        vector.set(0, 0);
        item.localToActorCoordinates(field, vector);


        matrixPositionX = (int)(vector.x + item.getOriginX())/60;
        matrixPositionY = (int)(vector.y + item.getOriginY())/60;

        if(canTake(item, matrixPositionX, matrixPositionY) && item.isActive){
            //Фокусуємо сусідні елементи
            if(matrixPositionX != oldMatrixPositionX || matrixPositionY != oldMatrixPositionY){
                unfocus();
                focus(item.index, matrixPositionX, matrixPositionY);
                oldMatrixPositionX = matrixPositionX;
                oldMatrixPositionY = matrixPositionY;
            }
            //Додаємо на поле
            if(!item.isTouch){
                addActor(item);
                matrix[matrixPositionX][matrixPositionY] = item;
                Main.save.putInteger(matrixPositionX+"_"+matrixPositionY, item.index);

                item.setTouchable(Touchable.disabled);
                item.setPosition(vector.x, vector.y);
                item.startX = matrixPositionX * 60 + 2;
                item.startY = matrixPositionY * 60 + 2;

                item.addAction(parallel(
                        moveTo(item.startX, item.startY, 0.5f, Interpolation.fade),
                        scaleTo(1,1, 0.5f, Interpolation.pow3Out)
                ));

                if(focusSet.size() > 0){
                    item.changeIndex(item.index + focusSet.size() * 2);
                    Main.save.putInteger(matrixPositionX+"_"+matrixPositionY, item.index);

                    for(HashSet<GridPoint2> temp1 : focusSet){
                        for(GridPoint2 temp2 : temp1){
                            removeActor(matrix[temp2.x][temp2.y]);
                            matrix[temp2.x][temp2.y] = null;
                            Main.save.putInteger(temp2.x+"_"+temp2.y, -1);
                        }
                    }
                }
                Main.save.flush();
                return true;
            }
        }else{
            item.isActive = false;
            unfocus();
            oldMatrixPositionX = matrixPositionX;
            oldMatrixPositionY = matrixPositionY;
        }
        return false;
    }
    //true якщо можна помістити об'єкт на клітку
    public boolean canTake(Item item, int x, int y){
        if(x >= 0 && x < matrix.length && y >= 0 && y < matrix.length){
            if(matrix[x][y] == null){
                return true;
            }
        }
        return false;
    }

    ArrayList<HashSet<GridPoint2>> focusSet = new ArrayList<>();
    int focusCount = 0;
    Item elem;
    //
    public void focus(int index, int x, int y){
        focusSet.add(new HashSet<GridPoint2>());

        if(focusPlus(index, x, y+1)){
            focusPlus(index, x-1, y+1);
            focusPlus(index, x, y+2);
            focusPlus(index, x+1, y+1);
        }
        if(focusPlus(index, x+1, y)){
            focusPlus(index, x+1, y+1);
            focusPlus(index, x+2, y);
            focusPlus(index, x+1, y-1);
        }
        if(focusPlus(index, x, y-1)){
            focusPlus(index, x+1, y-1);
            focusPlus(index, x, y-2);
            focusPlus(index, x-1, y-1);
        }
        if(focusPlus(index, x-1, y)){
            focusPlus(index, x-1, y-1);
            focusPlus(index, x-2, y);
            focusPlus(index, x-1, y+1);
        }

        if(focusSet.get(focusCount).size() > 1){
            for(GridPoint2 temp : focusSet.get(focusCount)){
                elem = matrix[temp.x][temp.y];

                elem.addAction(forever(sequence(
                        moveTo(elem.getX() + (x*60 - elem.getX()) / (focusCount + 1.5f),
                                elem.getY() + (y*60 - elem.getY()) / (focusCount + 1.5f),
                                0.5f, Interpolation.fade),
                        moveTo(elem.startX,
                                elem.startY,
                                0.5f, Interpolation.fade)
                )));
            }
            focusCount++;
            focus(index + 2, x, y);
        }else{
            focusSet.remove(focusCount);
        }
    }
    private boolean focusPlus(int index, int xPlus, int yPlus){
        if(xPlus >= 0 && xPlus < matrix.length &&
        yPlus >= 0 && yPlus < matrix.length){
            elem = matrix[xPlus][yPlus];

            if(elem != null && elem.index == index){
                focusSet.get(focusCount).add(new GridPoint2(xPlus, yPlus));
                return true;
            }
        }
        return false;
    }
    public void unfocus(){
        for(HashSet<GridPoint2> temp1 : focusSet){
            for(GridPoint2 temp2 : temp1){
                elem = matrix[temp2.x][temp2.y];

                if(elem != null){
                    elem.clearActions();
                    elem.addAction(sequence(
                            moveTo(elem.startX, elem.startY, 0.5f, Interpolation.fade)
                    ));
                }
            }
        }
        focusCount = 0;
        focusSet.clear();
    }
    public boolean gameOver(){
        for(int i = 0; i < MATRIX_SIZE; i++){
            for(int j = 0; j < MATRIX_SIZE; j++){
                if(matrix[i][j] == null)
                    return false;
            }
        }
        return true;
    }
    public void isEdges(boolean is){
        if(!edges.hasActions()){
            if(is){
                edges.addAction(alpha(1, 0.12f));
            }else{
                edges.addAction(alpha(0, 0.12f));
            }
        }

    }
}
