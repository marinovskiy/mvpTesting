package com.alex.mvptesting.notes;

import com.alex.mvptesting.entities.Note;

import java.util.List;

public interface NotesContract {

    interface View {

        void showNotes(List<Note> notes);

        void showError();

        void showAddNoteActivity();

        void showNoteDetailsActivity(Integer noteId);

    }

    interface UserActionsListener {

        void loadNotes();

        void showAddNoteActivity();

        void showNoteDetailsActivity(Integer noteId);

    }
}