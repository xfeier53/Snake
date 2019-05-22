/*
Authorship: Feier Xiao
 */

package com.anu.snake;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import java.util.List;

public class RankActivity extends AppCompatActivity implements View.OnClickListener {

    private Button restart, exit;
    private TextView thisScore, bestScore, record;
    private String account, recordString, recordStringWithSpace, recordStringWithLine, recordName;
    private int myBestScore, myThisScore, maxIndex;
    private List<Data> recordList;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        restart = findViewById(R.id.restart);
        restart.setOnClickListener(this);
        exit = findViewById(R.id.exit);
        exit.setOnClickListener(this);
        thisScore = findViewById(R.id.thisScore);
        bestScore = findViewById(R.id.bestScore);
        record = findViewById(R.id.record);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        account = bundle.getString("account");
        myThisScore = bundle.getInt("score");
        thisScore.setText(""+myThisScore);
        getData();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // Retrieve the user's best score
                case CONSTANTS.SCORE_PROCESS: {
                    Bundle bundle = msg.getData();
                    myBestScore = bundle.getInt("result");
                    bestScore.setText("" + myBestScore);
                    if (myThisScore > myBestScore) {
                        setBestScore();
                    }
                }
                break;

                // Retrive the record
                case CONSTANTS.RECORD_PROCESS: {
                    recordList = new ArrayList<>();
                    Bundle bundle = msg.getData();
                    recordString = bundle.getString("result");
                    String[] rows = recordString.split(" ");
                    // Splid the string and store the data into list of Data type
                    for (int i = 0; i < 10; i++) {
                        if (i % 2 == 0) {
                            recordName = rows[i];
                        } else {
                            recordList.add(new Data(recordName, Integer.valueOf(rows[i])));
                        }
                    }
                    // Sorting the list based on the score
                    for (int i = 0; i < 5; i++) {
                        maxIndex = i;
                        // Find the maximum score
                        for (int j = i + 1; j < 5; j++) {
                            if (recordList.get(maxIndex).score < recordList.get(j).score) {
                                maxIndex = j;
                            }
                        }
                        // Swap the value for i and maximum
                        if (i != maxIndex) {
                            Data temp = recordList.get(maxIndex);
                            recordList.remove(maxIndex);
                            recordList.add(maxIndex, recordList.get(i));
                            recordList.remove(i);
                            recordList.add(i, temp);
                        }
                    }
                    recordStringWithSpace = "";
                    recordStringWithLine = "";
                    // I is a counter, we need only first 5 records
                    int i = 0;
                    // Convert the list into String with format
                    for (Data d : recordList) {
                        if (i++ == 5) {
                            break;
                        }
                        // Check whether the score is better than the any record
                        if (flag == false && myThisScore > d.score) {
                            flag = true;
                            // Need the format with space
                            recordStringWithSpace = recordStringWithSpace + account + " " + myThisScore + " ";
                            recordStringWithLine = recordStringWithLine + account + " " + myThisScore + "\n";
                            if (i++ == 5) {
                                break;
                            }
                        }
                        recordStringWithSpace = recordStringWithSpace + d.name + " " + d.score + " ";
                        recordStringWithLine = recordStringWithLine + d.name + " " + d.score + "\n";
                    }
                    if (flag == true) {
                        setRecord();
                    }
                    record.setText(recordStringWithLine);
                }
                break;

                case CONSTANTS.NEW_RECORD_PROCESS: {
                    Bundle bundle = msg.getData();
                    String result = bundle.getString("result");
                    // Process based on the response information
                    if (result.equals("success")) {
                        // New record
                        Toast.makeText(RankActivity.this, "New World Record！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;


                case CONSTANTS.NEW_SCORE_PROCESS: {
                    Bundle bundle = msg.getData();
                    String result = bundle.getString("result");
                    // Process based on the response information
                    if (result.equals("success")) {
                        // New record
                        Toast.makeText(RankActivity.this, "New Personal Record！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    };

    public void getData() {
        // A thread to retrieve the user's best score
        new Thread(new Runnable() {
            @Override
            public void run() {
                int result = HTTPConnection.getBestScoreByPost(account);
                Bundle bundle = new Bundle();
                bundle.putInt("result", result);
                Message msg = new Message();
                msg.what = CONSTANTS.SCORE_PROCESS;
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }).start();

        // A thread to retrieve the world records
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HTTPConnection.getRecordByPost();
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                Message msg = new Message();
                msg.what = CONSTANTS.RECORD_PROCESS;
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }).start();
    }

    public void setRecord() {
        // A thread to set the new world score
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HTTPConnection.setRecordByPost(recordStringWithSpace);
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                Message msg = new Message();
                msg.what = CONSTANTS.NEW_RECORD_PROCESS;
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }).start();
    }

    public void setBestScore() {
        // A thread to set the user's best score
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HTTPConnection.setBestScoreByPost(account, myThisScore);
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                Message msg = new Message();
                msg.what = CONSTANTS.NEW_SCORE_PROCESS;
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }).start();
    }


    // Bind the onclick method with the buttons
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.restart: {
                Intent intent = new Intent(this, GameActivity.class);
                // Use Bundle for multiple parameters
                Bundle bundle = new Bundle();
                // Put in multiple parameters and turn to the new activity
                bundle.putString("account", account);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
            break;
            case R.id.exit: {
                // Turn to the register page
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            break;
        }
    }

    // A class that store two value, used to process the records list
    class Data {
        String name;
        int score;

        public Data(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }
}
