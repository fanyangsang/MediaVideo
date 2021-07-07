package com.zut.lpb.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.zut.lpb.R;
import com.zut.lpb.base.BaseActivity;
import com.zut.lpb.player.LiveVideo;

import butterknife.BindView;

public class VideoPlayActivity extends BaseActivity {
    @BindView(R.id.live_player)
    LiveVideo liveVideo;
    @Override
    protected int getLayout() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        WindowManager wm = (WindowManager) this.getSystemService(this.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        ViewGroup.LayoutParams lp = liveVideo.getLayoutParams();
        lp.width = height+60;
        lp.height =width;
        liveVideo.setLayoutParams(lp);
        liveVideo.setUp(url, false, title);
        liveVideo.startPlayLogic();
        liveVideo.setOnFullscreenButtonClick(() -> {
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            liveVideo.onPause();
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
