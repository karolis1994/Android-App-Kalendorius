package com.kalendorius.karolis.kalendorius;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "notes.db";
    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "itemName";
    public static final String COLUMN_DATE = "itemDate";
    public static final String COLUMN_TEXT = "noteText";
    public static final String COLUMN_TIME = "eventTime";
    public static final String COLUMN_REPEAT = "noteRepeat";
    public static final String COLUMN_ALERT = "eventAlert";
    public static final String TABLE_EVENTS = "events";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryNote = "CREATE TABLE " + TABLE_NOTES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT UNIQUE, " +  COLUMN_DATE + " INTEGER, " +
                COLUMN_TEXT + " TEXT " + " );";
        String queryEvent = "CREATE TABLE " + TABLE_EVENTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT UNIQUE, " +  COLUMN_DATE + " INTEGER, " +
                COLUMN_TIME + " INTEGER, " + COLUMN_REPEAT + " INTEGER, " +
                COLUMN_ALERT + " INTEGER " + ");";
        db.execSQL(queryNote);
        db.execSQL(queryEvent);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    public void addNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, note.get_name());
        values.put(COLUMN_DATE, note.get_date());
        values.put(COLUMN_TEXT, note.get_text());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    public void addEvent(Event event) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, event.get_name());
        values.put(COLUMN_DATE, event.get_date());
        values.put(COLUMN_TIME, event.get_time());
        values.put(COLUMN_REPEAT, event.get_repeat());
        values.put(COLUMN_ALERT, event.get_alert());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }

    public String[] getNotes() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + " FROM " + TABLE_NOTES + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String allNotes[] = new String[c.getCount()];
        int i = 0;
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex(COLUMN_ID)) != null && c.getString(c.getColumnIndex(COLUMN_NAME)) != null) {
                allNotes[i] = c.getString(c.getColumnIndex(COLUMN_ID));
                allNotes[i] += ". " + c.getString(c.getColumnIndex(COLUMN_NAME));
                i++;
                c.moveToNext();
            }
        }
        c.close();
        return allNotes;
    }

    public String[] getEvents() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + " FROM " + TABLE_EVENTS + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String allEvents[] = new String[c.getCount()];
        int i = 0;
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex(COLUMN_ID)) != null && c.getString(c.getColumnIndex(COLUMN_NAME)) != null) {
                allEvents[i] = c.getString(c.getColumnIndex(COLUMN_ID));
                allEvents[i] += ". " + c.getString(c.getColumnIndex(COLUMN_NAME));
                i++;
                c.moveToNext();
            }
        }
        c.close();
        return allEvents;
    }

    public Note getNote(int pos) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NOTES + " WHERE " + COLUMN_ID + "=" + pos + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        String idString, name, dateString, text;
        idString = c.getString(c.getColumnIndex(COLUMN_ID));
        name = c.getString(c.getColumnIndex(COLUMN_NAME));
        dateString = c.getString(c.getColumnIndex(COLUMN_DATE));
        text = c.getString(c.getColumnIndex(COLUMN_TEXT));

        int date;
        int id;
        date = Integer.parseInt(dateString);
        id = Integer.parseInt(idString);
        Note note = new Note(id, name, date, text);
        c.close();
        return note;
    }

    public Event getEvent(int pos) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_ID + "=" + pos + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        String idString, name, dateString, timeString, alertString, repeatString;
        idString = c.getString(c.getColumnIndex(COLUMN_ID));
        name = c.getString(c.getColumnIndex(COLUMN_NAME));
        dateString = c.getString(c.getColumnIndex(COLUMN_DATE));
        timeString = c.getString(c.getColumnIndex(COLUMN_TIME));
        alertString = c.getString(c.getColumnIndex(COLUMN_ALERT));
        repeatString = c.getString(c.getColumnIndex(COLUMN_REPEAT));

        int date;
        int time;
        int alert;
        int repeat;
        int id;
        id = Integer.parseInt(idString);
        date = Integer.parseInt(dateString);
        time = Integer.parseInt(timeString);
        alert = Integer.parseInt(alertString);
        repeat = Integer.parseInt(repeatString);
        Event event = new Event(id, name, date, time, repeat, alert);
        c.close();
        return event;
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NOTES + " SET " + COLUMN_NAME + "='" + note.get_name() +
                "', " + COLUMN_DATE + "=" + note.get_date() + ", " + COLUMN_TEXT + "='" + note.get_text() +
                "' WHERE " + COLUMN_ID + "=" + note.get_id();
        db.execSQL(query);
        db.close();
    }

    public void updateEvent(Event event) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_EVENTS + " SET " + COLUMN_NAME + "='" + event.get_name() +
                "', " + COLUMN_DATE + "='" + event.get_date() + "', " + COLUMN_TIME+ "='" + event.get_time() +
                "', " + COLUMN_REPEAT + "='" + event.get_repeat() + "', " + COLUMN_ALERT +
                "='" + event.get_alert() + "' WHERE " + COLUMN_ID + "=" + event.get_id();
        db.execSQL(query);
        db.close();
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NOTES + " WHERE " + COLUMN_ID + "=" + id;
        db.execSQL(query);
        db.close();
    }

    public void deleteEvent(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_EVENTS + " WHERE " + COLUMN_ID + "=" + id;
        db.execSQL(query);
        db.close();
    }

    public int[] getEventRepeatStats() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_ALERT + ", " + COLUMN_REPEAT + " FROM " + TABLE_EVENTS + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int alertAndRepeat[] = new int[c.getCount()*2];
        String alertAndRepeatString[] = new String[c.getCount()*2];
        int i = 0;
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex(COLUMN_ALERT)) != null && c.getString(c.getColumnIndex(COLUMN_REPEAT)) != null) {
                alertAndRepeatString[i] = c.getString(c.getColumnIndex(COLUMN_ALERT));
                i++;
                alertAndRepeatString[i] = c.getString(c.getColumnIndex(COLUMN_REPEAT));
                i++;
                c.moveToNext();
            }
        }
        i = 0;
        for(String s : alertAndRepeatString) {
            alertAndRepeat[i] = Integer.parseInt(alertAndRepeatString[i]);
            i++;
        }
        c.close();
        return alertAndRepeat;
    }

    public int[] getEventDates() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_DATE + ", " + COLUMN_TIME + " FROM " + TABLE_EVENTS + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int dateAndTime[] = new int[c.getCount()*2];
        String dateAndTimeString[] = new String[c.getCount()*2];
        int i = 0;
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex(COLUMN_DATE)) != null && c.getString(c.getColumnIndex(COLUMN_TIME)) != null) {
                dateAndTimeString[i] = c.getString(c.getColumnIndex(COLUMN_DATE));
                i++;
                dateAndTimeString[i] = c.getString(c.getColumnIndex(COLUMN_TIME));
                i++;
                c.moveToNext();
            }
        }
        i = 0;
        for(String s : dateAndTimeString) {
            dateAndTime[i] = Integer.parseInt(dateAndTimeString[i]);
            i++;
        }
        c.close();
        return dateAndTime;
    }

    public String getEventName(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_NAME + " FROM " + TABLE_EVENTS + " WHERE " + COLUMN_ID + "=" + id + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String name = "event";
        if(c.getString(c.getColumnIndex(COLUMN_NAME)) != null) {
            name = c.getString(c.getColumnIndex(COLUMN_NAME));
            c.moveToNext();
        }
        c.close();
        return name;
    }

    public int[] getEventIDs() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_EVENTS + ";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int ids[] = new int[c.getCount()];
        String idsString[] = new String[c.getCount()];
        int i = 0;
        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex(COLUMN_ID)) != null) {
                idsString[i] = c.getString(c.getColumnIndex(COLUMN_ID));
                ids[i] = Integer.parseInt(idsString[i]);
                i++;
                c.moveToNext();
            }
        }
        c.close();
        return ids;
    }

    public void removeTables() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
    }

}
