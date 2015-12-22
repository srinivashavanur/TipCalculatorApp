package com.example.tipcalculator;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;

/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TipCalculator.db";
    public static final String TABLE_NAME = "tip_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "SEEK_PERCENT";
    public static final String COL_3 = "BILL_TOTAL_AMT";
    public static final String COL_4 = "TIP";
    public static final String COL_5 = "TOTAL_BILL_AMT";
    public static final String COL_6 = "TOTAL_SPLITS";
    public static final String COL_7 = "created_at";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,SEEK_PERCENT TEXT NOT NULL,BILL_TOTAL_AMT TEXT NOT NULL,TIP TEXT NOT NULL,TOTAL_BILL_AMT TEXT NOT NULL, TOTAL_SPLITS TEXT NOT NULL, created_at DATETIME DEFAULT CURRENT_TIMESTAMP)");
    	//db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,SEEK_PERCENT TEXT,BILL_TOTAL_AMT TEXT NOT NULL,TIP TEXT NOT NULL,TOTAL_BILL_AMT TEXT NOT NULL, TOTAL_SPLITS TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(int seekpercent,String billtotal,String tip, String total_bill, String total_split) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm:ss"); 
        Date date = new Date();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,seekpercent);
        contentValues.put(COL_3,billtotal);
        contentValues.put(COL_4,tip);
        contentValues.put(COL_5,total_bill);
        contentValues.put(COL_6,total_split);
        contentValues.put("created_at",dateFormat.format(date));
        long result = db.insert(TABLE_NAME,null ,contentValues);
       
        if(result == -1)
            return false;
        else
            return true;
    }
    
    public Cursor fetch(SQLiteDatabase db) {
    	
		String[] columns = new String[] { DatabaseHelper.COL_2, DatabaseHelper.COL_3,DatabaseHelper.COL_4,DatabaseHelper.COL_5,DatabaseHelper.COL_6,DatabaseHelper.COL_7};
		Cursor cursor = db.query(DatabaseHelper.TABLE_NAME,columns, null, null, null, null, DatabaseHelper.COL_7+" DESC");
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}
    
    
 public void deleteDB(SQLiteDatabase db) {
    	
		
		 db.delete(TABLE_NAME, null, null);
		
	}
}