package com.example.synerzip.recircle_android.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.synerzip.recircle_android.R;
import com.example.synerzip.recircle_android.utilities.RCLog;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.squareup.timessquare.CalendarPickerView.SelectionMode.RANGE;

/**
 * Created by Prajakta Patil on 24/3/17.
 * Copyright © 2016 Synerzip. All rights reserved
 */
public class CalendarActivity extends AppCompatActivity {

    @BindView(R.id.calendar_view)
    public CalendarPickerView mPickerView;

    Date fromDate, toDate;

    Date selectFromDate, selectToDate;

    @BindView(R.id.txt_from_date)
    public TextView mTxtFromDate;

    @BindView(R.id.txt_to_date)
    public TextView mTxtToDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        Date today = new Date();
        mPickerView.init(today, nextYear.getTime()).withSelectedDate(today).inMode(RANGE);
        mPickerView.init(today, nextYear.getTime()).inMode(RANGE);

        //on date selected listener
        mPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {

            @Override
            public void onDateSelected(Date date) {
                ArrayList<Date> selectedDates = (ArrayList<Date>) mPickerView.getSelectedDates();
                selectFromDate = selectedDates.get(0);
                selectToDate = selectedDates.get(selectedDates.size() - 1);

                DateFormat formatter = new SimpleDateFormat(getString(R.string.date_format));
                try {
                    fromDate = formatter.parse(selectFromDate.toString());
                    toDate = formatter.parse(selectToDate.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar calFromDate = Calendar.getInstance();
                Calendar calToDate = Calendar.getInstance();
                calFromDate.setTime(fromDate);
                calToDate.setTime(toDate);

                CharSequence weekdayFromDate =
                        android.text.format.DateFormat.format(getString(R.string.day_format), fromDate);
                CharSequence weekdayToDate =
                        android.text.format.DateFormat.format(getString(R.string.day_format), toDate);
                CharSequence monthFromDate =
                        android.text.format.DateFormat.format(getString(R.string.month_format), fromDate);
                CharSequence monthToDate =
                        android.text.format.DateFormat.format(getString(R.string.month_format), toDate);

                String formatedFromDate =
                        weekdayFromDate + " , " + calFromDate.get(Calendar.DATE) + " " + monthFromDate
                        + " " + calFromDate.get(Calendar.YEAR);
                String formatedToDate =
                        weekdayToDate + " , " + calToDate.get(Calendar.DATE) + " " + monthToDate
                        + " " + calToDate.get(Calendar.YEAR);

                mTxtFromDate.setText(formatedFromDate);
                mTxtToDate.setText(formatedToDate);
            }

            @Override
            public void onDateUnselected(Date date) {
            }
        });
    }

    @OnClick(R.id.txt_cancel)
    public void txtCancel(View view) {
        Intent intent = new Intent(CalendarActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_save)
    public void btnSave(View view) {
        if (!fromDate.equals("") && !toDate.equals("")) {
            Intent intent = new Intent(CalendarActivity.this, SearchActivity.class);
            intent.putExtra(getString(R.string.from_date), fromDate.toString());
            intent.putExtra(getString(R.string.to_date), toDate.toString());
            setResult(RESULT_OK, intent);
            finish();
        } else {
            RCLog.showToast(CalendarActivity.this, getString(R.string.error_dates));
        }
    }

    @OnClick(R.id.txt_reset)
    public void txtReset(View view) {
        mTxtFromDate.setText(getString(R.string.enter_start_date));
        mTxtToDate.setText(R.string.enter_end_date);
    }
}