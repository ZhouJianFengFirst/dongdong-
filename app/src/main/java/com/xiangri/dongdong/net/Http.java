package com.xiangri.dongdong.net;

/**
 * date:2018/10/17
 */
public class Http {

    public static String BASE_URL="http://www.zhaoapi.cn";
    //首页轮播图接口
    public static String BANNER_URL=BASE_URL+"/ad/getAd";

    //获取九宫格
    public static String JIU_URL=BASE_URL+"/product/getCatagory";

    //获取商品
    public static String SHOP_URL=BASE_URL+"/product/getCarts?uid=71";

    //获取子类商品
    public static String SHOP_CHILD_URL=BASE_URL+"/product/getProductCatagory";

    public static String SHOP_DETAILS_URL=BASE_URL+"/product/getProductDetail?pid=";

    //登录接口
    public static String USER_LOGIN_URL =BASE_URL+"/user/login";

    //注册接口
    public static String USER_REG_URL = BASE_URL+"/user/reg";
    //获取购物车
    public static String GET_SHOP_CAR_URL=BASE_URL+"/product/getCarts";
    //增加购物车商品
    public static String ADD_SHOP_CAR_URL=BASE_URL+"/product/addCart";
    //删除商品
    public static String DEL_SHOP_CAR_URL=BASE_URL+"/product/deleteCart";

    public static String RIGHT_URL = BASE_URL+"/product/getProductCatagory?cid=";
}