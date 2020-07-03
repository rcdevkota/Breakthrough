package com.example.breakthrough;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.core.content.ContextCompat;

import com.example.breakthrough.object.Guard;
import com.example.breakthrough.object.Obstacles;
import com.example.breakthrough.object.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.Scanner;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    private GameLoop gameLoop;
    private Point [] movePattern;
    private Guard[] guards;
    private Obstacles[] obstacles;


    public Game(Context context , int level) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);


        joystick = new Joystick(new Point(1900,1080-(250)), 150,40, super.getHeight());



        //map

        Point map[];
        map = new Point[] {
                new Point(100,100),
                new Point(100,1000),
                new Point(500,1000),
                new Point(500,100)
        };
        obstacles = new Obstacles[1];
        obstacles[0] =new Obstacles(map);


        player =new Player(obstacles ,getContext(), joystick,new Point(550,550),30);
        //Guard configurantin
        ladeDatei(context,0);

        movePattern = new Point[3];
        movePattern[0]= new Point(10,10);
        movePattern[1] = new Point(0,500);
        movePattern[2]= new Point(300,500);

        guards = new Guard[2];
        guards[0] =new Guard(getContext(),0,30 ,movePattern);

        movePattern = new Point[2];
        movePattern[0]= new Point(200,90);
        movePattern[1]= new Point(330,0);
        guards[1] =new Guard(getContext(),0,30 ,movePattern);

        setFocusable(true);
    }

    private void ladeDatei(Context context ,int level) {

        String datName = "level"+level+".txt";
        String string = "";
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(datName), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                System.out.println(mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
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
       // drowUPS(canvas);
       // drowFPS(canvas);
        joystick.drow(canvas);
        player.draw(canvas);
        for(int i = 0; i <guards.length; i++){
            guards[i].draw(canvas);
        }
        for(int i = 0; i <obstacles.length; i++){
            obstacles[i].draw(canvas);
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