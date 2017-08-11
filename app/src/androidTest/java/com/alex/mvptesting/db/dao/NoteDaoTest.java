package com.alex.mvptesting.db.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.alex.mvptesting.db.AppDatabase;
import com.alex.mvptesting.entities.Note;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;

import static org.mockito.ArgumentMatchers.any;

@RunWith(AndroidJUnit4.class)
public class NoteDaoTest {

    private static final Note NOTE = new Note("Title", "Text");
    private static final Integer NOTE_ID = 1;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase appDatabase;

    @Before
    public void initDb() throws Exception {
        appDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase.class
        )
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        appDatabase.close();
    }

    @Test
    public void getNotesWhenNoNoteInserted() {
        appDatabase.noteDao().getAll()
                .test()
                .assertNoValues();
    }

    @Test
    public void insertNoteAndGerForCheck() {
        appDatabase.noteDao().insert(NOTE);
        NOTE.setId(NOTE_ID);

        appDatabase.noteDao().getById(NOTE_ID)
                .test()
                .assertValue(new Predicate<Note>() {
                    @Override
                    public boolean test(@NonNull Note note) throws Exception {
                        return note != null && note.getId() == NOTE_ID && note.equals(NOTE);
                    }
                });
    }

//    @Test
//    public void getNotes() {
//        appDatabase.noteDao().getAll()
//                .test()
//                .assertValues(any());
//    }
}