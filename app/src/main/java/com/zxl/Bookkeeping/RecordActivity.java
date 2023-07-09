package com.zxl.Bookkeeping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zxl.Adapter.TypeBaseAdapter;
import com.zxl.DateBase.DBManager;
import com.zxl.Entity.AccountBean;
import com.zxl.Entity.TypeBean;
import com.zxl.Utils.Util;
import com.zxl.View.MyGridView;
import com.zxl.Bookkeeping.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RecordActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "RecordActivity";
    //title
    private ImageView record_title_iv;
    private TextView record_title_tv;
    //date
    private Switch record_date_sw;
    private TextView record_date_tv2;
    private CalendarView record_date_cv;
    //time
    private Switch record_time_sw;
    private TextView record_time_tv2;
    private TimePicker record_time_tp;
    //label
    private Switch record_label_sw;
    private MyGridView record_label_gv;
    //type
    private Switch record_type_sw_out,record_type_sw_in;
    //notes
    private EditText record_notes_et;
    private ImageView record_notes_iv;
    //money
    private EditText record_money_et;
    private ImageView record_money_iv;
    private String[] weekString;
    private List<TypeBean> typeBeanList;
    private TypeBaseAdapter adapter;
    private AccountBean accountBean;
    private HashMap<String, Integer> map = Util.getCalenderTime();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        typeBeanList = new ArrayList<>();
        adapter = new TypeBaseAdapter(getApplicationContext(),typeBeanList);
        initView();
        initGridView();
        setMyGridViewListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDateAndTime();
        setCalenderViewListener();
        setTimePickerListener();
    }

    //初始化标签
    private void initGridView() {
        if(record_type_sw_out.isChecked()){
            typeBeanList.clear();
            typeBeanList.addAll(DBManager.getTypeList(0));
        }else {
            typeBeanList.clear();
            typeBeanList.addAll(DBManager.getTypeList(1));
        }
        record_label_gv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    //更改标签
    private void changeGridView(){
        if(record_type_sw_out.isChecked()){
            typeBeanList.clear();
            typeBeanList.addAll(DBManager.getTypeList(0));
        }else {
            typeBeanList.clear();
            typeBeanList.addAll(DBManager.getTypeList(1));
        }
        adapter.notifyDataSetChanged();
    }
    //给标签设置item点击事件
    private void setMyGridViewListener(){
        if(record_label_gv==null)return;
        record_label_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //当前点击的视图位置变更
                adapter.selectPos = position;
                adapter.notifyDataSetInvalidated();//提示绘制发生变化
                log("当前GridView内Item的位置:"+position);
                //AccountBean数据添加
                TypeBean item = (TypeBean) parent.getItemAtPosition(position);
                if(item!=null){
                    accountBean.setTypeName(item.getTypeName());
                    accountBean.setsImageId(item.getsImageId());
                }
            }
        });
    }

    //初始化日期和时间
    private void initDateAndTime() {
        Integer year = map.get("year");
        Integer month = map.get("month");
        Integer day = map.get("day");
        //初始化日期
        String date = Util.getYearMonthDayStr(year, month, day);
        record_date_tv2.setText(date);
        //初始化CalenderView
        long dateTime = new Date().getTime();
        record_date_cv.setDate(dateTime);
        //初始化时间
        Integer hour = map.get("hour");
        Integer minute = map.get("minute");
        StringBuilder time = new StringBuilder();
        String hourMinuteStr = Util.getHourMinuteStr(hour, "时", minute, "分");
        record_time_tv2.setText(hourMinuteStr);
        //初始化timePicker
        record_time_tp.setHour(hour);
        record_time_tp.setMinute(minute);
        record_time_tp.setIs24HourView(true);
    }

    //设置CalenderView的Listener
    private void setCalenderViewListener(){
        record_date_cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String time = Util.getYearMonthDayStr(year, month+1, dayOfMonth);
                accountBean.setYear(year);
                accountBean.setMonth(month+1);
                accountBean.setDay(dayOfMonth);
                accountBean.setTime(time);
                record_date_tv2.setText(time);
            }
        });
    }
    //设置TimePicker的变化监听
    private void setTimePickerListener(){
        record_time_tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String hourMinuteStr = Util.getHourMinuteStr(hourOfDay, "时", minute, "分");
                accountBean.setHour(hourOfDay);
                accountBean.setMinute(minute);
                record_time_tv2.setText(hourMinuteStr);
            }
        });
    }

    //init View widget
    private void initView(){
        accountBean = new AccountBean();
        //星期数组获取
        weekString = getResources().getStringArray(R.array.week);
        //控件赋值
        record_title_iv = findViewById(R.id.record_title_iv);
        record_title_tv = findViewById(R.id.record_title_tv);
        record_date_sw = findViewById(R.id.record_date_sw);
        record_date_tv2 = findViewById(R.id.record_date_tv2);
        record_date_cv = findViewById(R.id.record_date_cv);
        record_time_sw = findViewById(R.id.record_time_sw);
        record_time_tv2 = findViewById(R.id.record_time_tv2);
        record_time_tp = findViewById(R.id.record_time_tp);
        record_label_sw = findViewById(R.id.record_label_sw);
        record_label_gv = findViewById(R.id.record_label_gv);
        record_type_sw_out = findViewById(R.id.record_type_sw_out);
        record_type_sw_in = findViewById(R.id.record_type_sw_in);
        record_notes_et = findViewById(R.id.record_notes_et);
        record_notes_iv = findViewById(R.id.record_notes_iv);
        record_money_et = findViewById(R.id.record_money_et);
        record_money_iv = findViewById(R.id.record_money_iv);
        //初始化不显示部分控件
        record_date_cv.setVisibility(View.GONE);
        record_time_tp.setVisibility(View.GONE);
        record_label_gv.setVisibility(View.GONE);
        //设置事件
        record_date_sw.setOnCheckedChangeListener(this);
        record_time_sw.setOnCheckedChangeListener(this);
        record_label_sw.setOnCheckedChangeListener(this);
        record_type_sw_out.setOnCheckedChangeListener(this);
        record_type_sw_in.setOnCheckedChangeListener(this);

        record_title_iv.setOnClickListener(this);
        record_title_tv.setOnClickListener(this);
        record_notes_iv.setOnClickListener(this);
        record_money_iv.setOnClickListener(this);

        //初始化类型
        record_type_sw_out.setChecked(true);
        record_type_sw_in.setChecked(false);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.record_title_iv){
            log("退出Record界面");
            finish();
        } else if (v.getId() == R.id.record_title_tv) {
            log("点击添加按键,添加一条记录");
            addDataToDB();
        } else if (v == record_notes_iv) {
            log("record_notes_iv");
            record_notes_et.setText("");
        } else if (v == record_money_iv){
            log("record_notes_et");
            record_money_et.setText("");
        }
    }

    private void addDataToDB() {
        //判断日期
        if (!record_date_sw.isChecked()){
            accountBean.setYear(map.get("year"));
            accountBean.setMonth(map.get("month"));
            accountBean.setDay(map.get("day"));
            accountBean.setTime(
                    Util.getYearMonthDayStr(
                            map.get("year"),
                            map.get("month"),
                            map.get("day"))
            );
        }
        //判断时间
        if(!record_time_sw.isChecked()){
            accountBean.setHour(map.get("hour"));
            accountBean.setMinute(map.get("minute"));
        }
        //判断支出或者收入
        if(record_type_sw_in.isChecked()){
            accountBean.setKind(1);
        } else if (record_type_sw_out.isChecked()) {
            accountBean.setKind(0);
        }
        //判断标签
        if(!record_label_sw.isChecked()){
            TypeBean item = (TypeBean) adapter.getItem(0);
            accountBean.setTypeName(item.getTypeName());
            accountBean.setsImageId(item.getsImageId());
        }
        //获取备注
        String notes = record_notes_et.getText().toString();
        accountBean.setNotes(notes);
        //获取金额
        String moneyStr = record_money_et.getText().toString();
        if(TextUtils.isEmpty(moneyStr)){
            showAlertDialog();
            return;
        }
        float money = Float.parseFloat(moneyStr);
        accountBean.setMoney(money);
        //存储数据
        log(accountBean.toString());
        Boolean aBoolean = DBManager.insertDataToAccountTB(accountBean);
        if(aBoolean){
            Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"添加失败",Toast.LENGTH_SHORT).show();
        }
    }

    //展示金额错误的警告窗口
    private void showAlertDialog(){
        new AlertDialog.Builder(this).setTitle("金额错误")
                .setIcon(R.mipmap.icon_jinggao)
                .setMessage("请输入正确的金额")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        record_money_et.setText("");
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.record_date_sw){
            if(buttonView.isChecked()){
                record_date_cv.setVisibility(View.VISIBLE);
            }else {
                record_date_cv.setVisibility(View.GONE);
            }
        } else if (buttonView.getId() == R.id.record_time_sw) {
            if(buttonView.isChecked()){
                record_time_tp.setVisibility(View.VISIBLE);
            }else {
                record_time_tp.setVisibility(View.GONE);
            }
        } else if (buttonView.getId() == R.id.record_label_sw) {
            if(buttonView.isChecked()){
                record_label_gv.setVisibility(View.VISIBLE);
            }else {
                record_label_gv.setVisibility(View.GONE);
            }
        } else if (buttonView.getId() == R.id.record_type_sw_out) {
            if(record_type_sw_out.isChecked()){
                accountBean.setKind(0);
                record_type_sw_in.setChecked(false);
            }else {
                accountBean.setKind(1);
                record_type_sw_in.setChecked(true);
            }
            changeGridView();
        } else if (buttonView.getId() == R.id.record_type_sw_in) {
            if(record_type_sw_in.isChecked()){
                accountBean.setKind(1);
                record_type_sw_out.setChecked(false);
            }else {
                accountBean.setKind(0);
                record_type_sw_out.setChecked(true);
            }
            changeGridView();
        }
    }


    private void log(String str){
        Log.d(TAG,str);
    }
}
