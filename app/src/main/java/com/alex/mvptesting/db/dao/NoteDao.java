package com.alex.mvptesting.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.alex.mvptesting.entities.Note;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes")
    Flowable<List<Note>> getAll();

    @Query("SELECT * FROM notes WHERE id = (:noteId)")
    Note getById(int noteId);

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

}