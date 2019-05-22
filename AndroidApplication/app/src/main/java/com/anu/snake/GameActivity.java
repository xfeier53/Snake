package com.anu.snake;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

/*
Authorship: Yu Wang
            Tao Xu
            Yue Zhou
 */
public class GameActivity extends Activity {
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
