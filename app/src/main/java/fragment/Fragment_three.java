package fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.viencent.sport.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Bean.Stadium;
import application.MyApplication;
import mode.StadiumData;
import tools.MyOrientationListener;
import widget.CheckNavigationPopup;


/**
 * 附近场馆
 * Created by Viencent on 2016/7/19.
 */
public class Fragment_three extends Fragment implements OnGetPoiSearchResultListener {
    private MapView mapView;
    private BaiduMap baiduMap;
    private View view;
    private Context context;
    private ImageButton traffic, myLocation, common, site, vicinity;

    //定位相关的变量
    private LocationClient locationClient;
    private MyLocationListenner locationLinstener;
    private Boolean isFirstIn = true;
    private double latitude, longtitude;//当前经纬度
    //自定义定位图标
    private BitmapDescriptor iconLocation;
    private MyOrientationListener orientationListener;
    private float currentX;
    //覆盖物相关变量
    private BitmapDescriptor mMarker;
    private RelativeLayout markerLy;

    //获取附近商家信息的底层对象
    private StadiumData stadiumData;

    private List<PoiInfo> stadiums;


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.common:
                    baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    break;
                case R.id.site:
                    baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    break;
                case R.id.traffic:
                    if (baiduMap.isTrafficEnabled()) {
                        baiduMap.setTrafficEnabled(false);
                        traffic.setImageResource(R.mipmap.traffic);
                    } else {
                        baiduMap.setTrafficEnabled(true);
                        traffic.setImageResource(R.mipmap.traffic_choice);
                    }
                    break;
                case R.id.myLocation:
                    centerToMyLocation();
                    break;
                case R.id.vicinity:
                    if (stadiumData == null) {
                        stadiumData = new StadiumData(Fragment_three.this);
                    }
                    //addOverlays(Stadium.stadia);
                    stadiumData.searchPOI(new LatLng(latitude, longtitude));
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplication());
        view = inflater.inflate(R.layout.fragment_three, null);
        context = getActivity();
        initView();
        initLocation();
        initinitMarker();
        overlayListenner();


        //方法里面的参数是设置地图初始化时的显示比例
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        baiduMap.setMapStatus(msu);
        return view;
    }

    //初始化地图的覆盖物图标和弹出布局
    private void initinitMarker() {
        mMarker = BitmapDescriptorFactory.fromResource(R.mipmap.stadium);
        markerLy = (RelativeLayout) view.findViewById(R.id.stadium_info);

    }

    private void initLocation() {
        locationClient = new LocationClient(this.getActivity().getApplicationContext());
        locationLinstener = new MyLocationListenner();
        locationClient.registerLocationListener(locationLinstener);

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true); //要想获取位置必须设置这个方法为True
        option.setOpenGps(true);
        option.setScanSpan(1000);
        locationClient.setLocOption(option);
        //初始化图标
        iconLocation = BitmapDescriptorFactory.fromResource(R.mipmap.orientation);
        orientationListener = new MyOrientationListener(context);

        orientationListener.setOritationListener(new MyOrientationListener.OnOritationListener() {
            @Override
            public void onOrientationChanged(float x) {
                currentX = x;
            }
        });
    }

    //添加覆盖物
    private void addOverlays(List<PoiInfo> stadiums) {
        baiduMap.clear();
        LatLng latLng = null;
        Marker marker = null;
        LatLng l = new LatLng(latitude, longtitude);
        OverlayOptions option;
        for (PoiInfo stadium : stadiums) {
            LatLng location = stadium.location;
            latLng = new LatLng(location.latitude, location.longitude);
            option = new MarkerOptions().position(latLng).icon(mMarker).zIndex(5);
            marker = (Marker) baiduMap.addOverlay(option);
            Bundle bundle = new Bundle();
            //将数据绑定在marker中
            bundle.putString("name", stadium.name);
            bundle.putString("phone", stadium.phoneNum);
            bundle.putString("address", stadium.address);
            bundle.putDouble("lat", stadium.location.latitude);
            bundle.putDouble("long", stadium.location.longitude);
            marker.setExtraInfo(bundle);
        }
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.setMapStatus(msu);


    }

    private void centerToMyLocation() {
        LatLng latLng = new LatLng(latitude, longtitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(latLng, 15f);
        baiduMap.animateMapStatus(msu);
    }

    //初始化控件添加点击事件
    private void initView() {


        //初始化地图
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.showZoomControls(false);
        baiduMap = mapView.getMap();

        //初始化控件
        common = (ImageButton) view.findViewById(R.id.common);
        site = (ImageButton) view.findViewById(R.id.site);
        traffic = (ImageButton) view.findViewById(R.id.traffic);
        myLocation = (ImageButton) view.findViewById(R.id.myLocation);
        vicinity = (ImageButton) view.findViewById(R.id.vicinity);

        //设置按钮的背景图片透明度
        traffic.getBackground().setAlpha(170);
        site.getBackground().setAlpha(170);
        common.getBackground().setAlpha(170);

        //添加点击事件
        common.setOnClickListener(listener);
        site.setOnClickListener(listener);
        traffic.setOnClickListener(listener);
        myLocation.setOnClickListener(listener);
        vicinity.setOnClickListener(listener);
    }

    //开启定位
    @Override
    public void onStart() {
        super.onStart();
        baiduMap.setMyLocationEnabled(true);
        //判断是否启动
        if (!locationClient.isStarted())
            locationClient.start();
        //开启方向传感器
        orientationListener.start();


    }

    @Override
    public void onStop() {
        super.onStop();
        //停止定位
        baiduMap.setMyLocationEnabled(false);
        locationClient.stop();
        //停止方向传感器
        orientationListener.stop();
    }

    //将地图的生命周期和碎片的生命周期绑定
    @Override
    public void onPause() {
        mapView.setVisibility(View.INVISIBLE);
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mapView.setVisibility(View.VISIBLE);
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    //给地图覆盖物添加点击事件
    public void overlayListenner() {
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //将绑定的在marker里面的数据取出来
                final Bundle extraInfo = marker.getExtraInfo();
                //实例化弹窗里面的控件
                TextView stadiumName = (TextView) markerLy.findViewById(R.id.stadium_name);
                TextView stadiumLocation = (TextView) markerLy.findViewById(R.id.stadium_location);
                TextView stadiumDistance = (TextView) markerLy.findViewById(R.id.stadium_distance);
                final TextView stadiumPhone = (TextView) markerLy.findViewById(R.id.stadium_phone);


                //开始导航按钮
                ImageButton depart = (ImageButton) markerLy.findViewById(R.id.depart);
                depart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        baiduMap.clear();
                        markerLy.setVisibility(View.GONE);
                        centerToMyLocation();
                        CheckNavigationPopup checkNavigationPopup = new CheckNavigationPopup(getContext(), new LatLng(latitude, longtitude), new LatLng(extraInfo.getDouble("lat"), extraInfo.getDouble("long")),extraInfo.getString("address"));
                        checkNavigationPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);

                        //Toast.makeText(context, "暴击一万次即可开始导航", Toast.LENGTH_SHORT).show();
                    }
                });
                //实现点击号码拨打商家电话
                stadiumPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phoneNum = stadiumPhone.getText().toString();
                        if (!TextUtils.isEmpty(phoneNum) && !phoneNum.equals("暂无电话号码")) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "不可拨打电话", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                int distance = (int) DistanceUtil.getDistance(new LatLng(latitude, longtitude), new LatLng(extraInfo.getDouble("lat"), extraInfo.getDouble("long")));
                if (distance > -1) {
                    if (distance < 1000) {
                        stadiumDistance.setText("距离" + distance + "m");
                    } else {
                        stadiumDistance.setText("距离" + Math.round(distance / 100d) / 10d + "km");
                    }
                }

                stadiumName.setText(extraInfo.getString("name", "未知"));

                stadiumLocation.setText(extraInfo.getString("address", "未知"));
                if (!TextUtils.isEmpty(extraInfo.getString("phone"))) {

                    stadiumPhone.setText(extraInfo.getString("phone", "未知"));
                } else {
                    stadiumPhone.setText("暂无电话号码");
                }


                //设置布局为可见
                markerLy.setVisibility(View.VISIBLE);
                return true;
            }
        });

        //点击其他地方弹窗消失
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                markerLy.setVisibility(View.GONE);
                baiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

    }


    //接口方法返回附近的健身场馆
    @Override
    public void onGetPoiResult(PoiResult poiResult) {

        if (stadiums == null) {
            stadiums = new ArrayList<>();
        }
        stadiums = poiResult.getAllPoi();

        if (stadiums != null && stadiums.size() > 0) {
            addOverlays(stadiums);
        } else {
            Toast.makeText(getContext(), "暂未周边查询到数据", Toast.LENGTH_SHORT).show();
        }


    }


    //根据UID查询到商家详细信息回调
    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    //室内检索回调
    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }


    private class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            MyLocationData data = new MyLocationData.Builder()
                    .direction(currentX)
                    .accuracy(bdLocation.getRadius())
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();

            //设置定位图标
            MyLocationConfiguration configuration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, iconLocation);
            baiduMap.setMyLocationConfigeration(configuration);
            baiduMap.setMyLocationData(data);
            latitude = bdLocation.getLatitude();
            longtitude = bdLocation.getLongitude();

            if (isFirstIn) {
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(msu);
                isFirstIn = false;
            }

        }
    }
}
