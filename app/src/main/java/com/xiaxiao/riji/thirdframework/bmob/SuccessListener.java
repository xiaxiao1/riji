package com.xiaxiao.riji.thirdframework.bmob;



import com.xiaxiao.riji.util.XiaoUtil;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2016/11/4.
 */
public abstract class SuccessListener implements BmobListener {

        @Override
        public void onError(BmobException e) {
                if (e!=null) {
                        XiaoUtil.l(e.getMessage());
                }
        }
}
