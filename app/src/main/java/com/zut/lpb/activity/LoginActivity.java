package com.zut.lpb.activity;

import android.content.Intent;
import android.database.Cursor;
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
import com.zut.lpb.utils.Md5Utils;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.ed_mobile)
    EditText edMobile;
    @BindView(R.id.ed_code)
    EditText edPassword;
    @BindView(R.id.iv_login)
    TextView tvLogin;
    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;

    private SQLiteDatabase sqLiteDatabase;

    private static String TAG = "LoginActivity";

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        //创建数据库
        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(this,"video_db");
        sqLiteDatabase = dataBaseOpenHelper.getReadableDatabase();

        tvLogin.setOnClickListener(v ->{
            String name = edMobile.getText().toString().trim();
            String password = edPassword.getText().toString().trim();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT 1 FROM user WHERE name ="+"'"+name +"'"+"AND password ="+"'"+password+"'",null);
            if (cursor.getCount() ==0){
                ToastUtils.showLong("账号或密码错误，请重新登录");
            }else {
                //将账号和密码md5之后，作为token存入缓存中
                String token = Md5Utils.md5(name+password);
                SPUtils.getInstance().put("token",token);
                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(this,SignUpActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
