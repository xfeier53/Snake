/*
Authorship: Yu Wang
            Tao Xu
            Yue Zhou
 */

package com.anu.snake;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

public class GameActivity extends Activity {
    public GameView gameView;
    public String account;
    public int score;
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
        EventBus.getDefault().register(this);

    }

    @EventBus.Subscribe
    public void eventReceiver(EventHandler event) {
        if(event.getText() == "dead"){
            Intent intent = getIntent();
            Bundle bundle = intent.getBundleExtra("data");
            account = bundle.getString("account");
            score = gameView.m_score;

            intent = new Intent(GameActivity.this, RankActivity.class);
            // Use Bundle for multiple parameters
            bundle = new Bundle();
            // Put in multiple parameters and turn to the new activity
            bundle.putString("account", account);
            bundle.putInt("score", score);
            intent.putExtra("data", bundle);
            startActivity(intent);
        }
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
