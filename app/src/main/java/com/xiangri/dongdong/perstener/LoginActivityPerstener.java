package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.LoginActivity;
import com.xiangri.dongdong.activity.RegisterActivity;
import com.xiangri.dongdong.entity.UserBean;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.net.Http;
import com.xiangri.dongdong.utils.SpUtil;

import java.util.HashMap;
import java.util.Map;

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
                ((LoginActivity)mContext).startActivity(new Intent(mContext,RegisterActivity.class));
                break;
            case R.id.user_find_password:
                break;
        }
    }

    private void userLogin(String username, String userpass) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(userpass)) {
            toast("用户友情提示：","用户名密码不能为空",4000);
            return;
        }
//        RequestBody body = new FormBody.Builder().add("mobile",username).add("password",userpass).build();
        Map<String,String> map = new HashMap<>();
        map.put("mobile",username);
        map.put("password",userpass);
        postString(Http.USER_LOGIN_URL,USER_LOGIN,map);
    }

    @Override
    public void successString(String data, int type) {
        super.successString(data, type);
        switch (type){
            case USER_LOGIN:
                saveUserMessage(data);
                break;
        }
    }

    private void saveUserMessage(String data) {
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(data, UserBean.class);
        if ("0".equals(userBean.getCode())){
            SpUtil.getInserter(mContext).saveData("username",userBean.getData().getUsername()).putString("uid",userBean.getData().getUid()+"").putString("password",userBean.getData().getPassword()).putBoolean("login_flag",true).putString("token",userBean.getData().getToken()).putString("nickname",userBean.getData().getNickname()+"").putString("icon",userBean.getData().getIcon()+"").commit();
            ((LoginActivity)mContext).finish();
        }else {
            toast("用户友情提示：","用户名密码错误",4000);
        }
    }

    @Override
        public void failString(String msg) {
            super.failString(msg);
        toast("用户友情提示：","请检查网络",4000);
    }
}