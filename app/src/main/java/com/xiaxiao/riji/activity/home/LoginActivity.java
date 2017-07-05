package com.xiaxiao.riji.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xiaxiao.riji.R;
import com.xiaxiao.riji.bean.RiJiUser;
import com.xiaxiao.riji.customview.BaseActivity;
import com.xiaxiao.riji.thirdframework.bmob.BmobListener;
import com.xiaxiao.riji.util.XiaoUtil;

import cn.bmob.v3.exception.BmobException;

public class LoginActivity extends BaseActivity {

    EditText name_et;
    EditText password_et;
    Button login_btn;
    Button register_btn;
    String name;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRefreshEnable(false);
        setBarTitle("日迹");
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = name_et.getText().toString().trim();
                password = password_et.getText().toString().trim();
                if (name.length()==0||password.length()==0) {
                    XiaoUtil.customToast(LoginActivity.this,"信息为空或密码小于6位");
                    return;
                }
                RiJiUser riJiUser = new RiJiUser();
                riJiUser.setUsername(name);
                riJiUser.setPassword(password);

                startRefresh();
                login_btn.setEnabled(false);
                register_btn.setEnabled(false);
                XiaoUtil.hideSoftInput(LoginActivity.this);
                riJiBmobServer.login(riJiUser, new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                        XiaoUtil.customToast(LoginActivity.this,"登录成功");
                        finish();
                    }

                    @Override
                    public void onError(BmobException e) {
                        XiaoUtil.customToast(LoginActivity.this,"账号或密码错误");
                        stopRefresh();
                        login_btn.setEnabled(true);
                        register_btn.setEnabled(true);
                    }
                });
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = name_et.getText().toString().trim();
                password = password_et.getText().toString().trim();
                if (name.length()==0||password.length()<=0) {
                    XiaoUtil.customToast(LoginActivity.this,"信息为空或密码小于6位");
                    return;
                }
                RiJiUser riJiUser = new RiJiUser();
                riJiUser.setUsername(name);
                riJiUser.setPassword(password);

                startRefresh();
                login_btn.setEnabled(false);
                register_btn.setEnabled(false);
                XiaoUtil.hideSoftInput(LoginActivity.this);
                riJiBmobServer.register(riJiUser, new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        XiaoUtil.customToast(LoginActivity.this,"注册成功，请使用账号密码登录");
                        stopRefresh();
                        login_btn.setEnabled(true);
                        register_btn.setEnabled(true);

                        password_et.setText("");
                    }

                    @Override
                    public void onError(BmobException e) {
                        if (e.getErrorCode() == 202) {
                            XiaoUtil.customToast(LoginActivity.this, "用户名已存在");
                        } else {
                            XiaoUtil.customToast(LoginActivity.this, "系统异常，请稍候再试");
                        }

                        stopRefresh();
                        login_btn.setEnabled(true);
                        register_btn.setEnabled(true);
                    }
                });
            }
        });

    }

    @Override
    public void onRefreshing() {

    }

    @Override
    public void initViews() {
        name_et = (EditText) findViewById(R.id.name_et);
        password_et = (EditText) findViewById(R.id.password_et);
        login_btn = (Button) findViewById(R.id.login_btn);
        register_btn= (Button) findViewById(R.id.register_btn);
    }
}
