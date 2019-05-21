package com.anu.snake;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private int ResultCode = 2;
    private final static int REGISTER_VALIDATION = 2;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("u[0-9]{7}\\@anu.edu.au");
    private Button register, back;
    private EditText account, pass_1, pass_2, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.register);
        back = findViewById(R.id.back);
        register.setOnClickListener(this);
        back.setOnClickListener(this);
        account = findViewById(R.id.account);
        pass_1 = findViewById(R.id.password);
        pass_2 = findViewById(R.id.password2);
        email = findViewById(R.id.email);
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
                            intent.putExtra("account", account.getText().toString());
                            intent.putExtra("password", pass_1.getText().toString());
                            setResult(ResultCode, intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "This account name or email has been registered", Toast.LENGTH_LONG).show();
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
            case R.id.back:
                this.finish();
                break;

            case R.id.register:
                if (account.getText().toString().equals("") || pass_1.getText().toString().equals("") || pass_2.getText().toString().equals("") || email.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Empty input", Toast.LENGTH_LONG).show();
                } else if (!EMAIL_PATTERN.matcher(email.getText().toString()).matches()) {
                    Toast.makeText(RegisterActivity.this, "Please register with the ANU email, start with lower case \"u\" ", Toast.LENGTH_LONG).show();
                } else if (!pass_1.getText().toString().equals(pass_2.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Different Password Confirmed", Toast.LENGTH_LONG).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = HTTPConnection.RegisterByPost(account.getText().toString(), pass_1.getText().toString(), email.getText().toString());
                            Bundle bundle = new Bundle();
                            bundle.putString("result", result);
                            Message msg = new Message();
                            msg.what = REGISTER_VALIDATION;
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    }).start();
                }
                break;
        }
    }

}
