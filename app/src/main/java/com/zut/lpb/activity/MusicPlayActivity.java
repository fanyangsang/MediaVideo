package com.zut.lpb.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zut.lpb.R;
import com.zut.lpb.base.BaseActivity;

import butterknife.BindView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MusicPlayActivity extends BaseActivity {
    @BindView(R.id.gif_auth)
    GifImageView gifAuth;
    @BindView(R.id.tv_congra)
    TextView tvCongra;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @Override
    protected int getLayout() {
        return R.layout.activity_music_play;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        tvCongra.setText(title);
        //进行音乐的循环播放
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(url));
        mediaPlayer.start();
        //循环进行ui的转动
        GifDrawable gifDrawable = (GifDrawable) gifAuth.getDrawable();
        gifDrawable.setLoopCount(0);
        ivBack.setOnClickListener(view -> {
            mediaPlayer.stop();
            finish();
        });
    }

    @Override
    protected void initListener() {


    }

    @Override
    protected void initData() {

    }
}
