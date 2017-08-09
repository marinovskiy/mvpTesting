package com.alex.mvptesting.application;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.alex.mvptesting.data.source.local.AppDatabase;
import com.alex.mvptesting.data.source.local.DatabaseInfo;

public class NotesApplication extends Application {

    public static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        appDatabase = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                DatabaseInfo.DB_NAME
        ).build();

//        appDatabase.c
    }
}