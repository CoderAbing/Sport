package com.example.viencent.sport;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;

import application.MyApplication;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 运动界面
 * Created by Vincent on 2016/10/3.
 */
public class SportActivity extends Activity {
    @Bind(R.id.sport_map)
    MapView sportMap;
    @Bind(R.id.tv_sport_distence)
    TextView tvSportDistence;
    @Bind(R.id.tv_sport_duration)
    TextView tvSportDuration;
    @Bind(R.id.tv_sport_speed)
    TextView tvSportSpeed;
    @Bind(R.id.iv_sport_stop)
    ImageView ivSportStop;


    //设置轨迹唯一标记(手机品牌+型号+IMEI)
//    private final String IMEI = android.os.Build.BRAND + Build.MODEL + ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);
        ButterKnife.bind(this);
        MyApplication.getSingleton().addActivity(this);//将Activity加入到容器同意管理
        initView();
    }

    private void initView() {
        AssetManager assets = this.getAssets();
        Typeface fromAsset = Typeface.createFromAsset(assets, "fonts/din_condensed_bold.ttf");
        tvSportDistence.setTypeface(fromAsset);
        tvSportDistence.setTextSize(30);
        tvSportDuration.setTypeface(fromAsset);
        tvSportDuration.setTextSize(45);
        tvSportSpeed.setTypeface(fromAsset);
        tvSportSpeed.setTextSize(30);
    }


    @OnClick(R.id.iv_sport_stop)
    public void onClick() {
    }
}
