package com.bartish.oooist.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Record extends Score {
    private Texture record = new Texture(Gdx.files.internal("record.png"));
    public Record(int s) {
        super(s);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor());
        batch.draw(record, getX() - dynamicWidth/ 2, getY() - record.getHeight() / 2);
        backdrop += record.getWidth();
        super.draw(batch, parentAlpha);
    }

    @Override
    protected int updateDynamicWidth() {
        dynamicWidth = super.updateDynamicWidth() + record.getWidth();
        return dynamicWidth;
    }
}

