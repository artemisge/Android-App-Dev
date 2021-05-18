package com.example.meditation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME="Meditation.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null,3);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB){
        myDB.execSQL("CREATE TABLE IF NOT EXISTS USER(ID INTEGER, NAME TEXT, DAYS_STRAIGHT INTEGER, AWARD1 INTEGER, AWARD2 INTEGER, AWARD3 INTEGER, AWARD4 INTEGER)");
        myDB.execSQL("CREATE TABLE IF NOT EXISTS MED_STATS(DAY INTEGER, MONTH INTEGER, YEAR INTEGER)");
        userInit(myDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i , int i1)
    {
        clearDatabase();
    }


    // day -> day that the meditation STARTED, not finished
    public boolean addMeditation(String currentDay){
        int day, month, year;
        String[] cd = currentDay.split("-");
        day = Integer.parseInt(cd[1]);
        month = Integer.parseInt(cd[0]);
        year = Integer.parseInt(cd[2]);

        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DAY", day);
        contentValues.put("MONTH", month);
        contentValues.put("YEAR", year);
        long insert = myDB.insert("MED_STATS",null,contentValues);
        myDB.close();
        if(insert == -1) return false;
        else return true;
    }

    public void updateStreak(String currentDay) throws ParseException {
        int streak;

        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        // get previous day
        String previousDay = simpleDateFormat.format(new Date(simpleDateFormat.parse(currentDay).getTime() - (1000*60*60*24)));

        SQLiteDatabase myDB = this.getWritableDatabase();

        Cursor cursor = myDB.rawQuery("SELECT * FROM USER;", null);
        cursor.moveToFirst();
        String name  = cursor.getString(1);
        int stat1 = cursor.getInt(2);
        int aw1 = cursor.getInt(3);
        int aw2 = cursor.getInt(4);
        int aw3 = cursor.getInt(5);
        int aw4 = cursor.getInt(6);
        myDB.close();
        if (checkDay(previousDay)) {
            // streak ++
            stat1++;
        } else {
            // streak = 1 (only current day)
            stat1 = 1;
        }
        myDB = this.getWritableDatabase();

        myDB.execSQL("DROP TABLE IF EXISTS USER");
        myDB.execSQL("CREATE TABLE IF NOT EXISTS USER(ID INTEGER, NAME TEXT, DAYS_STRAIGHT INTEGER, AWARD1 INTEGER, AWARD2 INTEGER, AWARD3 INTEGER, AWARD4 INTEGER)");

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", 1);
        contentValues.put("NAME", name);
        contentValues.put("DAYS_STRAIGHT", stat1);
        contentValues.put("AWARD1", aw1);
        contentValues.put("AWARD2", aw2);
        contentValues.put("AWARD3", aw3);
        contentValues.put("AWARD4", aw4);

        myDB.insert("USER",null,contentValues);

        cursor.close();
        myDB.close();
    }

    public void updateAwards() {
        // check streak and update awards
    }

    public boolean checkDay(String currentDay) {
        int day, month, year;
        String[] cd = currentDay.split("-");
        day = Integer.parseInt(cd[1]);
        month = Integer.parseInt(cd[0]);
        year = Integer.parseInt(cd[2]);
        System.out.println(year);

        SQLiteDatabase myDB = this.getReadableDatabase();
        String queryString = "SELECT * FROM MED_STATS WHERE DAY = " +  day + " AND MONTH = " + month + " AND YEAR = " + year;;
        Cursor cursor = myDB.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            myDB.close();
            System.out.println("truueee check");
            return true;
            // day already inserted
        }
        System.out.println("false check");
        cursor.close();
        myDB.close();
        return false;
    }

    public boolean[] loadAwards() {
        // check database
        boolean[] unlocked = {true, false, false, false}; // tmp
        return unlocked;
    }


    public void userInit(SQLiteDatabase myDB) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", 1);
        contentValues.put("NAME", "Your Name");
        contentValues.put("DAYS_STRAIGHT", 0);
        contentValues.put("AWARD1", 0);
        contentValues.put("AWARD2", 0);
        contentValues.put("AWARD3", 0);
        contentValues.put("AWARD4", 0);

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
        int aw1 = cursor.getInt(3);
        int aw2 = cursor.getInt(4);
        int aw3 = cursor.getInt(5);
        int aw4 = cursor.getInt(6);


        myDB.execSQL("DROP TABLE IF EXISTS USER");
        myDB.execSQL("CREATE TABLE IF NOT EXISTS USER(ID INTEGER, NAME TEXT, DAYS_STRAIGHT INTEGER, AWARD1 INTEGER, AWARD2 INTEGER, AWARD3 INTEGER, AWARD4 INTEGER)");

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", 1);
        contentValues.put("NAME", name);
        contentValues.put("DAYS_STRAIGHT", stat1);
        contentValues.put("AWARD1", aw1);
        contentValues.put("AWARD2", aw2);
        contentValues.put("AWARD3", aw3);
        contentValues.put("AWARD4", aw4);

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
                int day = cursor.getInt(0);
                int month = cursor.getInt(1);
                int year = cursor.getInt(2);
                String s = day + "/" + month + "/" + year;
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
        myDB.execSQL("CREATE TABLE IF NOT EXISTS USER(ID INTEGER, NAME TEXT, DAYS_STRAIGHT INTEGER, AWARD1 INTEGER, AWARD2 INTEGER, AWARD3 INTEGER, AWARD4 INTEGER)");

        myDB.execSQL("DROP TABLE IF EXISTS MED_STATS");
        myDB.execSQL("CREATE TABLE IF NOT EXISTS MED_STATS(DAY INTEGER, MONTH INTEGER, YEAR INTEGER)");

        userInit(myDB);
        myDB.close();
    }

    public int getStreak() {
        int streak;
        SQLiteDatabase myDB = this.getReadableDatabase();

        String queryString = "SELECT * FROM USER;";
        Cursor cursor = myDB.rawQuery(queryString, null);
        cursor.moveToFirst();
        streak = cursor.getInt(2);

        cursor.close();
        myDB.close();
        return streak;
    }

    public int getTotalDays() {
        SQLiteDatabase myDB = this.getReadableDatabase();

        int total_days = (int) DatabaseUtils.queryNumEntries(myDB, "MED_STATS");

        myDB.close();
        return total_days;
    }
}