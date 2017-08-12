package com.alex.mvptesting.notes;

import com.alex.mvptesting.AbstractPresenter;
import com.alex.mvptesting.data.repository.NotesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

class NotesPresenter extends AbstractPresenter<NotesContract.View>
        implements NotesContract.Presenter {

    @NonNull
    private final NotesRepository notesRepository;

    NotesPresenter(@NonNull NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public void loadNotes() {
        addSubscription(
                notesRepository.getAllNotes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(notes -> {
                            if (isViewAttached()) {
                                if (notes.isEmpty()) {
                                    getView().showEmptyView();
                                } else {
                                    getView().hideEmptyViewIfNeed();
                                    getView().showNotes(notes);
                                }
                            }
                        }, throwable -> {
                            if (isViewAttached()) {
                                getView().showError();
                            }
                        })
        );
    }

    @Override
    public void showAddNoteActivity() {
        if (isViewAttached()) {
            getView().showAddNoteActivity();
        }
    }

    @Override
    public void showNoteDetailsActivity(Integer noteId) {
        if (isViewAttached()) {
            getView().showNoteDetailsActivity(noteId);
        }
    }
}