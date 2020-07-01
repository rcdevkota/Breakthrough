package com.example.breakthrough.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.breakthrough.Joystick;
import com.example.breakthrough.R;

public class Player extends Character {
    private static final double MAX_SPEED = 20;
    private final Joystick joystick;

    private float radius;
    private Paint paint;
    private Obstacles[] obstacles;

    public Player(Obstacles[] obstacles,Context context,Joystick joystick, float posX, float posY, float radius){
        super(posX,posY);
        this.obstacles = obstacles;
        this.joystick = joystick;
        this.posX = posX;
        this.posX = posY;
        this.radius = radius;

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {

        canvas.drawCircle(posX*canvas.getHeight()/1080, posY*canvas.getHeight()/1080, radius*canvas.getHeight()/1080, paint);
    }

    @Override
    public void setPosition() {

    }

    public void update() {
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;
        double[] col =obstacles[0].wallCollision(posX, posY, velocityX, velocityY,radius);

            posY += col[1];

            posX += col[0];


    }

    public void setPosition(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }


}
