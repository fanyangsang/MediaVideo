package com.zut.lpb.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.zut.lpb.R;
import com.zut.lpb.activity.AddUrlActivity;
import com.zut.lpb.adapter.VideoListAdapter;
import com.zut.lpb.adapter.VideoManageListAdapter;
import com.zut.lpb.base.BaseFragment;
import com.zut.lpb.bean.VideoBean;
import com.zut.lpb.sqlite.DataBaseOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ManageFragment extends BaseFragment {
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.rc_video_list)
    RecyclerView rcVideoList;
    private SQLiteDatabase sqLiteDatabase;
    private VideoManageListAdapter videoManageListAdapter;
    private AlertDialog.Builder builder;
    @Override
    protected int getLayout() {
        return R.layout.fragment_manage;
    }

    @Override
    protected void initView(View view) {
    }
    //由不可见变为可见时，加载列表

    @Override
    public void onResume() {
        super.onResume();
        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(getContext(),"video_db");
        sqLiteDatabase = dataBaseOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM video",null);
        Log.d("ManageFragment",""+cursor.getCount());
        JSONArray jsonArray = new JSONArray();
        while (cursor.moveToNext()){
            try {
                JSONObject jo = new JSONObject();
                jo.put("name",cursor.getString(cursor.getColumnIndex("name")));
                jo.put("type",cursor.getString(cursor.getColumnIndex("type")));
                jo.put("createTime",cursor.getString(cursor.getColumnIndex("create_time")));
                jo.put("url",cursor.getString(cursor.getColumnIndex("url")));
                jo.put("id",cursor.getInt(cursor.getColumnIndex("id")));
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
        videoManageListAdapter = new VideoManageListAdapter(videoBeanList);
        rcVideoList.setAdapter(videoManageListAdapter);
        //设置子布局的监听
        if (videoManageListAdapter instanceof VideoManageListAdapter){
            videoManageListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId()==R.id.tv_remove){
                //移除某个列表
                builder = new AlertDialog.Builder(getContext());
                builder.setTitle(videoBeanList.get(position).getType().equals("0")?"你确定要删除此条视频吗？":"你确定要删除此条音频吗？");
                builder.setPositiveButton("确定", (dialogInterface, i) -> {
                    int id = videoBeanList.get(position).getId();
                    String sql = "DELETE FROM video WHERE id ="+id+"";
                    sqLiteDatabase.execSQL(sql);
                    onResume();
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }
            });
        }

    }

    @Override
    protected void initListener(View view) {
    tvAdd.setOnClickListener(v->{
        Intent intent = new Intent(getContext(), AddUrlActivity.class);
        startActivity(intent);
    });
    }

    @Override
    protected void initData() {
    }
}
