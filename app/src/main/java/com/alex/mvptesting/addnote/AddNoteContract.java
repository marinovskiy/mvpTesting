package com.alex.mvptesting.addnote;

public interface AddNoteContract {

    interface View {

        void showEmptyNoteError();

        void closeAddNoteActivity();

        void showError(String message);

    }

    interface UserActionsListener {

        void saveNote(String title, String text);

    }
}