package com.alex.mvptesting.model;

import com.alex.mvptesting.db.AppDatabase;
import com.alex.mvptesting.entities.Note;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.internal.operators.completable.CompletableFromAction;

public class NotesRepositoryImpl implements NotesRepository {

    private AppDatabase appDatabase;

    public NotesRepositoryImpl(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public Completable addNote(final Note note) {
        return new CompletableFromAction(new Action() {
            @Override
            public void run() throws Exception {
                appDatabase.noteDao().insert(note);
            }
        });
    }

    @Override
    public Flowable<List<Note>> getAllNotes() {
        return appDatabase.noteDao().getAll();
    }

    @Override
    public Single<Note> getNoteById(Integer noteId) {
        return appDatabase.noteDao().getById(noteId);
    }
}