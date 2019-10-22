package com.example.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import data.DatabaseOpenHelper;
import model.Notes;

public class MainActivity extends AppCompatActivity {
    ArrayList<Notes> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView noteList = findViewById(R.id.noteList);
        refreshNotes();

        NoteArrayAdapter noteArrayAdapter = new NoteArrayAdapter(this, R.layout.note_row, notes);
        noteList.setAdapter(noteArrayAdapter);
        noteArrayAdapter.notifyDataSetChanged();
    }

    public void  refreshNotes() {
        DatabaseOpenHelper dbo = new DatabaseOpenHelper(this);
        ArrayList<Notes> mNote = dbo.getNotes();

        for(int i=0; i<mNote.size(); i++) {
            Notes nNote = new Notes();
            nNote.setNoteTitle(mNote.get(i).getNoteTitle());
            nNote.setNoteContent(mNote.get(i).getNoteContent());
            nNote.setNoteDate(mNote.get(i).getNoteDate());
            nNote.setNoteID(mNote.get(i).getNoteID());

            notes.add(nNote);
        }
        dbo.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addNote) {
            Intent intent = new Intent(this, Editor.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
