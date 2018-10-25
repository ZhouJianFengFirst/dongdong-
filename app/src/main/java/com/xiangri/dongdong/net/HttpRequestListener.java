package com.xiangri.dongdong.net;

public interface HttpRequestListener {
    void successRequest(String data);

    void fail(String msg);
}