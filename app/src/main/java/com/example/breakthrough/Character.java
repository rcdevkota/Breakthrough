package com.example.breakthrough;

import android.graphics.Canvas;

public abstract class Character {

    protected float posX;
    protected float posY;

    protected double velocityX;
    protected double velocityY;

    public Character (float posX, float posY){

        this.posX =posX;
        this.posY =posY;
    }

    public abstract void draw(Canvas canvas);
    public abstract void setPosition();

}
