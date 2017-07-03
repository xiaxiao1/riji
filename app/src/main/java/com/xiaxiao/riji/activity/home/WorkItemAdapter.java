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
    static{
        statusMap.put(1, "已完成");
        statusMap.put(0, "没做完");
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

        holder.index_tv.setText("第"+(position+1)+"个项目");
        holder.work_tv.setText(workItem.getWork());
        holder.status_tv.setText(statusMap.get(workItem.getFinish()));

        return convertView;
    }

    class Holder {
        TextView index_tv;
        TextView work_tv;
        TextView status_tv;

        public Holder(View view) {
            index_tv = (TextView)   view.findViewById(R.id.index_tv);
            work_tv = (TextView)    view.findViewById(R.id.work_tv);
            status_tv = (TextView)  view.findViewById(R.id.status_tv);
        }

    }
}
