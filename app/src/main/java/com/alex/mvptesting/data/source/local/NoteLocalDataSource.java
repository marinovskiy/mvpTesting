package com.alex.mvptesting.data.source.local;

import com.alex.mvptesting.data.source.NoteDataSource;
import com.alex.mvptesting.db.dao.NoteDao;
import com.alex.mvptesting.entities.Note;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;

public class NoteLocalDataSource implements NoteDataSource {

    @NonNull
    private final NoteDao noteDao;

    public NoteLocalDataSource(@NonNull NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    public Flowable<List<Note>> loadNotes() {
        return noteDao.getAll();
    }

    @Override
    public Flowable<Note> loadNoteById(int noteId) {
        return noteDao.getById(noteId);
    }

    @Override
    public void addNewNote(Note note) {
        noteDao.insert(note);
    }

    @Override
    public void updateNote(Note note) {
        noteDao.update(note);
    }

    @Override
    public void deleteNoteById(Integer noteId) {
        noteDao.deleteById(noteId);
    }

    @Override
    public void deleteAllNotes() {
        noteDao.deleteAll();
    }
}