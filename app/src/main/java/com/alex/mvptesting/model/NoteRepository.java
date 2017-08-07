package com.alex.mvptesting.model;

import com.alex.mvptesting.entities.Note;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface NoteRepository {

    Completable addNote(Note note);

    Flowable<List<Note>> getAllNotes();

}