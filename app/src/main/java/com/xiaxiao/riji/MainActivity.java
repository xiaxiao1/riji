package com.xiaxiao.riji;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiaxiao.riji.activity.home.HomeActivity;
import com.xiaxiao.riji.activity.home.RiJiStartUpActivity;
import com.xiaxiao.riji.customview.BaseActivity;
import com.xiaxiao.riji.thirdframework.bmob.BmobIniter;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.BmobInstallation;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public void onRefreshing() {

    }

    @Override
    public void initViews() {

    }

    public void toTestactivity(View view) {
        startActivity(new Intent(this, RiJiStartUpActivity.class));

    }


}
