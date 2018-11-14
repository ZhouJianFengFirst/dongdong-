package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.activity.MyMessageActivity;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.utils.PermissionUtils;

import java.io.File;

public class MyMessageActivityPerstener extends AppDelegate implements View.OnClickListener {

    private Context mContext;
    private SimpleDraweeView userSimpView;
    private String[] contents = {"相机", "相册"};
    private File rootSD;
    private Uri uri;
    public static final int CAMERA = 0x123;
    public static final int PICK = 0x124;

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
        setClick(this, R.id.user_up_tioimage, R.id.user_vip, R.id.user_sex, R.id.user_address);
    }

    private void setEvent() {
        userSimpView = (SimpleDraweeView) getView(R.id.litter_user_image);
        userSimpView.setImageResource(R.drawable.login);
        setClick(this, R.id.user_up_tioimage);
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
        }
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
                        PermissionUtils.permission(mContext,new PermissionUtils.PermissionListener() {
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

    public void setSimplDrawView(Uri uri) {
        userSimpView.setImageURI(uri);
    }

    public Uri getUri() {
        return uri;
    }
}