package widget;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.viencent.sport.R;

import java.util.List;


/**
 * 选择城市的PoPupWindow
 * Created by Vincent on 2016/9/24.
 */
public class CityPopupWindow extends PopupWindow {
    private View contentView;
    private ListView listView;
    private ArrayAdapter adapter;
    private Activity activity;
    private int id;

    public CityPopupWindow(final Activity context, List<String> list) {
        this.activity = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popupwindow_city, null);
        listView = (ListView) contentView.findViewById(R.id.list_city);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                id = i;
                dismiss();
            }
        });
        adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        //设置弹窗要显示的布局
        this.setContentView(contentView);
        //设置窗体的宽度
        this.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        //设置弹窗的高度
        //设置窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //刷新状态
        this.update();
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.AnimationPreview);

        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = contentView.findViewById(R.id.layout_inner).getTop();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return false;
            }
        });

    }

    public int getid() {
        return id;
    }

    //显示弹窗
    public void showPopupwindow(View parent) {
        int h = activity.getWindowManager().getDefaultDisplay().getHeight();
        int w = activity.getWindowManager().getDefaultDisplay().getWidth();
        if (!this.isShowing()) {
            this.showAtLocation(parent, Gravity.CENTER, 0, 0);

        } else {
            this.dismiss();
        }
    }


}
