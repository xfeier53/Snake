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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankActivity extends AppCompatActivity {

    private Button restart, back;
    private final static int SCORE = 2;
    private final static int RECORD = 3;
    private TextView thisScore, bestScore, record;
    private String account, stringRecord, recordName;
    private int myBestScore, myThisScore, maxIndex;
    private List<Data> recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        restart = findViewById(R.id.restart);
        back = findViewById(R.id.back);
        thisScore = findViewById(R.id.thisScore);
        bestScore = findViewById(R.id.bestScore);
        record = findViewById(R.id.record);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        account = bundle.getString("account");
        myThisScore = bundle.getInt("score");
        thisScore.setText("" + myThisScore);
        getBestScore();
        getRecord();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCORE: {
                    Bundle bundle = msg.getData();
                    myBestScore = bundle.getInt("result");
                    bestScore.setText("" + myBestScore);
                }
                break;

                case RECORD: {
                    recordList = new ArrayList<>();
                    Bundle bundle = msg.getData();
                    stringRecord = bundle.getString("result");
                    String[] rows = stringRecord.split(" ");
                    for (int i = 0; i < 10; i++) {
                        if (i % 2 == 0) {
                            recordName = rows[i];
                        } else {
                            recordList.add(new Data(recordName, Integer.valueOf(rows[i])));
                        }
                    }

                    for (int i = 0; i < 5; i++) {
                        maxIndex = i;
                        for (int j = i + 1; j < 5; j++) {
                            if (recordList.get(maxIndex).score < recordList.get(j).score) {
                                maxIndex = j;
                            }
                        }
                        if (i != maxIndex) {
                            Data temp = recordList.get(maxIndex);
                            recordList.remove(maxIndex);
                            recordList.add(maxIndex, recordList.get(i));
                            recordList.remove(i);
                            recordList.add(i, temp);
                        }
                    }
                    stringRecord = "";
                    for (Data d : recordList) {
                        stringRecord = stringRecord + d.name + " " + d.score + "\n";
                    }
                    record.setText(stringRecord);
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

    public void getRecord() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HTTPConnection.GetRecord();
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                Message msg = new Message();
                msg.what = RECORD;
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }).start();
    }

    class Data {
        String name;
        int score;

        public Data(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }
}
