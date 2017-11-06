package com.dyy.yonxin.library2.cacheforandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DBStateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbstate);
    }

    public void clickHasKey(View view) {
        Intent intent = new Intent(this,DBActivity.class);
        startActivity(intent);
    }

    public void clickKeyNone(View view) {
        Intent intent = new Intent(this,DBNoKeyActivity.class);
        startActivity(intent);
    }
}
