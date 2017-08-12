package com.alex.mvptesting.settings;

import com.alex.mvptesting.AbstractPresenter;
import com.alex.mvptesting.data.repository.NotesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class SettingsPresenter extends AbstractPresenter<SettingsContract.View>
        implements SettingsContract.Presenter {

    @NonNull
    private final NotesRepository notesRepository;

    SettingsPresenter(@NonNull NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public void closeSettingsActivity() {
        if (isViewAttached()) {
            getView().closeSettingsActivity();
        }
    }

    @Override
    public void deleteAllNotes() {
        addSubscription(
                notesRepository.deleteAllNotes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            if (isViewAttached()) {
                                getView().allNoteHaveBeenDeleted();
                            }
                        }, throwable -> {
                            if (isViewAttached()) {
                                getView().showErrorDeletingNotes(throwable.getMessage());
                            }
                        })
        );
    }
}