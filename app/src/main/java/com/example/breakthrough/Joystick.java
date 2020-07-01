package com.example.breakthrough;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {
    private Paint  innerCirclePaint;
    private Paint outerCirclePaint;
    private int outerCircleCenterPosX;
    private int outerCircleCenterPosY;
    private int innerCircleCenterPosX;
    private int innerCircleCenterPosY;
    private int outerCircleRadius;
    private int innerCircleRadius;
    private double joystickCenterToTouchDistance;
    private boolean isPressed;
    private  double actuatorX;
    private  double actuatorY;
    private int canvasHeight;

    public Joystick(int centerX, int centerY,int outerCircleRadius,int innerCircleRadius, int canvasHeight){

        outerCircleCenterPosX = centerX;
        outerCircleCenterPosY = centerY;
        innerCircleCenterPosX = centerX;
        innerCircleCenterPosY = centerY;
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
        innerCircleCenterPosX = (int) (outerCircleCenterPosX + actuatorX*outerCircleRadius);
        innerCircleCenterPosY = (int) (outerCircleCenterPosY + actuatorY*outerCircleRadius);
    }

    public void drow(Canvas canvas) {
        canvasHeight =canvas.getHeight();
        canvas.drawCircle(outerCircleCenterPosX*canvas.getHeight()/1080,outerCircleCenterPosY*canvas.getHeight()/1080,outerCircleRadius*canvas.getHeight()/1080,outerCirclePaint);
        canvas.drawCircle(innerCircleCenterPosX*canvas.getHeight()/1080,innerCircleCenterPosY*canvas.getHeight()/1080,innerCircleRadius*canvas.getHeight()/1080,innerCirclePaint);
    }

    public boolean isPressed(float touchPosX, float touchPosY) {
        joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(outerCircleCenterPosX*canvasHeight/1080 - touchPosX , 2) +
                Math.pow(outerCircleCenterPosY*canvasHeight/1080 - touchPosY , 2)
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
        double deltaX = touchPosX- outerCircleCenterPosX*canvasHeight/1080;
        double deltaY = touchPosY- outerCircleCenterPosY*canvasHeight/1080;
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
