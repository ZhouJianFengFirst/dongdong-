package com.xiangri.dongdong.perstener;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.LoginActivity;
import com.xiangri.dongdong.activity.MyMessageActivity;
import com.xiangri.dongdong.entity.UserBean;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.net.Http;
import com.xiangri.dongdong.utils.PermissionUtils;
import com.xiangri.dongdong.utils.SpUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MyMessageActivityPerstener extends AppDelegate implements View.OnClickListener {

    private Context mContext;
    private SimpleDraweeView userSimpView;
    private String[] contents = {"相机", "相册"};
    private File rootSD;
    private Uri uri;
    public static final int CAMERA = 0x123;
    public static final int PICK = 0x124;
    private TextView nickName;
    private LinearLayout updataLayout;
    private EditText etUpdata;
    private static final int UPDATA_NICKNAME_CONTENT = 0x125;
    private String nickname;

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
        //设置点击事件
        setClick(this, R.id.user_up_tioimage, R.id.user_sex, R.id.user_address);
        String nickname = (String) SpUtil.getInserter(mContext).getSpData("nickname", "");
        if ("null".equals(nickname)) {
            nickName.setText("未设置");
        } else {
            nickName.setText(nickname);
        }
    }

    private void setEvent() {
        userSimpView = (SimpleDraweeView) getView(R.id.litter_user_image);
        userSimpView.setImageResource(R.drawable.login);
        nickName = (TextView) getView(R.id.txt_usernick);
        updataLayout = (LinearLayout) getView(R.id.updata_layout);
        etUpdata = (EditText) getView(R.id.et_updata_nickname);
        getView(R.id.image_down).setOnClickListener(this);
        getView(R.id.btn_updata).setOnClickListener(this);
        setClick(this, R.id.user_up_tioimage, R.id.txt_user_cancle, R.id.user_nickname);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_message;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_up_tioimage:
                ShowBar();
                break;
            case R.id.txt_user_cancle:
                cancleUserMessage();
            case R.id.user_nickname:
                updataLayout.setVisibility(View.VISIBLE);
                setAnimation(updataLayout, 1280, 0, 1000);
                break;
            case R.id.btn_updata:
                upDataUserNiname();
                break;
            case R.id.image_down:
                setAnimation(updataLayout, 0, 1280, 1000);
                break;
        }
    }


    public void setAnimation(View view, int in, int to, int s) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", in, to);
        animator.setDuration(s);
        animator.start();
    }


    private void upDataUserNiname() {
        nickname = etUpdata.getText().toString().trim();
        if ("".equals(nickname) || nickname == null) {
            toast("温馨提示：", "内容不能为空", 1000);
            setAnimation(updataLayout, 0, 1280, 1000);
            /*updataLayout.setVisibility(View.GONE);*/
            return;
        }
        String uid = (String) SpUtil.getInserter(mContext).getSpData("uid", "");
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("nickname", nickname);
        getString(Http.UPDATA_NICKNAME, UPDATA_NICKNAME_CONTENT, map);
    }

    @Override
    public void successString(String data, int type) {
        super.successString(data, type);
        switch (type) {
            case UPDATA_NICKNAME_CONTENT:
                UserBean userBean = new Gson().fromJson(data, UserBean.class);
                if ("0".equals(userBean.getCode())) {
                    toast("修改成功");
                    SpUtil.getInserter(mContext).saveData("nickname", nickname).commit();
                    setAnimation(updataLayout, 0, 1280, 1000);
                    nickName.setText(nickname);
                    /*updataLayout.setVisibility(View.GONE);*/
                }
                break;
        }
    }

    @Override
    public void failString(String msg) {
        super.failString(msg);
        toast(msg);
        updataLayout.setVisibility(View.GONE);
    }

    private void cancleUserMessage() {
        SpUtil.getInserter(mContext).cancle().commit();
        ((MyMessageActivity) mContext).finish();
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
        toast("注销成功");
    }

    private void ShowBar() {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(mContext, "sd卡未挂载", Toast.LENGTH_SHORT).show();
            return;
        }

        rootSD = Environment.getExternalStorageDirectory();

        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("请选择相机")
                .setIcon(R.drawable.login)
                .setItems(contents, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        PermissionUtils.permission(mContext, new PermissionUtils.PermissionListener() {
                            @Override
                            public void success() {
                                switch (which) {
                                    case 0:
                                        Intent intentCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        File imagePath = new File(rootSD, "head.jpg");
                                        uri = Uri.fromFile(imagePath);
                                        intentCapture.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                        ((MyMessageActivity) mContext).startActivityForResult(intentCapture, CAMERA);
                                        break;
                                    case 1:
                                        Intent intentPick = new Intent(Intent.ACTION_PICK);
                                        intentPick.setType("image/*");
                                        ((MyMessageActivity) mContext).startActivityForResult(intentPick, PICK);
                                        break;
                                }
                            }
                        });

                    }
                })
                .create();
        dialog.show();
    }

    public Uri getUri() {
        return uri;
    }

    public void setSimplDrawView(Uri simplDrawView) {
        userSimpView.setImageURI(simplDrawView);
    }
}