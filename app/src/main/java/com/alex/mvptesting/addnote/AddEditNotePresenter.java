package com.alex.mvptesting.addnote;

import android.support.annotation.Nullable;

import com.alex.mvptesting.AbstractPresenter;
import com.alex.mvptesting.data.repository.NotesRepository;
import com.alex.mvptesting.entities.Note;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

class AddEditNotePresenter extends AbstractPresenter<AddEditNoteContract.View>
        implements AddEditNoteContract.Presenter {

    @NonNull
    private final NotesRepository notesRepository;

    AddEditNotePresenter(@NonNull NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public void saveNote(String title, String text) {
        Note newNote = new Note(title, text);
        if (newNote.isEmpty()) {
            if (isViewAttached()) {
                getView().showEmptyNoteError();
            }
            return;
        }

        addSubscription(
                notesRepository.addNote(newNote)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                                    if (isViewAttached()) {
                                        getView().closeAddEditNoteActivity();
                                    }
                                },
                                throwable -> {
                                    if (isViewAttached()) {
                                        getView().showError(throwable.getMessage());
                                    }
                                })
        );
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
                                if (note != null) {
                                    getView().setProgressIndicator(false);
                                    getView().showNote(note);
                                } else {
                                    getView().setProgressIndicator(false);
                                    getView().showMissingNote();
                                }
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
    public void updateNote(Integer id, String title, String text) {
        Note note = new Note(title, text);
        note.setId(id);

        if (note.isEmpty()) {
            if (isViewAttached()) {
                getView().showEmptyNoteError();
            }
            return;
        }

        addSubscription(
                notesRepository.updateNote(note)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            if (isViewAttached()) {
                                getView().closeAddEditNoteActivity();
                            }
                        }, throwable -> {
                            if (isViewAttached()) {
                                getView().showError(throwable.getMessage());
                            }
                        })
        );
    }
}