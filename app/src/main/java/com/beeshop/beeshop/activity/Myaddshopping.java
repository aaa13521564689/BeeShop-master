package com.beeshop.beeshop.activity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.ExpandableListAdapter;
import com.beeshop.beeshop.model.Data;
import com.beeshop.beeshop.model.GoodBean1;
import com.beeshop.beeshop.model.UserEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.LoggerUtil;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.LogUtil;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.beeshop.beeshop.views.SmoothCheckBox;
import com.beeshop.beeshop.views.UpdateView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.http.GET;

/**
 * Created by Administrator on 2018/8/22 0022.
*/
/**
 * 我的购物車
 */

public class Myaddshopping extends BaseActivity implements UpdateView, View.OnClickListener{
    private ExpandableListView mExpandableListView;
    private SmoothCheckBox mCbSelectAll;
    private TextView mTvAllMoney;
    private Button mBtnBuy;
//    private float allMoney;
//    private int allCount;
//    private boolean isAllSelect;
//    StringBuffer stringBuffer;
ArrayList<GoodBean1.Data>   dataList=   new ArrayList<GoodBean1.Data>();
    ExpandableListAdapter mAdapter;
    ResponseEntity   rr=new ResponseEntity();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addshopping);
        setTitleAndBackPressListener("我的购物車");
        initView();
        initData();
        initEvent();
        mBtnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initEvent() {
        mCbSelectAll.setOnClickListener(this);
    }

    private void initData() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN,""));
        HttpLoader.getInstance().showcarshopping(params1,mCompositeSubscription,new SubscriberCallBack<List<GoodBean1.Data>>(){
            @Override
            protected void onSuccess(List<GoodBean1.Data> response) {
                super.onSuccess(response);
           if(response.size()>0){
               dataList.clear();
               dataList.addAll(response);
               mAdapter= new ExpandableListAdapter(Myaddshopping.this,dataList,rr,mCompositeSubscription);
               mAdapter.setChangedListener(Myaddshopping.this);
               mExpandableListView.setAdapter(mAdapter);
               //展开所有的分组
               for (int i = 0; i < dataList.size(); i++) {
                   mExpandableListView.expandGroup(i);
               }

               hideNoContentView();
           }else{
               showNoContentView();

           }
            }
            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
             LoggerUtil.i("数据异常----"+errorBean.getCode()+"");

             rr=errorBean;



            }
            @Override
            public void onCompleted() {
                super.onCompleted();
                hideProgress();
            }
        });



    }



    private void initView() {
        mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        mCbSelectAll = (com.beeshop.beeshop.views.SmoothCheckBox) findViewById(R.id.cb_select_all);
        mTvAllMoney = (TextView) findViewById(R.id.tv_all_money);
        mBtnBuy = (Button) findViewById(R.id.btn_settlement);
        //去掉ExpandableListView 默认的箭头
        mExpandableListView.setGroupIndicator(null);

        //用于列表滑动时，EditText清除焦点，收起软键盘
        mExpandableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity
                            .INPUT_METHOD_SERVICE);
                    View focusView = getCurrentFocus();
                    if (focusView != null) {
                        inputMethodManager.hideSoftInputFromWindow(focusView.getWindowToken(), InputMethodManager
                                .HIDE_NOT_ALWAYS);
                        focusView.clearFocus();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

    }

    @Override
    public void update(boolean isAllSelected, int count, float price) {
        mBtnBuy.setText("结算(" + count + ")");
        mTvAllMoney.setText("¥" +String.format("%.2f",price) );
        mCbSelectAll.setChecked(isAllSelected);

    }


    @Override
    public void onClick(View v) {

        selectAll();
    }


    private void selectAll() {
        int allCount = rr.getAllCount();
        float allMoney = rr.getAllMoney();
        if (!mCbSelectAll.isChecked()) {
            rr.setAllSelect(true);
            for (int i = 0; i < dataList.size(); i++) {
                dataList.get(i).setSelected(true);
                for (int n = 0; n < dataList.get(i).getGoodDetail().size(); n++) {
                    if (!dataList.get(i).getGoodDetail().get(n).isSelected()) {
                        allCount++;
                        allMoney += Integer.valueOf(dataList.get(i).getGoodDetail().get(n).getNum())
                                * Float.valueOf(dataList.get(i).getGoodDetail().get(n).getPrice());
                        dataList.get(i).getGoodDetail().get(n).setSelected(true);
                    }
                }
            }
        } else {
            rr.setAllSelect(false);
            for (int i = 0; i < dataList.size(); i++) {
                dataList.get(i).setSelected(false);
                for (int n = 0; n < dataList.get(i).getGoodDetail().size(); n++) {
                    dataList.get(i).getGoodDetail().get(n).setSelected(false);
                }
                allCount = 0;
                allMoney = 0;
            }
        }
        rr.setAllMoney(allMoney);
        rr.setAllCount(allCount);
        update(rr.isAllSelect(), allCount, allMoney);
        if(dataList.size()>0) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
