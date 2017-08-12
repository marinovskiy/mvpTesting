package com.alex.mvptesting.addnote;

import com.alex.mvptesting.BasePresenter;
import com.alex.mvptesting.BaseView;
import com.alex.mvptesting.entities.Note;

interface AddEditNoteContract {

    interface View extends BaseView {

        void setProgressIndicator(boolean active);

        void showEmptyNoteError();

        void closeAddEditNoteActivity();

        void showError(String message);

        void showMissingNote();

        void showNote(Note note);

    }

    interface Presenter extends BasePresenter<View> {

        void saveNote(String title, String text);

        void getNote(Integer noteId);

        void updateNote(Integer id, String title, String text);

    }
}