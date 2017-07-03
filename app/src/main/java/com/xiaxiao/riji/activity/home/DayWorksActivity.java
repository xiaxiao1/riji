package com.xiaxiao.riji.activity.home;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.xiaxiao.riji.R;
import com.xiaxiao.riji.bean.DayWork;
import com.xiaxiao.riji.customview.BaseActivity;
import com.xiaxiao.riji.thirdframework.bmob.BmobListener;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;

public class DayWorksActivity extends BaseActivity{

    ListView listView;
    DayWorkAdapter dayWorkAdapter;
    List<DayWork> dayWorkList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_works);

        startRefresh();
        riJiBmobServer.getMyDayWorks(-1,new BmobListener() {
            @Override
            public void onSuccess(Object object) {
                stopRefresh();
                dayWorkList = (List<DayWork>) object;
                dayWorkAdapter = new DayWorkAdapter(DayWorksActivity.this, dayWorkList);
                listView.setAdapter(dayWorkAdapter);
            }

            @Override
            public void onError(BmobException e) {

            }
        });
    }

    @Override
    public void onRefreshing() {
        stopRefresh();
    }

    @Override
    public void initViews() {
        listView = (ListView) findViewById(R.id.listview);
    }
}
