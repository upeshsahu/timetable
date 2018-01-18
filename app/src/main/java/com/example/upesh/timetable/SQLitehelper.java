package com.example.upesh.timetable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by upesh on 22/9/17.
 */

public class SQLitehelper  extends SQLiteOpenHelper {


    public SQLitehelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void qweryData(String sql){
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String subject,String time,String teachername,String day){
        SQLiteDatabase database=getWritableDatabase();
        String sql="INSERT INTO TIMETABLE VALUES(NULL,?,?,?,?)";
        SQLiteStatement statement=database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,subject);
        statement.bindString(2,time);
        statement.bindString(3,teachername);
        statement.bindString(4,day);
        statement.executeInsert();
    }

        public void update (String updatecolumn,String newvalues,String conditionday,String conditiontime){
            ContentValues cv=new ContentValues();
            cv.put(updatecolumn,newvalues);
            SQLiteDatabase database=getWritableDatabase();

              database.update("TIMETABLE",cv,"time=? AND day=?",new String[]{conditiontime,conditionday});
            database.close();



    }
    public Cursor getdata(String sql)
    { SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sql,null);

    }

    public void delete_list(String cond1,String cond2,String cond3,String cond4){
        SQLiteDatabase database=getWritableDatabase();
        database.delete("TIMETABLE","day=? AND time=? AND sub=? AND name=?",new String[]{cond1,cond2,cond3,cond4});
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
   public Cursor query(String col,String condition){
       SQLiteDatabase database=getReadableDatabase();
       Cursor cursor=database.query("TIMETABLE",new String[]{"sub","day","time","name"},col+"=?",new String[]{condition},null,null,null,null);
       return cursor;

   }
    public Cursor query(String condition){
        SQLiteDatabase database=getReadableDatabase();
        Cursor cursor=database.query("TIMETABLE",new String[]{"id","sub","day","time","name"},"day=?",new String[]{condition},null,null,null,null);
        return cursor;


    }
    public Cursor query_main(String conditionday,String conditiontime){
        SQLiteDatabase database=getReadableDatabase();
        Cursor cursor=database.query("TIMETABLE",new String[]{"time","sub","name"},"day=? and time=?",new String[]{conditionday,conditiontime},null,null,null,null);

        return cursor;


    }
    public Cursor queryy(String col){
        SQLiteDatabase database=getReadableDatabase();
        Cursor cursor=database.query("TIMETABLE",new String[]{col},null,null,null,null,"time",null);
        return cursor;

    }




}
