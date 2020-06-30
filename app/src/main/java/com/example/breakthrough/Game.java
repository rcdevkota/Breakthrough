package com.example.breakthrough;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.core.content.ContextCompat;

import com.example.breakthrough.object.Guard;
import com.example.breakthrough.object.Player;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    private GameLoop gameLoop;
    private float [][] movePattern;
    private Guard[] guards;

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);
        joystick = new Joystick(150,400, 70,40);
        player =new Player(getContext(), joystick,500,500,30);

        movePattern = new float[3][2];
        movePattern[0][0]= 10;
        movePattern[0][1]= 10;
        movePattern[1][0]= 0;
        movePattern[1][1]= 500;
        movePattern[2][0]= 300;
        movePattern[2][1]= 500;

        guards = new Guard[2];
        guards[0] =new Guard(getContext(),0,30 ,movePattern);

        movePattern = new float[2][2];
        movePattern[0][0]= 200;
        movePattern[0][1]= 90;
        movePattern[1][0]= 330;
        movePattern[1][1]= 0;
        guards[1] =new Guard(getContext(),0,30 ,movePattern);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(joystick.isPressed((float)event.getX(), (float)event.getY())){
                    joystick.setIsPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()){
                    joystick.setActuator((float)event.getX(), (float)event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {


        Log.d("Game.java", "surfaceCreated()");


        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            gameLoop = new GameLoop(this, surfaceHolder);
        }
        gameLoop.startLoop();
    }
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drowUPS(canvas);
        drowFPS(canvas);
        joystick.drow(canvas);
        player.draw(canvas);
        for(int i = 0; i <guards.length; i++){
            guards[i].draw(canvas);
        }
    }










    public void drowUPS(Canvas canvas){
        String averagUPS = Double.toString(gameLoop.getAveregeUPS());
        Paint paint = new Paint();

        int color = ContextCompat.getColor(getContext(), R.color.debugge);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averagUPS, 100,200,paint);
    }

    public void drowFPS(Canvas canvas){
        String averagFPS = Double.toString(gameLoop.getAveregeFPS());
        Paint paint = new Paint();

        int color = ContextCompat.getColor(getContext(), R.color.debugge);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averagFPS, 100,300,paint);
    }

    public void update() {
        joystick.update();
        player.update();
        for(int i = 0; i <guards.length; i++) {
            guards[i].update();
        }
    }

    public void pause() {
        gameLoop.stopLoop();
    }
}
