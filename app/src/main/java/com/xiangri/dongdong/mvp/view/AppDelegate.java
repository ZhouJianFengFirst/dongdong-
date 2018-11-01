package com.xiangri.dongdong.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tapadoo.alerter.Alert;
import com.tapadoo.alerter.Alerter;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.net.HttpHelper;
import com.xiangri.dongdong.net.HttpRequestListener;

import okhttp3.RequestBody;

public abstract class AppDelegate implements IDelegate {

    private Context mContext;
    private SparseArray<View> views = new SparseArray<>();
    private View rootView;

    @Override
    public void initData() {

    }

    @Override
    public void creat(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        rootView = inflater.inflate(getLayoutId(), null);
    }

    protected abstract int getLayoutId();

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void initContext(Context context) {
        mContext = context;
    }

    @Override
    public void getString(String url, final int type) {
        new HttpHelper().doGet(url).setListener(new HttpRequestListener() {
            @Override
            public void successRequest(String data) {
                successString(data, type);
            }

            @Override
            public void fail(String msg) {
                failString(msg);
            }
        });
    }


    @Override
    public void postString(String url, final int type, RequestBody body) {
        new HttpHelper().doPost(url, body).setListener(new HttpRequestListener() {
            @Override
            public void successRequest(String data) {
                successString(data, type);
            }

            @Override
            public void fail(String msg) {
                fail(msg);
            }
        });
    }

    public void successString(String data, int type) {

    }

    public void failString(String msg) {

    }

    public void toast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
    public void toast(String title,String msg,int s){
        Alerter.create(((AppCompatActivity)mContext)).setBackgroundColor(R.color.colorPrimary)
                .setText(msg)
                .setTitle(title)
                .setDuration(s)
                .show();
    }

    public <T extends View> T getView(int viewId) {
        T view = (T) views.get(viewId);
        if (view == null) {
            view = rootView.findViewById(viewId);
            views.put(viewId, view);
        }
        return view;
    }

    public void setClick(View.OnClickListener listener, int... ids) {

        if (ids == null) {
            return;
        }
        for (int id : ids) {
            getView(id).setOnClickListener(listener);
        }
    }

    public void destory(){
        rootView = null;
    }
}
