package com.anu.snake;

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

    private Button login, register;
    private EditText id, password;
    private final static int LOGIN_VALIDATION = 1;
    private int RequestCode = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            id.setText(data.getStringExtra("id"));
            password.setText(data.getStringExtra("password"));
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN_VALIDATION: {
                    Bundle bundle = msg.getData();
                    String result = bundle.getString("result");
                    try {
                        if (result.equals("success")) {
                            Toast.makeText(MainActivity.this, "Login Successfully！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login: {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Using HTTP Connection to send request and process the respond
                        String result = HTTPConnection.LoginByPost(id.getText().toString(), password.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("result", result);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = LOGIN_VALIDATION;
                        handler.sendMessage(message);
                    }
                }).start();
            }
            break;
            case R.id.register: {
                // Turn to the register page
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, RequestCode);
            }
            break;
        }
    }
}
