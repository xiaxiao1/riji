package com.xiaxiao.riji.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xiaxiao.riji.R;
import com.xiaxiao.riji.activity.TodayWorkActivity;
import com.xiaxiao.riji.bean.DayWork;
import com.xiaxiao.riji.customview.BaseActivity;
import com.xiaxiao.riji.thirdframework.bmob.BmobListener;
import com.xiaxiao.riji.util.XiaoUtil;

import java.util.Date;
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

//
//        setRefreshEnable(false);
        setBarTitle("不积硅步 无以至千里");
        setLeftImage(R.drawable.left_gray);
        setRightImage(R.drawable.setting);
        getmCustomTopBar().getLeftImageView().setPadding(32, 0, 32, 0);
        getmCustomTopBar().getRightImageView().setPadding(30, 0, 30, 0);
            setTitleLeftAction(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DayWorksActivity.this.finish();
                }
            });
        setTitleRightAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DayWorksActivity.this,SettingActivity.class));
            }
        });
        startRefresh();
        getDatas();

    }

    @Override
    public void onRefreshing() {
        getDatas();
    }

    public void getDatas() {

        riJiBmobServer.getMyDayWorks(-1,riJiBmobServer.getLocalUser(),new BmobListener() {
            @Override
            public void onSuccess(Object object) {
                stopRefresh();
                dayWorkList = (List<DayWork>) object;
                dayWorkAdapter = new DayWorkAdapter(DayWorksActivity.this, dayWorkList);
                listView.setAdapter(dayWorkAdapter);
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
        listView = (ListView) findViewById(R.id.listview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dayWorkList==null||dayWorkList.size()==0) {
                    return;
                }
                Intent intent=new Intent(DayWorksActivity.this, TodayWorkActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("today",dayWorkList.get(position));
                intent.putExtras(bundle);
                Date d1=XiaoUtil.format(dayWorkList.get(position).getDate());
                Date d2=XiaoUtil.getToday();
                int c = d1.compareTo(d2);
                if (c == 0) {
                    intent.putExtra("istoday", true);
                } else {
                    intent.putExtra("istoday", false);
                }
                startActivity(intent);
            }
        });

        fixScrollConflict(listView);

    }
}
