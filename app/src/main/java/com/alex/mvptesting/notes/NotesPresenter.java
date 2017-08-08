package com.alex.mvptesting.notes;

import com.alex.mvptesting.entities.Note;
import com.alex.mvptesting.model.NotesRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NotesPresenter implements NotesContract.UserActionsListener {

    @NonNull
    private final NotesContract.View notesView;
    @NonNull
    private final NotesRepository notesRepository;

    public NotesPresenter(NotesContract.View notesView, NotesRepository notesRepository) {
        this.notesView = notesView;
        this.notesRepository = notesRepository;
    }

    @Override
    public void loadNotes() {
        notesRepository.getAllNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> notes) throws Exception {
                        notesView.showNotes(notes);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        notesView.showError();
                    }
                });
    }

    @Override
    public void showAddNoteActivity() {
        notesView.showAddNoteActivity();
    }

    @Override
    public void showNoteDetailsActivity(Integer noteId) {
        notesView.showNoteDetailsActivity(noteId);
    }
}
