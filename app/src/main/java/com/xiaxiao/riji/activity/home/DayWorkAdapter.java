package com.xiaxiao.riji.activity.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaxiao.riji.R;
import com.xiaxiao.riji.bean.DayWork;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by xiaxiao on 2017/7/3.
 */

public class DayWorkAdapter extends MyAdapter {


    public DayWorkAdapter(Context context,List list) {
        super(context,list);
        datas = list;
        this.context = context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        DayWork dayWork = (DayWork)datas.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.day_work_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder)convertView.getTag();
        }
        holder.day_tv.setText(dayWork.getDate());
        holder.dayWork_tv.setText(dayWork.getNote());
        holder.progress_tv.setText(dayWork.getProgress());
        return convertView;
    }



    class Holder {
        TextView day_tv;
        TextView dayWork_tv;
        TextView progress_tv;

        public Holder(View view) {
            day_tv = (TextView) view.findViewById(R.id.day_tv);
            dayWork_tv = (TextView) view.findViewById(R.id.day_work_tv);
            progress_tv = (TextView) view.findViewById(R.id.progress_tv);
        }
    }
}
