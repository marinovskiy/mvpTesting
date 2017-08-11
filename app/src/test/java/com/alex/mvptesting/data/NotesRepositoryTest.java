package com.alex.mvptesting.data;

import com.alex.mvptesting.data.repository.NotesRepository;
import com.alex.mvptesting.data.repository.NotesRepositoryImpl;
import com.alex.mvptesting.data.source.NoteDataSource;
import com.alex.mvptesting.entities.Note;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NotesRepositoryTest {

    private final List<Note> NOTES = Collections.singletonList(new Note("Title", "Text"));
    private final Note NOTE = new Note("Title", "Text");
    private final Integer NOTE_ID = 1;

    @Mock
    private NoteDataSource noteLocalDataSource;

    private NotesRepository notesRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        notesRepository = new NotesRepositoryImpl(noteLocalDataSource);
    }

    @Test
    public void loadNotes_requestAllNotesFromLocalDataSource() {
        when(noteLocalDataSource.loadNotes()).thenReturn(Flowable.just(NOTES));

        TestSubscriber<List<Note>> testSubscriber = new TestSubscriber<>();
        notesRepository.getAllNotes().subscribe(testSubscriber);

        verify(noteLocalDataSource).loadNotes();
        testSubscriber.assertValue(NOTES);
    }

    @Test
    public void loadNoteById_requestNoteByIdFromLocalDataSource() {
        when(noteLocalDataSource.loadNoteById(NOTE_ID)).thenReturn(Single.just(NOTE));

        TestObserver<Note> testObserver = new TestObserver<>();
        notesRepository.getNoteById(NOTE_ID).subscribe(testObserver);

        verify(noteLocalDataSource).loadNoteById(NOTE_ID);
        testObserver.assertValue(NOTE);
    }

    //TODO need to check result
    @Test
    public void insertNote_requestInsertNoteFromLocalDataSource() {
        notesRepository.addNote(NOTE);
////        assertThat(notesRepository.getAllNotes().blockingSingle().size(), is(1));
//
//        TestSubscriber<List<Note>> testSubscriber = new TestSubscriber<>();
//        notesRepository.getAllNotes().subscribe(testSubscriber);
//
//        assertThat(testSubscriber.values().size(), is(1));
    }
}