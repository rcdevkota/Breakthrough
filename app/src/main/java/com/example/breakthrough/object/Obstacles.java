package com.example.breakthrough.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;


public class Obstacles {
    private Point[]map;


    public Obstacles(Point[] map){
      this.map = map;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
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

    private float scalePoint(float in ,Canvas canvas){

        return in*canvas.getHeight()/1080;
    }

    public double[] wallCollision(float posX, float posY, double moveX, double moveY,float radius){

        double[] collision=new double [] {moveX,moveY};

        double[] cornerDistance=new double[2];
        cornerDistance[0] = Double.MAX_VALUE;
        cornerDistance[1] = Double.MAX_VALUE;
//horitzontal
        for(int i=0; i < map.length; i++) {



            if(map[i].y==map[(i + 1) % map.length].y) {
                double distanceNew = Math.abs(map[i].y - (posY + moveY))-radius;
                if((distanceNew<0) && ((map[(i + 1) % map.length].x < posX + moveX) == (map[i].x > posX + moveX))){

                    double distance=(map[i].y - posY<0? map[i].y-posY+radius: map[i].y-posY-radius);

                    if(Math.abs(distance)<Math.abs(collision[1]) ){
                        collision[1] = distance;
                    }
                }
                if(cornerColison(i, 'x', posX, moveX)){
                    cornerDistance[1] = minDistance(distancePointStraight(radius, 1, i,  posY ), cornerDistance[1]);
                    System.out.println((cornerDistance[1] + " ; " + ((map[i].y - posY) < 0)));
                }

            } else {
//vertikal
                double distanceNew = Math.abs(map[i].x - (posX + moveX))-radius;
                if((distanceNew<0) && ((map[(i + 1) % map.length].y < posY + moveY) == (map[i].y > posY + moveY))){
                    double distance=(map[i].x - posX<0? map[i].x-posX+radius: map[i].x-posX-radius);
                    if(Math.abs(distance)<Math.abs(collision[0])){
                        collision[0] = distance;
                    }
                }
                if(cornerColison(i, 'y', posY, moveY)){
                    cornerDistance[0] = minDistance(distancePointStraight(radius, 0, i,  posX ), cornerDistance[0]);
                }
            }
        }

            collision[0] =minDistance(cornerDistance[0],collision[0]);

            collision[1] =minDistance(cornerDistance[1],collision[1]);

        return collision;
    }

    private boolean cornerColison(int id, char xOrY, float pos, double move){
        if(xOrY=='x'){
            return Math.abs(map[id].x - (pos + move)) < 30 || Math.abs(map[(id + 1) % map.length].x - (pos + move)) < 30;
        }else {
            return Math.abs(map[id].y - (pos + move)) < 30 || Math.abs(map[(id + 1) % map.length].y - (pos + move)) < 30;
        }
    }

    private float distancePointStraight(float radius, int xOrY, int id, float pos ) {
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


    private double minDistance(double a , double b){

        if(Math.abs(a)<Math.abs(b)){
            return a;
        }else {
            return b;
        }
    }
}
