package com.zxl.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zxl.Bookkeeping.R;
import com.zxl.Entity.overview_classification;

import java.util.List;

public class OverViewOCAdapter extends BaseAdapter {
    private static final String TAG = "OverViewOCAdapter";
    private Context mContext;
    private List<overview_classification> ocList;

    public OverViewOCAdapter(Context context,List<overview_classification> list){
        this.mContext = context;
        this.ocList = list;
    }
    //添加数据
    public void addData(List<overview_classification> data){
        if(ocList != null){
            ocList.addAll(data);
            log(data.toString());
            log(ocList.toString());
        }
    }
    //清空数组
    public void clearData(){
        if(ocList != null){
            ocList.clear();
        }
    }
    //移除数据
    public void removeData(overview_classification ac){
        if(ocList!=null){
            ocList.remove(ac);
        }
    }

    @Override
    public int getCount() {
        return ocList.size();
    }

    @Override
    public Object getItem(int position) {
        return ocList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        log(position+"");
        ViewHolder vh ;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_overview_classiication,parent,false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        overview_classification oc = ocList.get(position);
        vh.item_classification_tv1.setText(oc.getTypeName());
        float  percent = (float)(Math.round( oc.getPercent()*10000))/100;
        vh.item_classification_tv2.setText(percent+"%");
        vh.item_classification_tv3.setText("￥ "+oc.getTotalMoney());
        double v = Double.parseDouble(percent + "");
        int vTemp  = (int)v;
        vh.item_classification_pg.setProgress(vTemp);

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        log(ocList.toString());
        log(ocList!=null?"true":"false");
    }

    public class ViewHolder{
        public ImageView item_classification_iv;
        public TextView item_classification_tv1;
        public TextView item_classification_tv2;
        public TextView item_classification_tv3;
        public ProgressBar item_classification_pg;

        public ViewHolder(View v){
            item_classification_iv = v.findViewById(R.id.item_classification_iv);
            item_classification_tv1 = v.findViewById(R.id.item_classification_tv1);
            item_classification_tv2 = v.findViewById(R.id.item_classification_tv2);
            item_classification_tv3 = v.findViewById(R.id.item_classification_tv3);
            item_classification_pg = v.findViewById(R.id.item_classification_pg);
        }
    }

    private void log(String str){
        Log.d(TAG,str);
    }
}
