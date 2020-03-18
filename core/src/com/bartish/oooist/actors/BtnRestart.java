package com.bartish.oooist.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.bartish.oooist.Main;
import com.bartish.oooist.utils.Executer;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class BtnRestart extends Actor {
    private Texture restart = new Texture(Gdx.files.internal("btn_restart.png"));
    private Texture ok = new Texture(Gdx.files.internal("btn_ok.png"));

    private float startX, startY;
    private boolean active = false;
    private Executer showExecuter, hideExecuter, clickOkExecuter;
    private Vector3 vector3 = new Vector3();

    public BtnRestart(int x, int y){
        startX = x;
        startY = y;
        setX(68);
        setTouchable(Touchable.enabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        vector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        Main.viewport.getCamera().unproject(vector3);

        if(Gdx.input.justTouched()){
            if (active) {
                if(vector3.x >= startX + getX() + 48 &&
                        vector3.x <= startX + getX() + 112 &&
                        vector3.y >= startY + getY() - 114 &&
                        vector3.y <= startY + getY() - 50){
                    clickOk();
                }else if(isTouchable()){
                    hide();
                }
            } else if(isTouchable()){
                if(vector3.x >= startX - 68 &&
                        vector3.x <= startX &&
                        vector3.y >= startY - 68 &&
                        vector3.y <= startY){
                    show();
                }
            }
        }

        batch.setColor(1, 1, 1, 1);
        batch.draw(restart, startX + getX() - 68, startY + getY() - 200);
        batch.draw(ok, startX + getX() + 48, startY + getY() - 114);
    }

    public void show(){
        addAction(moveTo(-132, 0, 0.3f, Interpolation.pow2));
        if(showExecuter != null) showExecuter.execute();
        active = true;
    }

    public void hide(){
        addAction(moveTo(0, 0, 0.3f, Interpolation.pow2));
        if(hideExecuter != null) hideExecuter.execute();
        active = false;
    }

    public void clickOk(){
        clickOkExecuter.execute();
    }

    public void addExecuter(Executer onActive, Executer onDisactive, Executer onClick){
        showExecuter = onActive;
        hideExecuter = onDisactive;
        clickOkExecuter = onClick;
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
