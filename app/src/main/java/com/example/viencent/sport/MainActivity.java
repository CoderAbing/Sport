package com.example.viencent.sport;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import application.MyApplication;
import fragment.Fragment_four;
import fragment.Fragment_one;
import fragment.Fragment_three;
import fragment.Fragment_two;
import widget.MultipleSelectDialog;

public class MainActivity extends FragmentActivity {

    private String TAG = "MainActivity";
    private FragmentManager manager;
    private FrameLayout sport_Layout, more_Layout, playground_Layout, mine_Layout;
    //声明碎片管理器
    private LinearLayout sport, mine, playground, more;
    private ImageView img_sport, img_mine, img_playground, img_more;
    private MultipleSelectDialog gpsDialog = null;
    private MultipleSelectDialog notWorkDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getSingleton().addActivity(this);
        initView();
        if (!MyApplication.isOpenGPS(this)) {
            openGPS();
        }
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.sport_framelayout, new Fragment_one(), "sport").commit();
        if (!MyApplication.checkNetworkAvailable(this)) {
            notWorkDialog = new MultipleSelectDialog.Builder(this).setMessage("系统未获取到数据流量，请先打开数据流量再执行下一步操作，前去打开？").setOnFinishListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS));
                    notWorkDialog.dismiss();
                }
            }).setOnCancelListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notWorkDialog.dismiss();
                }
            }).create();
            notWorkDialog.show();

        }
        this.addClick();
    }

    private void initView() {
        //实例化布局
        sport_Layout = (FrameLayout) findViewById(R.id.sport_framelayout);
        more_Layout = (FrameLayout) findViewById(R.id.more_framelayout);
        playground_Layout = (FrameLayout) findViewById(R.id.playground_framelayout);
        mine_Layout = (FrameLayout) findViewById(R.id.mine_framelayout);
        //实例化底部导航栏
        sport = (LinearLayout) findViewById(R.id.sport);
        playground = (LinearLayout) findViewById(R.id.playground);
        mine = (LinearLayout) findViewById(R.id.mine);
        more = (LinearLayout) findViewById(R.id.more);
        //实例化底部图片空间
        img_sport = (ImageView) findViewById(R.id.img_sport);
        img_more = (ImageView) findViewById(R.id.img_more);
        img_playground = (ImageView) findViewById(R.id.img_playground);
        img_mine = (ImageView) findViewById(R.id.img_mine);
    }

    //给底部按钮添加点击事件
    private void addClick() {
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sport_Layout.setVisibility(View.VISIBLE);
                more_Layout.setVisibility(View.INVISIBLE);
                playground_Layout.setVisibility(View.INVISIBLE);
                mine_Layout.setVisibility(View.INVISIBLE);
                img_sport.setImageResource(R.mipmap.sport_choice);
                img_more.setImageResource(R.mipmap.more);
                img_playground.setImageResource(R.mipmap.playground);
                img_mine.setImageResource(R.mipmap.mine);
                FragmentTransaction mTransation = manager.beginTransaction();
                mTransation.replace(R.id.sport_framelayout, new Fragment_one(), "sport");
                Fragment fragment = manager.findFragmentByTag("map");
                if (fragment != null) {
                    mTransation.remove(fragment);
                }
                mTransation.commit();
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sport_Layout.setVisibility(View.INVISIBLE);
                more_Layout.setVisibility(View.VISIBLE);
                playground_Layout.setVisibility(View.INVISIBLE);
                mine_Layout.setVisibility(View.INVISIBLE);
                img_more.setImageResource(R.mipmap.more_choice);
                img_sport.setImageResource(R.mipmap.sport);
                img_playground.setImageResource(R.mipmap.playground);
                img_mine.setImageResource(R.mipmap.mine);
                FragmentTransaction mTransation = manager.beginTransaction();
                mTransation.replace(R.id.more_framelayout, new Fragment_two(), "news");
                Fragment fragment = manager.findFragmentByTag("map");
                if (fragment != null) {
                    mTransation.remove(fragment);
                }
                mTransation.commit();
            }
        });
        playground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sport_Layout.setVisibility(View.INVISIBLE);
                more_Layout.setVisibility(View.INVISIBLE);
                playground_Layout.setVisibility(View.VISIBLE);
                mine_Layout.setVisibility(View.INVISIBLE);
                img_more.setImageResource(R.mipmap.more);
                img_sport.setImageResource(R.mipmap.sport);
                img_playground.setImageResource(R.mipmap.playground_choice);
                img_mine.setImageResource(R.mipmap.mine);
                manager.beginTransaction().replace(R.id.playground_framelayout, new Fragment_three(), "map").commit();

            }
        });
        mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sport_Layout.setVisibility(View.INVISIBLE);
                more_Layout.setVisibility(View.INVISIBLE);
                playground_Layout.setVisibility(View.INVISIBLE);
                mine_Layout.setVisibility(View.VISIBLE);
                img_more.setImageResource(R.mipmap.more);
                img_sport.setImageResource(R.mipmap.sport);
                img_playground.setImageResource(R.mipmap.playground);
                img_mine.setImageResource(R.mipmap.mine_choice);
                FragmentTransaction mTransation = manager.beginTransaction();
                mTransation.replace(R.id.mine_framelayout, new Fragment_four(), "mine");
                Fragment fragment = manager.findFragmentByTag("map");
                if (fragment != null) {
                    mTransation.remove(fragment);
                }
                mTransation.commit();
            }
        });
    }

    private long exitTime = 0;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                MyApplication.getSingleton().AppExit();//杀死所有的Activity,结束程序
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 判断是否开启GPS
     */
    private void openGPS() {
        if (!MyApplication.isOpenGPS(this)) {
            gpsDialog = new MultipleSelectDialog.Builder(this).setMessage("系统未获取到位置信息，请先打开GPS再执行下一步操作，是否前去打开？").setOnFinishListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                    gpsDialog.dismiss();
                }
            }).setOnCancelListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gpsDialog.dismiss();
                }
            }).create();
            gpsDialog.show();
        }
    }


}



