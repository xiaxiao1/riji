package com.xiaxiao.riji.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.xiaxiao.riji.R;
import com.xiaxiao.riji.bean.RiJiUser;
import com.xiaxiao.riji.customview.BaseActivity;

public class RiJiStartUpActivity extends BaseActivity {

    RiJiUser riJiUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ri_ji_start_up);
        setRefreshEnable(false);
        riJiUser = riJiBmobServer.getLocalUser();
        if (riJiUser != null && riJiUser.getObjectId() != null && riJiUser.getObjectId().length() > 0) {
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            startActivity(new Intent(this,LoginActivity.class));
        }
        finish();
    }

    @Override
    public void onRefreshing() {

    }

    @Override
    public void initViews() {

    }
}
