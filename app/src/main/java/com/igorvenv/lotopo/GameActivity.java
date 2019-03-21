package com.igorvenv.lotopo;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class GameActivity extends AppCompatActivity {

    GameField field;
    boolean running;
    Thread renderThread = new Thread(new Runnable() {

        @Override
        public void run() {
            long now = System.currentTimeMillis();
            long last = now;
            int delta;
            while(running){
                now = System.currentTimeMillis();
                delta = (int)(now - last);
                if (delta >= 1_000 / 60) {
                    last = now;
                    field.render();
                }
            }
        }

    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        field = new GameField(this);
        setContentView(field);
    }


    private void start(){
        Log.d("START", "start");
        if (renderThread.getState() == Thread.State.NEW) {
            running = true;
            renderThread.start();
        }
    }

    private void stop(){
        running = false;
        try {
            renderThread.join();
            Log.d("STOP", "joined");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("PAUSE", "onPause");
        stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("RESUME", "onResume");
        start();
    }

}
