package com.xiaxiao.riji.activity.home;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

//        startRefresh();
        setRefreshEnable(false);
        setBarTitle("不积硅步 无以至千里");
        setLeftImage(R.drawable.left_gray);
//        getmCustomTopBar().getLeftImageView().setBackgroundResource(R.drawable.finish_work_off_bg);
        getmCustomTopBar().getLeftImageView().setPadding(28, 0, 28, 0);
        setTitleLeftAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DayWorksActivity.this.finish();
            }
        });
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
    }
}
