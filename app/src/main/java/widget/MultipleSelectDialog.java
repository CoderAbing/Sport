package widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.viencent.sport.R;

/**
 * 自定义多选弹窗
 * Created by Vincent on 2017/2/19.
 */

public class MultipleSelectDialog extends Dialog {

    public MultipleSelectDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    public static class Builder {
        private Context context;
        private String content;
        private TextView cancel, finish;
        private View.OnClickListener okListeer;
        private View.OnClickListener noListener;

        public Builder(Context context) {
            this.context = context;
        }

        public MultipleSelectDialog.Builder setMessage(String message) {
            if (TextUtils.isEmpty(message)) {
                this.content = "";
            } else {
                this.content = message;
            }
            return this;
        }

        public MultipleSelectDialog.Builder setOnFinishListener(View.OnClickListener listener) {
            this.okListeer = listener;
            return this;
        }

        public MultipleSelectDialog.Builder setOnCancelListener(View.OnClickListener listener) {
            this.noListener = listener;
            return this;
        }

        public MultipleSelectDialog create() {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_multiple, null);
            MultipleSelectDialog dialog = new MultipleSelectDialog(context, R.style.customDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//设置取消dialog自带title
//            dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            dialog.setContentView(view, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT));
            ((TextView) view.findViewById(R.id.tv_multiple_context)).setText(content);
            finish = (TextView) view.findViewById(R.id.tv_multiple_finish);
            finish.setOnClickListener(okListeer);
            cancel = (TextView) view.findViewById(R.id.tv_multiple_cancel);
            cancel.setOnClickListener(noListener);
            return dialog;
        }

    }
}
