package com.kalendorius.karolis.kalendorius;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText name;
    private EditText date;
    private EditText text;
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        name = (EditText) findViewById(R.id.inputNoteName);
        date = (EditText) findViewById(R.id.inputNoteDate);
        text = (EditText) findViewById(R.id.inputNote);
        db = new DBHandler(this, null, null, 1);

        Bundle extraDate = getIntent().getExtras();
        if(extraDate == null) {
            return;
        }
        String s = extraDate.getString("date");
        date.setText(s);

    }

    public void onClickCreateNote(View v) {
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
        long milliseconds = d.getTime() / 1000;
        int seconds = (int) milliseconds;

        Note note = new Note(1, name.getText().toString(), seconds, text.getText().toString());
        db.addNote(note);

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
        if(!isValidText(text.getText().toString())) {
            text.setError("Must have a note");
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

    private boolean isValidText(String text) {
        if(text == "") {
            return false;
        }
        return true;
    }

}
