package Bean;

import com.example.viencent.sport.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 地图场馆实体类
 * Created by Viencent on 2016/7/29.
 */

public class Stadium implements Serializable {
    private static final long serialVersionUID = -57635324135676475l;
    private String name, distance, location;//名称，距离，位置
    private double latitude, longitude;//经度和纬度
    private int imgId, like;



    public Stadium(double latitude, double longitude, int imgId, String name,
                   String distance, int like) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.imgId = imgId;
        this.name = name;
        this.distance = distance;
        this.like = like;
    }

    public Stadium() {

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiatance() {
        return distance;
    }

    public void setDiatance(String diatance) {
        this.distance = diatance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int zan) {
        this.like = zan;
    }


}
