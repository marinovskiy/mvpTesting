package com.alex.mvptesting.data.repository;

import com.alex.mvptesting.entities.Note;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface NotesRepository {

    Flowable<List<Note>> getAllNotes();

    Flowable<Note> getNoteById(Integer noteId);

    Completable addNote(Note note);

    Completable updateNote(Note note);

    Completable deleteNoteById(Integer noteId);

    Completable deleteAllNotes();

}