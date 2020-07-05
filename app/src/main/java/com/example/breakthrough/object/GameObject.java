package com.example.breakthrough.object;

import android.graphics.Canvas;
import android.graphics.Point;

public abstract class GameObject {

    protected Point[]map;

    abstract  void draw(Canvas canvas);

    protected boolean zwischen(double punkt, int[] linie){

        return (linie[0]< punkt) == (linie[1]>punkt);
    }

    protected float scalePoint(float in ,Canvas canvas){

        return in*canvas.getHeight()/1080;
    }

    protected float distancePointStraight(float radius, int xOrY, int id, float pos ) {
        if(xOrY=='x'){
            if(map[id].x - pos < 0){
                return map[id].x - pos + radius;
            }else {
                return map[id].x - pos - radius;
            }
        }else {
            if(map[id].y - pos < 0){
                return map[id].y - pos + radius;
            }else {
                return map[id].y - pos - radius;
            }
        }
    }
}
