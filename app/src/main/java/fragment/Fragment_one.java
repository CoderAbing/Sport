package fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.viencent.sport.AnimationSkipActivity;
import com.example.viencent.sport.R;

import application.MyApplication;
import widget.SingleSelectionDialog;

/**
 * Created by Viencent on 2016/7/19.
 */
public class Fragment_one extends Fragment implements View.OnClickListener {
    private TextView myLocation;
    private ImageButton stratSport;
    private Context context;
    private LocationClient locationClient;
    private ImageButton walk, run, ride;
    private TextView tvTotalMileage;
    private SingleSelectionDialog dialog = null;
    private static int STATE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, null);
        this.context = inflater.getContext();
        myLocation = (TextView) view.findViewById(R.id.text_MyLocation);
        stratSport = (ImageButton) view.findViewById(R.id.strat_sport);
        tvTotalMileage = (TextView) view.findViewById(R.id.tv_total_mileage);
        AssetManager assets = getContext().getAssets();
        Typeface fromAsset = Typeface.createFromAsset(assets, "fonts/din_condensed_bold.ttf");
        tvTotalMileage.setTypeface(fromAsset);
        tvTotalMileage.setText("78.9");
        walk = (ImageButton) view.findViewById(R.id.walk);
        run = (ImageButton) view.findViewById(R.id.run);
        ride = (ImageButton) view.findViewById(R.id.ride);
        stratSport.setOnClickListener(this);
        run.setOnClickListener(this);
        walk.setOnClickListener(this);
        ride.setOnClickListener(this);
        getAddress();
        return view;

    }

    //获取当前位置
    private void getAddress() {
        locationClient = new LocationClient(context);
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (!(location.getAddrStr() == null)) {
                    myLocation.setText(location.getAddrStr());
                } else {
                    myLocation.setText("获取当前位置失败");
                }


            }
        });
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        locationClient.setLocOption(option);
    }


    //关闭弹窗
    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.strat_sport:

                if (!MyApplication.isOpenGPS(getContext())) {
                    //判断是否开启GPS
                    dialog = new SingleSelectionDialog.Builder(getContext()).setMessage("系统未获取到位置信息，请先打开GPS再执行下一步操作。").setOnFinishListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                            dismiss();
                        }
                    }).create();
                    dialog.show();
                } else if (!MyApplication.checkNetworkAvailable(getContext())) {
                    //判断是否打开数据流量
                    dialog = new SingleSelectionDialog.Builder(getContext()).setMessage("系统未获取到数据流量，请先打开数据流量再执行下一步操作。").setOnFinishListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS));
                            dismiss();
                        }
                    }).create();
                    dialog.show();
                } else {
                    Intent intent = new Intent(getActivity(), AnimationSkipActivity.class);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.walk:
                walk.setImageResource(R.mipmap.btn_walk_choice);
                run.setImageResource(R.mipmap.btn_run);
                ride.setImageResource(R.mipmap.btn_ride);
                STATE = 1;
                break;
            case R.id.run:
                walk.setImageResource(R.mipmap.btn_walk);
                run.setImageResource(R.mipmap.btn_run_choice);
                ride.setImageResource(R.mipmap.btn_ride);
                STATE = 2;
                break;
            case R.id.ride:
                walk.setImageResource(R.mipmap.btn_walk);
                run.setImageResource(R.mipmap.btn_run);
                ride.setImageResource(R.mipmap.btn_ride_choice);
                STATE = 3;
                break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        locationClient.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        locationClient.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        STATE = 0;
    }
}
