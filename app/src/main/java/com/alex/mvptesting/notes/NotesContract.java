package com.alex.mvptesting.notes;

import com.alex.mvptesting.entities.Note;

import java.util.List;

public class NotesContract {

    interface View {

        void showNotes(List<Note> notes);

        void showError();

        void showAddNoteActivity();

    }

    interface UserActionsListener {

        void loadNotes();

        void showAddNoteActivity();

    }
}