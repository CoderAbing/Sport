package com.example.viencent.sport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import application.MyApplication;

/**
 * 程序启动欢迎界面
 * Created by Vincent on 2016/9/30.
 */
public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        MyApplication.getSingleton().addActivity(this);
        new Thread(new Runnable() {//新建一个线程
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(2000);//毫秒
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();


    }
}
