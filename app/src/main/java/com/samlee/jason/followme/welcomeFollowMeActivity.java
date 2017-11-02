package com.samlee.jason.followme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class welcomeFollowMeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_follow_me);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(welcomeFollowMeActivity.this, followMeMainActivity.class);
                startActivity(intent);
                welcomeFollowMeActivity.this.finish();
            }
        }, 3000);

    }
}
