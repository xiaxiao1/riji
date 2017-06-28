package com.xiaxiao.riji.customview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xiaxiao.riji.R;
import com.xiaxiao.riji.runtimepermission.RuntimePermissionsManager;

public abstract  class BaseActivity extends AppCompatActivity {
    private LinearLayout parentLinearLayout;//把父类activity和子类activity的view都add到这里
    private CustomTopBar mCustomTopBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RuntimePermissionsManager runtimePermissionsManager;
    public static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initContentView();
    }

    /**
     * 初始化contentview,为所有的activity提供顶部栏和下拉刷新功能
     */
    private void initContentView() {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        mCustomTopBar = new CustomTopBar(this);
        linearLayout.addView(mCustomTopBar);
        View v=LayoutInflater.from(this).inflate(R.layout.activity_base, linearLayout, true);
        viewGroup.addView(linearLayout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_base);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshing();
            }
        });
        parentLinearLayout=(LinearLayout)v.findViewById(R.id.base_content_root_ll);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
        initViews();

    }

    @Override
    public void setContentView(View view) {

        parentLinearLayout.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {

        parentLinearLayout.addView(view, params);

    }


    /**
     * 设置顶部栏显示
     * @param visibility
     */
    public void setCustomTopBarVisibility(int visibility) {
        mCustomTopBar.setVisibility(visibility);
    }

    public void setTitleLeftAction(View.OnClickListener onClickListener) {
        mCustomTopBar.getLeftImageView().setOnClickListener(onClickListener);

    }
    public void setTitleRightAction(View.OnClickListener onClickListener) {
        mCustomTopBar.getRightImageView().setOnClickListener(onClickListener);
    }

    public void setBarTitle(String title) {
        mCustomTopBar.setTitle(title);
    }

    public void startRefresh() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(true);
//            mSwipeRefreshLayout.se
                }
            }
        });

    }

    public void setLeftImage(int drawableId) {
        if (drawableId<0) {
            mCustomTopBar.getLeftImageView().setVisibility(View.GONE);
            return;
        }
        mCustomTopBar.setLeftImg(drawableId);
    }
    public void setRightImage(int drawableId) {
        if (drawableId<0) {
            mCustomTopBar.getRightImageView().setVisibility(View.GONE);
            return;
        }
        mCustomTopBar.setRightImg(drawableId);

    }
    public void stopRefresh() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
//            mSwipeRefreshLayout.se
                }
            }
        });
    }

    public void setRefreshEnable(boolean refreshEnable) {
        mSwipeRefreshLayout.setEnabled(refreshEnable);
    }

    public abstract void onRefreshing();
    public abstract void initViews();

    public RuntimePermissionsManager getRuntimePermissionsManager() {
        if (runtimePermissionsManager==null) {
            runtimePermissionsManager = new RuntimePermissionsManager(this, false);
        }
        return runtimePermissionsManager;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        runtimePermissionsManager.handle(requestCode, permissions, grantResults);
    }
}