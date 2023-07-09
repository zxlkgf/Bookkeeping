package com.zxl.Bookkeeping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zxl.DateBase.DBManager;
import com.zxl.Entity.overview_classification;
import com.zxl.Utils.Util;
import com.zxl.anan.AAChartCore.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.zxl.anan.AAChartCore.AAChartCoreLib.AAChartCreator.AAChartView;
import com.zxl.anan.AAChartCore.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.zxl.anan.AAChartCore.AAChartCoreLib.AAChartEnum.AAChartType;
import com.zxl.Bookkeeping.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChartActivity extends AppCompatActivity {
    private AAChartView aaChartView;
    private String typeNameArr[];
    private List<overview_classification> ocList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        typeNameArr = getResources().getStringArray(R.array.typeName);
        ocList = new ArrayList<>();
        initChartView();
    }

    private void initChartView() {
        aaChartView = findViewById(R.id.AAChartView);
        //获取当前时间
        HashMap<String, Integer> calenderTime = Util.getCalenderTime();
        int year = calenderTime.get("year");
        int month = calenderTime.get("month");
        String date = Util.getYearMonthStr(year,month);
        //获取数据
        for (int i = 0; i < typeNameArr.length; i++) {
            overview_classification oc = DBManager.queryTypeSumByYearMonthKind(year, month, 0, typeNameArr[i]);
            if(oc.getTypeName() != null){
                ocList.add(oc);
            }
        }
        //获取标签名
        String strArray[] = new String[ocList.size()];
        Float dataArray[] = new Float[ocList.size()];
        for (int i = 0; i < ocList.size(); i++) {
            strArray[i] = ocList.get(i).getTypeName();
            dataArray[i] = ocList.get(i).getTotalMoney();
        }

        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Column)
                .title("月度账单总览")//设置标题
                .subtitle(date)//设置副标题
                .backgroundColor("#FFFFFF")//设置背景色白色
                .categories(strArray)
                .dataLabelsEnabled(true)
                .yAxisGridLineWidth(0f)
                .colorsTheme(new String[]{"#33e6cc"})
                .series(
                        new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Out")
                                .data(dataArray),
                });
        aaChartView.aa_drawChartWithChartModel(aaChartModel);
    }
}