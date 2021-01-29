package com.example.boncafe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Boncafe.db";
    private static final String BRANCHES_TABLE_NAME = "BranchesTable";
    private static final String name_en = "name_en";
    private static final String name_ar = "name_ar";
    private static final String address = "address";
    private static final String lat = "lat";
    private static final String lon = "lon";
    private static final String tel = "tel";
    private static final String no = "branch_no";

    //Table create statement
    private static final String tableStatementCreateUserTable = "CREATE TABLE " + BRANCHES_TABLE_NAME + "(" + name_en + " String," + name_ar + " String," + address + " String," + lat + " String," + lon + " String," + tel + " String," + no + " String)";

    //Database initializer
    SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tableStatementCreateUserTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BRANCHES_TABLE_NAME);

    }

    public boolean addnewBranch(String name_en, String name_ar, String address, String lat, String lon, String tel, String no) throws SQLiteException {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(this.name_en, name_en);
        cv.put(this.name_ar, name_ar);
        cv.put(this.address, address);
        cv.put(this.lat, lat);
        cv.put(this.lon, lon);
        cv.put(this.tel, tel);
        cv.put(this.no, no);

        long result = db.insert(BRANCHES_TABLE_NAME, null, cv);
        return result != 0;
    }

    public boolean updateBranch(String name_en, String name_ar, String address, String lat, String lon, String tel, String no) throws SQLiteException {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(this.name_en, name_en);
        cv.put(this.name_ar, name_ar);
        cv.put(this.address, address);
        cv.put(this.lat, lat);
        cv.put(this.lon, lon);
        cv.put(this.tel, tel);
        cv.put(this.no, no);
        long result = db.update(BRANCHES_TABLE_NAME, cv, this.no + "=" + "'" + no + "'", null);
        return result != 0;
    }

    public Cursor getbranches() {
        db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("Select * from " + BRANCHES_TABLE_NAME, null);
        return cur;
    }

    public Cursor getbranchbyname(String branchname) {
        db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("Select * from " + BRANCHES_TABLE_NAME + " where " + this.name_en + "='" + branchname + "'", null);
        return cur;
    }

}
