package com.example.upesh.timetable;

/**
 * Created by upesh on 28/9/17.
 */

public class Memo {
    private int id;
    private String data;
    private String sub;
    private String day;
    private String time;
    private String date;


    public  Memo(int id,String date,String time,String data,String sub,String day)
    {       this.id=id;
        this.date=date;
        this.sub=sub;
        this.time=time;
        this.day=day;
        this.data=data;


    }
    public  Memo(String date,String time,String data,String sub)
    {
        this.date=date;
        this.sub=sub;
        this.time=time;
        this.data=data;


    }

    public int getId(){return id;}
    public void setId(int id){ this.id=id;}

    public String getData(){return data;}
    public void setData(String data){this.data=data;}

    public String getSub(){return sub;}
    public void setSub(String sub){this.sub=sub;}

    public String getDay(){return day;}
    public void setDay(String day){this.day=day;}

    public String getTime(){return time;}
    public void setTime(String time){this.time=time;}

    public String getDate(){return date;}
    public void setDate(String date){this.date=date;}


}


