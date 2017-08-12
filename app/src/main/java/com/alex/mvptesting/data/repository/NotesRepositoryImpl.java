package com.alex.mvptesting.data.repository;

import com.alex.mvptesting.data.source.NoteDataSource;
import com.alex.mvptesting.entities.Note;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.internal.operators.completable.CompletableFromAction;

public class NotesRepositoryImpl implements NotesRepository {

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
    public Flowable<Note> getNoteById(Integer noteId) {
        return noteLocalDataSource.loadNoteById(noteId);
    }

    @Override
    public Completable addNote(Note note) {
        return new CompletableFromAction(() -> noteLocalDataSource.addNewNote(note));
    }

    @Override
    public Completable updateNote(Note note) {
        return new CompletableFromAction(() -> noteLocalDataSource.updateNote(note));
    }

    @Override
    public Completable deleteNoteById(Integer noteId) {
        return new CompletableFromAction(() -> noteLocalDataSource.deleteNoteById(noteId));
    }

    @Override
    public Completable deleteAllNotes() {
        return new CompletableFromAction(noteLocalDataSource::deleteAllNotes);
    }
}