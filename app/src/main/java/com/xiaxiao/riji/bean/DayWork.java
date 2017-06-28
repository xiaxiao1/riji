package com.xiaxiao.riji.bean;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiaxiao on 2017/6/23.
 */

public class DayWork extends BmobObject {
    String localId;

    //yyyy:mm:dd
    String date;

    //一天的备注
    String note;


    ArrayList<WorkItem> workItems;

    //完成度
    String progress;

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

    public ArrayList<WorkItem> getWorkItems() {
        return workItems;
    }

    public void setWorkItems(ArrayList<WorkItem> workItems) {
        this.workItems = workItems;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
