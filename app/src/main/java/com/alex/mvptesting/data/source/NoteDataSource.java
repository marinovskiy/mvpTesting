package com.alex.mvptesting.data.source;

import com.alex.mvptesting.entities.Note;

import java.util.List;

import io.reactivex.Flowable;

public interface NoteDataSource {

    Flowable<List<Note>> loadNotes();

    Flowable<Note> loadNoteById(int noteId);

    void saveNote(Note note);

    void updateNote(Note note);

    void deleteNoteById(Integer noteId);

    void deleteAllNotes();

}