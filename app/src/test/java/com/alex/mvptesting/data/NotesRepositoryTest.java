package com.alex.mvptesting.data;

import com.alex.mvptesting.data.source.local.AppDatabase;
import com.alex.mvptesting.entities.Note;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class NotesRepositoryTest {

    @Mock
    private AppDatabase appDatabase;

    private NotesRepository notesRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        notesRepository = new NotesRepositoryImpl(appDatabase);
    }

//    @Test
//    public void testGetAllNotes() {
////        notesRepository.getAllNotes();
//
////        verify(notesRepository).get
//    }
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