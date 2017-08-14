package com.alex.mvptesting.notedetails;

import com.alex.mvptesting.ImmediateSchedulerRule;
import com.alex.mvptesting.data.repository.NotesRepository;
import com.alex.mvptesting.entities.Note;

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

public class NoteDetailsPresenterTest {

    private static final Integer NOTE_ID_INVALID_TEST = null;
    private static final Integer NOTE_ID_TEST = 1;
    private static final Note NOTE_TEST = new Note("TitleTest", "TextTest");

    @Mock
    private NotesRepository notesRepository;

    @Mock
    private NoteDetailsContract.View noteDetailsView;

    private NoteDetailsPresenter noteDetailsPresenter;

    @Rule
    public ImmediateSchedulerRule immediateSchedulerRule = new ImmediateSchedulerRule();

    @Before
    public void setupNoteDetailsPresenter() {
        MockitoAnnotations.initMocks(this);

        noteDetailsPresenter = new NoteDetailsPresenter(notesRepository);
        noteDetailsPresenter.attach(noteDetailsView);
    }

    @Test
    public void getNoteFromRepositoryAndLoadIntoView() {
        when(notesRepository.getNote(NOTE_ID_TEST)).thenReturn(Flowable.just(NOTE_TEST));

        noteDetailsPresenter.getNote(NOTE_ID_TEST);

        verify(notesRepository).getNote(NOTE_ID_TEST);

        InOrder inOrder = Mockito.inOrder(noteDetailsView);
        inOrder.verify(noteDetailsView).setProgressIndicator(true);
        inOrder.verify(noteDetailsView).setProgressIndicator(false);
        inOrder.verify(noteDetailsView).showNote(NOTE_TEST);
    }

    @Test
    public void getUnknownNoteFromRepositoryAndLoadIntoView() {
        noteDetailsPresenter.getNote(NOTE_ID_INVALID_TEST);

        verify(noteDetailsView).showMissingNote();
    }

    @Test
    public void getNoteFromRepository_showError() {
        when(notesRepository.getNote(NOTE_ID_TEST)).thenReturn(Flowable.error(new Throwable()));

        noteDetailsPresenter.getNote(NOTE_ID_TEST);

        InOrder inOrder = Mockito.inOrder(noteDetailsView);
        inOrder.verify(noteDetailsView).setProgressIndicator(true);
        inOrder.verify(noteDetailsView).setProgressIndicator(false);
        inOrder.verify(noteDetailsView).showError(any());
    }

    @Test
    public void deleteNote_showMissingNote() {
        noteDetailsPresenter.deleteNoteById(NOTE_ID_INVALID_TEST);

        verify(noteDetailsView).showMissingNote();
    }

    @Test
    public void deleteNote_showSuccessMessageAndCloseNoteDetailsActivity() {
        when(notesRepository.deleteNoteById(NOTE_ID_TEST)).thenReturn(Completable.complete());

        noteDetailsPresenter.deleteNoteById(NOTE_ID_TEST);

        verify(noteDetailsView).showDeletedSuccessfulMessageAndCloseNoteDetailsActivity();
    }

    @Test
    public void deleteNote_showError() {
        when(notesRepository.deleteNoteById(NOTE_ID_TEST))
                .thenReturn(Completable.error(new Throwable()));

        noteDetailsPresenter.deleteNoteById(NOTE_ID_TEST);

        verify(noteDetailsView).showError(any());
    }
}