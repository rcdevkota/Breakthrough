package com.example.breakthrough.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.security.PublicKey;


public class Guard extends Character {

    private static final double MAX_SPEED = 2;
    private float orientation;
    private float radius;
    private Paint paint;
    private Point[] movePattern;
    private int i=0;
    private Obstacles map[];
    private Point[] fieldOfView;

    public Guard(Context context, float orientation,float radius, Point[] movePattern, Obstacles[] map) {
        super(movePattern[0]);
        this.map = map;
        this.orientation = orientation;
        this.radius = radius;
        this.movePattern = movePattern;
        paint = new Paint();
        paint.setColor(Color.RED);

        fieldOfView = new Point[1];
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(pos.x *canvas.getHeight()/1080, pos.y*canvas.getHeight()/1080, radius*canvas.getHeight()/1080, paint);
        if(fieldOfView[0]!=null){
            canvas.drawLine((fieldOfView[0].x+pos.x) *canvas.getHeight()/1080, (fieldOfView[0].y+pos.y) *canvas.getHeight()/1080, pos.x *canvas.getHeight()/1080, pos.y *canvas.getHeight()/1080,paint);

        }
           }

    @Override
    public void setPosition() {

    }

    public void update() {

        double laenge =  Math.sqrt(Math.pow(movePattern[i%movePattern.length].x - pos.x, 2) + Math.pow(movePattern[i%movePattern.length].y - pos.y, 2));
        getFieldOfView ();
        if(laenge<MAX_SPEED) {
            i++;
        } else {
            pos.x += Math.round((MAX_SPEED * (movePattern[i%movePattern.length].x - pos.x)) / laenge);
            pos.y += Math.round((MAX_SPEED * (movePattern[i%movePattern.length].y - pos.y)) / laenge);
        }
    }

    public boolean catcht(Point playerPos, float radius){
        for(int i =0; i< fieldOfView.length; i++){
            if(getDistance(playerPos,pos,fieldOfView[i])<radius){
                return true;
            }
        }
        return false;
    }

    private void getFieldOfView (){
        for(int i = 0; i < fieldOfView.length;i++){
            double minLegth = 10000;
            for(int j = 0; j < map.length;j++) {
                Point tempPoints = map[j].distens(pos,2);
                double tempMinLegth = pythagoras(tempPoints.x,tempPoints.y);


                if (tempMinLegth < minLegth && (tempPoints.x!=0 || tempPoints.y!=0)) {
                    fieldOfView[i] = tempPoints;
                    minLegth = tempMinLegth;
                }


            }
        }
    }

    private double pythagoras(double a , double b){
       return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    public static double getDistance(Point a, Point r1, Point r2) {
        if(r1.x==r2.x&&r1.y==r2.y){
            return Double.NaN;	//oder Distance von r1 bzw r2 zu a
        }

        double m1= (r2.y -r1.y)/(r2.x -r1.x);
        double b1 = r1.y-(m1*r1.x);

        if(m1==0.0){
            return Math.abs(b1-a.y);
        }

        if(Double.isInfinite(m1)){
            return Math.abs(r1.x-a.x);
        }

        double m2=-1.0/m1;
        double b2 = a.y-(m2*a.x);

        double xs=(b2-b1)/(m1-m2);
        double ys=m1*xs+b1;

        double c1=a.x-xs;
        double c2=a.y-ys;

        double distance = Math.sqrt(c1*c1+c2*c2);
        return distance;
    }

}