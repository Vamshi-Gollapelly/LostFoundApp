package com.vamshigollapelly.lostfoundapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME    = "lostandfound.db";
    private static final int    DB_VERSION = 1;

    public static final String TABLE   = "items";
    public static final String COL_ID  = "id";
    public static final String COL_TYPE= "type";
    public static final String COL_NAME= "name";
    public static final String COL_PHONE="phone";
    public static final String COL_DESC = "description";
    public static final String COL_DATE = "date";
    public static final String COL_LOC  = "location";
    public static final String COL_CAT  = "category";
    public static final String COL_IMG  = "image_uri";
    public static final String COL_STAMP= "timestamp";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE + " (" +
                    COL_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_TYPE + " TEXT," + COL_NAME + " TEXT," +
                    COL_PHONE+ " TEXT," + COL_DESC + " TEXT," +
                    COL_DATE + " TEXT," + COL_LOC  + " TEXT," +
                    COL_CAT  + " TEXT," + COL_IMG  + " TEXT," +
                    COL_STAMP+ " TEXT)";

    public DatabaseHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override public void onUpgrade(SQLiteDatabase db,
                                    int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    /** Insert a new item and return the row ID */
    public long insertItem(LostFoundItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TYPE,  item.getType());
        cv.put(COL_NAME,  item.getName());
        cv.put(COL_PHONE, item.getPhone());
        cv.put(COL_DESC,  item.getDescription());
        cv.put(COL_DATE,  item.getDate());
        cv.put(COL_LOC,   item.getLocation());
        cv.put(COL_CAT,   item.getCategory());
        cv.put(COL_IMG,   item.getImageUri());
        cv.put(COL_STAMP, item.getTimestamp());
        long id = db.insert(TABLE, null, cv);
        db.close();
        return id;
    }

    /** Get all items; pass null categoryFilter to get all */
    public List<LostFoundItem> getAllItems(String categoryFilter) {
        List<LostFoundItem> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sel = null;
        String[] selArgs = null;
        if (categoryFilter != null) {
            sel = COL_CAT + "=?";
            selArgs = new String[]{ categoryFilter };
        }
        Cursor c = db.query(TABLE, null, sel, selArgs,
                null, null, COL_STAMP + " DESC");
        if (c.moveToFirst()) {
            do { list.add(cursorToItem(c)); } while (c.moveToNext());
        }
        c.close(); db.close();
        return list;
    }

    /** Get a single item by its ID */
    public LostFoundItem getItemById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE, null,
                COL_ID + "=?", new String[]{ String.valueOf(id) },
                null, null, null);
        LostFoundItem item = null;
        if (c.moveToFirst()) item = cursorToItem(c);
        c.close(); db.close();
        return item;
    }

    /** Delete an item by its ID */
    public void deleteItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, COL_ID + "=?",
                new String[]{ String.valueOf(id) });
        db.close();
    }

    private LostFoundItem cursorToItem(Cursor c) {
        LostFoundItem item = new LostFoundItem();
        item.setId(c.getInt(c.getColumnIndexOrThrow(COL_ID)));
        item.setType(c.getString(c.getColumnIndexOrThrow(COL_TYPE)));
        item.setName(c.getString(c.getColumnIndexOrThrow(COL_NAME)));
        item.setPhone(c.getString(c.getColumnIndexOrThrow(COL_PHONE)));
        item.setDescription(c.getString(c.getColumnIndexOrThrow(COL_DESC)));
        item.setDate(c.getString(c.getColumnIndexOrThrow(COL_DATE)));
        item.setLocation(c.getString(c.getColumnIndexOrThrow(COL_LOC)));
        item.setCategory(c.getString(c.getColumnIndexOrThrow(COL_CAT)));
        item.setImageUri(c.getString(c.getColumnIndexOrThrow(COL_IMG)));
        item.setTimestamp(c.getString(c.getColumnIndexOrThrow(COL_STAMP)));
        return item;
    }
}
