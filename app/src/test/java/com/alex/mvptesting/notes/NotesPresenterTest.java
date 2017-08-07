package com.alex.mvptesting.notes;

import com.alex.mvptesting.addnote.ImmediateSchedulerRule;
import com.alex.mvptesting.entities.Note;
import com.alex.mvptesting.model.NoteRepository;
import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NotesPresenterTest {

    private static List<Note> NOTES = Lists.newArrayList(
            new Note("Title1", "Description1"),
            new Note("Title2", "Description2")
    );

    @Mock
    private NotesContract.View notesView;

    @Mock
    private NoteRepository noteRepository;

    private NotesPresenter notesPresenter;

    @Rule
    public ImmediateSchedulerRule immediateSchedulerRule = new ImmediateSchedulerRule();

    @Before
    public void setupNotesPresenter() {
        MockitoAnnotations.initMocks(this);
        notesPresenter = new NotesPresenter(notesView, noteRepository);
    }

    @Test
    public void loadNotesFromRepositoryAndLoadIntoView() {
        when(noteRepository.getAllNotes()).thenReturn(Flowable.just(NOTES));

        notesPresenter.loadNotes();

        verify(noteRepository).getAllNotes();
        verify(notesView).showNotes(NOTES);
    }
}