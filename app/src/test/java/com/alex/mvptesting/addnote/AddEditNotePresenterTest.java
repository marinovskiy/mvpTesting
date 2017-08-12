package com.alex.mvptesting.addnote;

import com.alex.mvptesting.ImmediateSchedulerRule;
import com.alex.mvptesting.entities.Note;
import com.alex.mvptesting.data.repository.NotesRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.internal.operators.completable.CompletableFromAction;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddEditNotePresenterTest {

    private final Note EMPTY_NOTE = new Note(null, "");
    private final Note NOTE = new Note("title", "text");

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
        addEditNotePresenter.saveNote(EMPTY_NOTE.getTitle(), EMPTY_NOTE.getText());

        verify(addNoteView).showEmptyNoteError();
    }

    @Test
    public void addNote_closeAddNoteActivity() {
        when(notesRepository.addNote(NOTE)).thenReturn(Completable.complete());

        addEditNotePresenter.saveNote(NOTE.getTitle(), NOTE.getText());

        verify(notesRepository).addNote(NOTE);
        verify(addNoteView).closeAddEditNoteActivity();
    }

    @Test
    public void loadNotesFromRepositoryAndShowError() {
        when(notesRepository.addNote(NOTE)).thenReturn(CompletableFromAction.error(new Throwable()));

        addEditNotePresenter.saveNote(NOTE.getTitle(), NOTE.getText());

        verify(notesRepository).addNote(NOTE);
        verify(addNoteView).showError(any());
    }
}