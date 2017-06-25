package widget;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.viencent.sport.R;

/**
 * 点击用户头像弹出菜单修改头像
 * Created by Viencent on 2016/8/17.
 */
public class UserHeadPopupwindow extends PopupWindow {
    private Button camera, photo, cancel;
    private View.OnClickListener listener;

    private View uHead;

    public UserHeadPopupwindow(Activity context, View.OnClickListener itemListenner) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //实例化布局和布局中的控件
        uHead = inflater.inflate(R.layout.user_head_change, null);
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        };
        //给大布局设置点击事件
        uHead.setOnClickListener(listener);
        camera = (Button) uHead.findViewById(R.id.camera);
        photo = (Button) uHead.findViewById(R.id.photo);
        cancel = (Button) uHead.findViewById(R.id.cancel);
        //给取消按钮添加点击事件
        cancel.setOnClickListener(listener);
        //设置其他按钮的点击事件
        camera.setOnClickListener(itemListenner);
        photo.setOnClickListener(itemListenner);
        //设置弹出窗口的布局
        this.setContentView(uHead);
        //设置弹窗的高和宽
        this.setWidth(ActionBar.LayoutParams.FILL_PARENT);
        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        //设置弹窗可点击
        this.setFocusable(true);
        //设置弹窗背景
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);//设置背景为透明的
        this.setBackgroundDrawable(dw);//设置背景
        //给布局添加触摸事件 如果点在布局的外面则销毁弹窗
        uHead.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = uHead.findViewById(R.id.pop_layout).getTop();
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
}
