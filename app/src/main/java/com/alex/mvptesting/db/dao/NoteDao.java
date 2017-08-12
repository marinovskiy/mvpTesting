package com.alex.mvptesting.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.alex.mvptesting.entities.Note;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes")
    Flowable<List<Note>> getAll();

    @Query("SELECT * FROM notes WHERE id = (:noteId)")
    Flowable<Note> getById(int noteId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Query("DELETE FROM notes WHERE id = (:noteId)")
    void deleteById(Integer noteId);

    @Query("DELETE FROM notes")
    void deleteAll();

}