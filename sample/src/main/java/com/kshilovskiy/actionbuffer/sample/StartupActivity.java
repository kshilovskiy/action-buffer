package com.kshilovskiy.actionbuffer.sample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class StartupActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        findViewById(R.id.start_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartupActivity.this, RetainedActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.start_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartupActivity.this, NonRetainedActivity.class);
                startActivity(intent);
            }
        });
    }
}
