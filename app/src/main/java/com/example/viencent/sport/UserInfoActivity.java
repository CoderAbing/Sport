package com.example.viencent.sport;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.CityPickerView;

import application.MyApplication;


/**
 * Created by Vincent on 2016/9/23.
 */
public class UserInfoActivity extends Activity {


    private Button saveInfo;
    private ImageButton back;
    private LinearLayout statureLayout;
    private EditText stature;
    public TextView province;
    private View.OnClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four_user_info);
        MyApplication.getSingleton().addActivity(this);
        saveInfo = (Button) findViewById(R.id.save_info);
        statureLayout = (LinearLayout) findViewById(R.id.info_layout_stature);
        stature = (EditText) findViewById(R.id.info_stature);
        province = (TextView) findViewById(R.id.info_province);
        back = (ImageButton) findViewById(R.id.news_back);
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                switch (id) {
                    case R.id.news_back:
                        finish();
                        break;
                    case R.id.save_info:
                        finish();
                        break;
                    case R.id.info_province:
                        //设置城市选择的属性
                        CityPickerView cityPicker = new CityPickerView(UserInfoActivity.this);
                        // 设置点击外部是否消失
                        cityPicker.setCancelable(true);
                        // 设置滚轮字体大小
                        cityPicker.setTextSize(18f);
                        /// 设置标题
                        cityPicker.setTitle("选择地区");
                        // 设置取消文字
                        cityPicker.setCancelText("取消");
                        // 设置取消文字颜色
                        cityPicker.setCancelTextColor(Color.BLACK);
                        // 设置取消文字大小
                        cityPicker.setCancelTextSize(14f);
                        // 设置确定文字
                        cityPicker.setSubmitText("确定");
                        // 设置确定文字颜色
                        cityPicker.setSubmitTextColor(Color.BLACK);
                        // 设置确定文字大小
                        cityPicker.setSubmitTextSize(14f);
                        // 设置头部背景
                        //cityPicker.setHeadBackgroundColor(Color.RED);
                        cityPicker.show();
                        cityPicker.setOnCitySelectListener(new CityPickerView.OnCitySelectListener() {
                            @Override
                            public void onCitySelect(String provinces, String city, String zone) {
                                String str = null;
                                if (TextUtils.isEmpty(city)) {
                                    Log.e("city", city);
                                    str = provinces + "-" + zone;
                                } else if (TextUtils.isEmpty(zone)) {
                                    Log.e("zone", zone);
                                    str = provinces + "-" + city;
                                } else {
                                    str = provinces + "-" + city + "-" + zone;
                                    Log.e("pro",str );
                                }
                                province.setText(str);
                            }


                        });
                        break;

                }
            }
        };
        province.setOnClickListener(listener);
        back.setOnClickListener(listener);
        saveInfo.setOnClickListener(listener);


        statureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stature.requestFocus();
                InputMethodManager imm = (InputMethodManager) stature.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        });
    }


}
