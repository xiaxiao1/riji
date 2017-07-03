package com.xiaxiao.riji.dbwork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.xiaxiao.riji.bean.DayWork;
import com.xiaxiao.riji.bean.WorkItem;
import com.xiaxiao.riji.util.XiaoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class DayWorkDBHelper extends SQLiteOpenHelper{

    //版本号
    public static final int VERTION=1;
    //数据库名称
    public static final  String  NAME="riji.db";
    //表名

    /**
     * id
     * date
     * note
     * progress
     */
    public static final String tableName_daywork = "daywork";
    public static final String tableName_workitem = "workitem";
    //建表语句
    private String createTable_daywork = "create table "+tableName_daywork+"(id varchar(30),date varchar(30),note varchar(30),progress varchar(30))";
    private String createTable_workitem = "create table "+tableName_workitem+"(id varchar(30),work varchar(30),finish int)";
    //执行操作的对象
    private SQLiteDatabase db;
    //must have
    public DayWorkDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int
            version) {
        super(context, name, factory, version);
        getDB();
    }

    //第一次创建数据库的时候调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tableName_daywork);
        db.execSQL(tableName_workitem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        XiaoUtil.l("update database");
        db.execSQL("drop table book");
        db.execSQL(tableName_daywork);
        db.execSQL(tableName_workitem);
    }

    /**
     * 向数据库添加一日计划
     * @param dayWork
     * @return
     */
    public boolean addDayWork(DayWork dayWork) {
        ContentValues value = createDayWorkContentValues(dayWork);
        if(-1==db.insert(tableName_daywork, null, value)){
            return false;
        }

//        return addWorkItems(dayWork.getWorkItems());
        return false;
    }

    /**
     * 从数据库删除某日计划
     * @param dayWork
     * @return
     */
    public boolean deleteDayWork(DayWork dayWork) {

       /* //删除条件
        String whereClause = "id=?";
        //删除条件参数
        String[] whereArgs = {""+dayWork.getLocalId()+""};
        //执行删除
        if(0<db.delete(tableName_daywork,whereClause,whereArgs)){
            return de;
        }*/
        //为啥要删除，，不行！
        return false;
    }

    public int  clearTable() {
        return db.delete(tableName_daywork, null, null)+db.delete(tableName_workitem, null, null);
    }
/*

    */
/**
     * 更新一本书的信息
     * @param book
     * @param item 是否同时更新
     * @return
     *//*

    public boolean update(BookBean book,boolean item) {
        ContentValues value = new ContentValues();
//        value.put("id",book.getId());
//        value.put("name",book.getName());
//        value.put("type",book.getBuyType());
//        value.put("added_time",book.getAddedTime());
//        value.put("read_status",book.getReadType());
        //更新条件
        String whereClause = "id=?";
        //更新条件参数
        String[] whereArgs = {""+book.getObjectId()+""};
        if(0<db.update(tableName, value, whereClause, whereArgs)){
            Util.L("update local DB ok");
            return true;
        }
        Util.L("update local DB error");
        return false;
    }

    */
/**
     * 获取不同类型的书
     * @param type
     * @return
     *//*

    public List<BookBean> getBooks(int type) {
        //查询条件
        String whereClause = null;
        //查询条件参数
        String[] whereArgs = null;
        if (type!=-1) {
            whereClause = "type=?";
            //查询条件参数
            whereArgs = new String[]{String.valueOf(type)};
        }
        Cursor cursor=db.query(tableName, null, whereClause, whereArgs, null, null, null, null);
        Util.L("count :"+cursor.getCount());
        if (cursor.getCount()<=0) {
            Util.L("no database datas");
            return null;
        }
        List<BookBean> list=new ArrayList<>();
        while(cursor.moveToNext()) {
            BookBean b=new BookBean();
//            b.setId(cursor.getString(0));
//            b.setBuyType(cursor.getInt(2));
//            b.setAddedTime(cursor.getInt(3));
//            b.setReadType(cursor.getInt(4));
            list.add(b);
        }
        cursor.close();
        return list;
    }

    */
/**
     * 模糊查询某一本书
     * @return
     *//*

    public List<BookBean> queryBooks(String bookName) {
        //查询条件
        String whereClause = "name like ?";
        //查询条件参数
        String[] whereArgs = {"%"+bookName+"%"};
        Cursor cursor=db.query(tableName, null, whereClause, whereArgs, null, null, null, null);
        Util.L("count :"+cursor.getCount());
        List<BookBean> list=new ArrayList<>();
        while(cursor.moveToNext()) {
            BookBean b=new BookBean();
//            b.setId(cursor.getString(0));
//            b.setBuyType(cursor.getInt(2));
//            b.setAddedTime(cursor.getInt(3));
//            b.setReadType(cursor.getInt(4));
            list.add(b);
        }
        cursor.close();
        return list;
    }

*/


    public boolean addWorkItem(WorkItem workItem) {
        return true;
    }

    public boolean addWorkItems(List<WorkItem> workItems) {
        if (workItems==null||workItems.size()==0) {
            return false;
        }
        for (WorkItem workItem:workItems) {
            addWorkItem(workItem);
        }

        return true;
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



    private SQLiteDatabase getDB() {
        if (db==null) {
            db = this.getWritableDatabase();
        }
        return db;
    }
    private void closeDB() {
        if (db!=null) {
            db.close();
        }
    }


    public ContentValues createDayWorkContentValues(DayWork dayWork) {
        ContentValues value = new ContentValues();
        value.put("id",dayWork.getLocalId());
        value.put("date ",dayWork.getDate());
        value.put("note",dayWork.getNote());
        value.put("progress",dayWork.getProgress());

        return value;
    }

    public ContentValues createWorkItemContentValues(WorkItem workItem) {
        ContentValues value = new ContentValues();
        value.put("id",workItem.getLocalId());
        value.put("work ",workItem.getWork());
        value.put("finish",workItem.getFinish());
        return value;
    }
}
