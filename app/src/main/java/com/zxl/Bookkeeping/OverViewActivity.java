package com.zxl.Bookkeeping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.zxl.Adapter.OverViewOCAdapter;
import com.zxl.Adapter.OverViewRankAdapter;
import com.zxl.DateBase.DBManager;
import com.zxl.Entity.AccountBean;
import com.zxl.Entity.overview_classification;
import com.zxl.Utils.Util;
import com.zxl.Bookkeeping.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OverViewActivity extends AppCompatActivity {
    private static final String TAG = "OverViewActivity";
    //title
    private TextView overview_title_tv1;
    private TextView overview_title_tv2;
    //classification
    private ListView overview_classification_lv;
    //rank
    private ListView overview_rank_lv;
    private HashMap<String,Integer> map = Util.getCalenderTime();
    int year,month;
    private String typeArray[];
    private float monthMoney;
    private int monthCount;
    private List<overview_classification> ocList;
    private OverViewOCAdapter OCAdapter;
    private List<AccountBean> RankList;
    private int ImageId[];
    private OverViewRankAdapter RankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        log("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_view);
        initView();
        initTitle();
    }

    @Override
    protected void onResume() {
        log("onResume");
        super.onResume();
        initOcList();
        initOcListView();
        initRankListView();
    }

    private void initView() {
        log("initView");
        ocList = new ArrayList<>();
        RankList = new ArrayList<>();
        OCAdapter = new OverViewOCAdapter(this,ocList);
        ImageId = new int[]{
                R.mipmap.overview_golden,
                R.mipmap.overview_silver,
                R.mipmap.overview_bronze
        };
        RankAdapter = new OverViewRankAdapter(this,RankList,ImageId);
        typeArray = getResources().getStringArray(R.array.typeName);
        overview_title_tv1 = findViewById(R.id.overview_title_tv1);
        overview_title_tv2 = findViewById(R.id.overview_title_tv2);
        overview_classification_lv = findViewById(R.id.overview_classification_lv);
        overview_rank_lv = findViewById(R.id.overview_rank_lv);
        overview_classification_lv.setAdapter(OCAdapter);
        overview_rank_lv.setAdapter(RankAdapter);
    }
    private void initTitle(){
        log("initTitle");
        year = map.get("year");
        month = map.get("month");
        monthMoney = DBManager.queryMonthCostByYearMonthKind(year, month, 0);
        monthCount = DBManager.queryCountByYearMonthKind(year,month,0);
        overview_title_tv1.setText("本月账单共计"+monthCount+"笔 合计");
        overview_title_tv2.setText("￥ "+ monthMoney);
    }
    private void initOcList(){
        log("initOcList");
        ocList.clear();
        //获取到初始化类型名，选择图片，类型总金额
        for (String name : typeArray){
            overview_classification oc = DBManager.queryTypeSumByYearMonthKind(year, month, 0, name);
            if(oc.getTypeName() != null){
                //增加金额占比
                oc.setPercent(oc.getTotalMoney()/monthMoney);
                ocList.add(oc);
            }
        }
        log(ocList.toString());
    }

    private void initOcListView(){
        log("initOcListView");
        OCAdapter.notifyDataSetChanged();
    }

    private void initRankListView(){
        List<AccountBean> list = DBManager.query3rdMaxOut(year, month, 0);
        log(list.toString());
        RankList.clear();
        RankList.addAll(list);
        Util.setListViewHeightBasedOnChildren(overview_classification_lv);
        RankAdapter.notifyDataSetChanged();
    }

    private void log(String str){
        Log.d(TAG,str);
    }
}