package com.example.breakthrough.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import static java.lang.Double.NaN;


public class Obstacles extends GameObject{

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

    double[] collisionData(float posX, float posY, double moveX, double moveY, float radius){

        double[] collision=new double [] {moveX,moveY};
        double[] cornerDistance=new double[2];
        cornerDistance[0] = Double.MAX_VALUE;
        cornerDistance[1] = Double.MAX_VALUE;

        for(int i=0; i < map.length; i++) {
            if(map[i].y==map[(i + 1) % map.length].y) {//horitzontal

                double distanceNew = Math.abs(map[i].y - (posY + moveY))-radius;
                if((distanceNew<0) && zwischen((posX + moveX),new int[]{(map[(i + 1) % map.length]).x , map[i].x})) {
                    double distance=distancePointStraight(radius,'y',i,posY);
                    collision[1] = minDistance(distance,collision[1]);
                }
            } else {//vertikal

                double distanceNew = Math.abs(map[i].x - (posX + moveX))-radius;
                if((distanceNew<0) && zwischen((posY + moveY) , new int[]{(map[(i + 1) % map.length]).y , map[i].y})){
                    double distance=distancePointStraight(radius,'x',i,posX);
                    collision[0] = minDistance(distance,collision[0]);
                }
            }



            if(distancePoints(map[i].x,map[i].y,posX+ moveX,posY+ moveY)<radius) {
                //double laenge =  Math.sqrt(Math.pow(map[i].x - (posX+ moveX), 2) + Math.pow(map[i].y - (posY+ moveY), 2));
                collision[0] =0;
                collision[1] =0;
            }


        }


        collision[0] =minDistance(cornerDistance[0],collision[0]);
        collision[1] =minDistance(cornerDistance[1],collision[1]);

        return collision;
    }

    public Point distens(Point posPlayer, float richtung) {

        Point tmpPoint = new Point(0,0);
        double distance = 100000;
        for(int i =0; i<map.length;i++) {

            if(map[i].y==map[(i + 1) % map.length].y) {//horitzontal
/*
                double ankathete = map[i].y - posPlayer.y;
                double gegenkathete  = Math.tan(richtung)*ankathete;
                double tempY = Math.tan(richtung);

                if( tempY * gegenkathete < 0  || ankathete < 0 ) {
                    if(zwischen(gegenkathete + posPlayer.x,new int [] {map[i].x,map[(i + 1) % map.length].x})) {
                        double distanceTemp = pythagoras(ankathete,gegenkathete);
                        if (distanceTemp < distance) {
                            distance=distanceTemp;
                            tmpPoint.x =(int)Math.round(ankathete);
                            tmpPoint.y =(int)Math.round(gegenkathete);
                        }
                    }
                }
                */

            } else {

                double ankathete = map[i].x - posPlayer.x;
                double gegenkathete  = Math.tan(richtung+Math.PI/2)*ankathete;

                double tempX = Math.tan(richtung+Math.PI/2);

                if(tempX*gegenkathete<0  || ankathete<0 ){
                    if(zwischen(gegenkathete + posPlayer.y,new int [] {map[i].y,map[(i + 1) % map.length].y})) {
                        double distanceTemp = pythagoras(ankathete,gegenkathete);
                         if (distanceTemp < distance) {
                            distance=distanceTemp;
                            tmpPoint.x =(int)Math.round(ankathete);
                            tmpPoint.y =(int)Math.round(gegenkathete);

                       }
                    }
                }
            }
        }
        return tmpPoint;
    }


    private double pythagoras(double a , double b){
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }


    private double distancePoints(double x1, double y1, double x2, double y2) {

        double ac = Math.abs(y2 - y1);
        double cb = Math.abs(x2 - x1);

        return Math.hypot(ac, cb);
    }

    private double minDistance(double a , double b){
        if(Math.abs(a)<Math.abs(b)){
            return a;
        }else {
            return b;
        }
    }


}
