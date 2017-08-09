package com.alex.mvptesting.notes;

import com.alex.mvptesting.entities.Note;
import com.alex.mvptesting.data.NotesRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NotesPresenter implements NotesContract.UserActionsListener {

    @NonNull
    private final NotesRepository notesRepository;

    @NonNull
    private NotesContract.View notesView;


    public NotesPresenter(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public void attach(NotesContract.View view) {
        notesView = view;
    }

    @Override
    public void detach() {
        notesView = null;
    }

    @Override
    public NotesContract.View getView() {
        return notesView;
    }

    @Override
    public void loadNotes() {
        notesRepository.getAllNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> notes) throws Exception {
                        if (notesView != null) {
                            notesView.showNotes(notes);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (notesView != null) {
                            notesView.showError();
                        }
                    }
                });
    }

    @Override
    public void showAddNoteActivity() {
        if (notesView != null) {
            notesView.showAddNoteActivity();
        }
    }

    @Override
    public void showNoteDetailsActivity(Integer noteId) {
        if (notesView != null) {
            notesView.showNoteDetailsActivity(noteId);
        }
    }
}
