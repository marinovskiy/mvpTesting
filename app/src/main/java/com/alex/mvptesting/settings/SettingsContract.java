package com.alex.mvptesting.settings;

import com.alex.mvptesting.BasePresenter;
import com.alex.mvptesting.BaseView;

public interface SettingsContract {

    interface View extends BaseView {

        void closeSettingsActivity();

        void allNoteHaveBeenDeleted();

        void showErrorDeletingNotes(String message);

    }

    interface Presenter extends BasePresenter<View> {

        void closeSettingsActivity();

        void deleteAllNotes();

    }

}