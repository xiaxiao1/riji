package com.xiaxiao.riji.activity.home;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaxiao.riji.R;
import com.xiaxiao.riji.bean.DayWork;
import com.xiaxiao.riji.bean.UserHeadImage;
import com.xiaxiao.riji.bean.WorkItem;
import com.xiaxiao.riji.thirdframework.bmob.BmobListener;
import com.xiaxiao.riji.thirdframework.bmob.RiJiBmobServer;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by xiaxiao on 2017/7/3.
 */

public class OthersDayWorkAdapter extends MyAdapter {

    RiJiBmobServer riJiBmobServer;
    HashMap<Integer,List<WorkItem>> workItemMaps;
    public OthersDayWorkAdapter(Context context, List list) {
        super(context,list);
        datas = list;
        this.context = context;
        riJiBmobServer = new RiJiBmobServer(context);
        workItemMaps = new HashMap<>();

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        DayWork dayWork = (DayWork)datas.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.others_day_work_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder)convertView.getTag();
        }
        holder.userHead_img.setImageResource(UserHeadImage.getImageResource(position));
        holder.username_tv.setText(dayWork.getOwner().getUsername());
        holder.yearMonth_tv.setText(dayWork.getDate());

        List<WorkItem> workItems;
        if (workItemMaps.containsKey(position)) {
            workItems = workItemMaps.get(position);
            initWorkItemView(workItems,holder);
        } else {
            riJiBmobServer.findWorkItems(dayWork, new BmobListener() {
                @Override
                public void onSuccess(Object object) {
                    List<WorkItem> workItems2 = (List<WorkItem>)object;
                    workItemMaps.put(position, workItems2);
                    initWorkItemView(workItems2, holder);

                }

                @Override
                public void onError(BmobException e) {

                }
            });
        }

        return convertView;
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void initWorkItemView(List<WorkItem> workItems, Holder holder) {
        int totalItem=0;
        int finishItem = 0;
        StringBuffer stringBuffer = new StringBuffer();
        totalItem = workItems.size();
        for (WorkItem workItem:workItems) {
            stringBuffer.append(workItem.getWork());
            stringBuffer.append("\n");
            if (workItem.getFinish()==WorkItem.WORK_ITEM_STATE_FINISH) {
                finishItem++;
            }
        }
        String s = stringBuffer.toString();
        if (s.length()==0) {
            s = "今天没有计划哦";
        }
        holder.dayWork_tv.setText(s);
        holder.progressNote_tv.setText("共"+totalItem+"事项  完成"+finishItem);
        if (totalItem == 0) {
            holder.progress_tv.setText("0.0%");
        } else {
            int t = (int) (finishItem * 10000.0f / totalItem + 0.5f);
            holder.progress_tv.setText(t/100.0f + "%");
        }

    }


    class Holder {
        ImageView userHead_img;
        TextView username_tv;
        TextView yearMonth_tv;
        TextView dayWork_tv;
        TextView progress_tv;
        TextView progressNote_tv;

        public Holder(View view) {
            userHead_img = (ImageView) view.findViewById(R.id.user_head_img);
            username_tv = (TextView) view.findViewById(R.id.user_name_tv);
            yearMonth_tv = (TextView) view.findViewById(R.id.date_tv);
            dayWork_tv = (TextView) view.findViewById(R.id.day_work_tv);
            progress_tv = (TextView) view.findViewById(R.id.progress_tv);
            progressNote_tv = (TextView) view.findViewById(R.id.progress_note_tv);
        }
    }
}
