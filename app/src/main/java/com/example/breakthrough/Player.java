package com.example.breakthrough;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Player {
    private static final double MAX_SPEED = 10;
    private float posX;
    private float posY;
    private float radius;
    private Paint paint;
    private double velocityX;
    private double velocityY;

    public Player(Context context, float posX, float posY, float radius){
        this.posX = posX;
        this.posX = posY;
        this.radius = radius;

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(posX, posY, radius, paint);
    }

    public void update(Joystick joystick) {
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;
        posX += velocityX;
        posY += velocityY;
    }

    public void setPosition(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }
}
