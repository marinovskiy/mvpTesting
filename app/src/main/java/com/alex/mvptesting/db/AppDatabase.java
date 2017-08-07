package com.alex.mvptesting.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.alex.mvptesting.db.dao.NoteDao;
import com.alex.mvptesting.entities.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

}