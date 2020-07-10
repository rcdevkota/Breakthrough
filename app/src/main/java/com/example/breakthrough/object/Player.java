package com.example.breakthrough.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import androidx.core.content.ContextCompat;

import com.example.breakthrough.Game;
import com.example.breakthrough.GameLoop;
import com.example.breakthrough.Joystick;
import com.example.breakthrough.R;

public class Player extends Character {
    private static final double MAX_SPEED = 20;
    private final Joystick joystick;

    private float radius;
    private Paint paint;
    private Obstacles[] obstacles;
    private Target target;

    public Player(Obstacles[] obstacles, Context context, Joystick joystick,  Point pos, float radius, Target target){
        super(pos);
        this.target= target;
        this.obstacles = obstacles;
        this.joystick = joystick;
        this.pos = pos;
        this.radius = radius;

        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }


    public Point getPos(){
        return pos;
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

        double[] col ={velocityX,velocityY};
        paint.setColor(Color.GRAY);
        if(target.wine(pos.x, pos.y,radius)){
            paint.setColor(Color.GREEN);
        }

        for(int i =0; i < obstacles.length;i++) {
            double[] newMoveobstacles =  obstacles[i].collisionData(pos.x, pos.y, velocityX, velocityY,radius);

            col[0] = minDistance(newMoveobstacles[0], col[0]);
            col[1] = minDistance(newMoveobstacles[1], col[1]);
        }
            pos.y += col[1];
            pos.x += col[0];


    }

    private double minDistance(double a , double b){

        if(Math.abs(a)<Math.abs(b)){
            return a;
        }else {
            return b;
        }
    }

    public void setPosition(Point pos) {
        this.pos = new Point (pos);
    }

}
