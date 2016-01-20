package com.kalendorius.karolis.kalendorius;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class NoteListActivity extends AppCompatActivity {

    private DBHandler db;
    private EditText search;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        db = new DBHandler(this, null, null, 1);
        String notes[] = db.getNotes();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, notes);
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
                        passNoteData(db.getNote(pos));

                    }
                }
        );

    }

    @Override
    protected void onResume() {
        super.onResume();
        String notes[] = db.getNotes();
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, notes);
        final ListView list = (ListView) findViewById(R.id.listElements);
        list.setAdapter(adapter);
    }

    public void passNoteData(Note note) {
        Intent i = new Intent(this, EditNoteActivity.class);
        i.putExtra("noteId", note.get_id());
        i.putExtra("noteName", note.get_name());
        i.putExtra("noteDate", note.get_date());
        i.putExtra("noteText", note.get_text());
        startActivity(i);
    }

}
