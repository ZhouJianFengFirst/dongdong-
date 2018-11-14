package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.RegisterActivity;
import com.xiangri.dongdong.entity.RegisterBean;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.net.Http;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivityPerstion extends AppDelegate implements View.OnClickListener {

    private static final int REGISTER_CONTENT = 1;
    private Context mContext;
    private EditText etUserName,etUserPass;

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.mContext = context;
    }

    @Override
    public void initData() {
        super.initData();
        //设置事件
        setEvent();
    }

    private void setEvent() {
        etUserName = (EditText)getView(R.id.et_username);
        etUserPass = (EditText)getView(R.id.et_password);
        getView(R.id.btn_reg).setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_reg:
                String username = etUserName.getText().toString().trim();
                String userpass = etUserPass.getText().toString().trim();
                userReg(username,userpass);
                break;
        }
    }

    private void userReg(String username, String userpass) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(userpass)){
            toast("注册友情提示：","用户名密码不能为空",4000);
            return;
        }
        /*RequestBody body = new FormBody.Builder().add("mobile",username).add("password",userpass).build();*/
        Map<String,String> map = new HashMap<>();
        map.put("mobile",username);
        map.put("password",userpass);
        postString(Http.USER_REG_URL,REGISTER_CONTENT,map);
    }

    @Override
    public void successString(String data, int type) {
        super.successString(data, type);
        switch (type){
            case REGISTER_CONTENT:
                 saveUser(data);
                break;
        }
    }

        private void saveUser(String data) {
        Gson gson = new Gson();
        RegisterBean regbean = gson.fromJson(data, RegisterBean.class);
        if ("0".equals(regbean.getCode())){
            toast("用户友情提示：",regbean.getMsg(),1500);
            ((RegisterActivity)mContext).finish();
        }else {
            toast("用户友情提示：",regbean.getMsg(),4000);
        }
    }

    @Override
    public void failString(String msg) {
        super.failString(msg);
        toast("用户友情提示：","请检查网络",4000);
    }
}
