package com.alex.mvptesting.notedetails;

import android.support.annotation.Nullable;

import com.alex.mvptesting.BasePresenter;
import com.alex.mvptesting.BaseView;
import com.alex.mvptesting.entities.Note;

interface NoteDetailsContract {

    interface View extends BaseView {

        void setProgressIndicator(boolean active);

        void showNote(Note note);

        void showMissingNote();

        void showError(String message);

    }

    interface Presenter extends BasePresenter<View> {

        void getNote(@Nullable Integer noteId);

    }
}