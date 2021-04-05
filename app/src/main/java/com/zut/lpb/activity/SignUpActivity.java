package com.zut.lpb.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.zut.lpb.R;
import com.zut.lpb.api.Api;
import com.zut.lpb.base.BaseActivity;
import com.zut.lpb.bean.UserBean;
import com.zut.lpb.sqlite.DataBaseOpenHelper;
import com.zut.lpb.utils.JsonCallBack;

import butterknife.BindView;

public class SignUpActivity extends BaseActivity {
    @BindView(R.id.ed_mobile)
    EditText edMobile;
    @BindView(R.id.ed_code)
    EditText edPassword;
    @BindView(R.id.iv_login)
    TextView tvSignUp;
    @BindView(R.id.ed_nickname)
    EditText edNickname;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    private static String TAG = "SignUpActivity";
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected int getLayout() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(this,"video_db");
        sqLiteDatabase = dataBaseOpenHelper.getWritableDatabase();

        tvSignUp.setOnClickListener(view -> {
            //插入数据
            // 创建ContentValues对象
            ContentValues values1 = new ContentValues();

            // 向该对象中插入键值对
            values1.put("name", edMobile.getText().toString().trim());
            values1.put("password", edPassword.getText().toString().trim());

            // 调用insert()方法将数据插入到数据库当中
            sqLiteDatabase.insert("user", null, values1);

            //关闭数据库
            sqLiteDatabase.close();

            ToastUtils.showLong("注册成功，请重新登录");
            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }

    @Override
    protected void initListener() {

        tvLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void initData() {

    }
}
