package com.example.breakthrough;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.Image;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.core.content.ContextCompat;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private GameLoop gameLoop;

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);
        player =new Player(getContext(), 500,500,30);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                player.setPosition((float)event.getX(), (float)event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                player.setPosition((float)event.getX(), (float)event.getY());
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) { gameLoop.startLoop(); }

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
        player.draw(canvas);
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

        player.update();
    }
}
