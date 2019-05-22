/*
Authorship: Feier Xiao
 */

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
import java.util.List;

public class RankActivity extends AppCompatActivity implements View.OnClickListener {

    private Button restart, exit;
    private TextView thisScore, bestScore, record;
    private String account, recordString, recordName;
    private int myBestScore, myThisScore, maxIndex;
    private List<Data> recordList;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

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
        thisScore.setText("" + myThisScore);
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
                    recordString = "";
                    // Convert the list into String in format
                    for (Data d : recordList) {
                        // Check whether the score is better than the any record
                        if (flag == false && myThisScore > d.score) {
                            flag = true;
                            recordString = recordString + account + " " + myThisScore + " ";
                        }
                        recordString = recordString + d.name + " " + d.score + " ";
                    }
                    if (flag == true) {
                        setRecord();
                    } else {
                        // Set the record for the activity
                        record.setText(recordString);
                    }
                }
                break;

                case CONSTANTS.NEW_RECORD_PROCESS: {
                    Bundle bundle = msg.getData();
                    String result = bundle.getString("result");
                    // Process based on the response information
                    if (result.equals("success")) {
                        // New record
                        Toast.makeText(RankActivity.this, "New Record！", Toast.LENGTH_SHORT).show();
                        Log.d("test", recordString);
                        String temp[] = recordString.split(" ");
                        recordString = "";
                        for (int i = 0; i < 10; i = i + 2) {
                            recordString = recordString + temp[i] + " " + temp[i + 1] + "\n";
                        }
                        // Set the record for the activity
                        record.setText(recordString);
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
        Thread t1 = new Thread(new Runnable() {
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
        });

        // A thread to retrieve the world records
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HTTPConnection.getRecord();
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                Message msg = new Message();
                msg.what = CONSTANTS.RECORD_PROCESS;
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });

        // A thread to set the user's best score
        Thread t3 = new Thread(new Runnable() {
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
        });
        try {
            t1.start();
            t1.join();
            t2.start();
            t2.join();
            if (myThisScore > myBestScore) {
                t3.start();
                t3.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRecord() {
        // A thread to set the new world score
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HTTPConnection.setRecord(recordString);
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                Message msg = new Message();
                msg.what = CONSTANTS.NEW_RECORD_PROCESS;
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
