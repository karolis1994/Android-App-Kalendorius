package com.kalendorius.karolis.kalendorius;


public class Note {

    private int _id;
    private String _name;
    private int _date;
    private String _text;

    public Note(int id, String name, int date, String note) {
        _name = name;
        _date = date;
        _text = note;
        _id = id;
    }

    public int get_id() {
        return _id;
    }

    public int get_date() {
        return _date;
    }

    public String get_name() {
        return _name;
    }

    public String get_text() {
        return _text;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_date(int _date) {
        this._date = _date;
    }

    public void set_id(int _Id) {
        this._id = _Id;
    }

    public void set_text(String _text) {
        this._text = _text;
    }
}
