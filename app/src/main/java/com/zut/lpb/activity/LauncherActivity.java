package com.zut.lpb.activity;

import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SPUtils;
import com.zut.lpb.R;
import com.zut.lpb.base.BaseActivity;
import com.zut.lpb.sqlite.DataBaseOpenHelper;
import com.zut.lpb.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class LauncherActivity extends BaseActivity {
    @BindView(R.id.rl_happy)
    RelativeLayout rlHappy;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected int getLayout() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT > 26) {
            ObjectAnimator animator4 = ObjectAnimator.ofFloat(rlHappy, "alpha", 0, 1);
            animator4.setDuration(2500);
            animator4.start();
        }
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
        //进行创建数据库的操作，并往视频表里添加一些信息
        if (SPUtils.getInstance().getBoolean("isFirst",true)) {

            DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(this, "video_db");
            sqLiteDatabase = dataBaseOpenHelper.getWritableDatabase();


            Map<String, String> map1 = new HashMap<>();
            map1.put("测试视频1", "https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218114723HDu3hhxqIT.mp4");
            map1.put("测试视频2", "https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218093206z8V1JuPlpe.mp4");
            map1.put("测试视频3", "https://stream7.iqilu.com/10339/article/202002/18/2fca1c77730e54c7b500573c2437003f.mp4");
            map1.put("测试视频4", "https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218025702PSiVKDB5ap.mp4");
            map1.put("测试视频5", "https://stream7.iqilu.com/10339/upload_transcode/202002/18/202002181038474liyNnnSzz.mp4");
            map1.put("测试视频6", "https://stream7.iqilu.com/10339/article/202002/18/02319a81c80afed90d9a2b9dc47f85b9.mp4");

            for (Map.Entry<String, String> entry : map1.entrySet()) {
                //在video表中新增一条记录
                // 创建ContentValues对象
                ContentValues values1 = new ContentValues();
                // 向该对象中插入键值对
                values1.put("url", entry.getValue());
                values1.put("type", "0");
                values1.put("name", entry.getKey());
                values1.put("create_time", Utils.getSystemTime());
                // 调用insert()方法将数据插入到数据库当中
                sqLiteDatabase.insert("video", null, values1);
            }

            Map<String, String> map2 = new HashMap<>();
            map2.put("测试音频1", "http://downsc.chinaz.net/Files/DownLoad/sound1/201906/11582.mp3");
            map2.put("测试音频2", "http://downsc.chinaz.net/files/download/sound1/201206/1638.mp3");
            map2.put("测试音频3", "http://downsc.chinaz.net/Files/DownLoad/sound1/201906/11582.mp3");

            for (Map.Entry<String, String> entry : map2.entrySet()) {
                //在video表中新增一条记录
                // 创建ContentValues对象
                ContentValues values1 = new ContentValues();
                // 向该对象中插入键值对
                values1.put("url", entry.getValue());
                values1.put("type", "1");
                values1.put("name", entry.getKey());
                values1.put("create_time", Utils.getSystemTime());
                // 调用insert()方法将数据插入到数据库当中
                sqLiteDatabase.insert("video", null, values1);
            }


            sqLiteDatabase.close();
            SPUtils.getInstance().put("isFirst",false);

        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LauncherActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000); //android里的延时操作

    }
}
