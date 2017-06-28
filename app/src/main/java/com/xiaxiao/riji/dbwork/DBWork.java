package com.xiaxiao.riji.dbwork;

import android.content.Context;

import com.xiaxiao.riji.bean.DayWork;
import com.xiaxiao.riji.bean.WorkItem;

import java.util.List;

/**
 * Created by xiaxiao on 2017/6/23.
 */

public class DBWork {

    DayWorkDBHelper dbHelper;
    Context mContext;

    private DBWork(Context context) {
        mContext = context;
        if (dbHelper==null) {
            dbHelper = new DayWorkDBHelper(context, DayWorkDBHelper.NAME, null, DayWorkDBHelper.VERTION);
        }
    }

    public DBWork getDBWorker(Context context) {
        return new DBWork(context);
    }

    public boolean addDayWork(DayWork dayWork) {

        return true;

    }

    public boolean deleteDayWork(String dayWorkId) {
        return true;
    }

    public boolean updateDayWork(DayWork dayWork) {
        return true;
    }

    public List<DayWork> getDayWorks() {
        return null;
    }


    public boolean addWorkItem(WorkItem workItem) {
        return true;
    }

    public void addWorkItems(List<WorkItem> workItems) {
        if (workItems==null||workItems.size()==0) {
            return;
        }
        for (WorkItem workItem:workItems) {
            addWorkItem(workItem);
        }
    }

    public void updateWorkItem(WorkItem workItem) {

    }

    public void updateWorkItems(List<WorkItem> workItems) {
        if (workItems==null||workItems.size()==0) {
            return;
        }
        for (WorkItem workItem:workItems) {
            updateWorkItem(workItem);
        }
    }

    public void deleteWorkItem(WorkItem workItem) {

    }

    public List<WorkItem> getWorkItems(String dayWorkId) {
        return null;
    }
}
