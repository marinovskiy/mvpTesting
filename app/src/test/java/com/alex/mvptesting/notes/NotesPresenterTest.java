package com.alex.mvptesting.notes;

import com.alex.mvptesting.addnote.ImmediateSchedulerRule;
import com.alex.mvptesting.model.NoteRepository;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NotesPresenterTest {

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
        when(noteRepository.getAllNotes()).thenReturn(Flowable.just(Collections.emptyList()));

        notesPresenter.loadNotes();

        verify(noteRepository).getAllNotes();
//        verify(notesView).showNotes();
    }

}