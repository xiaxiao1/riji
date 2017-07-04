package com.xiaxiao.riji.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    //是否是要显示今天当天的items
    boolean isToday=false;

    int i=0;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_work);
        setRightImage(-1);
        setRefreshEnable(false);
        todayWork=(DayWork) getIntent().getExtras().getSerializable("today");
        isToday = getIntent().getBooleanExtra("istoday", false);
        String[] dateStr = todayWork.getDate().split("\\.");
        String titleStr=dateStr[0]+"年"+dateStr[1]+"月"+dateStr[2]+"日";
        if (isToday) {
            addImg.setVisibility(View.VISIBLE);
            titleStr = titleStr + " 今天";
        } else {
            addImg.setVisibility(View.GONE);
        }
        setBarTitle(titleStr);
        setLeftImage(R.drawable.left_gray);
//        getmCustomTopBar().getLeftImageView().setBackgroundResource(R.drawable.finish_work_off_bg);
        getmCustomTopBar().getLeftImageView().setPadding(28, 0, 28, 0);
        setTitleLeftAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodayWorkActivity.this.finish();
            }
        });
        getDatas();




        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XiaoUtil.l("what is wrong");
                UIDialog.showAddWorkItem(TodayWorkActivity.this, new RiJiCallback() {
                    @Override
                    public void handle(Object object) {
                        addImg.setEnabled(false);
                        addItems((String)object);
                    }
                });
            }

        });
        changeItemStatus(isToday);

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
        listview = (ListView) findViewById(R.id.listview);
        /*View v = new View(this);
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.height = 160;
        v.setLayoutParams(params);
        listview.addFooterView(v);*/


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
                        addImg.setEnabled(true);
                        getDatas();
                    }

                    @Override
                    public void onError(BmobException e) {
                        addImg.setEnabled(true);
                    }
                });
            }

            @Override
            public void onError(BmobException e) {
                addImg.setEnabled(true);
            }
        });

    }

    public void changeItemStatus(boolean isToday) {
        if (isToday) {
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    UIDialog.showFinishWorkItemDialog(TodayWorkActivity.this, new RiJiCallback() {
                        @Override
                        public void handle(Object object) {
                            WorkItem workItem = workItemList.get(position);
                            int status = (int) object;
                            if (status!=workItem.getFinish()) {
                                workItem.setFinish(status);
                                riJiBmobServer.updateWorkItem(workItem, new BmobListener() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        XiaoUtil.customToast(TodayWorkActivity.this,"更新成功");
                                        getDatas();//refresh
                                    }

                                    @Override
                                    public void onError(BmobException e) {

                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
    }
}
