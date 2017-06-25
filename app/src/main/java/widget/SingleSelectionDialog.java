package widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.viencent.sport.R;

/**
 * Created by Vincent on 2017/2/18.
 */

public class SingleSelectionDialog extends Dialog {


    public SingleSelectionDialog(Context context, int style) {
        super(context, style);
    }


    public static class Builder {
        private Context context;
        private String content;
        private View.OnClickListener listeer;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            if (TextUtils.isEmpty(message)) {
                this.content = "";
            } else {
                this.content = message;
            }
            return this;
        }

        public Builder setOnFinishListener(View.OnClickListener listener) {
            this.listeer = listener;
            return this;
        }

        public SingleSelectionDialog create() {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_single, null);
            SingleSelectionDialog dialog = new SingleSelectionDialog(context, R.style.customDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//设置取消dialog自带title
            dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            int w = ((WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
            dialog.setContentView(view, new ViewGroup.LayoutParams((int) (w * 0.7)
                    , ViewGroup.LayoutParams.WRAP_CONTENT));
            ((TextView) view.findViewById(R.id.tv_single_context)).setText(content);
            TextView v = (TextView) view.findViewById(R.id.tv_single_finish);
            v.setOnClickListener(listeer);
            dialog.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                    return false;

                }
            });
            return dialog;
        }

    }
}
