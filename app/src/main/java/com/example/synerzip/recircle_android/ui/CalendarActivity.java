package com.example.synerzip.recircle_android.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.synerzip.recircle_android.R;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

import static com.squareup.timessquare.CalendarPickerView.SelectionMode.RANGE;

public class CalendarActivity extends AppCompatActivity {

    @BindView(R.id.calendar_view)
    public CalendarPickerView pickerView;

    Date fromDate, toDate;

    Date selectFromDate, selectToDate;

    public TextView txtFromDate;

    public TextView txtToDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calendar);

        pickerView = (CalendarPickerView) findViewById(R.id.calendar_view);
        txtFromDate = (TextView) findViewById(R.id.txt_from_date);
        txtToDate = (TextView) findViewById(R.id.txt_to_date);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        Date today = new Date();
        pickerView.init(today, nextYear.getTime()).withSelectedDate(today).inMode(RANGE);
        pickerView.init(today, nextYear.getTime()).inMode(RANGE);

        //on date selected listener
        pickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {

            @Override
            public void onDateSelected(Date date) {
                ArrayList<Date> selectedDates = (ArrayList<Date>) pickerView.getSelectedDates();
                selectFromDate = selectedDates.get(0);
                selectToDate = selectedDates.get(selectedDates.size() - 1);

                DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
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

                CharSequence weekdayFromDate = android.text.format.DateFormat.format("EEE", fromDate);
                CharSequence weekdayToDate = android.text.format.DateFormat.format("EEE", toDate);
                CharSequence monthFromDate = android.text.format.DateFormat.format("MMM", fromDate);
                CharSequence monthToDate = android.text.format.DateFormat.format("MMM", toDate);

                String formatedFromDate = weekdayFromDate + " , " + calFromDate.get(Calendar.DATE) + " " + monthFromDate
                        + " " + calFromDate.get(Calendar.YEAR);
                String formatedToDate = weekdayToDate + " , " + calToDate.get(Calendar.DATE) + " " + monthToDate
                        + " " + calToDate.get(Calendar.YEAR);

                txtFromDate.setText(formatedFromDate);
                txtToDate.setText(formatedToDate);
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
        Intent intent = new Intent(CalendarActivity.this, SearchActivity.class);
        intent.putExtra("fromDate", fromDate.toString());
        intent.putExtra("toDate", toDate.toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.txt_reset)
    public void txtReset(View view) {
        txtFromDate.setText(getString(R.string.enter_start_date));
        txtToDate.setText(R.string.enter_end_date);
    }

}
