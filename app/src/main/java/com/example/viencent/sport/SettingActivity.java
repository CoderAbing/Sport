package com.example.viencent.sport;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import application.MyApplication;

/**
 * 系统设置活动
 * Created by Vincent on 2016/9/25.
 */
public class SettingActivity extends Activity {
    private ImageButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four_seting);
        MyApplication.getSingleton().addActivity(this);
        button = (ImageButton)findViewById(R.id.news_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
