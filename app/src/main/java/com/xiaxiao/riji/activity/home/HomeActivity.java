package com.xiaxiao.riji.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaxiao.riji.R;
import com.xiaxiao.riji.activity.TodayWorkActivity;
import com.xiaxiao.riji.customview.BaseActivity;
import com.xiaxiao.riji.customview.roundcorner.RoundCornerImageView;
import com.xiaxiao.riji.util.XiaoUtil;

public class HomeActivity extends BaseActivity {
    private RoundCornerImageView img;
    private LinearLayout mainLl;
    private TextView dayTv;
    private TextView yearMonthTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setRefreshEnable(false);
        setCustomTopBarVisibility(View.GONE);
//        initViews();
        String[] date = XiaoUtil.getCurrentDate();
        dayTv.setText(date[2]);
        yearMonthTv.setText(date[0]+"年"+date[1]+"月");
        mainLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XiaoUtil.customToast(HomeActivity.this,"toady is a good day");
                startActivity(new Intent(HomeActivity.this, TodayWorkActivity.class));
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

    }
}
