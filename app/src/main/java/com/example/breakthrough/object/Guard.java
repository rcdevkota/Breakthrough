package com.example.breakthrough.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Guard extends Character {

    private static final double MAX_SPEED = 2;
    private float orientation;
    private float radius;
    private Paint paint;
    private Point[] movePattern;
    private int i=0;
    private Obstacles map[];
    private Point[] fieldOfView;

    public Guard(Context context, float orientation,float radius, Point[] movePattern, Obstacles[] map) {
        super(movePattern[0]);
        this.map = map;
        this.orientation = orientation;
        this.radius = radius;
        this.movePattern = movePattern;
        paint = new Paint();
        paint.setColor(Color.RED);

        fieldOfView = new Point[1];
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(pos.x *canvas.getHeight()/1080, pos.y*canvas.getHeight()/1080, radius*canvas.getHeight()/1080, paint);
    }

    @Override
    public void setPosition() {

    }

    public void update() {

        double laenge =  Math.sqrt(Math.pow(movePattern[i%movePattern.length].x - pos.x, 2) + Math.pow(movePattern[i%movePattern.length].y - pos.y, 2));

        if(laenge<MAX_SPEED) {
            i++;
        } else {
            pos.x += Math.round((MAX_SPEED * (movePattern[i%movePattern.length].x - pos.x)) / laenge);
            pos.y += Math.round((MAX_SPEED * (movePattern[i%movePattern.length].y - pos.y)) / laenge);
        }
    }
/*
    public Point[] visible() {
        Point a =map[1].map[0];
    }
*/
}