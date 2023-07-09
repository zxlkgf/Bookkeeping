package com.zxl.Utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.Calendar;
import java.util.HashMap;

public class Util {

    /**
     * 获取年月日时分秒
     * @return 返回值为Map数组
     */
    public static HashMap<String,Integer> getCalenderTime(){
        Calendar calendar = Calendar.getInstance();
        HashMap<String,Integer> map = new HashMap<String,Integer>(){
            {
                put("year",calendar.get(Calendar.YEAR));
                put("month",calendar.get(Calendar.MONTH)+1);
                put("day",calendar.get(Calendar.DAY_OF_MONTH));
                put("week",calendar.get(Calendar.WEEK_OF_MONTH)+1);
                put("hour",calendar.get(Calendar.HOUR_OF_DAY));
                put("minute",calendar.get(Calendar.MINUTE));
                put("second",calendar.get(Calendar.SECOND));
            }
        };
        return map;
    }


    private static String setStandardTimeOrDate(Integer num,String str){
        if (num!=null&&num<10){
            return "0"+num+str;
        }else{
            return num+str;
        }
    }

    public static String getYearMonthDayStr(int year,int month,int day){
        StringBuilder sb = new StringBuilder(year+"年");
        sb.append(setStandardTimeOrDate(month,"月"))
                .append(setStandardTimeOrDate(day,"日"));
        return sb.toString();
    }

    public static String getYearMonthStr(int year,int month){
        StringBuilder sb = new StringBuilder(year+"年");
        sb.append(setStandardTimeOrDate(month,"月"));
        return sb.toString();
    }

    public static String getHourMinuteStr(int hour,String midStr,int minute,String endStr){
        StringBuilder sb = new StringBuilder();
        sb.append(setStandardTimeOrDate(hour,midStr))
                .append(setStandardTimeOrDate(minute,endStr));
        return sb.toString();
    }



    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //只显示五行
        params.height = (5 * totalHeight / listAdapter.getCount()) + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
