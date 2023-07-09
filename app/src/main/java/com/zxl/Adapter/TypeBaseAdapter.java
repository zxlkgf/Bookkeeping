package com.zxl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxl.Bookkeeping.R;
import com.zxl.Entity.TypeBean;

import java.util.List;

public class TypeBaseAdapter extends BaseAdapter {
    private Context mContext;
    public List<TypeBean> mData;

    public int selectPos = 0;//选中的位置

    public TypeBaseAdapter(Context context, List<TypeBean> mData){
        this.mContext = context;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //这个适配器不考虑复用 不需要写viewHolder
    //所有的item都显示在一个界面上，没有多余的convertView 所以不用复写
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_record_gv, parent, false);
        }
        //
        ImageView iv = convertView.findViewById(R.id.item_record_iv);
        TextView tv = convertView.findViewById(R.id.item_record_tv);
        //获取指定位置的数据源
        TypeBean typeBean = mData.get(position);
        tv.setText(typeBean.getTypeName());
        //判断当前位置是否为选中位置
        if (selectPos == position) {
            iv.setImageResource(typeBean.getsImageId());
        } else {
            iv.setImageResource(typeBean.getImageId());
        }
        return convertView;
    }

}
