package com.xiaxiao.riji.customview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaxiao.riji.R;
import com.xiaxiao.riji.runtimepermission.RuntimePermissionsManager;
import com.xiaxiao.riji.thirdframework.bmob.RiJiBmobServer;

public abstract  class BaseActivity extends AppCompatActivity {
    private LinearLayout parentLinearLayout;//把父类activity和子类activity的view都add到这里
    private CustomTopBar mCustomTopBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RuntimePermissionsManager runtimePermissionsManager;
    public static Context mContext;
    public RiJiBmobServer riJiBmobServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        riJiBmobServer = new RiJiBmobServer(this);
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
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.finish_on);
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
        mCustomTopBar.getLeftTitleLl().setOnClickListener(onClickListener);

    }
    public void setTitleRightAction(View.OnClickListener onClickListener) {
        mCustomTopBar.getRightTitleLl().setOnClickListener(onClickListener);
    }

    public void setBarTitle(String title) {
        mCustomTopBar.setTitle(title);
    }

    public void startRefresh() {
       /* runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(true);
//            mSwipeRefreshLayout.se
                }
            }
        });*/
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            });
        }


    }

    public CustomTopBar getmCustomTopBar() {
        return mCustomTopBar;
    }
    public void setLeftImage(int drawableId) {
        if (drawableId<0) {
            mCustomTopBar.getLeftImageView().setVisibility(View.GONE);
            return;
        }
        mCustomTopBar.getLeftImageView().setVisibility(View.VISIBLE);
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

    public RiJiBmobServer getBmobServer() {
        return riJiBmobServer;
    }

    /**
     * 解决ListView和SwipeRefreshLayout的滑动冲突，如果当前页面有ListView的话
     * @param listView
     */
    public void fixScrollConflict(ListView listView) {

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if(view != null && view.getChildCount() > 0){
                    // check if the first item of the list is visible
                    boolean firstItemVisible = view.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = view.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                setRefreshEnable(totalItemCount==0||enable);
            }});
    }

    public void setEmptyListView(ListView listView ,String s) {
        TextView textView = new TextView(this);
        textView.setText(s);
        listView.setEmptyView(textView);
    }

}