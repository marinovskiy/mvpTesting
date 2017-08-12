package com.alex.mvptesting.notes;

import com.alex.mvptesting.BasePresenter;
import com.alex.mvptesting.BaseView;
import com.alex.mvptesting.entities.Note;

import java.util.List;

interface NotesContract {

    interface View extends BaseView {

        void showNotes(List<Note> notes);

        void showEmptyView();

        void hideEmptyViewIfNeed();

        void showError();

        void showAddNoteActivity();

        void showNoteDetailsActivity(Integer noteId);

    }

    interface Presenter extends BasePresenter<View> {

        void loadNotes();

        void showAddNoteActivity();

        void showNoteDetailsActivity(Integer noteId);

    }
}