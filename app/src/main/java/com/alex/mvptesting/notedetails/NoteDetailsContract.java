package com.alex.mvptesting.notedetails;

import android.support.annotation.Nullable;

import com.alex.mvptesting.BasePresenter;
import com.alex.mvptesting.entities.Note;

public interface NoteDetailsContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showNote(Note note);

        void showMissingNote();

        void showError(String message);

    }

    interface UserActionsListener extends BasePresenter<View> {

        void getNote(@Nullable Integer noteId);

    }
}