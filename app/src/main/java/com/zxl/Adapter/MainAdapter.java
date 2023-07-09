package com.zxl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxl.Bookkeeping.R;
import com.zxl.Entity.AccountBean;
import com.zxl.Utils.Util;

import java.util.List;

public class MainAdapter extends BaseAdapter {
    private Context mContext;
    private List<AccountBean> mDates;

    public MainAdapter(Context context, List<AccountBean> list){
        this.mContext = context;
        this.mDates = list;
    }

    //添加数据
    public void addData(List<AccountBean> data){
        if(mDates != null){
            mDates.addAll(data);
        }
    }
    //清空数组
    public void clearData(){
        if(mDates != null){
            mDates.clear();
        }
    }
    //移除数据
    public void removeData(AccountBean ac){
        if(mDates!=null){
            mDates.remove(ac);
        }
    }

    @Override
    public int getCount() {
        return mDates.size();
    }

    @Override
    public Object getItem(int position) {
        return mDates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_lv,parent,false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        AccountBean ac = mDates.get(position);
        vh.item_main_lv_data.setText(ac.getTime());
        vh.item_main_lv_iv.setImageResource(ac.getsImageId());
        vh.item_main_tv_label.setText("[ "+ac.getTypeName()+" ]");
        vh.item_main_tv_notes.setText(ac.getNotes());
        vh.item_main_tv_time.setText(Util.getHourMinuteStr(ac.getHour(),":",ac.getMinute(),""));
        vh.item_main_tv_money.setText("￥ "+ac.getMoney());

        return convertView;
    }

    public class ViewHolder{
        public TextView item_main_lv_data;
        public ImageView item_main_lv_iv;
        public TextView item_main_tv_label;
        public TextView item_main_tv_notes;
        public TextView item_main_tv_time;
        public TextView item_main_tv_money;

        public ViewHolder(View v){
            item_main_lv_data = v.findViewById(R.id.item_main_lv_data);
            item_main_lv_iv = v.findViewById(R.id.item_main_lv_iv);
            item_main_tv_label = v.findViewById(R.id.item_main_tv_label);
            item_main_tv_notes = v.findViewById(R.id.item_main_tv_notes);
            item_main_tv_time = v.findViewById(R.id.item_main_tv_time);
            item_main_tv_money = v.findViewById(R.id.item_main_tv_money);
        }
    }
}
