package com.example.breakthrough;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread{
    private static final double MAX_UPS = 30.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private Game game;
    private boolean isRunging =false;
    private SurfaceHolder surfaceHolder;
    private double averegeUPS;
    private double averegeFPS;


    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;

    }

    public double getAveregeUPS() {
        return averegeUPS;
    }

    public double getAveregeFPS() {
        return averegeFPS;
    }

    public void startLoop() {
        isRunging = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        int updateCount =0;
        int frameCount =0;

        long startTime;
        long elapsedTime;
        Long sleepTime;

        Canvas canvas =null;
        startTime = System.currentTimeMillis();


        while (isRunging){


            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    game.update();
                    updateCount++;

                    game.draw(canvas);
                }
            } catch (IllegalArgumentException e){
                e.printStackTrace();
            }finally {
                if(canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }


            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
                    if(sleepTime > 0){
                        try {
                            sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            while (sleepTime < 0 && updateCount < MAX_UPS-1){
                game.update();
                updateCount++;

                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            }
            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime >= 1000){
                averegeUPS = updateCount / (1E-3 *elapsedTime);
                averegeFPS = frameCount / (1E-3 *elapsedTime);
                updateCount =0;
                frameCount =0;
                startTime = System.currentTimeMillis();
            }
        }
    }
}