package com.xxm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.xxm.login.LoginManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private LoginManager loginManager;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        Toast.makeText(getApplicationContext(),"登录成功！",Toast.LENGTH_LONG).show();
        Button logoutButton = findViewById(R.id.logout_button);
        Button loginButton = findViewById(R.id.login_button);
        logoutButton.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                loginManager = new LoginManager("","",MainActivity.this);
                loginManager.logout();
                Intent mIntent = new Intent(MainActivity.this, LoginManager.class);
                MainActivity.this.startActivity(mIntent);
                finish();
            }
        });

        loginButton.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(mIntent);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        timer = new Timer();
        LogOutTimerTask logoutTimeTask = new LogOutTimerTask();
        timer.schedule(logoutTimeTask, 300000*6); //auto logout in 5 minutes
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    class LogOutTimerTask extends TimerTask{

        @Override
        public void run() {
            loginManager.logout();
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }

}
