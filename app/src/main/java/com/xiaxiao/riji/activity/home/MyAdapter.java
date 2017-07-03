package com.xiaxiao.riji.activity.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaxiao.riji.R;

import java.util.List;

/**
 * Created by xiaxiao on 2017/7/3.
 */

public abstract class MyAdapter extends BaseAdapter {
    protected  List datas;
    protected  Context context;


    public MyAdapter(Context context, List list) {
        datas = list;
        this.context = context;

    }
    @Override
    public int getCount() {
        return datas==null?0:datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void  update(List list) {
        datas.clear();
        datas=list;
        notifyDataSetChanged();

    }

    public void addList(List list) {
        if (list==null||list.size()==0) {
            return;
        }
        datas.addAll(list);
        notifyDataSetChanged();

    }

    public void removeAtPosition(int position) {
        datas.remove(position);
        notifyDataSetChanged();
    }

    public void removeObject(Object object) {
        datas.remove(object);
        notifyDataSetChanged();
    }

}
