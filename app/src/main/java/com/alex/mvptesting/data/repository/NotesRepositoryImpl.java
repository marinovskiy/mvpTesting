package com.alex.mvptesting.data.repository;

import com.alex.mvptesting.data.source.NoteDataSource;
import com.alex.mvptesting.db.AppDatabase;
import com.alex.mvptesting.entities.Note;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.internal.operators.completable.CompletableFromAction;

public class NotesRepositoryImpl implements NotesRepository {

//    private final AppDatabase appDatabase;
//
//    public NotesRepositoryImpl(@NonNull AppDatabase appDatabase) {
//        this.appDatabase = appDatabase;
//    }
//
//    @Override
//    public Flowable<List<Note>> getAllNotes() {
//        return appDatabase.noteDao().getAll();
//    }
//
//    @Override
//    public Single<Note> getNoteById(Integer noteId) {
//        return appDatabase.noteDao().getById(noteId);
//    }
//
//    @Override
//    public Completable addNote(final Note note) {
//        return new CompletableFromAction(new Action() {
//            @Override
//            public void run() throws Exception {
//                appDatabase.noteDao().insert(note);
//            }
//        });
//    }

    @NonNull
    private final NoteDataSource noteLocalDataSource;

    public NotesRepositoryImpl(@NonNull NoteDataSource noteLocalDataSource) {
        this.noteLocalDataSource = noteLocalDataSource;
    }

    @Override
    public Flowable<List<Note>> getAllNotes() {
        return noteLocalDataSource.loadNotes();
    }

    @Override
    public Single<Note> getNoteById(Integer noteId) {
        return noteLocalDataSource.loadNoteById(noteId);
    }

    @Override
    public Completable addNote(Note note) {
        return new CompletableFromAction(() -> noteLocalDataSource.addNewNote(note));
    }
}