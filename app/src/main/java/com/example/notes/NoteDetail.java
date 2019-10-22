package com.example.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import data.DatabaseOpenHelper;

public class NoteDetail extends AppCompatActivity {
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        TextView noteTitle = findViewById(R.id.noteDetailTitle);
        TextView noteContent = findViewById(R.id.noteDetailContent);
        TextView noteDate = findViewById(R.id.noteDetailDate);

        extras = getIntent().getExtras();
        noteTitle.setText(extras.getString("title"));
        noteContent.setText(extras.getString("content"));
        noteDate.setText(extras.getString("date"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.removeNote) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Delete note?");
            alert.setNegativeButton("No", null);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DatabaseOpenHelper dbo = new DatabaseOpenHelper(NoteDetail.this);
                    dbo.removeNote(extras.getInt("id"));
                    Intent intent = new Intent(NoteDetail.this, MainActivity.class);
                    startActivity(intent);
                    NoteDetail.this.finish();
                }
            });
            alert.show();
        }
        return true;
    }
}
