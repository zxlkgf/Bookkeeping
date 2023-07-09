package com.zxl.DateBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zxl.Entity.AccountBean;
import com.zxl.Entity.TypeBean;
import com.zxl.Entity.overview_classification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class DBManager {
    private static final String TAG = "DBManager";
    private static SQLiteDatabase dbw;
    private static SQLiteDatabase dbr;

    public static void initDB(Context context){
        DBOpenHelper helper = new DBOpenHelper(context);    //得到帮助类对象
        dbw = helper.getWritableDatabase();      //得到写数据对象
        dbr = helper.getReadableDatabase();      //得到读数据对象
    }

    /**
     * 按照kind获取存储在数据库内的type数据
     * var = 0 支出
     * var = 1 收入
     * @param var
     * @return 返回TypeBean数组
     */
    public static List<TypeBean> getTypeList(int var){
        List<TypeBean> list = new ArrayList<>();
        //读取typedb内的数据
        String sql = "SELECT * FROM "+DBOpenHelper.TB_TYPE+" WHERE kind = " + var;
        Cursor cursor = dbr.rawQuery(sql, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String typeName = cursor.getString(cursor.getColumnIndexOrThrow("typeName"));
            int imageId = cursor.getInt(cursor.getColumnIndexOrThrow("imageId"));
            int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("sImageId"));
            int kind = cursor.getInt(cursor.getColumnIndexOrThrow("kind"));
            list.add(new TypeBean(id,typeName,imageId,sImageId,kind));
        }
        cursor.close();
        return list;
    }

    /**
     * 将一个消费数据插入AccountTB内
     * @param accountBean
     * @return 返回Boolean
     */
    public static Boolean insertDataToAccountTB(AccountBean accountBean){
        ContentValues values = new ContentValues();
        values.put("typeName",accountBean.getTypeName());
        values.put("sImageId",accountBean.getsImageId());
        values.put("notes",accountBean.getNotes());
        values.put("money",accountBean.getMoney());
        values.put("time",accountBean.getTime());
        values.put("year",accountBean.getYear());
        values.put("month",accountBean.getMonth());
        values.put("day",accountBean.getDay());
        values.put("hour",accountBean.getHour());
        values.put("minute",accountBean.getMinute());
        values.put("kind",accountBean.getKind());
        long row = dbw.insert(DBOpenHelper.TB_ACCOUNT, null, values);
        return row>0?true:false;
    }

    /**
     * 按照年月和类型查询消费数据
     * @param year 年
     * @param month 月
     * @return  返回消费数据的数组
     */
    public static List<AccountBean> queryAccountDataByYearMonth(int year,int month){
        List<AccountBean> dataList = new ArrayList<>();
        String sql = "SELECT * FROM "+ DBOpenHelper.TB_ACCOUNT +" WHERE year = ? AND month = ? ORDER BY id DESC";
        Cursor cursor = dbr.rawQuery(sql, new String[]{year + "", month + ""}, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String typeName = cursor.getString(cursor.getColumnIndexOrThrow("typeName"));
            int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("sImageId"));
            String notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"));
            float money = cursor.getFloat(cursor.getColumnIndexOrThrow("money"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            int y = cursor.getInt(cursor.getColumnIndexOrThrow("year"));
            int mon = cursor.getInt(cursor.getColumnIndexOrThrow("month"));
            int d = cursor.getInt(cursor.getColumnIndexOrThrow("day"));
            int h = cursor.getInt(cursor.getColumnIndexOrThrow("hour"));
            int min = cursor.getInt(cursor.getColumnIndexOrThrow("minute"));
            int k = cursor.getInt(cursor.getColumnIndexOrThrow("kind"));
            AccountBean ab = new AccountBean(id, typeName, sImageId, notes, money, time, y, mon, d, h, min, k);
            dataList.add(ab);
        }
        cursor.close();
        return dataList;
    }


    public static List<AccountBean> queryAccountDataByYearMonthKind(int year,int month,int kind){
        List<AccountBean> dataList = new ArrayList<>();
        String sql = "SELECT * FROM "+ DBOpenHelper.TB_ACCOUNT +" WHERE year = ? AND month = ? AND kind = ? ORDER BY id DESC";
        Cursor cursor = dbr.rawQuery(sql, new String[]{year + "", month + "",kind+""}, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String typeName = cursor.getString(cursor.getColumnIndexOrThrow("typeName"));
            int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("sImageId"));
            String notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"));
            float money = cursor.getFloat(cursor.getColumnIndexOrThrow("money"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            int y = cursor.getInt(cursor.getColumnIndexOrThrow("year"));
            int mon = cursor.getInt(cursor.getColumnIndexOrThrow("month"));
            int d = cursor.getInt(cursor.getColumnIndexOrThrow("day"));
            int h = cursor.getInt(cursor.getColumnIndexOrThrow("hour"));
            int min = cursor.getInt(cursor.getColumnIndexOrThrow("minute"));
            int k = cursor.getInt(cursor.getColumnIndexOrThrow("kind"));
            AccountBean ab = new AccountBean(id, typeName, sImageId, notes, money, time, y, mon, d, h, min, k);
            dataList.add(ab);
        }
        cursor.close();
        return dataList;
    }

    /**
     * 在数据库查找每当日消费
     * @param year 年
     * @param month 月
     * @param day 日
     * @param kind 类型
     * @return 返回 FLOAT
     */
    public static Float queryDailyCostByYearMonthDayKind(int year,int month, int day,int kind){
        Float dailyMoney = 0.0F;
        String sql = "SELECT SUM(money) FROM " + DBOpenHelper.TB_ACCOUNT + " WHERE year = ? AND month = ? AND day = ? AND kind = ?";
        Cursor cursor = dbr.rawQuery(sql, new String[]{year + "", month + "", day + "", kind + ""});
        if(cursor.moveToFirst()){
            dailyMoney = cursor.getFloat(cursor.getColumnIndexOrThrow("SUM(money)"));
        }
        cursor.close();
        return dailyMoney;
    }

    /**
     * 在数据库查找当月消费
     * @param year 年
     * @param month 月
     * @param kind 类型
     * @return 返回 FLOAT
     */
    public static Float queryMonthCostByYearMonthKind(int year,int month, int kind){
        Float monthMoney = 0.0F;
        String sql = "SELECT SUM(money) FROM " + DBOpenHelper.TB_ACCOUNT + " WHERE year = ? AND month = ? AND kind = ?";
        Cursor cursor = dbr.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if(cursor.moveToNext()){
            monthMoney = cursor.getFloat(cursor.getColumnIndexOrThrow("SUM(money)"));
        }
        cursor.close();
        return monthMoney;
    }

    /**
     * 根据id删除一条数据
     * @param id 编号
     * @return 返回是否删除成功的标志
     */
    public static Boolean deleteAccountDataById(int id){
        int row = dbw.delete(DBOpenHelper.TB_ACCOUNT, "id = ?", new String[]{id + ""});
        return row>0?true:false;
    }


    /**
     * 根据Kind查看有多少笔支出或者收入
     * @param year 年
     * @param month 月
     * @param kind 日
     * @return 返回支出或者收入笔数
     */
    public static int queryCountByYearMonthKind(int year,int month, int kind){
        int OutCount = 0;
        String sql = "SELECT COUNT(money) FROM " + DBOpenHelper.TB_ACCOUNT + " WHERE year = ? AND month = ? AND kind = ?";
        Cursor cursor = dbr.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if(cursor.moveToNext()){
            OutCount = cursor.getInt(cursor.getColumnIndexOrThrow("COUNT(money)"));
        }
        cursor.close();
        return OutCount;
    }

    /**
     * 根据类型 查找总金额
     * @param year 年
     * @param month 悦
     * @param kind 日
     * @param TypeName 类型
     * @return
     */
    public static overview_classification queryTypeSumByYearMonthKind(int year, int month, int kind, String TypeName){
        overview_classification oc = new overview_classification();
        String sql = "SELECT SUM(money),sImageId,typeName FROM " + DBOpenHelper.TB_ACCOUNT + " WHERE year = ? AND month = ? AND kind = ? AND typeName = ?";
        Cursor cursor = dbr.rawQuery(sql, new String[]{year + "", month + "", kind + "",TypeName});
        if(cursor.moveToNext()){
           oc.setTotalMoney( cursor.getFloat(0));
           oc.setsImageId( cursor.getInt(1));
           oc.setTypeName(cursor.getString(2));
        }
        cursor.close();
        return oc;
    }

    public static List<AccountBean> query3rdMaxOut(int year,int month,int kind){
        List<AccountBean> list  = new ArrayList<>();
        String sql = "SELECT * FROM "+DBOpenHelper.TB_ACCOUNT+" WHERE year = ? AND month = ? AND kind = ? ORDER BY money DESC limit 3";
        Cursor cursor = dbr.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String typeName = cursor.getString(cursor.getColumnIndexOrThrow("typeName"));
            int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("sImageId"));
            String notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"));
            float money = cursor.getFloat(cursor.getColumnIndexOrThrow("money"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            int y = cursor.getInt(cursor.getColumnIndexOrThrow("year"));
            int mon = cursor.getInt(cursor.getColumnIndexOrThrow("month"));
            int d = cursor.getInt(cursor.getColumnIndexOrThrow("day"));
            int h = cursor.getInt(cursor.getColumnIndexOrThrow("hour"));
            int min = cursor.getInt(cursor.getColumnIndexOrThrow("minute"));
            int k = cursor.getInt(cursor.getColumnIndexOrThrow("kind"));
            AccountBean ab = new AccountBean(id, typeName, sImageId, notes, money, time, y, mon, d, h, min, k);
            log(ab.toString());
            list.add(ab);
        }
        return list;
    }


    private static void log(String str){
        Log.d(TAG,str);
    }
}
