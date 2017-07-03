package com.xiaxiao.riji.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaxiao.riji.R;
import com.xiaxiao.riji.activity.TodayWorkActivity;
import com.xiaxiao.riji.bean.DayWork;
import com.xiaxiao.riji.customview.BaseActivity;
import com.xiaxiao.riji.customview.roundcorner.RoundCornerImageView;
import com.xiaxiao.riji.thirdframework.bmob.BmobListener;
import com.xiaxiao.riji.util.XiaoUtil;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class HomeActivity extends BaseActivity {
    private RoundCornerImageView img;
    private LinearLayout mainLl;
    private TextView dayTv;
    private TextView yearMonthTv;
    private TextView pastTime_tv;
    DayWork todayWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setRefreshEnable(false);
        setCustomTopBarVisibility(View.GONE);

        riJiBmobServer.getMyDayWorks(1, new BmobListener() {
            @Override
            public void onSuccess(Object object) {
                List<DayWork> dayWorkList = (List<DayWork>) object;
                if (dayWorkList == null || dayWorkList.size() == 0) {//no data
                    createNewday();
                }
                else {
                    todayWork = dayWorkList.get(0);
                    if (XiaoUtil.format(todayWork.getDate()).compareTo(XiaoUtil.getToday()) < 0) {
                        createNewday();
                    } else {
                        showToday();
                    }
                }
            }

            @Override
            public void onError(BmobException e) {

            }
        });

    }

    @Override
    public void onRefreshing() {

    }

    public void initViews() {
        img = (RoundCornerImageView) findViewById(R.id.img);
        mainLl = (LinearLayout) findViewById(R.id.main_ll);
        dayTv = (TextView) findViewById(R.id.day_tv);
        yearMonthTv = (TextView) findViewById(R.id.year_month_tv);
        pastTime_tv = (TextView) findViewById(R.id.past_time_tv);

        pastTime_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, DayWorksActivity.class));
            }
        });

    }


    public void createNewday() {
        todayWork = new DayWork();
        todayWork.setDate(XiaoUtil.getFormatCurrentTime(""));
        riJiBmobServer.addDayWork(todayWork, new BmobListener() {
            @Override
            public void onSuccess(Object object) {
                todayWork.setObjectId((String)object);
                showToday();
            }

            @Override
            public void onError(BmobException e) {

            }
        });
    }

    public void showToday() {
        String[] date = todayWork.getDate().split("\\.");
        dayTv.setText(date[2]);
        yearMonthTv.setText(date[0]+"年"+date[1]+"月");
        mainLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                XiaoUtil.customToast(HomeActivity.this,"toady is a good day");
                Intent intent=new Intent(HomeActivity.this, TodayWorkActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("today",todayWork);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
