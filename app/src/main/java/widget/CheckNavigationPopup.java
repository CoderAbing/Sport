package widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.example.viencent.sport.R;

import java.io.File;


/**
 * Created by Vincent on 2017/2/5.
 */
public class CheckNavigationPopup extends PopupWindow implements View.OnClickListener {

    private Context context;

    private TextView baiduMap;

    private TextView autoNaviMap;

    private TextView webMap;

    private String address;

    private TextView cancel;
    private LatLng origin;
    private LatLng destination;

    public CheckNavigationPopup(Context context, LatLng origin, LatLng destination, String address) {
        this.address = address;
        this.context = context;
        this.origin = origin;
        this.destination = destination;

        View contentView = LayoutInflater.from(context).inflate(R.layout.popu_navigation_popupwindow, null);
        baiduMap = (TextView) contentView.findViewById(R.id.btn_popup_navigation_baidu);
        autoNaviMap = (TextView) contentView.findViewById(R.id.btn_popup_navigation_autoNavi);
        webMap = (TextView) contentView.findViewById(R.id.btn_popup_navigation_web);
        cancel = (TextView) contentView.findViewById(R.id.btn_popup_navigation_cancel);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setTouchable(true);
        this.setContentView(contentView);
        baiduMap.setOnClickListener(this);
        autoNaviMap.setOnClickListener(this);
        webMap.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.btn_popup_navigation_baidu:
                if (isInstallByread("com.baidu.BaiduMap")) {
                    Intent intent = new Intent();
                    intent.setData(Uri.parse("baidumap://map/direction?region=beijing&origin=" + origin.latitude + "," + origin.longitude + "&destination=" + destination.latitude + "," + destination.longitude + "&mode=walking"));
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "请先安装百度地图客户端", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_popup_navigation_autoNavi:
                if (isInstallByread("com.autonavi.minimap")) {
                    Intent intent1 = new Intent();
                    intent1.setAction(Intent.ACTION_VIEW);
                    intent1.addCategory(Intent.CATEGORY_DEFAULT);
                    //将功能Scheme以URI的方式传入data
                    Uri uri = Uri.parse("androidamap://navi?sourceApplication=懒虫&poiname=fangheng&lat=" + destination.latitude + "&lon=" + destination.longitude + "8&dev=1&style=1");
                    intent1.setData(uri);

                    //启动该页面即可
                    context.startActivity(intent1);
                } else {
                    Toast.makeText(context, "请先安装高德地图客户端", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.btn_popup_navigation_web:
                Intent intent2 = new Intent(Intent.ACTION_VIEW);
                intent2.setData(Uri.parse("http://api.map.baidu.com/marker?location=" + destination.latitude + "," + destination.longitude + "&title=我的位置&content=" + address + "&output=html"));
                context.startActivity(intent2);
                break;
            case R.id.btn_popup_navigation_cancel:
                this.dismiss();
                break;

        }

    }

    /**
     * 判断是否安装目标应用
     *
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }
}
