package com.alex.mvptesting.notedetails;

import android.support.annotation.Nullable;

import com.alex.mvptesting.AbstractPresenter;
import com.alex.mvptesting.data.repository.NotesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

class NoteDetailsPresenter extends AbstractPresenter<NoteDetailsContract.View>
        implements NoteDetailsContract.Presenter {

    @NonNull
    private final NotesRepository notesRepository;

    NoteDetailsPresenter(@NonNull NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public void getNote(@Nullable Integer noteId) {
        if (null == noteId || -1 == noteId) {
            if (isViewAttached()) {
                getView().showMissingNote();
            }
            return;
        }

        if (isViewAttached()) {
            getView().setProgressIndicator(true);
        }

        addSubscription(
                notesRepository.getNoteById(noteId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(note -> {
                            if (isViewAttached()) {
                                getView().setProgressIndicator(false);
                                getView().showMissingNote();
                            }
                        }, throwable -> {
                            if (isViewAttached()) {
                                getView().setProgressIndicator(false);
                                getView().showError(throwable.getMessage());
                            }
                        })
        );
    }

    @Override
    public void deleteNoteById(@Nullable Integer noteId) {
        if (null == noteId) {
            if (isViewAttached()) {
                getView().showMissingNote();
            }
            return;
        }

        addSubscription(
                notesRepository.deleteNoteById(noteId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            if (isViewAttached()) {
                                getView().showDeletedSuccessfulMessageAndCloseNoteDetailsActivity();
                            }
                        }, throwable -> {
                            if (isViewAttached()) {
                                getView().showError(throwable.getMessage());
                            }
                        })
        );
    }
}