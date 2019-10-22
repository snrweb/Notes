package com.example.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseOpenHelper;
import model.Notes;

public class Editor extends AppCompatActivity {

    Notes notes = new Notes();
    DatabaseOpenHelper dbo = new DatabaseOpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

    }

    public void saveToDB(View v) {
        EditText noteTitle = findViewById(R.id.noteTitleInput);
        EditText noteContent = findViewById(R.id.noteContentInput);

        notes.setNoteTitle(noteTitle.getText().toString());
        notes.setNoteContent(noteContent.getText().toString());

        if(!noteTitle.getText().toString().isEmpty() && !noteContent.getText().toString().isEmpty()) {
            dbo.addNote(notes);
            dbo.close();

            noteTitle.setText("");
            noteContent.setText("");

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Notes must contain title and content", Toast.LENGTH_LONG).show();
        }

    }
}
