package com.zut.lpb.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zut.lpb.R;
import com.zut.lpb.activity.AboutActivity;
import com.zut.lpb.activity.VideoPlayActivity;
import com.zut.lpb.adapter.VideoListAdapter;
import com.zut.lpb.base.BaseFragment;
import com.zut.lpb.bean.VideoBean;
import com.zut.lpb.player.LiveVideo;
import com.zut.lpb.sqlite.DataBaseOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoListFragment extends BaseFragment {
    @BindView(R.id.rc_video_list)
    RecyclerView rcVideoList;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ed_search_name)
    EditText edSearchName;
    @BindView(R.id.tv_banner)
    Banner banner;
    private List<String> imgPaths;
    private List<String> titles;
    private SQLiteDatabase sqLiteDatabase;
    private VideoListAdapter videoListAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        initBanner();
        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(getContext(), "video_db");
        sqLiteDatabase = dataBaseOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM video WHERE type ='0'", null);
        Log.d("ManageFragment", "" + cursor.getCount());
        JSONArray jsonArray = new JSONArray();
        while (cursor.moveToNext()) {
            try {
                JSONObject jo = new JSONObject();
                jo.put("name", cursor.getString(cursor.getColumnIndex("name")));
                jo.put("type", cursor.getString(cursor.getColumnIndex("type")));
                jo.put("createTime", cursor.getString(cursor.getColumnIndex("create_time")));
                jo.put("url", cursor.getString(cursor.getColumnIndex("url")));
                jsonArray.put(jo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //解析JSONArray
        Gson gson = new Gson();
        List<VideoBean> videoBeanList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                VideoBean videoBean = gson.fromJson(jsonArray.get(i).toString(), VideoBean.class);
                videoBeanList.add(videoBean);
            }

        } catch (Exception e) {
        }
        LinearLayoutManager ms = new LinearLayoutManager(getContext());
        // ms.setOrientation(LinearLayoutManager.HORIZONTAL); //设置成横向滑动
        rcVideoList.setLayoutManager(ms);
        videoListAdapter = new VideoListAdapter(videoBeanList);
        rcVideoList.setAdapter(videoListAdapter);

        if (videoListAdapter instanceof VideoListAdapter) {
            videoListAdapter.setOnItemClickListener((adapter, view1, position) -> {
                try {
                    VideoBean videoBean = gson.fromJson(jsonArray.get(position).toString(), VideoBean.class);
                    Intent intent = new Intent(getContext(), VideoPlayActivity.class);
                    intent.putExtra("url", videoBean.getUrl());
                    intent.putExtra("title", videoBean.getName());
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            });
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }

    @Override
    protected void initListener(View view) {
        // 搜索姓名
        edSearchName.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(getContext(), "video_db");
                sqLiteDatabase = dataBaseOpenHelper.getWritableDatabase();
                String name = edSearchName.getText().toString();
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM video WHERE type ='0' AND name LIKE" + "'%" + name + "%'", null);
                Log.d("ManageFragment", "" + cursor.getCount());
                JSONArray jsonArray = new JSONArray();
                while (cursor.moveToNext()) {
                    try {
                        JSONObject jo = new JSONObject();
                        jo.put("name", cursor.getString(cursor.getColumnIndex("name")));
                        jo.put("type", cursor.getString(cursor.getColumnIndex("type")));
                        jo.put("createTime", cursor.getString(cursor.getColumnIndex("create_time")));
                        jo.put("url", cursor.getString(cursor.getColumnIndex("url")));
                        jsonArray.put(jo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //解析JSONArray
                Gson gson = new Gson();
                List<VideoBean> videoBeanList = new ArrayList<>();
                try {
                    for (int j = 0; j < jsonArray.length(); j++) {
                        VideoBean videoBean = gson.fromJson(jsonArray.get(j).toString(), VideoBean.class);
                        videoBeanList.add(videoBean);
                    }

                } catch (Exception e) {
                }
                LinearLayoutManager ms = new LinearLayoutManager(getContext());
                // ms.setOrientation(LinearLayoutManager.HORIZONTAL); //设置成横向滑动
                rcVideoList.setLayoutManager(ms);
                videoListAdapter = new VideoListAdapter(videoBeanList);
                rcVideoList.setAdapter(videoListAdapter);

                if (videoListAdapter instanceof VideoListAdapter) {
                    videoListAdapter.setOnItemClickListener((adapter, view1, position) -> {
                        try {
                            VideoBean videoBean = gson.fromJson(jsonArray.get(position).toString(), VideoBean.class);
                            Intent intent = new Intent(getContext(), VideoPlayActivity.class);
                            intent.putExtra("url", videoBean.getUrl());
                            intent.putExtra("title", videoBean.getName());
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    });
                }
                return true;
            }
            return false;
        });

        //监听搜索的按钮
        tvSearch.setOnClickListener(v -> {
            DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(getContext(), "video_db");
            sqLiteDatabase = dataBaseOpenHelper.getWritableDatabase();
            String name = edSearchName.getText().toString();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM video WHERE type ='0' AND name LIKE" + "'%" + name + "%'", null);
            Log.d("ManageFragment", "" + cursor.getCount());
            JSONArray jsonArray = new JSONArray();
            while (cursor.moveToNext()) {
                try {
                    JSONObject jo = new JSONObject();
                    jo.put("name", cursor.getString(cursor.getColumnIndex("name")));
                    jo.put("type", cursor.getString(cursor.getColumnIndex("type")));
                    jo.put("createTime", cursor.getString(cursor.getColumnIndex("create_time")));
                    jo.put("url", cursor.getString(cursor.getColumnIndex("url")));
                    jsonArray.put(jo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //解析JSONArray
            Gson gson = new Gson();
            List<VideoBean> videoBeanList = new ArrayList<>();
            try {
                for (int j = 0; j < jsonArray.length(); j++) {
                    VideoBean videoBean = gson.fromJson(jsonArray.get(j).toString(), VideoBean.class);
                    videoBeanList.add(videoBean);
                }

            } catch (Exception e) {
            }
            LinearLayoutManager ms = new LinearLayoutManager(getContext());
            // ms.setOrientation(LinearLayoutManager.HORIZONTAL); //设置成横向滑动
            rcVideoList.setLayoutManager(ms);
            videoListAdapter = new VideoListAdapter(videoBeanList);
            rcVideoList.setAdapter(videoListAdapter);

            if (videoListAdapter instanceof VideoListAdapter) {
                videoListAdapter.setOnItemClickListener((adapter, view1, position) -> {
                    try {
                        VideoBean videoBean = gson.fromJson(jsonArray.get(position).toString(), VideoBean.class);
                        Intent intent = new Intent(getContext(), VideoPlayActivity.class);
                        intent.putExtra("url", videoBean.getUrl());
                        intent.putExtra("title", videoBean.getName());
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                });
            }

        });
    }

    private void initBanner() {
        imgPaths = new ArrayList<>();
        imgPaths.add("file:///android_asset/pic_005.jpeg");
        imgPaths.add("file:///android_asset/pic_003.jpeg");
        imgPaths.add("file:///android_asset/pic_004.jpeg");
        imgPaths.add("file:///android_asset/pic_001.jpeg");
//        imgPaths.add("file:///android_asset/pic_002.jpeg");
        titles = new ArrayList<>();
        titles.add("聚焦中工流量平台");
        titles.add("打造龙湖网红主播");
        titles.add("创造新郑视频神话");
        titles.add("全新改版，欢迎体验");

        //设置banner样式

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new ImageLoader() {
                                  @Override
                                  public void displayImage(Context context, Object path, ImageView imageView) {
                                      imageView.setPadding(20, 0, 20, 0);
                                      Glide.with(context).asBitmap().load((String) path).listener(new RequestListener<Bitmap>() {
                                          @Override
                                          public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                              return false;
                                          }

                                          @Override
                                          public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                              return false;
                                          }
                                      }).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).into(imageView);
                                  }
                              }

        );
        banner.setOnBannerListener(position -> {
         Intent intent = new Intent(getContext(), AboutActivity.class);
         startActivity(intent);
        });
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.setImages(imgPaths);
        banner.setBannerTitles(titles);
        banner.start();
    }

    @Override
    protected void initData() {

    }
}
