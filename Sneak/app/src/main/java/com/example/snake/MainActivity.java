package com.example.snake;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
/*
Authorship: Yu Wang
            Tao Xu
            Yue Zhou
 */
public class MainActivity extends Activity {
    private GameView gameView;
    //This method loads the GameView, and get the screen size of the phone or tablet.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        gameView = new GameView(this,size);
        setContentView(gameView);
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
