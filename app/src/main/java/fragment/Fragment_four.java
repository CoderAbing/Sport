package fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.viencent.sport.R;
import com.example.viencent.sport.RemindAcitvity;
import com.example.viencent.sport.SettingActivity;
import com.example.viencent.sport.StatisticsActivity;
import com.example.viencent.sport.UserInfoActivity;

import java.io.File;

import widget.CircleImageView;
import widget.UserHeadPopupwindow;

/**
 * Created by Viencent on 2016/7/19.
 */
public class Fragment_four extends Fragment {

    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "header.jpg";

    private UserHeadPopupwindow userHead;
    private CircleImageView user_head;
    private Activity context;
    private LinearLayout info, statistics, remind, setting;
    //给布局创建点击事件
    private View.OnClickListener lisetener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.layout_info:
                    Toast.makeText(context, "个人资料", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.layout_statistics:
                    Toast.makeText(context, "统计", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(getActivity(), StatisticsActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.layout_remind:
                    Toast.makeText(context, "提醒", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(getActivity(), RemindAcitvity.class);
                    startActivity(intent2);
                    break;
                case R.id.layout_setting:
                    Toast.makeText(context, "系统设置", Toast.LENGTH_SHORT).show();
                    Intent intent3 = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent3);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, null);
        user_head = (CircleImageView) view.findViewById(R.id.user_head);
        info = (LinearLayout) view.findViewById(R.id.layout_info);
        statistics = (LinearLayout) view.findViewById(R.id.layout_statistics);
        remind = (LinearLayout) view.findViewById(R.id.layout_remind);
        setting = (LinearLayout) view.findViewById(R.id.layout_setting);
        //给布局添加事件
        info.setOnClickListener(lisetener);
        statistics.setOnClickListener(lisetener);
        remind.setOnClickListener(lisetener);
        setting.setOnClickListener(lisetener);

        context = getActivity();
        user_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //实例化弹窗
                userHead = new UserHeadPopupwindow(context, itemLisener);
                //显示窗口
                userHead.showAtLocation(view.findViewById(R.id.user_head),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        return view;
    }

    //popupWindow点击点击事件
    private View.OnClickListener itemLisener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            userHead.dismiss();
            switch (view.getId()) {
                case R.id.camera:
                    //在这里是实现调用相机拍照
                    Toast.makeText(context, "点击了拍照", Toast.LENGTH_SHORT).show();
                    if (isSdcardExisting()) {
                        Intent cameraIntent = new Intent(
                                "android.media.action.IMAGE_CAPTURE");
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
                        cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                    } else {
                        Toast.makeText(context, "请插入sd卡", Toast.LENGTH_LONG)
                                .show();
                    }
                    break;
                case R.id.photo:
                    //在这里实现调用手机自带图库
                    Toast.makeText(context, "点击了相册", Toast.LENGTH_SHORT).show();
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
            }
        }
    };

    private boolean isSdcardExisting() {
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取图片地址
     */
    private Uri getImageUri() {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }


    /**
     * 修改图片的大小
     */
    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }

    /**
     * 判断Intent返回的结果
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    resizeImage(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (isSdcardExisting()) {
                        resizeImage(getImageUri());
                    } else {
                        Toast.makeText(context, "未找到存储卡，无法存储照片！",
                                Toast.LENGTH_LONG).show();
                    }
                    break;

                case RESIZE_REQUEST_CODE:
                    if (data != null) {
                        showResizeImage(data);
                    }
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 显示调整大小后的图片
     */
    private void showResizeImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            user_head.setImageDrawable(drawable);
        }
    }


}
