package com.beeshop.beeshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;
import com.beeshop.beeshop.R;
import com.beeshop.beeshop.adapter.ItemOnClickListener;
import com.beeshop.beeshop.adapter.ProductManagerAdapter;
import com.beeshop.beeshop.model.PicListEntity;
import com.beeshop.beeshop.model.ProductListEntity;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.ToastUtils;

import java.util.HashMap;

/**
 * Author：cooper
 * Time：  2018/5/20 下午12:53
 * Description：编辑产品
 */
public class ProductManagerActivity extends BaseListActivity<ProductListEntity.ListBean>  {

    private TextView mTvAddProduct;
    private ProductManagerAdapter mProductManagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleAndBackPressListener("编辑产品");
    }

    @Override
    protected int getContentView() {
        return R.layout.aty_product_manager;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mProductManagerAdapter = new ProductManagerAdapter(this,mList);
        mProductManagerAdapter.setmItemOnClickListener(new ItemOnClickListener() {
            @Override
            public void onItemClick(int position) {
                ProductListEntity.ListBean listBean = mList.get(position);
                Intent intent = new Intent(ProductManagerActivity.this, ProductEditActivity.class);
                intent.putExtra(ProductEditActivity.PARAM_PRODUCT_INFO,listBean);
                startActivity(intent);
            }
        });
        return mProductManagerAdapter;
    }

    @Override
    protected void initView() {
        super.initView();
        mTvAddProduct = findViewById(R.id.tv_add_product);
        mTvAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductManagerActivity.this,ProductEditActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getProductList();
    }

    /**
     * 获取店铺照片别表
     */
    private void getProductList() {
        HashMap<String, Object> params1 = new HashMap<>();
        params1.put("token", mToken);
        HttpLoader.getInstance().getProductList(params1, mCompositeSubscription, new SubscriberCallBack<ProductListEntity>(this, this) {

            @Override
            protected void onSuccess(ProductListEntity response) {
                super.onSuccess(response);
                mList.clear();
                mList.addAll(response.getList());
                mProductManagerAdapter.notifyDataSetChanged();
                hideProgress();
            }

            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast(errorBean.getMsg());
                hideProgress();
            }

        });
    }
}
