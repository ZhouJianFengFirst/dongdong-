package com.xiangri.dongdong;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.xiangri.dongdong.mvp.persenter.BaseActivity;
import com.xiangri.dongdong.perstener.FragmentHomePersenter;
import com.xiangri.dongdong.perstener.MainActivityPerstener;
import com.xiangri.dongdong.utils.SpUtil;
import com.yzq.zxinglibrary.common.Constant;

public class MainActivity extends BaseActivity<MainActivityPerstener> {

    @Override
    public Class<MainActivityPerstener> getDelegateClass() {
        return MainActivityPerstener.class;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FragmentHomePersenter.START_ACTIVITY && resultCode == RESULT_OK) {
                //处理扫描结果（在界面上显示）
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    String extra = data.getStringExtra(Constant.CODED_CONTENT);
                    Toast.makeText(this,extra,Toast.LENGTH_SHORT).show();
                }
        }
    }
}