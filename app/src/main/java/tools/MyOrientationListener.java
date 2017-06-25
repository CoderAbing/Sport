package tools;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 判断手机方向
 * Created by Viencent on 2016/7/28.
 */
public class MyOrientationListener implements SensorEventListener {

    private SensorManager sensorManager;
    private Context context;
    private Sensor sensor;

    private OnOritationListener oritationListener;

    private float lastX;

    public MyOrientationListener(Context context) {
        this.context = context;
    }

    public void start() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            //获得方向传感器
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float x = event.values[SensorManager.DATA_X];
            if (Math.abs(x - lastX) > 0.1) {
                if (oritationListener != null) {
                    oritationListener.onOrientationChanged(x);
                }
            }
            lastX = x;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void setOritationListener(OnOritationListener oritationListener) {
        this.oritationListener = oritationListener;
    }

    public interface OnOritationListener {
        void onOrientationChanged(float x);
    }

}

