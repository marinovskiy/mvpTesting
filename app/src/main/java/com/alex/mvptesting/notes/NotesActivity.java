package com.alex.mvptesting.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alex.mvptesting.BaseActivity;
import com.alex.mvptesting.R;
import com.alex.mvptesting.adapters.NotesRecyclerViewAdapter;
import com.alex.mvptesting.addnote.AddEditNoteActivity;
import com.alex.mvptesting.application.NotesApplication;
import com.alex.mvptesting.data.repository.NotesRepositoryImpl;
import com.alex.mvptesting.data.source.local.NoteLocalDataSource;
import com.alex.mvptesting.entities.Note;
import com.alex.mvptesting.notedetails.NoteDetailsActivity;
import com.alex.mvptesting.settings.SettingsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NotesActivity extends BaseActivity implements NotesContract.View {

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.rv_notes)
    RecyclerView rvNotes;
    @BindView(R.id.ll_no_notes)
    LinearLayout llNoNotes;

    private NotesContract.Presenter presenter;

    private NotesRecyclerViewAdapter rvNotesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        presenter = new NotesPresenter(
                new NotesRepositoryImpl(
                        new NoteLocalDataSource(NotesApplication.appDatabase.noteDao())
                )
        );
        presenter.attach(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//            getSupportActionBar().setHomeButtonEnabled(false);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        rvNotes.setLayoutManager(new GridLayoutManager(this, 2));

        presenter.loadNotes();
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.fab_add_note)
    public void onViewClicked() {
        presenter.showAddNoteActivity();
    }

    @Override
    public void showNotes(List<Note> notes) {
        if (rvNotesAdapter == null) {
            rvNotesAdapter = new NotesRecyclerViewAdapter(notes);
            rvNotes.setAdapter(rvNotesAdapter);

            rvNotesAdapter.setOnItemClickListener(position -> presenter.showNoteDetailsActivity(
                    rvNotesAdapter.getNotes().get(position).getId()
            ));
        } else {
            rvNotesAdapter.updateNotes(notes);
        }
    }

    @Override
    public void showEmptyView() {
        rvNotes.setVisibility(View.GONE);
        llNoNotes.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyViewIfNeed() {
        if (llNoNotes.getVisibility() == View.VISIBLE) {
            llNoNotes.setVisibility(View.GONE);
            rvNotes.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.error_occurred_try_later_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddNoteActivity() {
        startActivity(new Intent(this, AddEditNoteActivity.class));
    }

    @Override
    public void showNoteDetailsActivity(Integer noteId) {
        Intent intentNoteDetails = new Intent(this, NoteDetailsActivity.class);
        intentNoteDetails.putExtra(NoteDetailsActivity.INTENT_KEY_NOTE_ID, noteId);
        startActivity(intentNoteDetails);
    }
}