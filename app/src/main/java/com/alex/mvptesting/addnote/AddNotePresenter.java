package com.alex.mvptesting.addnote;

import com.alex.mvptesting.AbstractPresenter;
import com.alex.mvptesting.data.repository.NotesRepository;
import com.alex.mvptesting.entities.Note;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

class AddNotePresenter extends AbstractPresenter<AddNoteContract.View>
        implements AddNoteContract.Presenter {

    @NonNull
    private final NotesRepository notesRepository;

    AddNotePresenter(@NonNull NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public void saveNote(String title, String text) {
        Note newNote = new Note(title, text);
        if (newNote.isEmpty()) {
            if (isViewAttached()) {
                getView().showEmptyNoteError();
            }
        } else {
            addSubscription(
                    notesRepository.addNote(newNote)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> {
                                        if (isViewAttached()) {
                                            getView().closeAddNoteActivity();
                                        }
                                    },
                                    throwable -> {
                                        if (isViewAttached()) {
                                            getView().showError(throwable.getMessage());
                                        }
                                    })
            );
        }
    }
}