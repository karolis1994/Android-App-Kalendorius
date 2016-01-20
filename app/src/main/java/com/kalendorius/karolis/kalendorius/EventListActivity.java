package com.kalendorius.karolis.kalendorius;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventListActivity extends AppCompatActivity {

    private DBHandler db;
    private EditText search;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        db = new DBHandler(this, null, null, 1);
        String events[] = db.getEvents();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, events);
        final ListView list = (ListView) findViewById(R.id.listElements);
        list.setTextFilterEnabled(true);
        list.setAdapter(adapter);
        search = (EditText) findViewById(R.id.inputSearch);

        search.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Object item = list.getItemAtPosition(position);
                        String myitem = item.toString();
                        String noteId[] = myitem.split("\\.");
                        int pos = Integer.parseInt(noteId[0]);
                        passNoteData(db.getEvent(pos));
                    }
                }
        );

    }

    public String changeDateFormat(int sec) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis((long) sec*1000);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        String dateString = formatter.format(cal.getTime());
        return dateString;
    }

    public void setAlarms() {
        int ids[] = db.getEventIDs();
        int dateAndTime[] = db.getEventDates();
        int alertAndRepeat[] = db.getEventRepeatStats();
        int x = dateAndTime.length / 2;

        if(x == 0) {
            return;
        }
        Intent intent = new Intent(this, NotificationReceive.class);


        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        NotificationReceive.cancelNotifications(this);

        int i;
        int z = 0;
        String s;
        int hrs = 0;
        int min = 0;
        String dates[];

        int alert;
        int repeat;
        for(i = 0; i < x; i++) {
            intent.putExtra("eventId", ids[i]);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ids[i], intent, 0);
            am.cancel(pendingIntent);

            s = changeDateFormat(dateAndTime[z]);
            alert = alertAndRepeat[z] * 1000;

            z++;
            hrs = dateAndTime[z] / 3600;
            min = hrs * 3600;
            min = dateAndTime[z] - min;
            min = min / 60;

            repeat = alertAndRepeat[z];
            z++;

            dates = s.split("-");

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DATE, Integer.parseInt(dates[2]));
            cal.set(Calendar.MONTH, Integer.parseInt(dates[1]) - 1);
            cal.set(Calendar.YEAR, Integer.parseInt(dates[0]));

            cal.set(Calendar.HOUR_OF_DAY, hrs);
            cal.set(Calendar.MINUTE, min);
            cal.set(Calendar.SECOND, 00);
            if(!(cal.getTimeInMillis() - alert < Calendar.getInstance().getTimeInMillis())) {
                if (repeat == 0) {
                    am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() - alert, pendingIntent);
                } else {
                    am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() - alert, 24 * 60 * 60 * 1000, pendingIntent);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAlarms();
        String events[] = db.getEvents();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, events);
        final ListView list = (ListView) findViewById(R.id.listElements);
        list.setAdapter(adapter);
    }

    public void passNoteData(Event event) {
        Intent i = new Intent(this, EditEventActivity.class);
        i.putExtra("eventId", event.get_id());
        i.putExtra("eventName", event.get_name());
        i.putExtra("eventDate", event.get_date());
        i.putExtra("eventTime", event.get_time());
        i.putExtra("eventAlert", event.get_alert());
        i.putExtra("eventRepeat", event.get_repeat());
        startActivity(i);
    }
}
