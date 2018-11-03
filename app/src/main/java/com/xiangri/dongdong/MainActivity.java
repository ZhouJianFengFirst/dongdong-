package com.xiangri.dongdong;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.xiangri.dongdong.mvp.persenter.BaseActivity;
import com.xiangri.dongdong.perstener.FragmentHomePersenter;
import com.xiangri.dongdong.perstener.MainActivityPerstener;
import com.xiangri.dongdong.utils.SpUtil;

public class MainActivity extends BaseActivity<MainActivityPerstener> {

    @Override
    public Class<MainActivityPerstener> getDelegateClass() {
        return MainActivityPerstener.class;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && resultCode == FragmentHomePersenter.START_ACTIVITY) {
            Toast.makeText(this, "666", Toast.LENGTH_SHORT).show();
        }
    }
}