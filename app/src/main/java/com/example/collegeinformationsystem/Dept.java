package com.example.collegeinformationsystem;

public class Dept {
    public static final String TABLE_NAME = "Department";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DEPARTMENT = "dept";

    private int id;
    private String dept;
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DEPARTMENT + " TEXT "

                    + ")";
    public Dept() {
    }
    public Dept(int id, String dept) {
        this.id = id;
        this.dept = dept;

    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String toString(){
        return dept;
    }
}



