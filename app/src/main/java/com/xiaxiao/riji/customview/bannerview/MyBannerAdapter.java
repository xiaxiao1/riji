package com.xiaxiao.riji.customview.bannerview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaxiao on 2017/5/4.
 * 为自定义BannerView设置的adapter类
 */

public abstract class MyBannerAdapter<T> extends PagerAdapter {
     Context mContext;
    List<T> mDatas;
    List<View> itemViews;
    int mItemViewLayoutId;
    public MyBannerAdapter(Context context, int itemLayoutId, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewLayoutId = itemLayoutId;
        initItemsView();
    }

    private void initItemsView() {
        if (mDatas==null||mDatas.size()==0) {
            return;
        }
        itemViews = new ArrayList<>();
        for (int i=0;i<mDatas.size();i++) {
            itemViews.add(getOneItemView());
        }
        //用于循环滑动
        itemViews.add(getOneItemView());
        itemViews.add(getOneItemView());

    }


    private View getOneItemView() {
        return LayoutInflater.from(mContext).inflate(mItemViewLayoutId,null);
    }

    public List<View> getItemViews() {
        return itemViews;
    }

    @Override
    public int getCount() {
        return mDatas.size()+2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(itemViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(itemViews.get(position));
        if (position==0) {
            onItemInstantiated(itemViews.get(position), mDatas.get(mDatas.size()-1),position);
        } else if (position == itemViews.size() - 1) {
            onItemInstantiated(itemViews.get(position), mDatas.get(0), position);
        } else {
            onItemInstantiated(itemViews.get(position), mDatas.get(position-1), position);
        }
        return itemViews.get(position);
    }

    /**
     * 在instantiateItem()方法中执行的，对每一个item的设置操作在这里进行，
     * @param item 当前item View
     * @param obj 当前item要加载的数据
     * @param position 当前item的index
     */
    public  abstract void onItemInstantiated(View item, T obj,int position);
}
