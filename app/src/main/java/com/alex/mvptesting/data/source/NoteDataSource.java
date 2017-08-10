package com.alex.mvptesting.data.source;

import com.alex.mvptesting.entities.Note;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface NoteDataSource {

    Flowable<List<Note>> loadNotes();

    Single<Note> loadNoteById(int noteId);

    void addNewNote(Note note);

}