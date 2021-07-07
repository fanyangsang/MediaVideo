package com.zut.lpb.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.zut.lpb.R;
import com.zut.lpb.activity.MusicPlayActivity;
import com.zut.lpb.activity.VideoPlayActivity;
import com.zut.lpb.adapter.VideoListAdapter;
import com.zut.lpb.base.BaseFragment;
import com.zut.lpb.bean.VideoBean;
import com.zut.lpb.sqlite.DataBaseOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MusicListFragment extends BaseFragment {
    @BindView(R.id.rc_video_list)
    RecyclerView rcVideoList;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ed_search_name)
    EditText edSearchName;
    @BindView(R.id.tv_banner)
    Banner banner;
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
        banner.setVisibility(View.GONE);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(getContext(),"video_db");
            sqLiteDatabase = dataBaseOpenHelper.getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM video WHERE type ='1'",null);
            Log.d("ManageFragment",""+cursor.getCount());
            JSONArray jsonArray = new JSONArray();
            while (cursor.moveToNext()){
                try {
                    JSONObject jo = new JSONObject();
                    jo.put("name",cursor.getString(cursor.getColumnIndex("name")));
                    jo.put("type",cursor.getString(cursor.getColumnIndex("type")));
                    jo.put("createTime",cursor.getString(cursor.getColumnIndex("create_time")));
                    jo.put("url",cursor.getString(cursor.getColumnIndex("url")));
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
                    VideoBean videoBean = gson.fromJson(jsonArray.get(i).toString(),VideoBean.class);
                    videoBeanList.add(videoBean);
                }

            }catch (Exception e){
            }
            LinearLayoutManager ms = new LinearLayoutManager(getContext());
            // ms.setOrientation(LinearLayoutManager.HORIZONTAL); //设置成横向滑动
            rcVideoList.setLayoutManager(ms);
            videoListAdapter = new VideoListAdapter(videoBeanList);
            rcVideoList.setAdapter(videoListAdapter);

            if (videoListAdapter instanceof  VideoListAdapter){
                videoListAdapter.setOnItemClickListener((adapter, view1, position) -> {
                    try {
                        VideoBean videoBean = gson.fromJson(jsonArray.get(position).toString(), VideoBean.class);
                        if (videoBean.getUrl().endsWith("mp3") ||videoBean.getUrl().endsWith("MP3") ){
                            Intent intent = new Intent(getContext(), MusicPlayActivity.class);
                            intent.putExtra("url", videoBean.getUrl());
                            intent.putExtra("title", videoBean.getName());
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(getContext(), VideoPlayActivity.class);
                            intent.putExtra("url", videoBean.getUrl());
                            intent.putExtra("title", videoBean.getName());
                            startActivity(intent);
                        }
//                        Intent intent = new Intent();
//                        intent.setData(Uri.parse(videoBean.getUrl()));
//                        intent.setAction(Intent.ACTION_VIEW);
//                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                });
            }
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
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM video WHERE type ='1' AND name LIKE" + "'%" + name + "%'", null);
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
                            if (videoBean.getUrl().endsWith("mp3") ||videoBean.getUrl().endsWith("MP3") ){
                                Intent intent = new Intent(getContext(), MusicPlayActivity.class);
                                intent.putExtra("url", videoBean.getUrl());
                                intent.putExtra("title", videoBean.getName());
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(getContext(), VideoPlayActivity.class);
                                intent.putExtra("url", videoBean.getUrl());
                                intent.putExtra("title", videoBean.getName());
                                startActivity(intent);
                            }
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
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM video WHERE type ='1' AND name LIKE" + "'%" + name + "%'", null);
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
                        //如果url以mp3结尾，则直接跳转到Intent.ACTION_VIEW
                        if (videoBean.getUrl().endsWith("mp3") ||videoBean.getUrl().endsWith("MP3") ){
                            Intent intent = new Intent(getContext(), MusicPlayActivity.class);
                            intent.putExtra("url", videoBean.getUrl());
                            intent.putExtra("title", videoBean.getName());
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(getContext(), VideoPlayActivity.class);
                            intent.putExtra("url", videoBean.getUrl());
                            intent.putExtra("title", videoBean.getName());
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                });
            }

        });

    }

    @Override
    protected void initData() {

    }
}
