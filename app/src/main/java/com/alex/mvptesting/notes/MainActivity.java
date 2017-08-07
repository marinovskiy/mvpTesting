package com.alex.mvptesting.notes;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.alex.mvptesting.R;
import com.alex.mvptesting.activities.BaseActivity;
import com.alex.mvptesting.adapters.NotesRecyclerViewAdapter;
import com.alex.mvptesting.adapters.OnItemClickListener;
import com.alex.mvptesting.addnote.AddNoteActivity;
import com.alex.mvptesting.db.AppDatabase;
import com.alex.mvptesting.db.DatabaseInfo;
import com.alex.mvptesting.entities.Note;
import com.alex.mvptesting.model.NoteRepositoryImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements NotesContract.View {

    private static final int REQUEST_CODE_ADD_NOTE = 1001;

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.rv_notes)
    RecyclerView rvNotes;

    private NotesContract.UserActionsListener actionListener;

    private GridLayoutManager gridLayoutManager;
    private NotesRecyclerViewAdapter rvNotesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionListener = new NotesPresenter(this, new NoteRepositoryImpl(
                Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DatabaseInfo.DB_NAME).build()
        ));

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//            getSupportActionBar().setHomeButtonEnabled(false);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        gridLayoutManager = new GridLayoutManager(this, 2);
        rvNotes.setLayoutManager(gridLayoutManager);

        actionListener.loadNotes();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADD_NOTE:
                    // reload notes
                    actionListener.loadNotes();
                    break;
            }
        }
    }

    @OnClick(R.id.fab_add_note)
    public void onViewClicked() {
        actionListener.showAddNoteActivity();
    }

    @Override
    public void showNotes(List<Note> notes) {
        //TODO reuse adapter
        rvNotesAdapter = new NotesRecyclerViewAdapter(notes);
        rvNotes.setAdapter(rvNotesAdapter);

        rvNotesAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO start detail activity through presenter
            }
        });
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
}