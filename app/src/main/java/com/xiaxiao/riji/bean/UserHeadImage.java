package com.xiaxiao.riji.bean;

import com.xiaxiao.riji.R;

import java.util.HashMap;

/**
 * Created by xiaxiao on 2017/7/14.
 */

public class UserHeadImage {
    private static HashMap<Integer,Integer> headImg;
    static{
        headImg = new HashMap<>();
        headImg.put(0, R.drawable.r0);
        headImg.put(1, R.drawable.r1);
        headImg.put(2, R.drawable.r2);
        headImg.put(3, R.drawable.r3);
        headImg.put(4, R.drawable.r4);
        headImg.put(5, R.drawable.r5);
        headImg.put(6, R.drawable.r6);
        headImg.put(7, R.drawable.r7);
        headImg.put(8, R.drawable.r8);
        headImg.put(9, R.drawable.r9);
        headImg.put(10, R.drawable.r10);
        headImg.put(11, R.drawable.r11);
        headImg.put(12, R.drawable.r12);
    }

    public static int getImageResource(int index) {
        index=index%13;
        if (headImg.containsKey(index)) {
            return headImg.get(index);
        }
        return -1;
    }
}
