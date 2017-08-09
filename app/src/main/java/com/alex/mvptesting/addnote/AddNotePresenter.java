package com.alex.mvptesting.addnote;

import com.alex.mvptesting.entities.Note;
import com.alex.mvptesting.data.NotesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddNotePresenter implements AddNoteContract.UserActionsListener {

    public static final String TAG = AddNotePresenter.class.getSimpleName();

    @NonNull
    private final NotesRepository notesRepository;

    @NonNull
    private AddNoteContract.View addNoteView;

    public AddNotePresenter(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override
    public void attach(AddNoteContract.View view) {
        addNoteView = view;
    }

    @Override
    public void detach() {
        addNoteView = null;
    }

    @Override
    public AddNoteContract.View getView() {
        return null;
    }

    @Override
    public void saveNote(String title, String text) {
        Note newNote = new Note(title, text);
        if (newNote.isEmpty()) {
            addNoteView.showEmptyNoteError();
        } else {
            notesRepository.addNote(newNote)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action() {
                        @Override
                        public void run() throws Exception {
                            addNoteView.closeAddNoteActivity();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            addNoteView.showError(throwable.getMessage());
                        }
                    });
        }
    }
}