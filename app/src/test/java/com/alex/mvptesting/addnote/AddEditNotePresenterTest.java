package com.alex.mvptesting.addnote;

import com.alex.mvptesting.ImmediateSchedulerRule;
import com.alex.mvptesting.entities.Note;
import com.alex.mvptesting.data.repository.NotesRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.internal.operators.completable.CompletableFromAction;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddEditNotePresenterTest {

    private final Note EMPTY_NOTE_TEST = new Note(null, "");
    private final Note NOTE_TEST = new Note("title", "text");
    private final Integer NOTE_ID_TEST = 1;

    @Mock
    private NotesRepository notesRepository;

    @Mock
    private AddEditNoteContract.View addNoteView;

    private AddEditNotePresenter addEditNotePresenter;

    @Rule
    public ImmediateSchedulerRule immediateSchedulerRule = new ImmediateSchedulerRule();

    @Before
    public void setupAddNotePresenter() {
        MockitoAnnotations.initMocks(this);

        addEditNotePresenter = new AddEditNotePresenter(notesRepository);
        addEditNotePresenter.attach(addNoteView);
    }

    @Test
    public void addNote_showsEmptyNoteError() {
        addEditNotePresenter.saveNote(EMPTY_NOTE_TEST.getTitle(), EMPTY_NOTE_TEST.getText());

        verify(addNoteView).showEmptyNoteError();
    }

    @Test
    public void addNote_closeAddEDitNoteActivity() {
        when(notesRepository.saveNote(NOTE_TEST)).thenReturn(Completable.complete());

        addEditNotePresenter.saveNote(NOTE_TEST.getTitle(), NOTE_TEST.getText());

        verify(notesRepository).saveNote(NOTE_TEST);
        verify(addNoteView).closeAddEditNoteActivity();
    }

    @Test
    public void saveNote_showsError() {
        when(notesRepository.saveNote(NOTE_TEST))
                .thenReturn(CompletableFromAction.error(new Throwable()));

        addEditNotePresenter.saveNote(NOTE_TEST.getTitle(), NOTE_TEST.getText());

        verify(notesRepository).saveNote(NOTE_TEST);
        verify(addNoteView).showError(any());
    }

    @Test
    public void getNoteFromRepository_showMissingNoteError() {
        addEditNotePresenter.getNote(null);

        verify(addNoteView).showMissingNote();
    }

    @Test
    public void getNoteFromRepositoryAndShowIntoView() {
        when(notesRepository.getNote(NOTE_ID_TEST)).thenReturn(Flowable.just(NOTE_TEST));

        notesRepository.getNote(NOTE_ID_TEST);

        verify(notesRepository).getNote(NOTE_ID_TEST);

        InOrder inOrder = Mockito.inOrder(addNoteView);
        inOrder.verify(addNoteView).setProgressIndicator(true);
        inOrder.verify(addNoteView).setProgressIndicator(false);
        inOrder.verify(addNoteView).showNote(NOTE_TEST);
    }

    @Test
    public void getNoteFromRepository_showsError() {
        when(notesRepository.getNote(NOTE_ID_TEST)).thenReturn(Flowable.error(new Throwable()));

        addEditNotePresenter.getNote(NOTE_ID_TEST);

        verify(notesRepository).getNote(NOTE_ID_TEST);
        verify(addNoteView).showError(any());
    }

    @Test
    public void updateNote_showsEmptyNoteError() {
        addEditNotePresenter.updateNote(NOTE_ID_TEST, EMPTY_NOTE_TEST.getTitle(), EMPTY_NOTE_TEST.getText());

        verify(addNoteView).showEmptyNoteError();
    }

    @Test
    public void updateNote_closeAddEditNoteActivity() {
        when(notesRepository.saveNote(NOTE_TEST)).thenReturn(Completable.complete());

        addEditNotePresenter.saveNote(NOTE_TEST.getTitle(), NOTE_TEST.getText());

        verify(notesRepository).saveNote(NOTE_TEST);
        verify(addNoteView).closeAddEditNoteActivity();
    }

//    @Test
//    public void updateNote_showError() {
//        when(notesRepository.updateNote(NOTE_TEST))
//                .thenReturn(CompletableFromAction.error(new Throwable()));
//
//        addEditNotePresenter.updateNote(NOTE_ID_TEST, NOTE_TEST.getTitle(), NOTE_TEST.getText());
//
//        verify(notesRepository).updateNote(NOTE_TEST);
//        verify(addNoteView).showError(any());
//    }
}