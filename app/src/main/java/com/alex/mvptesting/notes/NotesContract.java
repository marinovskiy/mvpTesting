package com.alex.mvptesting.notes;

import com.alex.mvptesting.BasePresenter;
import com.alex.mvptesting.entities.Note;

import java.util.List;

public interface NotesContract {

    interface View {

        void showNotes(List<Note> notes);

        void showError();

        void showAddNoteActivity();

        void showNoteDetailsActivity(Integer noteId);

    }

    interface UserActionsListener extends BasePresenter/*<View>*/ {

        void loadNotes();

        void showAddNoteActivity();

        void showNoteDetailsActivity(Integer noteId);

    }
}