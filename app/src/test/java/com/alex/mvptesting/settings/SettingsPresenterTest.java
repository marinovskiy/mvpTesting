package com.alex.mvptesting.settings;

import com.alex.mvptesting.ImmediateSchedulerRule;
import com.alex.mvptesting.data.repository.NotesRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SettingsPresenterTest {

    @Mock
    private SettingsContract.View settingsView;

    @Mock
    private NotesRepository notesRepository;

    private SettingsPresenter settingsPresenter;

    @Rule
    public ImmediateSchedulerRule immediateSchedulerRule = new ImmediateSchedulerRule();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        settingsPresenter = new SettingsPresenter(notesRepository);
        settingsPresenter.attach(settingsView);
    }

    @Test
    public void closeSettingsActivity() {
        settingsPresenter.closeSettingsActivity();

        verify(settingsView).closeSettingsActivity();
    }

    @Test
    public void deleteAllNotes_showSuccessMessage() {
        when(notesRepository.deleteAllNotes()).thenReturn(Completable.complete());

        settingsPresenter.deleteAllNotes();

        verify(notesRepository).deleteAllNotes();
        verify(settingsView).allNoteHaveBeenDeleted();
    }

    @Test
    public void deleteAllNotes_showError() {
        when(notesRepository.deleteAllNotes()).thenReturn(Completable.error(new Throwable()));

        settingsPresenter.deleteAllNotes();

        verify(notesRepository).deleteAllNotes();
        verify(settingsView).showErrorDeletingNotes(any());
    }

}