package mode;


import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;

/**
 * 用于检索附近场馆信息返回数据给View
 * Created by Vincent on 2016/12/23.
 */
public class StadiumData {

    private OnGetPoiSearchResultListener listener;

    public StadiumData(OnGetPoiSearchResultListener listener) {
        this.listener = listener;

    }

    private PoiSearch poiSearch;

    /**
     * 向百度地图云端发起请求检索附近场馆
     */
    public void searchPOI(LatLng latLng) {

        poiSearch = PoiSearch.newInstance();

        poiSearch.setOnGetPoiSearchResultListener(listener);

        PoiNearbySearchOption option = new PoiNearbySearchOption();
        option.location(latLng);//中心点
        option.keyword("健身房").//关键字
                radius(5000)//范围
                .pageNum(0)//页码
                .pageCapacity(15);//每一页Size

        poiSearch.searchNearby(option);
    }

    //根据商家的UID查询详细信息
    public void searchPoiDetail(String UID) {

        if (poiSearch != null) {
            poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(UID));
        }

    }

}
