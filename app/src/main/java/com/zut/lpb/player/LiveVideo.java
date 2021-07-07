package com.zut.lpb.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.shuyu.gsyvideoplayer.player.IjkPlayerManager;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zut.lpb.R;

public class LiveVideo extends StandardGSYVideoPlayer {
    public LiveDataFullscreenButtonClick liveDataClick;//点击全屏按钮回调
    /**
     * 恢复暂停状态
     */
    public void onResume() {
        onVideoResume();
    }
    /**
     * 暂停状态
     */
    public void onPause() {
        onVideoPause();
    }

    /**
     * 接口回调
     * @param liveDataClick
     */
    public void setOnFullscreenButtonClick(LiveDataFullscreenButtonClick liveDataClick) {
        this.liveDataClick = liveDataClick;
    }

    /* 重写方法自定义layout id与video_layout_standard.xml一致 不重新使用系统默认布局*/
    @Override
    public int getLayoutId() {
        return R.layout.layout_video;
    }

    public LiveVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
        init();
    }

    public LiveVideo(Context context) {
        super(context);
        init();
    }

    public LiveVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /* 初始化操作 */
    private void init() {
//EXOPlayer内核，支持格式更多
// PlayerFactory.setPlayManager(Exo2PlayerManager.class);
//代理缓存模式，支持所有模式，不支持m3u8等，默认
// CacheFactory.setCacheManager(ProxyCacheManager.class);
//系统内核模式
// PlayerFactory.setPlayManager(SystemPlayerManager.class);
//ijk内核，默认模式
        PlayerFactory.setPlayManager(IjkPlayerManager.class);
        settingsVideo();
    }
    /* 一些播放器的设置 做一些UI的隐藏 可根据自己需求*/
    public void settingsVideo() {
        GSYVideoType.enableMediaCodec();//使能硬解码，播放前设置
        Debuger.enable();//打开GSY的Log
//隐藏一些UI
        setViewShowState(mBottomContainer, VISIBLE);
        setViewShowState(mTopContainer, VISIBLE);
        setViewShowState(mLockScreen, GONE);
        setViewShowState(mLoadingProgressBar, GONE);
        setViewShowState(mTopContainer, GONE);
        setViewShowState(mThumbImageView, GONE);
        setViewShowState(mBottomProgressBar, GONE);
//显示一些UI 进度 时间 当前时间 全屏 返回 加载Loading 暂停开始
        setViewShowState(mStartButton, VISIBLE);
        setViewShowState(mLoadingProgressBar, VISIBLE);
        setViewShowState(mFullscreenButton, VISIBLE);
        setViewShowState(mBackButton, GONE);
        setViewShowState(mProgressBar, VISIBLE);
        setViewShowState(mCurrentTimeTextView, VISIBLE);
        setViewShowState(mTotalTimeTextView, VISIBLE);
        setEnlargeImageRes(R.drawable.quanping);
        setShrinkImageRes(R.drawable.quanping);
    }

    //拦截事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mFullscreenButton.setOnClickListener(view -> liveDataClick.onClick());
        return super.dispatchTouchEvent(ev);
    }


    public interface LiveDataFullscreenButtonClick {
        void onClick();
    }
}
