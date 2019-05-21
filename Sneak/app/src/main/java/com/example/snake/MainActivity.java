package com.example.snake;

import android.content.res.Resources;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        gameView = new GameView(this,size);
        setContentView(gameView);
//        sendControlMessage();

    }
    @Override
    protected void onResume(){
        super.onResume();
        gameView.resume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        gameView.pause();
    }

}
