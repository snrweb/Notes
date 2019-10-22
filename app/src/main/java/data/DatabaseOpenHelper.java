package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Notes;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    ArrayList<Notes> notes = new ArrayList<>();

    public DatabaseOpenHelper(Context context) {
        super(context, Constants.DBNAME, null, Constants.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createdTable = "CREATE TABLE " + Constants.DBTABLE + "(" +
                Constants.DBID + " INTEGER PRIMARY KEY," + Constants.DBTITLE + " TEXT," +
                Constants.DBCONTENT + " TEXT," + Constants.DBDATE + " LONG );";
        db.execSQL(createdTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.DBTABLE);
        onCreate(db);
    }

    public void addNote(Notes mNotes) {
        SQLiteDatabase db = this.getWritableDatabase();

        String title = mNotes.getNoteTitle();
        String content = mNotes.getNoteContent();

        ContentValues values = new ContentValues();
        values.put(Constants.DBTITLE, title);
        values.put(Constants.DBCONTENT, content);
        values.put(Constants.DBDATE, System.currentTimeMillis());

        db.insert(Constants.DBTABLE, null, values);
        db.close();
    }

    public ArrayList<Notes> getNotes() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.DBTABLE, new String[] {Constants.DBID, Constants.DBTITLE,
                Constants.DBCONTENT, Constants.DBDATE}, null, null, null, null, Constants.DBDATE + " DESC");

        if(cursor.moveToFirst()) {
            do {

                Notes note = new Notes();
                note.setNoteTitle(cursor.getString(cursor.getColumnIndex(Constants.DBTITLE)));
                note.setNoteContent(cursor.getString(cursor.getColumnIndex(Constants.DBCONTENT)));
                note.setNoteID(cursor.getInt(cursor.getColumnIndex(Constants.DBID)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String noteDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DBDATE))).getTime());
                note.setNoteDate(noteDate);

                notes.add(note);

            } while (cursor.moveToNext());
        }

        db.close();
        return notes;
    }

    public void removeNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.DBTABLE, Constants.DBID + " = ?", new String[] {String.valueOf(id)});
        db.close();
    }

}
