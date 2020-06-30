package com.example.breakthrough.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.breakthrough.Joystick;
import com.example.breakthrough.R;

public class Player extends Character{
    private static final double MAX_SPEED = 10;
    private final Joystick joystick;

    private float radius;
    private Paint paint;


    public Player(Context context,Joystick joystick, float posX, float posY, float radius){
        super(posX,posY);
        this.joystick = joystick;
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

    @Override
    public void setPosition() {

    }

    public void update() {
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
