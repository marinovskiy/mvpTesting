package com.alex.mvptesting.notes;

import com.alex.mvptesting.ImmediateSchedulerRule;
import com.alex.mvptesting.data.repository.NotesRepository;
import com.alex.mvptesting.entities.Note;
import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Flowable;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NotesPresenterTest {

    private final Integer NOTE_ID = 1;

    private final List<Note> NOTES = Lists.newArrayList(
            new Note("Title1", "Description1"),
            new Note("Title2", "Description2")
    );

    @Mock
    private NotesContract.View notesView;

    @Mock
    private NotesRepository notesRepository;

    private NotesPresenter notesPresenter;

    @Rule
    public ImmediateSchedulerRule immediateSchedulerRule = new ImmediateSchedulerRule();

    @Before
    public void setupNotesPresenter() {
        MockitoAnnotations.initMocks(this);

        notesPresenter = new NotesPresenter(notesRepository);
        notesPresenter.attach(notesView);
    }

    @Test
    public void clickOnFab_ShowsAddNoteUi() {
        notesPresenter.showAddNoteActivity();

        verify(notesView).showAddNoteActivity();
    }

    @Test
    public void clickOnList_showsNoteDetailsScreen() {
        notesPresenter.showNoteDetailsActivity(NOTE_ID);

        verify(notesView).showNoteDetailsActivity(NOTE_ID);
    }

    @Test
    public void loadNotesFromRepositoryAndLoadIntoView() {
        when(notesRepository.getAllNotes()).thenReturn(Flowable.just(NOTES));

        notesPresenter.loadNotes();

        verify(notesRepository).getAllNotes();
        verify(notesView).showNotes(NOTES);
    }

    @Test
    public void loadNotesFromRepositoryAndShowError() {
        when(notesRepository.getAllNotes()).thenReturn(Flowable.error(new Throwable()));

        notesPresenter.loadNotes();

        verify(notesRepository).getAllNotes();
        verify(notesView).showError();
    }

//    @Test
//    public void attachTest() {
//        notesPresenter.attach(notesView);
//
//        assertNotNull(notesView);
//    }
//
//    @Test
//    public void detachTest() {
//        notesPresenter.detach();
//
//        assertNull(notesPresenter.getView());
//    }
}