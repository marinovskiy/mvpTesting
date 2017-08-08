package com.alex.mvptesting.notedetails;

import android.support.annotation.Nullable;

import com.alex.mvptesting.entities.Note;
import com.alex.mvptesting.model.NotesRepository;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NoteDetailsPresenter implements NoteDetailsContract.UserActionsListener {

    @NonNull
    private final NoteDetailsContract.View noteDetailsView;
    @NonNull
    private final NotesRepository notesRepository;

    public NoteDetailsPresenter(NoteDetailsContract.View noteDetailsView,
                                NotesRepository notesRepository) {
        this.noteDetailsView = noteDetailsView;
        this.notesRepository = notesRepository;
    }

    @Override
    public void getNote(@Nullable Integer noteId) {
        if (null == noteId || -1 == noteId) {
            noteDetailsView.showMissingNote();
            return;
        }

        noteDetailsView.setProgressIndicator(true);

        notesRepository.getNoteById(noteId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Note>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull Note note) {
                        noteDetailsView.setProgressIndicator(false);
                        if (note == null) {
                            noteDetailsView.showMissingNote();
                        } else {
                            noteDetailsView.showNote(note);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        noteDetailsView.setProgressIndicator(false);
                        noteDetailsView.showError(e.getMessage());
                    }
                });
    }
}