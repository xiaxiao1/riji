package com.xiaxiao.riji.activity.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaxiao.riji.R;
import com.xiaxiao.riji.bean.RiJiUser;
import com.xiaxiao.riji.customview.BaseActivity;
import com.xiaxiao.riji.util.XiaoUtil;

import cn.bmob.v3.BmobUser;

public class SettingActivity extends BaseActivity {

    Button out_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setRefreshEnable(false);
        setLeftImage(R.drawable.left_gray);
        setBarTitle("嗨，"+riJiBmobServer.getLocalUser().getUsername());
        getmCustomTopBar().getLeftImageView().setPadding(32, 0, 32, 0);
        setTitleLeftAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
        out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RiJiUser riJiUser=riJiBmobServer.getLocalUser();
                if (riJiUser!=null&&riJiUser.getObjectId().length()>0) {
                    riJiUser=riJiBmobServer.logout();
                    if (riJiUser==null) {
                        XiaoUtil.customToast(SettingActivity.this,"退出登录");
                    }
                    Intent i = new Intent(SettingActivity.this,LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();

                }
            }
        });
    }

    @Override
    public void onRefreshing() {

    }

    @Override
    public void initViews() {
        out_btn = (Button) findViewById(R.id.out_btn);
    }
}
