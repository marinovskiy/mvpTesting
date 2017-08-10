package com.alex.mvptesting.data;

import com.alex.mvptesting.data.source.local.AppDatabase;
import com.alex.mvptesting.entities.Note;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class NotesRepositoryTest {

    private final List<Note> NOTES = Collections.singletonList(new Note("Title", "Text"));

    @Mock
    private AppDatabase appDatabase;

    private NotesRepository notesRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        notesRepository = new NotesRepositoryImpl(appDatabase);
    }

    @Test
    public void testGetAllNotes() {
        when(notesRepository.getAllNotes()).thenReturn(Flowable.just(NOTES));

        TestSubscriber<List<Note>> subscriber = new TestSubscriber<>();
        notesRepository.getAllNotes().subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertSubscribed();
//        assertThat(subscriber.get).isEqualtTo(NOTES));

//        verify(notesRepository).get

//        assertThat();
    }
//
//    @Test
//    public void testAddNote() {
//        Note note = new Note("Title", "Text");
//
//        notesRepository.addNote(note);
//
//        verify(appDatabase).noteDao().insert(note);
//    }
}