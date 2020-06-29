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

    public Joystick(int centerX, int centerY,int outerCircleRadius,int innerCircleRadius){
        outerCircleCenterPosX = centerX;
        outerCircleCenterPosY = centerY;
        innerCircleCenterPosX = centerX;
        innerCircleCenterPosY = centerY;

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
        canvas.drawCircle(outerCircleCenterPosX,outerCircleCenterPosY,outerCircleRadius,outerCirclePaint);
        canvas.drawCircle(innerCircleCenterPosX,innerCircleCenterPosY,innerCircleRadius,innerCirclePaint);
    }

    public boolean isPressed(float touchPosX, float touchPosY) {
        joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(outerCircleCenterPosX - touchPosX , 2) +
                Math.pow(outerCircleCenterPosY - touchPosY , 2)
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
        double deltaX = touchPosX- outerCircleCenterPosX;
        double deltaY = touchPosY- outerCircleCenterPosY;
        double deltaDistance = Math.sqrt(Math.pow(deltaX , 2) + Math.pow(deltaY , 2));
        if(deltaDistance < outerCircleRadius){
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
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
