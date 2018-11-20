package com.xiangri.dongdong.perstener;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiangri.dongdong.R;
import com.xiangri.dongdong.adapters.CarAdapter;
import com.xiangri.dongdong.adapters.CarShopAdapter;
import com.xiangri.dongdong.entity.CarBean;
import com.xiangri.dongdong.entity.HistoryEntity;
import com.xiangri.dongdong.entity.UserBean;
import com.xiangri.dongdong.mvp.view.AppDelegate;
import com.xiangri.dongdong.net.Http;
import com.xiangri.dongdong.sql.SqlUtil;
import com.xiangri.dongdong.utils.DialogUtils;
import com.xiangri.dongdong.utils.NetworkUtils;
import com.xiangri.dongdong.utils.SpUtil;
import com.xiangri.dongdong.view.TopView;

import java.util.HashMap;
import java.util.Map;

public class FragmentShopCarPersenter extends AppDelegate implements View.OnClickListener, TopView.BackListener, CarShopAdapter.ShopCarBackNumListener {

    private static final int CAR_LIST_REQUEST = 1;
    private Context mContext;
    private ImageView selectAll;
    private TextView total;
    private TextView close;
    private RecyclerView recyList;
    private boolean flag = true;
    private CarBean carBean;
    private CarAdapter carAdapter;
    private double priceAll = 0.0;
    private int numAll = 0;
    private TextView txtDelete;
    private TextView ok;
    private static final int DELECT_CONTNET = 0x123;
    private TopView topView;
    private static final int CAR_LIST_CONTENT = 0x124;
    private ImageView notShop;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_shopcar;
    }

    @Override
    public void initData() {
        super.initData();
        //设置事件
        setEvent();
        Boolean isLogion = (Boolean) SpUtil.getInserter(mContext).getSpData("login_flag", false);
        if (!isLogion) {
            DialogUtils dialogUtils = new DialogUtils(mContext);
            dialogUtils.show();
        }
    }

    private void setEvent() {
        selectAll = (ImageView) getView(R.id.selectAll);
        total = (TextView) getView(R.id.total);
        close = (TextView) getView(R.id.close);
        recyList = (RecyclerView) getView(R.id.recycar);
        txtDelete = (TextView) getView(R.id.txt_delete);
        notShop = (ImageView) getView(R.id.image_notshop);
        ok = (TextView) getView(R.id.ok);
        topView = (TopView) getView(R.id.topview);
        topView.setListener(this);
        topView.showLeft(false);
        txtDelete.setOnClickListener(this);
        ok.setOnClickListener(this);
        setClick(this, R.id.selectAll, R.id.close);
        carAdapter = new CarAdapter(mContext);
        carAdapter.setListener(this);
        recyList.setAdapter(carAdapter);
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        this.mContext = context;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectAll:
                setSelect();
                break;
            case R.id.close:
                break;
            case R.id.txt_delete:
                delectShop();
                break;
            case R.id.ok:
                show(false);
                break;
        }
    }

    //删除商品的方法
    private void delectShop() {
        //第一步：获取用户id
        String uid = getUid();
        for (int i = 0; i < carBean.getData().size(); i++) {
            for (int j = 0; j < carBean.getData().get(i).getList().size(); j++) {
                if (carBean.getData().get(i).getList().get(j).isIschecked()) {
                    int pid = carBean.getData().get(i).getList().get(j).getPid();
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("uid", uid);
                    map.put("pid", pid + "");
                    map.put("source", "android");
                    getString(Http.DEL_SHOP_CAR_URL, DELECT_CONTNET, map);
                    carBean.getData().get(i).getList().remove(j);
                }
            }
            int size = carBean.getData().get(i).getList().size();
            if (size == 0) {
                carBean.getData().remove(i);
            }
        }
        carAdapter.setList(carBean.getData());

        if (carBean.getData().size() == 0) {
            notShop.setVisibility(View.VISIBLE);
        } else {
            notShop.setVisibility(View.GONE);
        }
        // notifyChange();
        /*getString(Http.GET_SHOP_CAR_URL + "?uid=" + uid + "", CAR_LIST_CONTENT, null);*/
    }

    //设置全选全不选
    private void setSelect() {
        if (flag) {
            selectAll.setImageResource(R.drawable.cricle_yes);
            setSelectMouthed(true);
            carAdapter.notifyDataSetChanged();
        } else {
            selectAll.setImageResource(R.drawable.cricle_no);
            setSelectMouthed(false);
            carAdapter.notifyDataSetChanged();
            priceAll = 0.0;
            numAll = 0;
            setContent();
        }
        flag = !flag;
    }


    /**
     * 选择的方法
     *
     * @param flag
     */
    public void setSelectMouthed(boolean flag) {
        for (int i = 0; i < carBean.getData().size(); i++) {
            for (int j = 0; j < carBean.getData().get(i).getList().size(); j++) {
                carBean.getData().get(i).getList().get(j).setIschecked(flag);
                if (carBean.getData().get(i).getList().get(j).isIschecked()) {
                    priceAll += carBean.getData().get(i).getList().get(j).getPrice();
                    numAll += 1;
                }
            }
        }
        setContent();
    }

    //设置合计和计算
    public void setContent() {
        total.setText("合计：" + priceAll);
        close.setText("结算：(" + numAll + ")");
        priceAll = 0.0;
        numAll = 0;
    }

    @Override
    public void successString(String data, int type) {
        super.successString(data, type);
        switch (type) {
            case CAR_LIST_REQUEST:
                setCarList(data);
                SqlUtil.getInstens().insert(CAR_LIST_REQUEST+"",data);
                break;
            case DELECT_CONTNET:
                UserBean userBean = new Gson().fromJson(data, UserBean.class);
                if ("0".equals(userBean.getCode())) {
                    priceAll = 0.0;
                    numAll = 0;
                    setContent();
                    toast("删除成功");
                } else {
                    toast("删除失败");
                }
                show(false);
                break;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public void setCarList(String carList) {
        if (carList.contains(">")) {
            return;
        }

        if ("null".equals(carList)) {
            notShop.setVisibility(View.VISIBLE);
            return;
        }
        notShop.setVisibility(View.GONE);
        Gson gson = new Gson();
        carBean = gson.fromJson(carList, CarBean.class);
        if ("1".equals(carBean.getCode())) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyList.setLayoutManager(linearLayoutManager);
        carAdapter.setList(carBean.getData());
    }


    public void notifyChange() {
        Boolean isLogion = (Boolean) SpUtil.getInserter(mContext).getSpData("login_flag", false);
        if (!isLogion) {
            return;
        }
        String uid = getUid();
        //设置列表的网络数据
        if (!NetworkUtils.isConnected(mContext)){
            HistoryEntity shopCraHistory = SqlUtil.getInstens().queryByType(CAR_LIST_REQUEST + "");
            setCarList(shopCraHistory.getHistory());
            return;
        }
        getString(Http.GET_SHOP_CAR_URL + "?uid=" + uid + "", CAR_LIST_REQUEST, null);

    }

    public String getUid() {
        String uid = (String) SpUtil.getInserter(mContext).getSpData("uid", "-1");
        return uid;
    }

    @Override
    public void back() {
        show(true);
    }

    /**
     * 控制删除的显示与隐藏
     *
     * @param flag
     */
    public void show(boolean flag) {
        if (flag) {
            total.setVisibility(View.GONE);
            close.setVisibility(View.GONE);
            txtDelete.setVisibility(View.VISIBLE);
            topView.showRight(false);
            ok.setVisibility(View.VISIBLE);
        } else {
            total.setVisibility(View.VISIBLE);
            close.setVisibility(View.VISIBLE);
            txtDelete.setVisibility(View.GONE);
            topView.showRight(true);
            ok.setVisibility(View.GONE);
        }
    }

    @Override
    public void failString(String msg) {
        super.failString(msg);
        toast(msg);
    }

    @Override
    public void back(int num) {
        priceAll = 0.0;
        numAll = 0;
        for (int i = 0; i < carBean.getData().size(); i++) {
            for (int j = 0; j < carBean.getData().get(i).getList().size(); j++) {
                if (carBean.getData().get(i).getList().get(j).isIschecked()) {
                    CarBean.DataBean.ListBean listBean = carBean.getData().get(i).getList().get(j);
                    priceAll += listBean.getPrice() * num;
                    numAll += 1;
                }
            }
        }
        setContent();
    }
}