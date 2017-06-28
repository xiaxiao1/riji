package com.xiaxiao.riji.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.xiaxiao.riji.R;
import com.xiaxiao.riji.customview.BaseActivity;

public class TodayWorkActivity extends BaseActivity {
    private ImageView addImg;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_work);
//        initViews();
        setBarTitle("Time so Fast");
        setRightImage(-1);

    }

    @Override
    public void onRefreshing() {
        stopRefresh();
    }

    public void initViews() {
        addImg = (ImageView) findViewById(R.id.add_img);
        listview = (ListView) findViewById(R.id.listview);

    }
}
