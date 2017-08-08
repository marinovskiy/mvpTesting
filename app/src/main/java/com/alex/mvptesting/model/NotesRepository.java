package com.alex.mvptesting.model;

import com.alex.mvptesting.entities.Note;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface NotesRepository {

    Completable addNote(Note note);

    Flowable<List<Note>> getAllNotes();

    Single<Note> getNoteById(Integer noteId);

}