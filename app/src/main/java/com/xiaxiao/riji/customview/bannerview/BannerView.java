package com.xiaxiao.riji.customview.bannerview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xiaxiao.riji.R;
import com.xiaxiao.riji.customview.BaseActivity;

import java.util.List;

/**
 * Created by xiaxiao on 2017/5/4.
 * 自定义的顶部banner view.可以循环滑动，可以在对应的adapter中更换不同的item view文件
 */

public class BannerView extends RelativeLayout {
    //bannerview 根view
    View mRootView;
    Context mContext;
    //具体操作的viewPager
    ViewPager mViewpager;
    //viewpager装载的View列表，为了实现循环滑动，其size比对应的数据列表大2
    List<View> itemViews;
    LinearLayout dots_ll;
    ImageView[] dotImgs;
    int[] dotDrawables;
    //用来解决viewpager的滑动和当前activity的下拉刷新冲突时，标记一次完整的当前触摸事件的
    boolean theMession=false;
//    List<Object> mDatas;
//    int itemViewLayoutId;

    public BannerView(Context context) {
        super(context);
        initBanner(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBanner(context);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBanner(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initBanner(context);
    }

    private void initBanner(Context context) {
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.banner_view, this);
        mViewpager = (ViewPager) mRootView.findViewById(R.id.viewpager);
        dots_ll = (LinearLayout) mRootView.findViewById(R.id.index_dot_ll);
        dotDrawables=new int[]{R.drawable.tab2,R.drawable.tab2_off};

        //循环滑动的原理在这个监听事件中实现的
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                XiaoUtil.l("onPageScrolled "+position);
            }

            @Override
            public void onPageSelected(int position) {
//                XiaoUtil.l("onPageSelected "+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                XiaoUtil.l("onPageScrollStateChanged  "+mViewpager.getCurrentItem());
                int currentItemIndex=mViewpager.getCurrentItem();
                if (state==ViewPager.SCROLL_STATE_IDLE&&currentItemIndex+1==itemViews.size()) {
                    mViewpager.setCurrentItem(1, false);

                }
                if (state==ViewPager.SCROLL_STATE_IDLE&&currentItemIndex==0) {
                    mViewpager.setCurrentItem(itemViews.size()-2, false);

                }
                if (state==ViewPager.SCROLL_STATE_IDLE){
                    if (currentItemIndex==0) {
                        changeDots(dotImgs.length-1);
                    } else if (currentItemIndex + 1 == itemViews.size()) {
                        changeDots(0);
                    } else {
                        changeDots(currentItemIndex-1);
                    }
                }
            }
        });


    }

    public void setAdapter(MyBannerAdapter pagerAdapter) {
        mViewpager.setAdapter(pagerAdapter);
        itemViews = pagerAdapter.getItemViews();
        initDots();
        //设置viewpager初始化时显示的那个item，因为头尾是用来实现循环的，所以真正的第一个是index=1.
        mViewpager.setCurrentItem(1);
    }

    /**
     * 解决和BaseActivity滑动干扰，比如viewpager左右滑动时还有上下滑的动作时，会触发activity的下拉刷新动作。
     * @param activity
     */
    public void fixSlideDisturbWith(final BaseActivity activity) {
        mViewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!theMession) {
                    float x=0;
                    float y=0;
                    if (event.getAction()==MotionEvent.ACTION_DOWN) {
                        x=event.getX();
                        y=event.getY();
                    }
                    if (event.getAction()==MotionEvent.ACTION_MOVE) {
                        if (Math.abs(event.getX()-x) >= Math.abs(event.getY()-y)) {
                            theMession=true;
                            activity.setRefreshEnable(false);
                        }
                    }


                }

                if (event.getAction()==MotionEvent.ACTION_UP) {
                    activity.setRefreshEnable(true);
                    theMession=false;
                }
                return false;
            }
        });
    }

    private void initDots() {
        int size = itemViews.size()-2;
        dotImgs = new ImageView[size];
        for (int i = 0; i < size; i++) {
            ImageView dotImg = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(14,0,14,0);
            params.height=mContext.getResources().getDimensionPixelOffset(R.dimen.bannerview_dot_size);
            params.width=mContext.getResources().getDimensionPixelOffset(R.dimen.bannerview_dot_size);
            dotImg.setLayoutParams(params);
            dotImg.setImageResource(dotDrawables[0]);
            dotImgs[i] = dotImg;
            dots_ll.addView(dotImg);
        }
        changeDots(0);
    }

    private void changeDots(int currentIndex) {
        for (int i=0;i<dotImgs.length;i++) {
            if (i == currentIndex) {
                dotImgs[i].setImageResource(dotDrawables[0]);
            } else {
                dotImgs[i].setImageResource(dotDrawables[1]);
            }
        }
    }
    public void setDotDrawables(int onDrawableId, int offDrawableId) {
        dotDrawables[0] = onDrawableId;
        dotDrawables[1] = offDrawableId;
    }
   /* public View getOneItemView() {
        return LayoutInflater.from(mContext).inflate(itemViewLayoutId,null);
    }*/





}
