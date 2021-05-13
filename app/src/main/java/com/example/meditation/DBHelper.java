package com.example.meditation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME="Meditation.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB){
        return;
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i , int i1)
    {
        clearDatabase();
    }


    // day -> day that the meditation STARTED, not finished
    public Boolean addMeditation(int time, int day, int month, int year){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MED_TIME", time);
        contentValues.put("DAY", day);
        contentValues.put("MONTH", month);
        contentValues.put("YEAR", year);
        long insert = myDB.insert("MED_STATS",null,contentValues);
        //myDB.close();
        if(insert == -1) return false;
        else return true;
    }

    public void userInit() {
        SQLiteDatabase myDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", 1);
        contentValues.put("NAME", "Your Name");
        contentValues.put("MED_MAX_TIME", 0);
        contentValues.put("DAYS_STRAIGHT", 0);

        myDB.insert("USER",null,contentValues);

        //myDB.close();
    }

    // change user local name
    public void setName(String name) {
        SQLiteDatabase myDB = this.getWritableDatabase();

        //String queryString = "UPDATE USER SET NAME = '" + name + "' WHERE ID = 1;";

        //Cursor cursor = myDB.rawQuery(queryString, null);
        Cursor cursor = myDB.rawQuery("SELECT * FROM USER;", null);
        cursor.moveToFirst();
        int stat1 = cursor.getInt(2);
        int stat2 = cursor.getInt(3);

        myDB.execSQL("DROP TABLE IF EXISTS USER");
        myDB.execSQL("CREATE TABLE USER(ID INTEGER, NAME TEXT, MED_MAX_TIME INTEGER, DAYS_STRAIGHT INTEGER)");

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", 1);
        contentValues.put("NAME", name);
        contentValues.put("MED_MAX_TIME", stat1);
        contentValues.put("DAYS_STRAIGHT", stat2);

        myDB.insert("USER",null,contentValues);

        cursor.close();
        myDB.close();
    }

    public String getName() {
        String name;
        SQLiteDatabase myDB = this.getReadableDatabase();

        String queryString = "SELECT * FROM USER;";
        Cursor cursor = myDB.rawQuery(queryString, null);
        cursor.moveToFirst();
        name = cursor.getString(1);

        cursor.close();
        myDB.close();
        return name;
    }

    // fetch data -> test to see if it works
    public List<String> fetchData(){
        List<String> returnList = new ArrayList<>();
        returnList.add("empty?");

        String queryString = "SELECT * FROM MED_STATS";
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery(queryString, null);

        // if there are entries
        if (cursor.moveToFirst()) {
            // get elements
            do {
                int med_time = cursor.getInt(0);
                int day = cursor.getInt(1);
                int month = cursor.getInt(2);
                int year = cursor.getInt(3);
                String s = med_time + " " + day + "/" + month + "/" + year;
                returnList.add(s);
            } while(cursor.moveToNext());
        } else {
            // nope
        }

        cursor.close();
        myDB.close();
        return returnList;
    }

    public void clearDatabase() {
        SQLiteDatabase myDB = this.getWritableDatabase();

        myDB.execSQL("DROP TABLE IF EXISTS USER");
        myDB.execSQL("CREATE TABLE USER(ID INTEGER, NAME TEXT, MED_MAX_TIME INTEGER, DAYS_STRAIGHT INTEGER)");

        myDB.execSQL("DROP TABLE IF EXISTS MED_STATS");
        myDB.execSQL("CREATE TABLE MED_STATS(MED_TIME INTEGER, DAY INTEGER, MONTH INTEGER, YEAR INTEGER)");

        userInit();
        myDB.close();
    }

    public int getStreak() {
        int streak;
        SQLiteDatabase myDB = this.getReadableDatabase();

        String queryString = "SELECT * FROM USER;";
        Cursor cursor = myDB.rawQuery(queryString, null);
        cursor.moveToFirst();
        streak = cursor.getInt(3);

        cursor.close();
        myDB.close();
        return streak;
    }

    public int getTotalTime() {
        int total_time;
        SQLiteDatabase myDB = this.getReadableDatabase();

        String queryString = "SELECT * FROM USER;";
        Cursor cursor = myDB.rawQuery(queryString, null);
        cursor.moveToFirst();
        total_time = cursor.getInt(2);

        cursor.close();
        myDB.close();
        return total_time;
    }

    // fetch data -> test to see if it works
    public List<String> fetchData2(){
        List<String> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM USER";
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery(queryString, null);

        // if there are entries
        if (cursor.moveToFirst()) {
            // get elements
            do {
                int med_time = cursor.getInt(0);
                String day = cursor.getString(1);
                int month = cursor.getInt(2);
                int year = cursor.getInt(3);
                String s = med_time + " " + day + "/" + month + "/" + year;
                returnList.add(s);
            } while(cursor.moveToNext());
        } else {
            // nope
        }

        cursor.close();
        myDB.close();
        return returnList;
    }
    // Sad... ίσως όχι τόσο... Αλλά δεν θέλουμε login πλέον :)
/*
    public Boolean insertData(String username, String password){
        SQLiteDatabase myDB =this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = myDB.insert("users",null,contentValues);
        if(result==-1) return false;
        else return true;
    }

    public Boolean checkUsername(String username){
        SQLiteDatabase myDB= this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username = ?",new String[] {username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username = ? and password= ?", new String[] {username, password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }*/
}