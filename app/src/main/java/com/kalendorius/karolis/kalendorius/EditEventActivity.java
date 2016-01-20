package com.kalendorius.karolis.kalendorius;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditEventActivity extends AppCompatActivity {

    private EditText name;
    private EditText date;
    private EditText time;
    private Spinner alert;
    private Switch repeat;
    private DBHandler db;
    private int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        name = (EditText) findViewById(R.id.inputEventName);
        date = (EditText) findViewById(R.id.inputEventDate);
        time = (EditText) findViewById(R.id.inputEventTime);
        alert = (Spinner) findViewById(R.id.spinnerAlert);
        repeat = (Switch) findViewById(R.id.switchRepeat);
        db = new DBHandler(this, null, null, 1);

        Bundle noteData = getIntent().getExtras();
        if(noteData == null) {
            return;
        }

        eventId = noteData.getInt("eventId");
        name.setText(noteData.getString("eventName"));

        if(noteData.getInt("eventRepeat") == 1) {
            repeat.setChecked(true);
        } else {
            repeat.setChecked(false);
        }

        if(noteData.getInt("eventAlert") == 0) {
            alert.setSelection(0);
        } else if(noteData.getInt("eventAlert") == 5*60) {
            alert.setSelection(1);
        } else if(noteData.getInt("eventAlert") == 10*60) {
            alert.setSelection(2);
        } else if(noteData.getInt("eventAlert") == 15*60) {
            alert.setSelection(3);
        } else if(noteData.getInt("eventAlert") == 30*60) {
            alert.setSelection(4);
        } else {
            alert.setSelection(5);
        }

        int timeSeconds = noteData.getInt("eventTime");
        int hrs = timeSeconds / 3600;
        int min = hrs * 3600;
        min = timeSeconds - min;
        min = min / 60;
        if(min / 10 == 0) {
            time.setText(hrs + ":0" + min);
        }
        else {
            time.setText(hrs + ":" + min);
        }

        long milliSeconds = noteData.getInt("eventDate");
        milliSeconds = milliSeconds * 1000;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        date.setText(formatter.format(calendar.getTime()));
    }

    public void onClickEditEvent(View v) {
        if(!inputCheck()){
            return;
        }

        String string_date = date.getText().toString();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");
        Date d;
        try{
            d = f.parse(string_date);
        } catch(ParseException e) {
            return;
        }
        long milliseconds = d.getTime();
        milliseconds = milliseconds / 1000;
        int dateSeconds = (int) milliseconds;

        String timeString = time.getText().toString();
        String timeArray[] = timeString.split(":");
        int timeSeconds = (Integer.parseInt(timeArray[0]) * 3600);
        timeSeconds += Integer.parseInt(timeArray[1]) * 60;

        String alertString = alert.getSelectedItem().toString();
        String alertTimeString[] = alertString.split(" ");
        int alertSeconds;
        if(!alertTimeString[0].equals("1")) {
            alertSeconds = Integer.parseInt(alertTimeString[0]);
            alertSeconds = alertSeconds * 60;
        }
        else {
            alertSeconds = 3600;
        }
        int i = 0;
        if(repeat.isChecked()){
            i = 1;
        }
        else {
            i = 0;
        }

        Event event = new Event(eventId, name.getText().toString(), dateSeconds, timeSeconds, i, alertSeconds);
        db.updateEvent(event);
        finish();
    }

    public void onClickDeleteEvent(View v) {
        db.deleteEvent(eventId);
        NotificationReceive.cancelNotifications(this);
        finish();
    }

    private boolean inputCheck() {
        boolean t[] = {true, true, true};
        if(!isValidName(name.getText().toString())) {
            name.setError("Must have a name");
            t[0] = false;
        }
        if(!isValidDate(date.getText().toString())) {
            date.setError("Wrong date format");
            t[1] = false;
        }
        if(!isValidTime(time.getText().toString())) {
            time.setError("Wrong time format");
            t[2] = false;
        }
        for(boolean x : t) {
            if(x == false) {
                return x;
            }
        }
        return true;
    }

    private boolean isValidDate(String date) {
        String DATE_FORMAT = "^\\d{4}[-/\\s]?((((0[13578])|(1[02]))[-/\\s]?(([0-2][0-9])|(3[01])))|(((0[469])|(11))[-/\\s]?(([0-2][0-9])|(30)))|(02[-/\\s]?[0-2][0-9]))$";
        Pattern pattern = Pattern.compile(DATE_FORMAT);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    private boolean isValidName(String name) {
        if(name == "") {
            return false;
        }
        return true;
    }

    private boolean isValidTime(String time) {
        String TIME_FORMAT = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
        Pattern pattern = Pattern.compile(TIME_FORMAT);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }

}
