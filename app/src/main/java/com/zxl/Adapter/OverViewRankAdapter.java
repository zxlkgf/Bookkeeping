package com.zxl.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxl.Bookkeeping.R;
import com.zxl.Entity.AccountBean;

import java.util.List;

public class OverViewRankAdapter extends BaseAdapter {
    private static final String TAG = "OverViewRankAdapter";
    private Context mContext;
    private List<AccountBean> mDatas;
    private int imageId[];

    public OverViewRankAdapter(Context context,List<AccountBean> list,int imageIdArray[]){
        this.mContext = context;
        this.mDatas = list;
        this.imageId = imageIdArray;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh ;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_overview_rank,parent,false);
            vh = new OverViewRankAdapter.ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh = (OverViewRankAdapter.ViewHolder) convertView.getTag();
        }
        AccountBean ac = mDatas.get(position);
        vh.item_overview_iv.setImageResource(imageId[position]);
        vh.item_overview_tv1.setText(ac.getTypeName());
        vh.item_overview_tv2.setText(ac.getNotes());
        vh.item_overview_tv3.setText("￥ "+ac.getMoney());

        return convertView;
    }

    public class ViewHolder{
        public ImageView item_overview_iv;
        public TextView item_overview_tv1;
        public TextView item_overview_tv2;
        public TextView item_overview_tv3;

        public ViewHolder(View v){
            item_overview_iv = v.findViewById(R.id.item_overview_iv);
            item_overview_tv1 = v.findViewById(R.id.item_overview_tv1);
            item_overview_tv2 = v.findViewById(R.id.item_overview_tv2);
            item_overview_tv3 = v.findViewById(R.id.item_overview_tv3);

        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        log(mDatas.toString());
    }

    private void log(String str){
        Log.d(TAG,str);
    }
}
