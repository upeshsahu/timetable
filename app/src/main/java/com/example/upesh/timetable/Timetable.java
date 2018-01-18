package com.example.upesh.timetable;

/**
 * Created by upesh on 22/9/17.
 */

public class Timetable {
    private int id;
    private String name;
    private String sub;
    private String day;
    private String time;
    private String data;


    public  Timetable(int id,String sub,String time,String name,String day)
    {       this.id=id;
        this.name=name;
        this.sub=sub;
        this.time=time;
        this.day=day;


    }
    public  Timetable(String sub,String time,String name,String day)
    {
        this.name=name;
        this.sub=sub;
        this.time=time;
        this.day=day;


    }
    public  Timetable(String sub,String time,String name)
    {
        this.name=name;
        this.sub=sub;
        this.time=time;
    }
    public int getId(){return id;}
    public void setId(int id){ this.id=id;}

    public String getName(){return name;}
    public void setName(String name){this.name=name;}

    public String getSub(){return sub;}
    public void setSub(String sub){this.sub=sub;}

    public String getDay(){return day;}
    public void setDay(String day){this.day=day;}

    public String getTime(){return time;}
    public void setTime(String time){this.time=time;}



}

