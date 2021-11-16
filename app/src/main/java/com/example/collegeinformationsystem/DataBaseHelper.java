package com.example.collegeinformationsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "student_db";
    private static final String TAG = "DataBaseHelper";
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Dept.CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Dept.TABLE_NAME);

    }
    public long insertdept(Dept dept) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Dept.COLUMN_DEPARTMENT,dept.getDept());
        Log.e(TAG, "TABLE_NAME");
        long id = db.insert(Dept.TABLE_NAME, null, values);
        db.close();
        return id;
    }
    public long insertstaff(Staff staff){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Staff.COLUMN_NAME,staff.getName());
        values.put(Staff.COLUMN_DEPT,staff.getDepartment());
        values.put(Staff.COLUMN_EXP,staff.getExp());
        long id=db.insert(Staff.TABLE_NAME,null,values);
        db.close();
        return id;
    }
    public long insertdept( String dept) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Dept.COLUMN_DEPARTMENT,dept);
        Log.e(TAG, "TABLE_NAME");
        long id = db.insert(Dept.TABLE_NAME, null, values);
        db.close();

        return id;
    }
    public long insertstaff(String staff){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Staff.COLUMN_NAME,staff);
        values.put(Staff.COLUMN_DEPT,staff);
        values.put(Staff.COLUMN_EXP,staff);
        long id=db.insert(Staff.TABLE_NAME,null,values);
        db.close();
        return id;
    }
    public Dept getdept(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Dept.TABLE_NAME,
                new String[]{Dept.COLUMN_ID, Dept.COLUMN_DEPARTMENT},
                Dept.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Dept dept= new Dept(
                cursor.getInt(cursor.getColumnIndex(Dept.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Dept.COLUMN_DEPARTMENT)));
        cursor.close();
        return dept;
    }
    public Staff getstaff(long id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(Staff.TABLE_NAME,new String[]{Staff.COLUMN_ID,Staff.COLUMN_NAME,Staff.COLUMN_DEPT,Staff.COLUMN_EXP},Staff.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor !=null)
            cursor.moveToFirst();
        Staff staff=new Staff(cursor.getInt(cursor.getColumnIndex(Staff.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Staff.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Staff.COLUMN_DEPT)),
                cursor.getString(cursor.getColumnIndex(Staff.COLUMN_EXP)));
        cursor.close();
        return staff;
    }
    public List<Dept> getAllDept() {
        List<Dept> dep= new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Dept.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Dept dept = new Dept();
                dept.setId(cursor.getInt(cursor.getColumnIndex(Dept.COLUMN_ID)));
                dept.setDept(cursor.getString(cursor.getColumnIndex(Dept.COLUMN_DEPARTMENT)));

               dep.add(dept);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return dep;
    }
    public List<Staff>getAllStaff(){
        List<Staff>staf=new ArrayList<>();
        String selectQuery="SELECT * FROM "+Staff.TABLE_NAME;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                Staff staff= new Staff();
                staff.setId(cursor.getInt(cursor.getColumnIndex(Staff.COLUMN_ID)));
                staff.setName(cursor.getString(cursor.getColumnIndex(Staff.COLUMN_NAME)));
                staff.setExp(cursor.getString(cursor.getColumnIndex(Staff.COLUMN_EXP)));
                staff.setDepartment(cursor.getString(cursor.getColumnIndex(Staff.COLUMN_DEPT)));

                staf.add(staff);
            }while (cursor.moveToFirst());

        }
        db.close();
        return staf;
    }
    public int getDeptCount() {
        String countQuery = "SELECT  * FROM " + Dept.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }
    public int updateDept(Dept dept) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Dept.COLUMN_DEPARTMENT, dept.getDept());

        return db.update(Dept.TABLE_NAME, values, Dept.COLUMN_ID + " = ?",
                new String[]{String.valueOf(dept.getId())});
    }
    public int updateStaff(Staff staff){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Staff.COLUMN_NAME,staff.getName());
        values.put(Staff.COLUMN_DEPT,staff.getDepartment());
        values.put(Staff.COLUMN_EXP,staff.getExp());
        return  db.update(Staff.TABLE_NAME,values,Staff.COLUMN_ID + " =? " ,
                new String[]{String.valueOf(staff.getId())});
    }
    public void deleteDept(Dept dept) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Dept.TABLE_NAME, Dept.COLUMN_ID + " = ?",
                new String[]{String.valueOf(dept.getId())});
        db.close();
    }
    public  void  deleteStaff (Staff staff){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Staff.TABLE_NAME,Staff.COLUMN_ID + "=?",
                new String[]{ String.valueOf(staff.getId())});
    }
}
