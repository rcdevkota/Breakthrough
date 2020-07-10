package com.example.breakthrough.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Target  extends GameObject{

    public Target(Point[] map){
        this.map = map;
    }

    public boolean wine(float posX, float posY, float radius) {
        boolean colidirt = false;
        for(int i = 0; i < map.length; i++) {
            if(zwischen(posY,new int[]{(map[(i + 1) % map.length]).y , map[i].y})){
                if((distancePointStraight( radius,'x',i,posX)) < 0){
                    return true;
                }
            }

        }
        return colidirt;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        for(int i=0; i<map.length;i++){
            canvas.drawLine(
                    scalePoint(map[i].x,canvas),
                    scalePoint(map[i].y,canvas),
                    scalePoint(map[(i+1)%map.length].x,canvas),
                    scalePoint(map[(i+1)%map.length].y,canvas),
                    paint);
        }
    }

    private double minDistance(double a , double b) {
        if(Math.abs(a)<Math.abs(b)) {
            return a;
        } else {
            return b;
        }
    }
}
