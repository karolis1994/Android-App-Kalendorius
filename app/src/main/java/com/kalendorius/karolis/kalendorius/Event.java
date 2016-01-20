package com.kalendorius.karolis.kalendorius;


public class Event {

    private int _id;
    private String _name;
    private int _date;
    private int _time;
    private int _repeat;
    private int _alert;

    public Event(int id, String name, int date, int time, int repeat, int alert) {
        _name = name;
        _date = date;
        _time = time;
        _repeat = repeat;
        _alert = alert;
        _id = id;
    }

    public void set_date(int _date) {
        this._date = _date;
    }

    public void set_Id(int _Id) {
        this._id = _Id;
    }

    public void set_alert(int _alert) {
        this._alert = _alert;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_repeat(int _repeat) {
        this._repeat = _repeat;
    }

    public void set_time(int _time) {
        this._time = _time;
    }

    public int get_id() {
        return _id;
    }

    public int get_date() {
        return _date;
    }

    public int get_alert() {
        return _alert;
    }

    public String get_name() {
        return _name;
    }

    public int get_repeat() {
        return _repeat;
    }

    public int get_time() {
        return _time;
    }
}
