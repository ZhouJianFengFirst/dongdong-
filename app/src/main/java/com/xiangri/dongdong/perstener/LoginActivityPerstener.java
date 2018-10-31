package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiangri.dongdong.R;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.net.Http;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class LoginActivityPerstener extends AppDelegate implements View.OnClickListener {

    private static final int USER_LOGIN = 1;
    private Context mContext;
    private EditText etUserName;
    private EditText etPassWord;
    private Button btnLogin;
    private TextView txtNewUserRecest;
    private TextView txtUserFindPassword;

    @Override
    public void initData() {
        super.initData();

        //设置事件
        setEvent();


    }

    private void setEvent() {
        etUserName = (EditText) getView(R.id.et_username);
        etPassWord = (EditText) getView(R.id.et_password);
        btnLogin = (Button) getView(R.id.btn_login);
        txtNewUserRecest = (TextView) getView(R.id.new_user_recest);
        txtUserFindPassword = (TextView) getView(R.id.user_find_password);
        btnLogin.setOnClickListener(this);
        txtNewUserRecest.setOnClickListener(this);
        txtUserFindPassword.setOnClickListener(this);
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.mContext = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String username = etUserName.getText().toString().trim();
                String userpass = etPassWord.getText().toString().trim();
                userLogin(username, userpass);
                break;
            case R.id.new_user_recest:
                break;
            case R.id.user_find_password:
                break;
        }
    }

    private void userLogin(String username, String userpass) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(userpass)) {
            toast("用户名密码不能为空");
            return;
        }
        RequestBody body = new FormBody.Builder().add("mobile",username).add("password",userpass).build();
        postString(Http.USER_LOGIN_URL,USER_LOGIN,body);
    }

    @Override
    public void successString(String data, int type) {
        super.successString(data, type);
        switch (type){
            case USER_LOGIN:
                Log.d("Tag",data+">>>");
                break;
        }
    }

    @Override
    public void failString(String msg) {
        super.failString(msg);
    }
}