package com.alex.mvptesting.addnote;

import com.alex.mvptesting.entities.Note;
import com.alex.mvptesting.model.NoteRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddNotePresenterTest {

    private final Note NOTE = new Note("title", "text");

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private AddNoteContract.View addNoteView;

    private AddNotePresenter addNotePresenter;

    @Rule
    public ImmediateSchedulerRule immediateSchedulerRule = new ImmediateSchedulerRule();

    @Before
    public void setupAddNotePresenter() {
        MockitoAnnotations.initMocks(this);
        addNotePresenter = new AddNotePresenter(addNoteView, noteRepository);
    }

    @Test
    public void addNote_showsSuccessMessage() {
        when(noteRepository.addNote(NOTE)).thenReturn(Completable.complete());

        addNotePresenter.saveNote(NOTE.getTitle(), NOTE.getText());

        verify(noteRepository).addNote(NOTE);
        verify(addNoteView).closeAddNoteActivity();
    }
}