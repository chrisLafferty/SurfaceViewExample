package edu.cascadia.brianb.surfaceviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TestSurfaceView extends AppCompatActivity {

    MySurfaceView mySurfaceView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySurfaceView = new MySurfaceView(this);
        setContentView(mySurfaceView);
    }

    class MySurfaceView extends SurfaceView
            implements SurfaceHolder.Callback{

        MakeCircle thread = null;
        SurfaceHolder surfaceHolder;
        volatile boolean running = false;

        public MySurfaceView(Context context) {
            super(context);
            surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int f, int w, int h) {}

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder){
            thread = new MakeCircle(surfaceHolder);
            thread.setRunning(true);
            thread.setPriority(1);
            thread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder){
            boolean retry = true;
            thread.setRunning(false);
            while(retry){
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
