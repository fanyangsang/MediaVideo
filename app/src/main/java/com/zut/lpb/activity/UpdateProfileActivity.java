package com.zut.lpb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.zut.lpb.R;
import com.zut.lpb.api.Api;
import com.zut.lpb.base.BaseActivity;
import com.zut.lpb.utils.JsonCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UpdateProfileActivity extends BaseActivity {
    @BindView(R.id.tv_titles)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    EditText edName;
    @BindView(R.id.tv_birthday)
    EditText edAge;
    @BindView(R.id.tv_email)
    EditText edEmail;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitle.setText("插入数据");
    }

    @Override
    protected void initListener() {
        JSONObject param = new JSONObject();

            tvSubmit.setOnClickListener(view ->
            {
                try {
                    param.put("age",edAge.getText().toString());
                    param.put("nickname",edName.getText().toString());
                    param.put("email",edEmail.getText().toString());
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),param.toString());
                    OkGo.post(Api.UPDATE_USER)
                            .headers("token", SPUtils.getInstance().getString("token"))
                            .upRequestBody(requestBody)
                            .execute(new JsonCallBack() {
                                @Override
                                public void onResponse(String json) {
                                    ToastUtils.showLong(json);
                                }
                                @Override
                                public void onError(String json) {

                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
    }

    @Override
    protected void initData() {
        OkGo.get(Api.FIND_USER_ALL)
                .headers("token", SPUtils.getInstance().getString("token"))
                .execute(new JsonCallBack() {
                    @Override
                    public void onResponse(String json) {
                        ToastUtils.showLong(json);
                    }

                    @Override
                    public void onError(String json) {

                    }
                });
    }

}
