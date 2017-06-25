package widget;

import android.app.Dialog;
import android.content.Context;

import com.example.viencent.sport.R;

/**
 * 自定义分享Dialog
 * Created by Vincent on 2016/11/9.
 */
public class ShareDialog extends Dialog {
    public ShareDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.setContentView(R.layout.share_dialog_layout);
    }

//    dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
//    view = LayoutInflater.from(this).inflate(R.layout.share_dialog_layout, null);
//    dialog.setContentView(view);//将布局设置给Dialog
//    Window dialogWindow = dialog.getWindow();//获取当前Activity的窗体
//    dialogWindow.setGravity(Gravity.BOTTOM);//设置窗体从底部弹出
//    WindowManager.LayoutParams lp = dialogWindow.getAttributes();//获取窗体的属性
//    lp.y = 20;//设置Dialog距离底部的距离
//    dialogWindow.setAttributes(lp);//将属性设置给弹窗
//    dialog.show();//显示对话框
//    initView();
//    break;
}
