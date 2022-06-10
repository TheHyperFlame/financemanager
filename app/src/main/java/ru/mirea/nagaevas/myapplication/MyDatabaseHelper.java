package ru.mirea.nagaevas.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Finances.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_finances";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_SUM = "sum";


    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //создание бд
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_DATE + " DATE, " +
                        COLUMN_CATEGORY + " TEXT, " +
                        COLUMN_SUM + " REAL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //дроп таблицы если она есть
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    void addSpending(String date, String category, Float sum) { //добавление записи в бд. contentValues - контейнер для данных, чтобы передавать их в бд
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_CATEGORY, category);
        contentValues.put(COLUMN_SUM, String.valueOf(sum));
        long result = database.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Added!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() { //чтение данных из бд
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;
        if (database != null) {
            cursor = database.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String category, String sum) { //изменение данных в бд
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_SUM, sum);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteData(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readDataFromDate(String dateBegin, String dateEnd) { //чтение данных из бд в интервале дат
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE DATE BETWEEN " + dateBegin + " AND " + dateEnd;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;
        if (database != null) {
            cursor = database.rawQuery(query, null);
        }
        return cursor;
    }
}
