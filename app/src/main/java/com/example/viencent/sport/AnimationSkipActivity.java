package com.example.viencent.sport;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;

import java.io.InputStream;

import application.MyApplication;


/**
 * 跳转运动界面的过渡动画
 * Created by Vincent on 2017/2/18.
 */

public class AnimationSkipActivity extends Activity {

    private String TAG = "AnimationSkipActivity";
    private ImageView imageView;

    //动画所需图片
    private int[] images = {R.drawable.three_fingers, R.drawable.two_fingers, R.drawable.one_finger};

    private ObjectAnimator animat1, animat2, animat3, animat4 = null;

    private AnimatorSet animationSet = null;

    private Vibrator vibrator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_animation_skip);
        MyApplication.getSingleton().addActivity(this);
        imageView = (ImageView) findViewById(R.id.iv_skip_gesture);
        //   imageView.setImageResource(images[0]);
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(imageView, "scaleX", 0f, 1f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(imageView, "scaleY", 0f, 1f);
        final AnimatorSet animSet = new AnimatorSet();
        anim1.setInterpolator(new BounceInterpolator());
        anim2.setInterpolator(new BounceInterpolator());
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0f);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 0f);
        animSet.setDuration(1000);
        anim4.addListener(new Animator.AnimatorListener() {
            int i = 1;

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (i < 3) {
                    imageView.setImageResource(images[i]);
                    animSet.start();
                    i = i + 1;
                } else {
                    Intent intent = new Intent(imageView.getContext(), SportActivity.class);
                    startActivity(intent);
                    vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(700);
                    finish();
                    onDestroy();
                    return;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        //两个动画同时执行
        animSet.play(anim1).with(anim2);
        animSet.play(anim3).with(anim4).after(anim2);
        animSet.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        animationSet = null;
        animat1 = null;
        animat2 = null;
        animat3 = null;
        animat4 = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
