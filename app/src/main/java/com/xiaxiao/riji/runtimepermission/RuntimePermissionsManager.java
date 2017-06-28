package com.xiaxiao.riji.runtimepermission;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;

/**
 * 运行时权限申请管理类，负责运行时对权限的单个或多个请求，并对请求结果做出响应，
 * 需要在调用的类中重载onRequestPermissionsResult方法：
 * @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
    grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        runtimePermissionsManager.handle(requestCode, permissions, grantResults);
    }
 * Created by xiaxiao on 2016/10/20.
 */
public class RuntimePermissionsManager {
    private Activity activity;
    private int requestCode=8888;
    private RequestCallback requestCallback;
    //变量为true时， 刚进程序，申请的权限没有被全部通过时，则退出程序。
    private boolean exitWhileNotAllPermissionsGranted=false;

    /**
     * 本程序中需要用到的权限,切记，对应的权限一定要在manifest文件中写上，，，注意write_settings权限的申请
     */
    public final static String[] requestedPermissions= new String[]{
            Manifest.permission.ACCESS_NETWORK_STATE
            , Manifest.permission.ACCESS_COARSE_LOCATION
            , Manifest.permission.INTERNET
            , Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.WAKE_LOCK
            , Manifest.permission.CHANGE_WIFI_STATE
            , Manifest.permission.ACCESS_WIFI_STATE
            , Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.CALL_PHONE
            , Manifest.permission.GET_TASKS
            , Manifest.permission.CAMERA
            , Manifest.permission.WRITE_CONTACTS
            , Manifest.permission.READ_CALENDAR
            , Manifest.permission.READ_CALL_LOG
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.RECEIVE_BOOT_COMPLETED
            , Manifest.permission.BROADCAST_STICKY
            , Manifest.permission.PROCESS_OUTGOING_CALLS
            , Manifest.permission.MODIFY_AUDIO_SETTINGS
            , Manifest.permission.VIBRATE
            , Manifest.permission.GET_PACKAGE_SIZE
            , Manifest.permission.SEND_SMS
            , Manifest.permission.RECEIVE_SMS
            , Manifest.permission.READ_SMS
            , Manifest.permission.CHANGE_NETWORK_STATE
            , Manifest.permission.GET_ACCOUNTS
            , Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
            , Manifest.permission.RECORD_AUDIO
    };

    public RuntimePermissionsManager(Activity activity, boolean exitWhileNotAllPermissionsGranted) {
        this.exitWhileNotAllPermissionsGranted=exitWhileNotAllPermissionsGranted;
        this.activity=activity;
    }

    /**
     * 检查所给的权限是否已被授权
     * @param permission 要检查的权限名字
     * @return true:已被授权；false:未被授权
     */
    public boolean checkPermission(String permission) {
        return  permission!=null&&(ContextCompat.checkSelfPermission(activity,permission)== PackageManager.PERMISSION_GRANTED);
    }

    /**
     * 请求授权,限内部调用
     * @param permissions
     * 当不为null时，表示请求传入的权限，
     */
    private void requestPermissions(String[] permissions) {
        if (permissions==null) {
            return;
        }
        //过滤出目前还未被授权的权限
        String[] noGrantedPermissions;
        ArrayList<String> l=new ArrayList<String>();
        for (String s:permissions) {
            if (!checkPermission(s)) {
                l.add(s);
            }
        }
        if (l.size()==0) {
            if (requestCallback!=null) {
                requestCallback.requestSuccess();
            }
            return;
        }
        //一次性请求所有未被授权的权限
        noGrantedPermissions=(String[])l.toArray(new String[l.size()]);
        ActivityCompat.requestPermissions(activity,
                noGrantedPermissions,
                requestCode);
    }

    /**
     * 请求程序所有运行时需要申请的权限
     */
    public void requestPermissions(String[] permissions, RequestCallback requestCallback) {
        this.requestCallback=requestCallback;
        requestPermissions(permissions);
    }

    /**
     * 请求指定的单个权限
     * @param permission 给定的权限
     */
    private void requestPermission(String permission) {

        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            showDialog();
        } else {
            requestPermissions(new String[]{permission});
        }
    }

    /**
     * 处理权限申请的回调方法
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void handle(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCallback==null) {
            return;
        }
        if (requestCode == this.requestCode) {
            int grantedNum=permissions.length;
            Log.i("xx","permissions size:"+permissions.length+"  grantResult size:"+grantResults.length);
            for (int i=0;i<grantResults.length;i++) {
                int result=grantResults[i];
                if (result!= PackageManager.PERMISSION_GRANTED) {
                    grantedNum--;
                }
            }

            if (grantedNum < permissions.length) {
                //判断是否必须全部允许权限才能进入程序，否则退出
                if (exitWhileNotAllPermissionsGranted) {
                    showDialog();
                } else {
                    requestCallback.requestFailed();
                }
            } else {//全部通过
                requestCallback.requestSuccess();
            }


        } else {
          Log.i("xx","error:requestCode not match");
        }

    }

    /**
     * 检测权限，如未被授权则申请，并继续处理业务
     * @param permission 需要使用的权限
     * @param requestCallback 回调类，负责处理具体业务，包含权限已被授权和申请后的情况
     */
    public void workwithPermission(String permission, RequestCallback requestCallback) {
        if (permission==null||requestCallback==null) {
            return;
        }
        this.requestCallback=requestCallback;
        if (checkPermission(permission)) {
            requestCallback.requestSuccess();
        } else {
            requestPermission(permission);

        }

    }

    private void showDialog(){
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setMessage("下一步操作需要开启相应的权限，不开启将无法正常工作！")
                .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i=new Intent();
                        i.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        i.setData(Uri.fromParts("package", activity.getPackageName(), null));
                        activity.startActivity(i);
                        activity.finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (exitWhileNotAllPermissionsGranted) {
                            activity.finish();
                        }
                    }
                }).create();
        dialog.show();
    }

    public interface RequestCallback {
        public void requestSuccess();
        public void requestFailed();
    }

    /**
     *以下是运行时请求的权限分组
     */
    /*public final static String[] group_CONTACTS={
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.GET_ACCOUNTS,
        Manifest.permission.READ_CONTACTS};

    public final static String[] group_PHONE={
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.WRITE_CALL_LOG,
        Manifest.permission.USE_SIP,
        Manifest.permission.PROCESS_OUTGOING_CALLS,
        Manifest.permission.ADD_VOICEMAIL};

    public final static String[] group_CALENDAR={
        Manifest.permission.READ_CALENDAR,
        Manifest.permission.WRITE_CALENDAR};

    public final static String[] group_CAMERA={
        Manifest.permission.CAMERA};

    public final
    static String[] group_SENSORS={
        Manifest.permission.BODY_SENSORS};

    public final static String[] group_LOCATION={
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

    public final static String[] group_STORAGE={
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public final static String[] group_MICROPHONE={
        Manifest.permission.RECORD_AUDIO};

    public final static String[] group_SMS={
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_WAP_PUSH,
        Manifest.permission.RECEIVE_MMS,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.SEND_SMS,
        //Manifest.permission.READ_CELL_BROADCASTS
        };
*/
}
























































