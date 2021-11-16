package com.example.collegeinformationsystem;

public class Staff {
    public static final String TABLE_NAME = "Staff_info";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DEPT ="department";
    public static  final String COLUMN_EXP="Exp";
    private int id;
    private String name;
    private String department;
    private String Exp;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_DEPT + " TEXT ,"
                    + COLUMN_EXP +" TEXT "
                    + ")";
    public Staff() {
    }
    public Staff(int id, String name, String department ,String Exp) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.Exp=Exp;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getExp() {
        return Exp;
    }
    public void setExp(String exp) {
        Exp = exp;
    }
    public String toString(){
        return name;
    }
}



