package com.example.notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.Notes;

public class NoteArrayAdapter extends ArrayAdapter<Notes> {

    public Activity activity;
    public ArrayList<Notes> mNotes;
    int layoutResource;
    public Notes n;

    public NoteArrayAdapter(Activity act, int resource, ArrayList<Notes> notes) {
        super(act, resource, notes);
        activity = act;
        layoutResource = resource;
        mNotes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mNotes.size();
    }

    @Override
    public Notes getItem(int position) {
        return mNotes.get(position);
    }

    @Override
    public int getPosition(Notes item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        viewHolder holder = null;

        if(row == null || (row.getTag()) == null) {
            holder = new viewHolder();
            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(R.layout.note_row, null);

            holder.noteTitle = row.findViewById(R.id.noteTitle);
            holder.noteContent = row.findViewById(R.id.noteContent);
            holder.noteDate = row.findViewById(R.id.noteDate);

            final viewHolder finalHolder = holder;

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, NoteDetail.class);
                    intent.putExtra("title", finalHolder.noteTitle.getText().toString());
                    intent.putExtra("content", finalHolder.noteContent.getText().toString());
                    intent.putExtra("date", finalHolder.noteDate.getText().toString());
                    intent.putExtra("id", finalHolder.noteID);

                    activity.startActivity(intent);
                }
            });

            row.setTag(holder);
        } else {
            holder = (viewHolder) row.getTag();
        }

        holder.viewNote = getItem(position);

        holder.noteTitle.setText(holder.viewNote.getNoteTitle());
        holder.noteContent.setText(holder.viewNote.getNoteContent());
        holder.noteDate.setText(holder.viewNote.getNoteDate());
        holder.noteID = holder.viewNote.getNoteID();

        return row;
    }


    class viewHolder {
        Notes viewNote;
        int noteID;
        TextView noteTitle;
        TextView noteContent;
        TextView noteDate;
    }
}

