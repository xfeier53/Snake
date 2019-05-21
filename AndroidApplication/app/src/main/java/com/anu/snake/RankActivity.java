package com.anu.snake;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RankActivity extends AppCompatActivity {

    private Button restart, back;
    private final static int SCORE = 2;
    private TextView thisScore, bestScore, record;
    private String account;
    private int myBestScore, myThisScore;
    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        restart = findViewById(R.id.restart);
        back = findViewById(R.id.back);
        thisScore = findViewById(R.id.thisScore);
        bestScore = findViewById(R.id.bestScore);
        record = findViewById(R.id.record);

        Intent intent=getIntent();
        Bundle bundle= intent.getBundleExtra("data");
        account = bundle.getString("account");
        myThisScore = bundle.getInt("score");
        thisScore.setText(""+myThisScore);
        getBestScore();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCORE: {
                    Bundle bundle = msg.getData();
                    myBestScore = bundle.getInt("result");
                    bestScore.setText(""+myBestScore);
                }
                break;
            }
        }
    };

    public void getBestScore() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int result = HTTPConnection.GetBestScoreByPost(account);
                Bundle bundle = new Bundle();
                bundle.putInt("result", result);
                Message msg = new Message();
                msg.what = SCORE;
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }).start();
    }

}
