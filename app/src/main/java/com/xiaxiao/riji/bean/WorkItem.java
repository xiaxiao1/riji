package com.xiaxiao.riji.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiaxiao on 2017/6/23.
 */
public class WorkItem extends BmobObject{
    public static final int WORK_ITEM_STATE_FINISH=1;
    public static final int WORK_ITEM_STATE_UNFINISH=0;

    String localId;
    String work;

    // 1:finish;0:unfinish
    int finish;

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}
