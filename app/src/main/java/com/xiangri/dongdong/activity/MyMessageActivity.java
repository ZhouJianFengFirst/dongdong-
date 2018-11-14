package com.xiangri.dongdong.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xiangri.dongdong.mvp.persenter.BaseActivity;
import com.xiangri.dongdong.net.BaseObserver;
import com.xiangri.dongdong.net.RetrofitHelper;
import com.xiangri.dongdong.perstener.MyMessageActivityPerstener;
import com.xiangri.dongdong.utils.SpUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;

public class MyMessageActivity extends BaseActivity<MyMessageActivityPerstener> {

    private static final int CROP_REQUEST_CODE = 0x125;
    private String path = "/sdcard/myHead/";
    private String fileName;

    @Override
    public Class<MyMessageActivityPerstener> getDelegateClass() {
        return MyMessageActivityPerstener.class;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/myHead/head.jpg");
        if (bitmap != null){
            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
            delegate.setSimplDrawView(uri);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case MyMessageActivityPerstener.CAMERA:
                Uri uri0 = delegate.getUri();
                cropPhoto(uri0);//裁剪图片
                break;
            case MyMessageActivityPerstener.PICK:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case CROP_REQUEST_CODE:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras == null) {
                        return;
                    }
                    Bitmap headBit = extras.getParcelable("data");
                    Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), headBit, null, null));
                    delegate.setSimplDrawView(uri);
                    if (headBit != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(headBit);
                        Log.d("Iiii",fileName);
                        File file = new File(fileName);
                        upLoad(file);/*上传到服务器*/
                    }
                }
                break;
        }
    }


    public void upLoad(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String uid = (String) SpUtil.getInserter(this).getSpData("uid", "");
        Log.d("uid",uid);
        BaseObserver<ResponseBody> ob = new BaseObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    Log.d("TagAAAAA", responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("TagAAAAA", e.getMessage());
            }
        };
        RetrofitHelper.getInstens().upLoad(file, uid, ob);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        fileName = path + "head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void cropPhoto(Uri uri) {
        Log.i("cropPhoto",uri.getPath());
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 127);
        intent.putExtra("outputY", 127);
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", false);//不启用人脸识别
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }
}