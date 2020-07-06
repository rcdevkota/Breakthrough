package com.example.breakthrough.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Obstacles {
    private float[][]map;


    public Obstacles(float[][] map){
      this.map = map;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        for(int i=0; i<map[0].length;i++){
            canvas.drawLine(scale(map[0][i],canvas),scale(map[1][i],canvas),scale(map[0][(i+1)%map[0].length],canvas),scale(map[1][(i+1)%map[0].length],canvas),paint);
        }



    }

    private float  scale(float in ,Canvas canvas){
        return in*canvas.getHeight()/1080;
    }

    public double[] wallCollision(float posX, float posY, double moveX, double moveY,float radius){

        double[] collision=new double [] {moveX,moveY};

        double[] cornerDistance=new double[2];
        cornerDistance[0] = Double.MAX_VALUE;
        cornerDistance[1] = Double.MAX_VALUE;
//horitzontal
        for(int i=0; i < map[0].length; i++) {



            if((map[1][i]==map[1][(i + 1) % map[0].length])) {
                double distanceNew = Math.abs(map[1][i] - (posY + moveY))-radius;
                if((distanceNew<0) && ((map[0][(i + 1) % map[0].length] < posX + moveX) == (map[0][i] > posX + moveX))){

                    double distance=(map[1][i] - posY<0? map[1][i]-posY+radius: map[1][i]-posY-radius);

                    if(Math.abs(distance)<Math.abs(collision[1]) ){
                        collision[1] = distance;
                    }
                }
                if(cornerColison(i, 0, posX, moveX)){
                    cornerDistance[1] = minDistance(distancePointStraight(radius, 1, i,  posY ), cornerDistance[1]);
                    System.out.println((cornerDistance[1] + " ; " + ((map[1][i] - posY) < 0)));
                }

            } else {
//vertikal
                double distanceNew = Math.abs(map[0][i] - (posX + moveX))-radius;
                if((distanceNew<0) && ((map[1][(i + 1) % map[0].length] < posY + moveY) == (map[1][i] > posY + moveY))){
                    double distance=(map[0][i] - posX<0? map[0][i]-posX+radius: map[0][i]-posX-radius);
                    if(Math.abs(distance)<Math.abs(collision[0])){
                        collision[0] = distance;
                    }
                }
                if(cornerColison(i, 1, posY, moveY)){
                    cornerDistance[0] = minDistance(distancePointStraight(radius, 0, i,  posX ), cornerDistance[0]);
                }
            }
        }



            collision[0] =minDistance(cornerDistance[0],collision[0]);

            collision[1] =minDistance(cornerDistance[1],collision[1]);

        return collision;
    }

    private boolean cornerColison(int id, int xOrY, float pos, double move){
        return Math.abs(map[xOrY][id] - (pos + move)) < 30 || Math.abs(map[xOrY][(id + 1) % map[0].length] - (pos + move)) < 30;
    }

    private float distancePointStraight(float radius, int xOrY, int id, float pos ) {
        if(map[xOrY][id] - pos < 0){
            return map[xOrY][id] - pos + radius;
        }else {
            return map[xOrY][id] - pos - radius;
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
