package com.example.fakespotify;

public class Time{
    int seconds = 0 ;
    int minutes = 0 ;
    int hours = 0 ;

    Time(int hours,int minutes, int seconds)
    {
        this.hours = hours ;
        this.minutes = minutes ;
        this.seconds = seconds ;
        FixTime();
    }

    public int GetHours() {return hours ;}
    public int GetMinutes() {return minutes ;}

    public int GetSeconds() {return  seconds ;}

    private void FixTime()
    {
        minutes += seconds/60 ;
        seconds = seconds % 60 ;
        hours += minutes/60 ;
        minutes = minutes%60 ;
    }

    @Override
    public String toString()
    {
        String s = "" ;
        if(hours > 0)
            s = String.valueOf(hours) + "h " + String.valueOf(minutes) + "m " + String.valueOf(seconds) ;
        else if(minutes > 0)
            s = String.valueOf(minutes) + "m " + String.valueOf(seconds) + "s" ;
        else
            s = String.valueOf(seconds) + "s" ;
        return s ;
    }

    public void SetValue(Time t)
    {
        hours = t.hours ;
        minutes = t.minutes ;
        seconds = t.seconds ;
        FixTime() ;
    }

    public void SetValue(int hours, int minutes , int seconds)
    {
        this.hours = hours ;
        this.minutes = minutes ;
        this.seconds = seconds ;
        FixTime();
    }

    public void SetValue(int sec)
    {
        seconds = sec ;
        FixTime();
    }

    public void IncrementSeconds(int value)
    {
        this.seconds += value ;
        FixTime() ;
    }
}

