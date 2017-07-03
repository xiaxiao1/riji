package com.xiaxiao.riji.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.xiaxiao.riji.Listener.RiJiCallback;
import com.xiaxiao.riji.R;
import com.xiaxiao.riji.activity.home.DayWorksActivity;
import com.xiaxiao.riji.activity.home.WorkItemAdapter;
import com.xiaxiao.riji.bean.DayWork;
import com.xiaxiao.riji.bean.WorkItem;
import com.xiaxiao.riji.customview.BaseActivity;
import com.xiaxiao.riji.thirdframework.bmob.BmobListener;
import com.xiaxiao.riji.thirdframework.bmob.RiJiBmobServer;
import com.xiaxiao.riji.util.UIDialog;
import com.xiaxiao.riji.util.XiaoUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class TodayWorkActivity extends BaseActivity {
    private ImageView addImg;
    private ListView listview;
    DayWork todayWork;
    List<WorkItem> workItemList;
    WorkItemAdapter workItemAdapter;

    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_work);
        setBarTitle("Time so Fast");
        setRightImage(-1);
        setRefreshEnable(false);
        todayWork=(DayWork) getIntent().getExtras().getSerializable("today");

        getDatas();




        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XiaoUtil.l("what is wrong");
                UIDialog.showAddWorkItem(TodayWorkActivity.this, new RiJiCallback() {
                    @Override
                    public void handle(Object object) {
                        addItems((String)object);
                    }
                });
            }

        });

    }

    @Override
    public void onRefreshing() {
        getDatas();
    }



    public void getDatas() {
        startRefresh();
        riJiBmobServer.findWorkItems(todayWork, new BmobListener() {
            @Override
            public void onSuccess(Object object) {
                workItemList = (List<WorkItem>) object;
                if (workItemList!=null) {
                    if (workItemAdapter == null) {
                        workItemAdapter = new WorkItemAdapter(TodayWorkActivity.this, workItemList);
                        listview.setAdapter(workItemAdapter);
                    } else {
                        workItemAdapter.update(workItemList);
                    }
                }
                stopRefresh();
            }

            @Override
            public void onError(BmobException e) {
                stopRefresh();
            }
        });
    }
    @Override
    public void initViews() {
        addImg = (ImageView) findViewById(R.id.add_img);
        addImg.setImageResource(R.drawable.tab2);
        listview = (ListView) findViewById(R.id.listview);
    }


    public void addItems(String mes) {
        startRefresh();

        final WorkItem workItem = new WorkItem();
        workItem.setFinish(0);
        workItem.setWork(mes);
        riJiBmobServer.addWorkItem(workItem, new BmobListener() {
            @Override
            public void onSuccess(Object object) {
                BmobRelation relation = new BmobRelation();
                relation.add(workItem);
                todayWork.setWorkItems(relation);
                riJiBmobServer.updateDayWork(todayWork, new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        stopRefresh();
                        XiaoUtil.customToast(TodayWorkActivity.this,"添加成功");
                        getDatas();
                    }

                    @Override
                    public void onError(BmobException e) {

                    }
                });
            }

            @Override
            public void onError(BmobException e) {

            }
        });

    }
}
