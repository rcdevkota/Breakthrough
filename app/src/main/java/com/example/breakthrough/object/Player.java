package com.example.breakthrough.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import androidx.core.content.ContextCompat;

import com.example.breakthrough.Joystick;
import com.example.breakthrough.R;

public class Player extends Character {
    private static final double MAX_SPEED = 20;
    private final Joystick joystick;

    private float radius;
    private Paint paint;
    private Obstacles[] obstacles;

    public Player(Obstacles[] obstacles, Context context, Joystick joystick,  Point pos, float radius){
        super(pos);
        this.obstacles = obstacles;
        this.joystick = joystick;
        this.pos = pos;
        this.radius = radius;

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {

        canvas.drawCircle(pos.x*canvas.getHeight()/1080, pos.y*canvas.getHeight()/1080, radius*canvas.getHeight()/1080, paint);
    }

    @Override
    public void setPosition() {

    }

    public void update() {
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;
        double[] col =obstacles[0].wallCollision(pos.x, pos.y, velocityX, velocityY,radius);

            pos.y += col[1];
            pos.x += col[0];
    }

    public void setPosition(Point pos) {
        this.pos = new Point (pos.x,pos.y);
    }

}
