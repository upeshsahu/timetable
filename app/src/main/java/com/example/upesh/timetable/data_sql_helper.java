package com.example.upesh.timetable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by upesh on 28/9/17.
 */
  //this is the database for assignment and project
public class data_sql_helper extends SQLiteOpenHelper{

    public data_sql_helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void qweryData(String sql){
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String date,String time,String data,String sub,String day){
        SQLiteDatabase database=getWritableDatabase();
        String sql="INSERT INTO MEMORECORD VALUES(NULL,?,?,?,?,?)";
        SQLiteStatement statement=database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,date);
        statement.bindString(2,time);
        statement.bindString(3,data);
        statement.bindString(4,sub);
        statement.bindString(5,day);
        statement.executeInsert();
    }


    public void update(String table,String constraintcol,String conditionvalue,String newvalues){
        ContentValues cv=new ContentValues();
        cv.put(constraintcol,newvalues);
        SQLiteDatabase database=getWritableDatabase();

        int n=database.update(table,cv,"sub=?",new String[]{conditionvalue});
    }
    public void update2(String updatecolumn,String newvalues,String conditiondate,String conditiontime) {
        ContentValues cv = new ContentValues();
        cv.put(updatecolumn, newvalues);
        SQLiteDatabase database = getWritableDatabase();
        database.update("MEMORECORD", cv, "date=? AND time=?", new String[]{conditiondate, conditiontime});
        database.close();
    }
    public Cursor getdata(String sql)
    { SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sql,null);

    }
    public void delete(String conddate,String condtime){
        SQLiteDatabase database=getWritableDatabase();
        database.delete("MEMORECORD","date=? and time=?",new String[]{conddate,condtime});

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Cursor query(String conditiondate,String conditiontime){
        SQLiteDatabase database=getReadableDatabase();
        Cursor cursor=database.query("MEMORECORD",new String[]{"date","time","data","sub"},"date=? and time=?",new String[]{conditiondate,conditiontime},null,null,null,null);
        return cursor;

    }
    public Cursor query2(String conditiondate){
        SQLiteDatabase database=getReadableDatabase();
        Cursor cursor=database.query(" MEMORECORD",new String[]{"date","time","data","sub"},"date=?",new String[]{conditiondate},null,null,null,null);
        return cursor;


    }




}

