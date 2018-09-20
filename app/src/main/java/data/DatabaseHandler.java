package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;

import model.MyWish;

/**
 * Created by Yusara on 19/7/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private final ArrayList<MyWish> wishList = new ArrayList<>();

    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create our table
        String CREATE_WISHES_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "(" + Constants.KEY_ID + " INTEGER PRIMARY KEY, " + Constants.TITLE_NAME +
                " TEXT, " + Constants.CONTENT_NAME + " TEXT, " + Constants.DATE_NAME + " LONG);";

        db.execSQL(CREATE_WISHES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        //create a new one
        onCreate(db);

    }

    //delete a wish
    public void deleteWish(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ? ",
                new String[]{ String.valueOf(id)});

        db.close();

    }

    //add content to table
    public void addWishes (MyWish wish)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.TITLE_NAME, wish.getTitle());
        values.put(Constants.CONTENT_NAME, wish.getContent());
        values.put(Constants.DATE_NAME, System.currentTimeMillis());


        db.insert(Constants.TABLE_NAME, null, values);
        //db.insert(Constants.TABLE_NAME, null, values);

        Log.v("Wish successfully!", "yeah!!");

        db.close();

    }

    //Get All wishes
    public ArrayList<MyWish> getWishes()
    {

        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID, Constants.TITLE_NAME, Constants.CONTENT_NAME, Constants.DATE_NAME},null,null, null,null,Constants.DATE_NAME+ " DESC");

        //loop through cursor
        if(cursor.moveToFirst())
        {
            //retrieve then move kan data
            do
            {
                MyWish wish = new MyWish();
                wish.setTitle(cursor.getString(cursor.getColumnIndex(Constants.TITLE_NAME)));
                wish.setContent(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_NAME)));
                wish.setItemId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                //untuk retrieve blik date/time to array list
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String dateData = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());

                wish.setRecordDate(dateData);

                // last step msuk kan dlm arraylist yg kita da declare supaya msuk ke dlm listview
                wishList.add(wish);

            } while (cursor.moveToNext());

        }

        return wishList;
    }
}
