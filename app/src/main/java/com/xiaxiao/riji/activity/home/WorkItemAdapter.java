package com.xiaxiao.riji.activity.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaxiao.riji.R;
import com.xiaxiao.riji.bean.WorkItem;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xiaxiao on 2017/7/3.
 */

public class WorkItemAdapter extends MyAdapter {
    private static HashMap<Integer, String> statusMap = new HashMap<>();
    private static HashMap<Integer, Integer> statusBgMap = new HashMap<>();
    static{
        statusMap.put(WorkItem.WORK_ITEM_STATE_FINISH, "完成");
        statusMap.put(WorkItem.WORK_ITEM_STATE_UNFINISH, "未完成");

        statusBgMap.put(WorkItem.WORK_ITEM_STATE_FINISH, R.drawable.finish_work_on_bg);
        statusBgMap.put(WorkItem.WORK_ITEM_STATE_UNFINISH, R.drawable.finish_work_off_bg);
    }
    public WorkItemAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        WorkItem workItem = (WorkItem)datas.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.workitem_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder)convertView.getTag();
        }

        holder.index_tv.setText(""+(position+1));
        holder.time_tv.setText(workItem.getCreatedAt());
        holder.work_tv.setText(workItem.getWork());
        holder.status_tv.setText(statusMap.get(workItem.getFinish()));
        holder.status_tv.setBackgroundResource(statusBgMap.get(workItem.getFinish()));
        if (position+1==datas.size()) {
            convertView.setPadding(0,0,0,260);
        }

        return convertView;
    }

    class Holder {
        TextView index_tv;
        TextView work_tv;
        TextView status_tv;
        TextView time_tv;

        public Holder(View view) {
            index_tv = (TextView)   view.findViewById(R.id.index_tv);
            work_tv = (TextView)    view.findViewById(R.id.work_tv);
            status_tv = (TextView)  view.findViewById(R.id.status_tv);
            time_tv = (TextView)  view.findViewById(R.id.item_time_tv);
        }

    }
}
