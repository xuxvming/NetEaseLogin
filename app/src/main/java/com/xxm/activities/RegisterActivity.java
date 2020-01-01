package com.xxm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.xxm.login.RegisterManager;

public class RegisterActivity extends AppCompatActivity {

    private RegisterManager credentialManager;
    private EditText mUsernameView;
    private EditText mPasswordView;
    private EditText mPasswordViewSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mPasswordView = findViewById(R.id.password);
        mPasswordViewSecond = findViewById(R.id.password_second);
        mUsernameView = findViewById(R.id.username);
        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = mPasswordView.getText().toString();
                String username = mUsernameView.getText().toString();
                String passwordSecond = mPasswordViewSecond.getText().toString();
                credentialManager = new RegisterManager(username,password,RegisterActivity.this);

                if(!TextUtils.isEmpty(username) && !credentialManager.isUserNameValid()){
                    mUsernameView.setError(credentialManager.getUserNameErrorMessage());
                }else if (!TextUtils.isEmpty(password) && !credentialManager.isPasswordValid()){
                    mPasswordView.setError(credentialManager.getPassWordErrorMessage());
                }else if (!TextUtils.isEmpty(passwordSecond) && !credentialManager.isPasswordMatching(passwordSecond)){
                    mPasswordViewSecond.setError(credentialManager.getPassWordErrorMessage());
                }
                else{
                    if (credentialManager.register()){
                        Toast.makeText(getApplicationContext(), "注册成功",Toast.LENGTH_LONG).show();
                        Intent mIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        RegisterActivity.this.startActivity(mIntent);
                    }
                }
            }
        });

    }
}
