package com.alex.mvptesting.addnote;

import com.alex.mvptesting.BasePresenter;
import com.alex.mvptesting.BaseView;

interface AddNoteContract {

    interface View extends BaseView {

        void showEmptyNoteError();

        void closeAddNoteActivity();

        void showError(String message);

    }

    interface Presenter extends BasePresenter<View> {

        void saveNote(String title, String text);

    }
}