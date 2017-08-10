package com.alex.mvptesting.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.alex.mvptesting.R;
import com.alex.mvptesting.activities.BaseActivity;
import com.alex.mvptesting.adapters.NotesRecyclerViewAdapter;
import com.alex.mvptesting.adapters.OnItemClickListener;
import com.alex.mvptesting.addnote.AddNoteActivity;
import com.alex.mvptesting.application.NotesApplication;
import com.alex.mvptesting.data.repository.NotesRepositoryImpl;
import com.alex.mvptesting.data.source.local.NoteLocalDataSource;
import com.alex.mvptesting.entities.Note;
import com.alex.mvptesting.notedetails.NoteDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NotesActivity extends BaseActivity implements NotesContract.View {

    private static final int REQUEST_CODE_ADD_NOTE = 1001;

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.rv_notes)
    RecyclerView rvNotes;

    private NotesContract.UserActionsListener notesPresenter;

    private GridLayoutManager gridLayoutManager;
    private NotesRecyclerViewAdapter rvNotesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesPresenter = new NotesPresenter(
                this,
                new NotesRepositoryImpl(
                        new NoteLocalDataSource(NotesApplication.appDatabase.noteDao())
                )
        );

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//            getSupportActionBar().setHomeButtonEnabled(false);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        gridLayoutManager = new GridLayoutManager(this, 2);
        rvNotes.setLayoutManager(gridLayoutManager);

        notesPresenter.loadNotes();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADD_NOTE:
                    // reload notes
                    notesPresenter.loadNotes();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.fab_add_note)
    public void onViewClicked() {
        notesPresenter.showAddNoteActivity();
    }

    @Override
    public void showNotes(List<Note> notes) {
        if (rvNotesAdapter == null) {
            rvNotesAdapter = new NotesRecyclerViewAdapter(notes);
            rvNotes.setAdapter(rvNotesAdapter);

            rvNotesAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    notesPresenter.showNoteDetailsActivity(
                            rvNotesAdapter.getNotes().get(position).getId()
                    );
                }
            });
        } else {
            rvNotesAdapter.updateNotes(notes);
        }
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.error_occurred_try_later_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddNoteActivity() {
        startActivityForResult(
                new Intent(this, AddNoteActivity.class),
                REQUEST_CODE_ADD_NOTE
        );
    }

    @Override
    public void showNoteDetailsActivity(Integer noteId) {
        Intent intentNoteDetails = new Intent(this, NoteDetailsActivity.class);
        intentNoteDetails.putExtra(NoteDetailsActivity.INTENT_KEY_NOTE_ID, noteId);
        startActivity(intentNoteDetails);
    }
}