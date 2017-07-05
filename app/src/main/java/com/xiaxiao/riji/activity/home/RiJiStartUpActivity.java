package com.xiaxiao.riji.activity.home;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiaxiao.riji.R;
import com.xiaxiao.riji.bean.RiJiUser;
import com.xiaxiao.riji.customview.BaseActivity;
import com.xiaxiao.riji.util.XiaoUtil;

public class RiJiStartUpActivity extends BaseActivity {

    RiJiUser riJiUser;
    View mengceng;
    ValueAnimator valueAnimator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ri_ji_start_up);
        setRefreshEnable(false);
        setCustomTopBarVisibility(View.GONE);
        XiaoUtil.useBmob(this);
        valueAnimator= ValueAnimator.ofFloat(1.0f, 0.0f,0.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                mengceng.setAlpha(v);
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                goNext();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        valueAnimator.start();

    }

    @Override
    public void onRefreshing() {

    }

    @Override
    public void initViews() {
        mengceng = (View) findViewById(R.id.mengceng);
    }

    public void goNext() {
        riJiUser = riJiBmobServer.getLocalUser();
        if (riJiUser != null && riJiUser.getObjectId() != null && riJiUser.getObjectId().length() > 0) {
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            startActivity(new Intent(this,LoginActivity.class));
        }
        finish();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (valueAnimator!=null) {
            valueAnimator.cancel();
        }
    }
}
