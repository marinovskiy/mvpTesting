package com.alex.mvptesting.addnote;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.alex.mvptesting.db.AppDatabase;
import com.alex.mvptesting.entities.Note;
import com.alex.mvptesting.model.NoteRepository;
import com.alex.mvptesting.model.NoteRepositoryImpl;
import com.alex.mvptesting.notes.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddNotePresenterTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private AddNoteContract.View addNoteView;

    private AddNotePresenter addNotePresenter;

    private AppDatabase appDatabase;

    @Rule
    public ImmediateSchedulerRule immediateSchedulerRule = new ImmediateSchedulerRule();

    @Before
    public void setupAddNotePresenter() {
        MockitoAnnotations.initMocks(this);

//        // Override RxAndroid schedulers
//        RxAndroidPlugins.setMainThreadSchedulerHandler(new Function<Scheduler, Scheduler>() {
//            @Override
//            public Scheduler apply(Scheduler scheduler) throws Exception {
//                return Schedulers.trampoline();
//            }
//        });

//        addNoteView = Mockito.mock(AddNoteContract.View.class);
//        noteRepository = new NoteRepositoryImpl();
        addNotePresenter = new AddNotePresenter(addNoteView, noteRepository);
    }

    @Test
    public void addNote_showsSuccessMessage() {
        Note note = new Note("title", "text");

//        when(noteRepository.addNote(note)).thenReturn(Observable.just())

        addNotePresenter.saveNote(note.getTitle(), note.getText());

        verify(noteRepository).addNote(any(Note.class));
        verify(addNoteView).closeAddNoteActivity();
    }
}