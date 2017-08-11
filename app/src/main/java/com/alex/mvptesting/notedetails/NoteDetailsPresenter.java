package com.alex.mvptesting.notedetails;

import android.support.annotation.Nullable;

import com.alex.mvptesting.AbstractPresenter;
import com.alex.mvptesting.data.repository.NotesRepository;
import com.alex.mvptesting.entities.Note;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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

        //TODO addSubscription()
//        addSubscription(
                notesRepository.getNoteById(noteId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<Note>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                            }

                            @Override
                            public void onSuccess(@NonNull Note note) {
                                if (isViewAttached()) {
                                    getView().setProgressIndicator(false);
                                    getView().showNote(note);
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                if (isViewAttached()) {
                                    getView().setProgressIndicator(false);
                                    getView().showError(e.getMessage());
                                }
                            }
                        });
//        );
    }
}