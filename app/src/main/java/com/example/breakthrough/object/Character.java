package com.example.breakthrough.object;

import android.graphics.Canvas;
import android.graphics.Point;

public abstract class Character {

    protected  double ratOrientirung;
    protected Point pos;
    protected double velocityX;
    protected double velocityY;

    public Character(Point pos){
        this.pos = new Point(pos);
    }

    public abstract void draw(Canvas canvas);
    public abstract void setPosition();

}
