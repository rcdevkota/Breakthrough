package com.example.breakthrough.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;


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

    boolean[] wallCollision(float posX, float posY, double moveX, double moveY,float radius){

        boolean[] collision=new boolean[2];



        for(int i=0; i < map[0].length; i++) {

            if((map[1][i]==map[1][(i + 1) % map[0].length])) {
                collision[0] |= (Math.abs(map[1][i] - (posY + moveY))<radius) && ((map[0][(i + 1) % map[0].length] < posX + moveX) == (map[0][i] > posX + moveX));
                collision[0] |= Math.abs(map[0][i] - (posX+moveX))<radius && Math.abs(map[1][i] - (posY+moveY))<radius;
                collision[0] |= Math.abs(map[0][(i + 1) % map[0].length] - (posX+moveX))<radius && Math.abs(map[1][(i + 1) % map[0].length] - (posY+moveY))<radius;
            }else {
                collision[1] |= (Math.abs(map[0][i] - (posX + moveX))<radius) && ((map[1][(i + 1) % map[0].length] < posY + moveY) == (map[1][i] > posY + moveY));
                collision[1] |= Math.abs(map[0][i] - (posX+moveX))<radius && Math.abs(map[1][i] - (posY+moveY))<radius;
                collision[1] |= Math.abs(map[0][(i + 1) % map[0].length] - (posX+moveX))<30 && Math.abs(map[1][(i + 1) % map[0].length] - (posY+moveY))<radius;
            }
        }
        return collision;
    }
}
