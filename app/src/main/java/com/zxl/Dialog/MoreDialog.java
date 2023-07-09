package com.zxl.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.zxl.Bookkeeping.AboutActivity;
import com.zxl.Bookkeeping.ChartActivity;
import com.zxl.Bookkeeping.OverViewActivity;
import com.zxl.Bookkeeping.R;

public class MoreDialog extends Dialog implements View.OnClickListener {
    private static final String TAG = "MoreDialog";
    private Button dialog_more_btn_overview;
    private Button dialog_more_btn_setting;
    private Button dialog_more_btn_chart;
    private ImageView dialog_more_iv;
    private Button dialog_more_btn_about;

    public MoreDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_more);
        initView();
        setDialogSizeFitWindows();
    }

    private void initView() {
        dialog_more_btn_overview = findViewById(R.id.dialog_more_btn_overview);
        dialog_more_btn_setting = findViewById(R.id.dialog_more_btn_setting);
        dialog_more_btn_chart = findViewById(R.id.dialog_more_btn_chart);
        dialog_more_btn_about = findViewById(R.id.dialog_more_btn_about);
        dialog_more_iv = findViewById(R.id.dialog_more_iv);

        dialog_more_btn_overview.setOnClickListener(this);
        dialog_more_btn_setting.setOnClickListener(this);
        dialog_more_btn_chart.setOnClickListener(this);
        dialog_more_btn_about.setOnClickListener(this);
        dialog_more_iv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if(v.getId() == R.id.dialog_more_btn_overview){
            log("跳转图标界面");
            intent = new Intent(getContext(), OverViewActivity.class);
            getContext().startActivity(intent);
        } else if (v.getId() == R.id.dialog_more_btn_setting) {

        } else if (v.getId() == R.id.dialog_more_btn_chart) {
            log("跳转图标界面");
            intent = new Intent(getContext(), ChartActivity.class);
            getContext().startActivity(intent);
        } else if (v.getId() == R.id.dialog_more_btn_about) {
            log("跳转介绍页面");
            intent = new Intent(getContext(), AboutActivity.class);
            getContext().startActivity(intent);
        } else if (v.getId() == R.id.dialog_more_iv) {
            dismiss();
        }
    }

    public void setDialogSizeFitWindows(){
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        params.width = d.getWidth();
        params.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);//设置窗口的背景资源
        window.setAttributes(params);   //设置参数
    }

    private void log(String str){
        Log.d(TAG,str);
    }
}
