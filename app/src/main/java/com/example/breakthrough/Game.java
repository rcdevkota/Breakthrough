package com.example.breakthrough;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
import com.example.breakthrough.object.Target;

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
    private Guard[] guards;
    private Obstacles[] obstacles;
    private Target target;


    private Boolean erster = true;
    private Bitmap hintergund;


    public Game(Context context , int level) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        joystick = new Joystick(new Point(1700,1080-(650)), 150,40, super.getHeight());

        Point[][][] data = ladeDatei(context,0);

        obstacles = new Obstacles[data[1].length];
        for(int i=0; i < data[1].length;i++) {
            obstacles[i] = new Obstacles (data[1][i]);
        }

        target = new Target(data[2][0]);

        player =new Player(obstacles ,getContext(), joystick,data[0][0][0],30, target);

        //Guard configurantin

        guards = new Guard[data[3].length];
        for(int i=0; i < data[3].length;i++) {
            guards[i] =new Guard(getContext(),0,25 ,data[3][i],obstacles);
        }
        setFocusable(true);
    }

    private Point[][][] ladeDatei(Context context ,int level) {
        Point[][][] data =new Point[4][][];
        String datName = "level"+level+".txt";
        String string = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(datName), "UTF-8"));
            // do reading, usually loop until end of file reading
            String mLine;
            int i = 0;
            while ((mLine = reader.readLine()) != null) {
                //process line
                data[i]=getData(mLine);
                i++;
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
        return data;
    }

    private Point[][] getData(String text){
       // System.out.println(text);
        Point[][] data = null;
        String gruppe[] = text.split(":");

        data = new Point[gruppe.length][];
        for(int i = 0 ;i < gruppe.length; i++) {
            String elemente[] = gruppe[i].split(";");

            data[i] = new Point[elemente.length];

            for(int j = 0 ;j < elemente.length; j++) {



                String [] einzel = elemente[j].split(",");
                data[i][j] = new Point(Integer.parseInt(einzel[0]), Integer.parseInt(einzel[1]));

            }
        }
        return data;
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
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);



    }

    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);



         if(erster) {
            Bitmap b = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.hentergun);
            hintergund = Bitmap.createScaledBitmap(b, (int) (canvas.getWidth()), (int) (canvas.getHeight()), true);
        }
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.player);
        paint.setColor(color);
        canvas.drawBitmap(hintergund, 0, 0, paint);
        erster = false;

        target.draw(canvas);
        drowUPS(canvas);
        drowFPS(canvas);
        joystick.drow(canvas);
        player.draw(canvas);

        for(int i = 0; i <guards.length; i++) {
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