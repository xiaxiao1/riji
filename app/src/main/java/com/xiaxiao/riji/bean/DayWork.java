package com.xiaxiao.riji.bean;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by xiaxiao on 2017/6/23.
 */

public class DayWork extends BmobObject {
    public static final int DAYWORK_VISIBLE=1;//可见
    public static final int DAYWORK_INVISIBLE=0;//不可见

    String localId;

    //yyyy:mm:dd
    String date;

    //一天的备注
    String note;


    RiJiUser owner;
    BmobRelation workItems;

    //完成度
    String progress;

    int visible = DAYWORK_INVISIBLE;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BmobRelation getWorkItems() {
        return workItems;
    }

    public void setWorkItems(BmobRelation workItems) {
        this.workItems = workItems;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public RiJiUser getOwner() {
        return owner;
    }

    public void setOwner(RiJiUser owner) {
        this.owner = owner;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}
