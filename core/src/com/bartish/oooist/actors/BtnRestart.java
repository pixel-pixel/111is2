package com.bartish.oooist.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bartish.oooist.Main;
import com.bartish.oooist.utils.Executer;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class BtnRestart extends Actor {
    private Texture restart = new Texture(Gdx.files.internal("btn_restart.png"));
    private Texture ok = new Texture(Gdx.files.internal("btn_ok.png"));
    private float startX, startY;
    private boolean active = false;
    private Executer onActiveExecuter, onDisactiveExecuter, onOkClickExecuter;
    private Vector3 vector3 = new Vector3();

    public BtnRestart(int x, int y){
        startX = x;
        startY = y;
        setX(68);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        vector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        Main.viewport.getCamera().unproject(vector3);

        if(active){
             if(Gdx.input.justTouched()){
                 if(vector3.x >= startX + getX() + 48 &&
                        vector3.x <= startX + getX() + 112 &&
                        vector3.y >= startY + getY() - 114 &&
                        vector3.y <= startY + getY() - 50){
                     onOkClickExecuter.execute();
                 }else{
                     addAction(moveTo(0, 0, 0.3f, Interpolation.pow2));
                     if(onDisactiveExecuter != null) onDisactiveExecuter.execute();
                     active = false;
                 }
             }
        }else{
            if(Gdx.input.justTouched()){
                if(vector3.x >= startX - 68 &&
                        vector3.x <= startX &&
                        vector3.y >= startY - 68 &&
                        vector3.y <= startY){
                    addAction(moveTo(-132, 0, 0.3f, Interpolation.pow2));
                    if(onActiveExecuter != null) onActiveExecuter.execute();
                    active = true;
                }
            }
        }


        batch.setColor(1, 1, 1, 1);
        batch.draw(restart, startX + getX() - 68, startY + getY() - 200);
        batch.draw(ok, startX + getX() + 48, startY + getY() - 114);
    }

    public void addExecuter(Executer onActive, Executer onDisactive, Executer onClick){
        onActiveExecuter = onActive;
        onDisactiveExecuter = onDisactive;
        onOkClickExecuter = onClick;
    }
    public boolean isActive(){
        return active;
    }

    @Override
    public void setPosition(float x, float y) {
        startX = x;
        startY = y;
    }
}
