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
import com.xxm.login.LoginManager;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameView;
    private EditText mPasswordView;

    private LoginManager credentialManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsernameView = findViewById(R.id.email);

        mPasswordView = findViewById(R.id.password);

        final Button mLoginButton = findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = mPasswordView.getText().toString();
                String username = mUsernameView.getText().toString();
                credentialManager = new LoginManager(username,password,LoginActivity.this);
                if(!TextUtils.isEmpty(username) && !credentialManager.isUserNameValid()){
                    mUsernameView.setError(credentialManager.getUserNameErrorMessage());
                }else if (!TextUtils.isEmpty(password) && !credentialManager.isPasswordValid()){
                    if(credentialManager.isAccountLocked()){
                        Toast.makeText(getApplicationContext(),"您的账号已锁定，请24-N小时后再试",Toast.LENGTH_LONG).show();
                    }
                    mPasswordView.setError(credentialManager.getPassWordErrorMessage());
                }else if(credentialManager.isAccountLocked()){
                    Toast.makeText(getApplicationContext(),"您的账号已锁定，请24-N小时后再试",Toast.LENGTH_LONG).show();
                }else if (credentialManager.isLoggedIn()){
                    Toast.makeText(getApplicationContext(),"当前用户已登陆, 若更换用户请先退出",Toast.LENGTH_LONG).show();
                }
                else {
                    credentialManager.login();
                    Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(mIntent);
                }
            }
        });

        Button mRegisterButton = findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(mIntent);
            }
        });

    }
}

