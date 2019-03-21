package com.igorvenv.lotopo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class GameField extends SurfaceView implements SurfaceHolder.Callback {

    public static final int FOG = 0;
    public static final int PLAYER = 1;
    public static final int HEDGEHOG = 2;
    public static final int DRAGON = 3;
    public static final int ITEM = 4;
    public static final int VISIBLE_PLACE = 5;
    public static final int ABLE_PLACE = 6;

    public static final int CHUNK_SIZE = 100;
    public static final int[] COLORS = {0xFFFFFFFF, 0xFFFF0000, 0xFFFF9D00, 0xFF00FF00, 0xFFFFFF00, 0xFFC1B83A, 0xFF77ABFF};
    public static final int BORDER = 0xFF0000FF;
    public boolean dragging;
    public boolean thinking;


    SurfaceHolder holder;
    int[][] swamp;
    int x;
    int y;
    int cursorX;
    int cursorY;
    int firstX, firstY;
    GameLogicController controller;

    public GameField(Context context) {
        super(context);
        getHolder().addCallback(this);
        dragging = false;
        thinking = false;
        controller = new GameLogicController();
        this.swamp = controller.generate(); //getting the same swamp for GameField and GameController
    }

    public int[] getScreenSwampPosition(int x, int y){
        int[] ret = new int[2];
        ret[0] = (int) Math.floor((x - this.x) / CHUNK_SIZE);
        ret[1] = (int) Math.floor((y - this.y) / CHUNK_SIZE);
        return ret;
    }

    public void render(){

        if (holder == null) return;
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            try {
                clear(canvas);
                paint(canvas);
            } finally {
                holder.unlockCanvasAndPost(canvas);
            }
        }

    }

    private void paint(Canvas canvas){
        Paint p = new Paint();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 8; j++) {

                p.setColor(COLORS[swamp[i][j]]);

                drawChunk(canvas, i * CHUNK_SIZE, j * CHUNK_SIZE, p);

            }
        }
    }

    private void drawChunk(Canvas canvas, int x, int y, Paint p) { // drawing one piece of field
        int x0 = x + this.x, y0 = y + this.y, x1 = x0 + CHUNK_SIZE, y1 = y0 + CHUNK_SIZE;


        canvas.drawRect(x0, y0, x1, y1, p);
        p.setColor(BORDER);
        canvas.drawLine(x1, y0, x1, y1, p); // drawing border
        canvas.drawLine(x1, y1, x0, y1, p);
        canvas.drawLine(x0, y0, x0, y1, p);
        canvas.drawLine(x1, y0, x0, y0, p);
    }

    private void clear(Canvas c){
        c.drawColor(0xFFFFFFFF);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.x = 100;
        this.y = 100;
        this.holder = holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction()) {
                                            // scrolling map
            case MotionEvent.ACTION_DOWN:
                dragging = true;
                cursorX = (int)e.getX(); //remembering first finger's position
                cursorY = (int)e.getY();
                firstX = (int)e.getX(); //for handling touches
                firstY = (int)e.getY();
                break;

            case MotionEvent.ACTION_UP:
                dragging = false;
                Log.d("ON_TOUCH_EVENT", "up");
                //if finger moved less than 15 pixels
                if (firstY - 15 < (int)e.getY() && firstY + 15 > (int)e.getY()
                        && firstX - 15 < (int)e.getX() && firstX + 15 > (int)e.getX()
                        && !thinking){
                    thinking = true; //time for unit's computing
                    Log.d("ON_TOUCH_EVENT", "click");

                    int[] touched = getScreenSwampPosition((int)e.getX(), (int)e.getY()); //what chunk was pressed

                    controller.handleTouch(touched[0], touched[1]);

                    thinking = false;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (dragging) {
                    this.x += (int)e.getX() - cursorX;
                    this.y += (int)e.getY() - cursorY;
                    cursorX = (int)e.getX(); // updating finger's position
                    cursorY = (int)e.getY();
                }
                break;

        }


        return true;
    }

}
