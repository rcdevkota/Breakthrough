package com.example.breakthrough.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Guard extends Character{

    private static final double MAX_SPEED = 2;
    private float orientation;
    private float radius;
    private Paint paint;
    private float[][] movePattern;
    private int i=0;

    public Guard(Context context, float orientation,float radius, float[][] movePattern) {
        super(movePattern[0][0], movePattern[0][1]);
        this.orientation = orientation;
        this.radius = radius;
        this.movePattern = movePattern;
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(posX, posY, radius, paint);
    }

    @Override
    public void setPosition() {

    }

    public void update() {


            double laenge =  Math.sqrt(Math.pow(movePattern[i%movePattern.length][0] - posX, 2) + Math.pow(movePattern[i%movePattern.length][1] - posY, 2));
            if(!(laenge<MAX_SPEED) ) {
                posX += (float) (MAX_SPEED * (movePattern[i%movePattern.length][0] - posX) / laenge);
                posY += (float) (MAX_SPEED * (movePattern[i%movePattern.length][1] - posY) / laenge );
            } else {
                i++;
            }



    }
}
