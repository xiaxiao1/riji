package com.xiaxiao.riji.util;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaxiao.riji.Listener.RiJiCallback;
import com.xiaxiao.riji.R;
import com.xiaxiao.riji.bean.WorkItem;


/**
 * Created by Administrator on 2016/11/4.
 */
public class UIDialog {
    private boolean isShow=false;
    private ProgressDialog progressDialog;
    AlertDialog kdialog;
    AlertDialog takePhotoDialog;
    private Context context;
    public UIDialog(Context context) {
        this.context = context;
    }
    public void showWaitDialog() {
        progressDialog = ProgressDialog.show(context, "", "等会哦", false, true, null);
        isShow=true;
    }
    public void showWaitDialog(String message) {
        progressDialog = ProgressDialog.show(context, message, "等会哦", false, true, null);
        isShow=true;
    }

    public void dismissWaitDialog() {
        if (isShow) {
            progressDialog.dismiss();
        }
    }
    public void dismissTakePhotoDialog() {
        if (takePhotoDialog!=null&&takePhotoDialog.isShowing()) {
            takePhotoDialog.dismiss();
        }
    }

    public void showTakePhotoDialog(final CustomDialogListener customDialogListener) {
        if (takePhotoDialog!=null) {
            takePhotoDialog.show();
            return;
        }
        TextView takePhoto_tv;
        TextView fromGallery_tv;
        TextView cancle_tv;
        View view = View.inflate(context, R.layout.dialog_view_choose_photo, null);
        takePhoto_tv = (TextView) view.findViewById(R.id.take_photo_tv);
        fromGallery_tv = (TextView) view.findViewById(R.id.from_gallery_tv);
        cancle_tv = (TextView) view.findViewById(R.id.cancle_tv);
        if (customDialogListener!=null) {


            cancle_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissTakePhotoDialog();
                    customDialogListener.onItemClick(2);
                }
            });
            takePhoto_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Util.toast(context,"take photo");
                    dismissTakePhotoDialog();
                    customDialogListener.onItemClick(0);
                }
            });
            fromGallery_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Util.toast(context,"from gallery");
                    dismissTakePhotoDialog();
                    customDialogListener.onItemClick(1);
                }
            });
        }
        takePhotoDialog=new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();

        takePhotoDialog.getWindow().setBackgroundDrawableResource(R.drawable.whrite_solid_bg);
        takePhotoDialog.show();
        //一定得在show完dialog后来set属性
        WindowManager.LayoutParams lp = takePhotoDialog.getWindow().getAttributes();
        lp.width = context.getResources().getDimensionPixelSize(R.dimen.dialog_width);
        takePhotoDialog.getWindow().setAttributes(lp);
    }

    public void showAddNoteDialog(final CustomDialogListener customDialogListener) {

        TextView addNote_tv;

        View view = View.inflate(context, R.layout.dialog_view_add_note, null);
        addNote_tv = (TextView) view.findViewById(R.id.dialog_add_note_tv);

        final AlertDialog mdialog=new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();

        if (customDialogListener!=null) {

            addNote_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialogListener.onItemClick(0);
                    mdialog.dismiss();
                }
            });

        }

        mdialog.getWindow().setBackgroundDrawableResource(R.drawable.whrite_solid_bg);
        mdialog.show();
        //一定得在show完dialog后来set属性
        WindowManager.LayoutParams lp = mdialog.getWindow().getAttributes();
        lp.width = context.getResources().getDimensionPixelSize(R.dimen.dialog_width);
        mdialog.getWindow().setAttributes(lp);
    }

    /*public void showChooseTypeDialog(String text1,String text2, final CustomDialogListener customDialogListener) {

        TextView t1;
        TextView t2;

        View view = View.inflate(context, R.layout.dialog_view_choose_type, null);
        t1 = (TextView) view.findViewById(R.id.t1);
        t2 = (TextView) view.findViewById(R.id.t2);
        t1.setText(text1);
        t2.setText(text2);

        final AlertDialog mdialog=new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();

        if (customDialogListener!=null) {

            t1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialogListener.onItemClick(0);
                    mdialog.dismiss();
                }
            });
            t2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialogListener.onItemClick(1);
                    mdialog.dismiss();
                }
            });

        }

        mdialog.getWindow().setBackgroundDrawableResource(R.drawable.view_white_bg);
        mdialog.show();
        //一定得在show完dialog后来set属性
        WindowManager.LayoutParams lp = mdialog.getWindow().getAttributes();
        lp.width = context.getResources().getDimensionPixelSize(R.dimen.dialog_width);
        mdialog.getWindow().setAttributes(lp);
    }*/



    public interface CustomDialogListener{
        public void onItemClick(int index);
    }

    /**
     * 展示添加计划项目
     * @param context
     * @param riJiCallback
     * @return
     */
    public static Dialog showAddWorkItem(Context context, final RiJiCallback riJiCallback) {
        final Dialog dialog = new Dialog(context);
        View v = LayoutInflater.from(context).inflate(R.layout.add_work_item, null);

        final EditText editText = (EditText) v.findViewById(R.id.edit_et);
        Button button = (Button) v.findViewById(R.id.ok_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String mes = editText.getText().toString().trim();
                if (!mes.equals("")) {
                    dialog.dismiss();
                    riJiCallback.handle(mes);
                }
            }
        });

        dialog.setContentView(v);
        dialog.getWindow().getDecorView().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dialog.show();

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        return dialog;
    }

    /**
     * 展示计划项完成设置
     * @param context
     * @param riJiCallback
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static Dialog showFinishWorkItemDialog(Context context, final RiJiCallback riJiCallback) {
        final Dialog dialog = new Dialog(context);
        View v = LayoutInflater.from(context).inflate(R.layout.finish_work_dialog, null);

        final TextView on_tv= (TextView) v.findViewById(R.id.finish_on_tv);
        final TextView off_tv = (TextView) v.findViewById(R.id.finish_off_tv);

        on_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riJiCallback.handle(WorkItem.WORK_ITEM_STATE_FINISH);
                dialog.dismiss();
            }
        });
        off_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riJiCallback.handle(WorkItem.WORK_ITEM_STATE_UNFINISH);
                dialog.dismiss();
            }
        });

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(v);
        dialog.getWindow().getDecorView().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dialog.show();

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
//        dialog.getWindow().setBackgroundDrawable(null);
        //这一句可以设置dialog没有默认的白色边框底
        dialog.getWindow().getDecorView().setBackground(null);

        return dialog;
    }
}
