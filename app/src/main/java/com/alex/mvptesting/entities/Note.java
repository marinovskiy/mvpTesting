package com.alex.mvptesting.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String text;

//    public Note() {
//    }
//

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
    }

//    public Note(int id, String title, String text) {
//        this.id = id;
//        this.title = title;
//        this.text = text;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isEmpty() {
        return (title == null || "".equals(title)) &&
                (text == null || "".equals(text));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        return id == note.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}