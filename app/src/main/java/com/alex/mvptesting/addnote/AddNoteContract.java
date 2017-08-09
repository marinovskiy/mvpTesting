package com.alex.mvptesting.addnote;

import com.alex.mvptesting.BasePresenter;

public interface AddNoteContract {

    interface View {

        void showEmptyNoteError();

        void closeAddNoteActivity();

        void showError(String message);

    }

    interface UserActionsListener extends BasePresenter<View> {

        void saveNote(String title, String text);

    }
}