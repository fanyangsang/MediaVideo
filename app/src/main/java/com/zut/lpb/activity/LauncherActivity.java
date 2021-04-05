package com.zut.lpb.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

import com.zut.lpb.R;
import com.zut.lpb.base.BaseActivity;

import butterknife.BindView;

public class LauncherActivity extends BaseActivity {
    @BindView(R.id.rl_happy)
    RelativeLayout rlHappy;
    @Override
    protected int getLayout() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(rlHappy,"alpha", 0, 1);
        animator4.setDuration(2500);
        animator4.start();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //进行创建数据库的操作


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LauncherActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000); //android里的延时操作

    }
}
