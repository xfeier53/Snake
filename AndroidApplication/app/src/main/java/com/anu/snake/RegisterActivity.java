package com.anu.snake;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private int ResultCode = 2;
    private final static int REGISTER_VALIDATION = 2;
    private Button register, back;
    private EditText id, pass_1, pass_2, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = (Button) findViewById(R.id.register_do);
        register.setOnClickListener(this);
        id = (EditText) findViewById(R.id.account);
        pass_1 = (EditText) findViewById(R.id.password);
        pass_2 = (EditText) findViewById(R.id.password2);
        email = (EditText) findViewById(R.id.email);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REGISTER_VALIDATION: {
                    Bundle bundle = msg.getData();
                    String result = bundle.getString("result");
                    try {
                        if (result.equals("success")) {
                            Intent intent = new Intent();
                            intent.putExtra("id", id.getText().toString());
                            intent.putExtra("password", pass_1.getText().toString());
                            setResult(ResultCode, intent);
                            finish();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_do: {
                if (!pass_1.getText().toString().equals(pass_2.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Different Password Confirmed！", Toast.LENGTH_LONG).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = HTTPConnection.RegisterByPost(id.getText().toString(), pass_1.getText().toString(), email.getText().toString());
                            Bundle bundle = new Bundle();
                            bundle.putString("result", result);
                            Message msg = new Message();
                            msg.what = REGISTER_VALIDATION;
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    }).start();
                }
            }
            break;
        }
    }

}
