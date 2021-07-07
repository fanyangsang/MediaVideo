package com.zut.lpb.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.zut.lpb.R;
import com.zut.lpb.base.BaseActivity;
import com.zut.lpb.sqlite.DataBaseOpenHelper;
import com.zut.lpb.utils.BottomDialog;
import com.zut.lpb.utils.Utils;
import com.zut.lpb.utils.WheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddUrlActivity extends BaseActivity {

    @BindView(R.id.tv_titles)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_url)
    EditText tvUrl;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.rl_type)
    RelativeLayout rlType;
    private BottomDialog bottomDialog;
    private SQLiteDatabase sqLiteDatabase;
    private String value;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what ==100){
                String str = (String) msg.obj;
                tvType.setText(str);
            }
        }
    };
    @Override
    protected int getLayout() {
        return R.layout.activity_add_url;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    tvTitle.setText("添加Url");
    tvTitle.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(view -> {
           finish();
        });

        rlType.setOnClickListener(v->{
            List<String> limits = new ArrayList<>();
            limits.add("视频");
            limits.add("音乐");
            limits(limits, 100);

        });
        tvSubmit.setOnClickListener(v->{
            DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(this,"video_db");
            sqLiteDatabase = dataBaseOpenHelper.getWritableDatabase();
           String url = tvUrl.getText().toString();
           String type = tvType.getText().toString();
           String name = edName.getText().toString();
           //在video表中新增一条记录
            // 创建ContentValues对象
            ContentValues values1 = new ContentValues();
            // 向该对象中插入键值对
            values1.put("url", url);
            values1.put("type", type.equals("视频")?"0":"1");
            values1.put("name",name);
            values1.put("create_time", Utils.getSystemTime());
            // 调用insert()方法将数据插入到数据库当中
            sqLiteDatabase.insert("video", null, values1);
            //关闭数据库
            sqLiteDatabase.close();
            ToastUtils.showLong("添加成功");
            finish();
        });
    }

    @Override
    protected void initData() {

    }

    /**
     * 弹出选择框
     */
    private void limits(List<String> limits, int position) {
        if (bottomDialog != null && bottomDialog.isShowing()) {
            return;
        }
        View outerView1 = LayoutInflater.from(this).inflate(R.layout.dialog_repayment, null);
        final WheelView wv1 = outerView1.findViewById(R.id.wv1);
        wv1.setItems(limits, 0);
        TextView tv_ok = outerView1.findViewById(R.id.tv_ok);
        TextView tv_cancel = outerView1.findViewById(R.id.tv_cancel);
        tv_ok.setOnClickListener(view -> {
            bottomDialog.dismiss();
            String nTimeLimit = wv1.getSelectedItem();
            for (int i = 0; i < limits.size(); i++) {
                if (limits.get(i).equals(nTimeLimit)) {
                    value = limits.get(i);
                    break;
                }
            }
            Message message = new Message();
            message.what = 100;
            message.obj = value;
            message.arg1 = position;
            handler.sendMessage(message);
        });
        tv_cancel.setOnClickListener(view -> bottomDialog.dismiss());
        bottomDialog = new BottomDialog(this, R.style.ActionSheetDialogStyle);
        bottomDialog.setContentView(outerView1);
        bottomDialog.show();
    }
}
