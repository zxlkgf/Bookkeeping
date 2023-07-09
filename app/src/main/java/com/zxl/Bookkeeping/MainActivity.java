package com.zxl.Bookkeeping;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.zxl.Adapter.MainAdapter;
import com.zxl.DateBase.DBManager;
import com.zxl.Dialog.MoreDialog;
import com.zxl.Entity.AccountBean;
import com.zxl.Utils.Util;
import com.zxl.Bookkeeping.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    //main_layout
    private LinearLayout main_ll;
    private TextView main_daily_tv;
    private ListView main_lv;
    private Button main_btn_edit;
    private ImageButton main_btn_more;
    //item_main_layout
    private ImageView item_main_iv_back;
    private ImageView item_main_iv_search;
    private TextView item_main_top_rl_ll1_outTv2;
    private TextView item_main_top_rl_ll2_inTv2;
    private MainAdapter adapter;
    private List<AccountBean> dataList;
    private HashMap<String, Integer> map = Util.getCalenderTime();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataList = new ArrayList<>();
        adapter = new MainAdapter(this,dataList);
        initView();
        initHeadView();
        setListViewListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
        initListView();
        initCost();
    }

    private void initView(){
        main_ll = findViewById(R.id.main_ll);
        main_daily_tv = findViewById(R.id.main_daily_tv);
        main_lv = findViewById(R.id.main_lv);
        main_btn_edit = findViewById(R.id.main_btn_edit);
        main_btn_more = findViewById(R.id.main_btn_more);

        main_lv.setAdapter(adapter);
        main_btn_edit.setOnClickListener(this);
        main_btn_more.setOnClickListener(this);
    }
    private void initHeadView(){
        View inflate = getLayoutInflater().inflate(R.layout.item_main_header,null);
        item_main_iv_back = inflate.findViewById(R.id.item_main_iv_back);
        item_main_iv_search = inflate.findViewById(R.id.item_main_iv_search);
        item_main_top_rl_ll1_outTv2 = inflate.findViewById(R.id.item_main_top_rl_ll1_outTv2);
        item_main_top_rl_ll2_inTv2 = inflate.findViewById(R.id.item_main_top_rl_ll2_inTv2);
        main_ll.addView(inflate);

        item_main_iv_back.setOnClickListener(this);
        item_main_iv_search.setOnClickListener(this);
    }

    private void initListView(){
        //本月数据
        Integer year = map.get("year");
        Integer month = map.get("month");
        //查找数据
        List<AccountBean> yearAndMonthData = DBManager.queryAccountDataByYearMonth(year, month);
        log(yearAndMonthData.toString());
        //先清空再加入
        adapter.clearData();
        adapter.addData(yearAndMonthData);
        //提醒适配器
        adapter.notifyDataSetChanged();
    }


    private void initCost(){
        int year = map.get("year");
        int month = map.get("month");
        int day = map.get("day");
        //获取月支出 月收入
        Float outMonthMoney = DBManager.queryMonthCostByYearMonthKind(year, month, 0);
        Float inMonthMoney = DBManager.queryMonthCostByYearMonthKind(year, month, 1);

        //获取日支出 日收入
        Float outDailyMoney = DBManager.queryDailyCostByYearMonthDayKind(year, month, day, 0);
        Float inDailyMoney = DBManager.queryDailyCostByYearMonthDayKind(year, month, day, 1);

        //设置月显示
        item_main_top_rl_ll1_outTv2.setText("￥ "+outMonthMoney);
        item_main_top_rl_ll2_inTv2.setText("￥ "+inMonthMoney);

        //设置日显示
        main_daily_tv.setText("今日支出￥ "+outDailyMoney+" 收入￥ "+inDailyMoney);
    }
    //设置点击事件，删除单个条目
    private void setListViewListener(){
        if(main_lv == null)return;
        main_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AccountBean item = (AccountBean) parent.getItemAtPosition(position);
                log("position : "+position+" item id :"+item.getId());
                showAlertDialog(item);
                return  false;
            }
        });
    }

    private void showAlertDialog(AccountBean ac){
        new AlertDialog.Builder(this)
                .setTitle("删除确认")
                .setIcon(R.mipmap.icon_jinggao)
                .setMessage("您是否要删除该条记录?")
                .setNegativeButton("取消",null)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager.deleteAccountDataById(ac.getId());
                        adapter.removeData(ac);
                        adapter.notifyDataSetChanged();
                        initCost();
                    }
                }).show();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if(v.getId() == R.id.item_main_iv_back){
            log("R.id.item_main_iv_back");
            finish();
        }else if(v.getId() == R.id.item_main_iv_search){
            log("R.id.item_main_iv_search");
        }else if (v.getId() == R.id.main_btn_edit){
            log("R.id.main_btn_edit");
            intent = new Intent(this, RecordActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_btn_more) {
            showMoreDialog();
        }
    }

    private void showMoreDialog(){
        MoreDialog moreDialog = new MoreDialog(this);
        moreDialog.show();
    }

    private void log(String str){
        Log.d(TAG,str);
    }
}