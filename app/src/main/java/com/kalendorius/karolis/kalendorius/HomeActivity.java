package com.kalendorius.karolis.kalendorius;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    private CalendarView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cv = (CalendarView) this.findViewById(R.id.kalendorius);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2014);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long milliTime = calendar.getTimeInMillis();
        cv.setMinDate(milliTime);

        ViewGroup vg = (ViewGroup) cv.getChildAt(0);
        View child = vg.getChildAt(0);
        if(child instanceof TextView) {
            ((TextView)child).setTextColor(Color.BLACK);
        }
        final ImageButton buttonAtgal = (ImageButton) findViewById(R.id.buttonAtgal);
        final ImageButton buttonPirmyn = (ImageButton) findViewById(R.id.buttonPirmyn);
        final Button buttonUzrasas = (Button) findViewById(R.id.buttonCreateNote);
        final Button buttonIvykis = (Button) findViewById(R.id.buttonCreateEvent);

        buttonAtgal.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        onClickAtgal();
                    }
                }
        );

        buttonPirmyn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        onClickPirmyn();
                    }
                }
        );

        buttonUzrasas.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        onClickUzrasas();
                    }
                }
        );

        buttonIvykis.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        onClickIvykis();
                    }
                }
        );

    }

    public void onClickAtgal() {
        CalendarView cv = (CalendarView) this.findViewById(R.id.kalendorius);
        long milliTime = cv.getDate() - Long.parseLong("2592000000");
        cv.setDate(milliTime);
    }

    public void onClickPirmyn() {
        CalendarView cv = (CalendarView) this.findViewById(R.id.kalendorius);
        long milliTime = cv.getDate() + Long.parseLong("2592000000");
        cv.setDate(milliTime);
    }

    public void onClickUzrasas() {
        Intent i = new Intent(this, CreateNoteActivity.class);
        Long milliSeconds = cv.getDate();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        i.putExtra("date", formatter.format(calendar.getTime()));
        startActivity(i);
    }

    public void onClickIvykis() {
        Intent i = new Intent(this, CreateEventActivity.class);
        Long milliSeconds = cv.getDate();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        i.putExtra("date", formatter.format(calendar.getTime()));
        startActivity(i);
    }

}
