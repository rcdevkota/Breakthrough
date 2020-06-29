package com.example.breakthrough;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.core.content.ContextCompat;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoop gameLoop;
    private Context context;

    public Game(Context context) {
        super(context);


        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.context =context;
        gameLoop = new GameLoop(this, surfaceHolder);

        setFocusable(true);
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
    }

    public void drowUPS(Canvas canvas){
        String averagUPS = Double.toString(gameLoop.getAveregeUPS());
        Paint paint = new Paint();

        int color = ContextCompat.getColor(context, R.color.debugge);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averagUPS, 100,200,paint);
    }

    public void drowFPS(Canvas canvas){
        String averagFPS = Double.toString(gameLoop.getAveregeFPS());
        Paint paint = new Paint();

        int color = ContextCompat.getColor(context, R.color.debugge);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averagFPS, 100,300,paint);
    }

    public void update() {
    }
}
