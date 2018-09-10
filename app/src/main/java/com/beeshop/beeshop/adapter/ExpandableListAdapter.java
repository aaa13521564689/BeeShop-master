package com.beeshop.beeshop.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beeshop.beeshop.R;
import com.beeshop.beeshop.activity.ProductDetailActivity;
import com.beeshop.beeshop.model.GoodBean1;
import com.beeshop.beeshop.net.HttpLoader;
import com.beeshop.beeshop.net.ResponseEntity;
import com.beeshop.beeshop.net.SubscriberCallBack;
import com.beeshop.beeshop.utils.SharedPreferenceUtil;
import com.beeshop.beeshop.utils.ToastUtils;
import com.beeshop.beeshop.views.SmoothCheckBox;
import com.beeshop.beeshop.views.UpdateView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import rx.subscriptions.CompositeSubscription;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private static final String TAG = "ExpandableListAdapter";
    private Context mContext;
    private ArrayList<GoodBean1.Data> goodBean;
    private ResponseEntity rr;
    private CompositeSubscription mCompositeSubscription;
    private int  id;

    private UpdateView updateViewListener;
    protected static final int KEY_DATA = 0xFFF11133;

    public ExpandableListAdapter(Context mContext, ArrayList<GoodBean1.Data> goodBean, ResponseEntity rr, CompositeSubscription mCompositeSubscription) {
        this.mContext = mContext;
        this.goodBean = goodBean;
        this.rr = rr;
        this.mCompositeSubscription = mCompositeSubscription;
    }

    @Override
    public int getGroupCount() {
        return goodBean.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return
                goodBean.get(groupPosition).getGoodDetail().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return
                goodBean.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return goodBean.get(groupPosition).getGoodDetail().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shopingcart_group, parent, false);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.cbGroupItem.setTag(groupPosition);
        holder.cbGroupItem.setOnClickListener(listener);
        holder.tvPosition.setText(goodBean.get(groupPosition).getAddress());
        //根据获取的状态设置是否被选中
        if (goodBean.get(groupPosition).isSelected()) {
            if (!holder.cbGroupItem.isChecked()) {
                holder.cbGroupItem.setChecked(true);
            }
        } else {
            holder.cbGroupItem.setChecked(false);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shopingcart_child, parent, false);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        String tag = groupPosition + "," + childPosition;
        holder.cbItem.setTag(tag);
        holder.tvReduce.setTag(tag);

        holder.tvAdd.setTag(tag);
        holder.imgDelete.setTag(tag);
        holder.qwea.setTag(tag);
//        holder.imgIcon.setTag(tag);
        Glide.with(mContext).load(goodBean.get(groupPosition).getGoodDetail().get(childPosition).getCover())
                .apply(new RequestOptions().placeholder(R.drawable.default_banner).error(R.drawable.default_banner).dontAnimate().centerCrop())
                .into(holder.imgIcon);
        holder.cbItem.setOnClickListener(listener);
        holder.tvReduce.setOnClickListener(listener);
        //添加商品数量
        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = view.getTag().toString();
                String[] split;
                int groupId = 0;
                int childId = 0;
                int allCount = rr.getAllCount();
                float allMoney;
                if (tag.contains(",")) {
                    split = tag.split(",");
                    groupId = Integer.parseInt(split[0]);

                    childId = Integer.parseInt(split[1]);

                }

                int goodCount =  goodBean.get(groupId).getGoodDetail().get(childId).getNum();
                id=goodBean.get(groupId).getGoodDetail().get(childId).getId();
//                String c=String.valueOf(goodCount);
                goodBean.get(groupId).getGoodDetail().get(childId).setNum(addCount(goodCount));
                allMoney = rr.getAllMoney();
                if (goodBean.get(groupId).getGoodDetail().get(childId).isSelected()) {
                    allMoney += Float.valueOf(goodBean.get(groupId).getGoodDetail().get(childId).getPrice());
                    updateViewListener.update(rr.isAllSelect(), allCount, allMoney);
                }
                rr.setAllMoney(allMoney);
                notifyDataSetChanged();
            }
        });

        holder.imgDelete.setOnClickListener(listener);
        //根据获取的状态设置是否被选中
        if (goodBean.get(groupPosition).getGoodDetail().get(childPosition).isSelected()) {
            holder.cbItem.setChecked(true);
        } else {
            holder.cbItem.setChecked(false);
        }
        //设置数据
        holder.tvPrice.setText("¥" + goodBean.get(groupPosition).getGoodDetail().get(childPosition).getPrice());
        holder.tvGoodName.setText(goodBean.get(groupPosition).getGoodDetail().get(childPosition).getProduct_title());
        //对商品数量的监听
        EditTextWatcher textWatcher = (EditTextWatcher) holder.etCount.getTag(KEY_DATA);
        if (textWatcher != null) {
            holder.etCount.removeTextChangedListener(textWatcher);
        }
        holder.etCount.setText(String.valueOf(goodBean.get(groupPosition).getGoodDetail().get(childPosition).getNum()));
        EditTextWatcher watcher = new EditTextWatcher(goodBean.get(groupPosition).getGoodDetail().get(childPosition));
        holder.etCount.setTag(KEY_DATA, watcher);
        holder.etCount.addTextChangedListener(watcher);

        holder.etCount.setText(goodBean.get(groupPosition).getGoodDetail().get(childPosition).getNum()+"");

        return convertView;

    }

    /**
     * 商品数量EditText内容改变的监听
     */
    class EditTextWatcher implements TextWatcher {

        private GoodBean1.Data.GoodDetail intm;



        public EditTextWatcher( GoodBean1.Data.GoodDetail item) {
            this.intm = item;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(s.toString().trim())) {
                String textNum = s.toString().trim();
                int c= Integer.valueOf(textNum);
                intm.setNum(c);
            }
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SmoothCheckBox checkBox;
            String tag = v.getTag().toString();
            String[] split;
            int groupId = 0;
            int childId = 0;
            int childSize = 0;
            int groupPosition = 0;
            int allCount = rr.getAllCount();//被选中的item数量
            float allMoney = rr.getAllMoney();
            if (tag.contains(",")) {
                split = tag.split(",");
                groupId = Integer.parseInt(split[0]);
                childId = Integer.parseInt(split[1]);
            } else {
                groupPosition = Integer.parseInt(tag);
                childSize = goodBean.get(groupPosition).getGoodDetail().size();
            }
            switch (v.getId()) {
                case R.id.cb_group_item:
                    checkBox = (SmoothCheckBox) v;
                    //根据父checkbox的选中状态设置存储数据里面商品是否被选中
                    goodBean.get(groupPosition).setSelected(!checkBox.isChecked());
                    if (!checkBox.isChecked()) {
                        for (int i = 0; i < childSize; i++) {
                            if (!goodBean.get(groupPosition).getGoodDetail().get(i).isSelected()) {
                                allCount++;
                                goodBean.get(groupPosition).getGoodDetail().get(i).setSelected(!checkBox.isChecked());
                                allMoney += Integer.valueOf(goodBean.get(groupPosition).getGoodDetail().get(i).getNum())
                                        * Float.valueOf(goodBean.get(groupPosition).getGoodDetail().get(i).getPrice());
                            }
                        }
                    } else {
                        allCount -= childSize;
                        for (int i = 0; i < childSize; i++) {
                            goodBean.get(groupPosition).getGoodDetail().get(i).setSelected(!checkBox.isChecked());
                            allMoney -= Integer.valueOf(goodBean.get(groupPosition).getGoodDetail().get(i).getNum())
                                    * Float.valueOf(goodBean.get(groupPosition).getGoodDetail().get(i).getPrice());
                        }
                    }
                    //父item选中的数量
                    int fCount = 0;
                    //判断是否所有的父item都被选中，决定全选按钮状态
                    for (int i = 0; i < goodBean.size(); i++) {
                        if (goodBean.get(i).isSelected()) {
                            fCount++;
                        }
                    }
                    if (fCount == goodBean.size()) {
                        rr.setAllSelect(true);
                    } else {
                        rr.setAllSelect(false);
                    }
                    rr.setAllCount(allCount);
                    rr.setAllMoney(allMoney);
                    notifyDataSetChanged();
                    updateViewListener.update(rr.isAllSelect(), allCount, allMoney);
                    break;
                //单个子项item被点击
                case R.id.cb_item:
                    checkBox = (SmoothCheckBox) v;
                    int cCount = 0;//子item被选中的数量
                    int fcCount = 0;//父item被选中的数量
                    goodBean.get(groupId).getGoodDetail().get(childId).setSelected(!checkBox.isChecked());
                    //遍历父item所有数据，统计被选中的item数量
                    for (int i = 0; i < goodBean.get(groupId).getGoodDetail().size(); i++) {
                        if (goodBean.get(groupId).getGoodDetail().get(i).isSelected()) {
                            cCount++;
                        }
                    }
                    //判断是否所有的子item都被选中，决定父item状态
                    if (cCount == goodBean.get(groupId).getGoodDetail().size()) {
                        goodBean.get(groupId).setSelected(true);
                    } else {
                        goodBean.get(groupId).setSelected(false);
                    }
                    //判断是否所有的父item都被选中，决定全选按钮状态
                    for (int i = 0; i < goodBean.size(); i++) {
                        if (goodBean.get(i).isSelected()) {
                            fcCount++;
                        }
                    }
                    if (fcCount == goodBean.size()) {
                        rr.setAllSelect(true);
                    } else {
                        rr.setAllSelect(false);
                    }
                    //判断子item状态，更新结算总商品数和合计Money
                    if (!checkBox.isChecked()) {
                        allCount++;
                        allMoney += Integer.valueOf(goodBean.get(groupId).getGoodDetail().get(childId).getNum())
                                * Float.valueOf(goodBean.get(groupId).getGoodDetail().get(childId).getPrice());
                    } else {
                        allCount--;
                        allMoney -= Integer.valueOf(goodBean.get(groupId).getGoodDetail().get(childId).getNum())
                                * Float.valueOf(goodBean.get(groupId).getGoodDetail().get(childId).getPrice());
                    }
                    rr.setAllCount(allCount);
                    rr.setAllMoney(allMoney);
                    notifyDataSetChanged();
                    updateViewListener.update(rr.isAllSelect(), allCount, allMoney);
                    break;
                case R.id.tv_reduce:
                    //减少商品数量
                    int goodCount = goodBean.get(groupId).getGoodDetail().get(childId).getNum();
                    id=goodBean.get(groupId).getGoodDetail().get(childId).getId();
                    if (Integer.valueOf(goodCount) > 1) {
                        goodBean.get(groupId).getGoodDetail().get(childId).setNum(reduceCount(goodCount));
                        if (goodBean.get(groupId).getGoodDetail().get(childId).isSelected()) {
                            allMoney -= Float.valueOf(goodBean.get(groupId).getGoodDetail().get(childId).getPrice());
                            updateViewListener.update(rr.isAllSelect(), allCount, allMoney);
                        }
                        rr.setAllMoney(allMoney);
                        notifyDataSetChanged();
                    }
                    break;
                case R.id.img_delete:

                    id=goodBean.get(groupId).getGoodDetail().get(childId).getId();
                     int[] aa={id};
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
                    params.put("id",aa);
                    HttpLoader.getInstance().deleteshopping(params, mCompositeSubscription, new SubscriberCallBack() {

                        @Override
                        protected void onSuccess(ResponseEntity response) {
                            super.onSuccess(response);
                            Toast.makeText(mContext,"操作成功",Toast.LENGTH_LONG).show();


                        }
                        @Override
                        protected void onFailure(ResponseEntity errorBean) {
                            ToastUtils.showToast("-----------"+errorBean.getMsg());
                            Toast.makeText(mContext,"操作失败",Toast.LENGTH_LONG).show();
                        }
                    });

                    if(goodBean.get(groupId).getGoodDetail().get(childId).isSelected()){
                        allMoney -= Float.valueOf(goodBean.get(groupId).getGoodDetail().get(childId).getPrice())*
                                Integer.valueOf(goodBean.get(groupId).getGoodDetail().get(childId).getNum());
                        updateViewListener.update(rr.isAllSelect(), allCount, allMoney);
                        rr.setAllMoney(allMoney);
                        goodBean.get(groupId).getGoodDetail().remove(childId);

                        if (goodBean.get(groupId).getGoodDetail().size() == 0) {
                            goodBean.remove(groupId);
                        }

                        notifyDataSetChanged();

                    }else {
                        goodBean.get(groupId).getGoodDetail().remove(childId);

                        if (goodBean.get(groupId).getGoodDetail().size() == 0) {
                            goodBean.remove(groupId);
                        }

                        notifyDataSetChanged();
                    }



                    break;
            }
        }
    };

    public void setChangedListener(UpdateView listener) {
        if (updateViewListener == null) {
            this.updateViewListener = listener;
        }
    }

    private int addCount(int var) {
//        Integer integer = Integer.valueOf(var);
//        integer++;String a="1";
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
        params.put("id",id);
        params.put("factor",1);
        HttpLoader.getInstance().shopcarproshopping(params, mCompositeSubscription, new SubscriberCallBack() {

            @Override
            protected void onSuccess(ResponseEntity response) {
                super.onSuccess(response);
                Toast.makeText(mContext,"操作成功",Toast.LENGTH_LONG).show();

            }
            @Override
            protected void onFailure(ResponseEntity errorBean) {
                ToastUtils.showToast("-----------"+errorBean.getMsg());
                Toast.makeText(mContext,"操作失败",Toast.LENGTH_LONG).show();
            }
        });

        return ++var;
    }

    private int  reduceCount(int var) {
//        Integer integer = Integer.valueOf(var);
        if (var > 1) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("token", SharedPreferenceUtil.getUserPreferences(SharedPreferenceUtil.KEY_TOKEN, ""));
            params.put("id",id);
            params.put("factor",0);
            HttpLoader.getInstance().shopcarproshopping(params, mCompositeSubscription, new SubscriberCallBack() {

                @Override
                protected void onSuccess(ResponseEntity response) {
                    super.onSuccess(response);
                    Toast.makeText(mContext,"操作成功",Toast.LENGTH_LONG).show();

                }
                @Override
                protected void onFailure(ResponseEntity errorBean) {
                    ToastUtils.showToast("-----------"+errorBean.getMsg());
                    Toast.makeText(mContext,"操作失败",Toast.LENGTH_LONG).show();
                }
            });

            var--;
        }
        return var ;
    }

    static class GroupViewHolder {
        SmoothCheckBox cbGroupItem;
        TextView tvPosition;

        GroupViewHolder(View view) {
            cbGroupItem = (com.beeshop.beeshop.views.SmoothCheckBox) view.findViewById(R.id.cb_group_item);
            tvPosition = (TextView) view.findViewById(R.id.tv_position);
        }
    }

    static class ChildViewHolder {
        com.beeshop.beeshop.views.SmoothCheckBox cbItem;
        LinearLayout qwea;
        TextView tvPrice,tvGoodName;
        EditText etCount;
        TextView tvReduce;
        TextView tvAdd;
        ImageView imgDelete;
        ImageView imgIcon;

        ChildViewHolder(View view) {
            cbItem = (com.beeshop.beeshop.views.SmoothCheckBox) view.findViewById(R.id.cb_item);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            tvGoodName = (TextView) view.findViewById(R.id.tv_good_name);
            etCount = (EditText) view.findViewById(R.id.et_count);
            tvReduce = (TextView) view.findViewById(R.id.tv_reduce);
            tvAdd = (TextView) view.findViewById(R.id.tv_add);
            imgDelete = (ImageView) view.findViewById(R.id.img_delete);
            imgIcon = (ImageView) view.findViewById(R.id.img_icon);
            qwea=(LinearLayout)view.findViewById(R.id.qwea);
        }
    }



}
