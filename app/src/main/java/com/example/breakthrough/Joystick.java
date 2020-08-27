package com.example.breakthrough;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Joystick {
    private Paint  innerCirclePaint;
    private Paint outerCirclePaint;
    private Point outerCircleCenterPos;
    private Point innerCircleCenterPos;
    private int outerCircleRadius;
    private int innerCircleRadius;
    private double joystickCenterToTouchDistance;
    private boolean isPressed;
    private  double actuatorX;
    private  double actuatorY;
    private int canvasHeight;

    public Joystick(Point center,int outerCircleRadius,int innerCircleRadius, int canvasHeight){


        outerCircleCenterPos = new Point(center.x,center.y);
        innerCircleCenterPos = new Point(center.x,center.y);
        this.canvasHeight =canvasHeight;
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.RED);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    public double getActuatorX(){
        return  actuatorX;
    }

    public double getActuatorY(){
        return  actuatorY;
    }

    public void update() {
        updateInnerCircleCenterPos();
    }

    private void updateInnerCircleCenterPos() {
        innerCircleCenterPos.x = (int) (outerCircleCenterPos.x + actuatorX*outerCircleRadius);
        innerCircleCenterPos.y = (int) (outerCircleCenterPos.y + actuatorY*outerCircleRadius);
    }

    public void drow(Canvas canvas) {

        int scal=canvas.getHeight()/1080;
        canvasHeight =canvas.getHeight();
        canvas.drawCircle(outerCircleCenterPos.x*scal,outerCircleCenterPos.y*scal,outerCircleRadius*scal,outerCirclePaint);
        canvas.drawCircle(innerCircleCenterPos.x*scal,innerCircleCenterPos.y*scal,innerCircleRadius*scal,innerCirclePaint);
    }

    public boolean isPressed(float touchPosX, float touchPosY) {
        joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(outerCircleCenterPos.x*canvasHeight/1080 - touchPosX , 2) +
                Math.pow(outerCircleCenterPos.y*canvasHeight/1080 - touchPosY , 2)
        );
        return joystickCenterToTouchDistance < outerCircleRadius;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed(){
        return isPressed;
    }

    public void setActuator(float touchPosX, float touchPosY) {

        double deltaX = touchPosX- outerCircleCenterPos.x*canvasHeight/1080;
        double deltaY = touchPosY- outerCircleCenterPos.y*canvasHeight/1080;
        double deltaDistance = Math.sqrt(Math.pow(deltaX , 2) + Math.pow(deltaY , 2));
        if(deltaDistance < outerCircleRadius){
            actuatorX = deltaX/outerCircleRadius*canvasHeight/1080;
            actuatorY = deltaY/outerCircleRadius*canvasHeight/1080;
        }else{
            actuatorX= deltaX/deltaDistance;
            actuatorY= deltaY/deltaDistance;
        }
    }

    public void resetActuator() {
        actuatorX =0.0;
        actuatorY =0.0;
    }
}
