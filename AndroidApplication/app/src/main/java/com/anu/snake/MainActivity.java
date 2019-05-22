/*
Authorship: Feier Xiao
 */

package com.anu.snake;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login, register, howToPlay;
    private EditText account, password;

    private Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the Button, EditView
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        howToPlay = findViewById(R.id.howToPlay);
        howToPlay.setOnClickListener(this);
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);

        test = findViewById(R.id.test);
        test.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Process the result from register page
        if (requestCode == CONSTANTS.MAIN_REQUEST_REGISTER && resultCode == CONSTANTS.REGISTER_RESPONSE_MAIN) {
            // Set the account and password for the new user after registration
            account.setText(data.getStringExtra("account"));
            password.setText(data.getStringExtra("password"));
        }
    }

    // Handler: process the HTTP response
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CONSTANTS.LOGIN_VALIDATION: {
                    Bundle bundle = msg.getData();
                    String result = bundle.getString("result");
                    // Process based on the response information
                    try {
                        if (result.equals("success")) {
                            Intent intent = new Intent(MainActivity.this, GameActivity.class);
                            // Use Bundle for multiple parameters
                            bundle = new Bundle();
                            // Put in multiple parameters and turn to the new activity
                            bundle.putString("account", account.getText().toString());
                            intent.putExtra("data", bundle);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Fail！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    };


    // Set the onclick listener for the button
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login: {
                // HTTP connection can not be handled in the main thread, we need a thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Pass in the parameter
                        String result = HTTPConnection.loginByPost(account.getText().toString(), password.getText().toString());
                        // Use Bundle for multiple parameters. Althought only one parameter here, use it here for consistency
                        Bundle bundle = new Bundle();
                        bundle.putString("result", result);
                        Message message = new Message();
                        message.setData(bundle);
                        // Use handler to process response
                        message.what = CONSTANTS.LOGIN_VALIDATION;
                        handler.sendMessage(message);
                    }
                }).start();
            }
            break;
            case R.id.register: {
                // Turn to the register page
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, CONSTANTS.MAIN_REQUEST_REGISTER);
            }
            break;
            case R.id.test: {
                Intent intent = new Intent(MainActivity.this, RankActivity.class);
                // Use Bundle for multiple parameters
                Bundle bundle = new Bundle();
                // Put in multiple parameters and turn to the new activity
                bundle.putString("account", "Yuan");
                bundle.putInt("score", 5000);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
            break;

            case R.id.howToPlay: {
                // Pop up a dialog to show the rule of the game
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("How To Play??");
                builder.setMessage("1. Swipe to control snake\n" +
                        "2. Touch the red obstacle, the bound, the snake body, GAME OVER!\n" +
                        "3. White food: 1 mark, snake length increase, speed up\n" +
                        "4. Darkblue food : 5 marks, snake length greatly increase, speed down\n" +
                        "5. Skyblue fodd: 10 marks, snake won't increase, only 4 in each game");
                // Positive button, need no action
                builder.setPositiveButton("OK", null);
                // Can not be cancel
                builder.setCancelable(false);
                builder.show();
            }
            break;
        }
    }
}
