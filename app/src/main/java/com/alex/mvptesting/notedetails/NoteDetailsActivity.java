package com.alex.mvptesting.notedetails;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.mvptesting.R;
import com.alex.mvptesting.activities.BaseActivity;
import com.alex.mvptesting.application.NotesApplication;
import com.alex.mvptesting.entities.Note;
import com.alex.mvptesting.data.NotesRepositoryImpl;

import butterknife.BindView;

public class NoteDetailsActivity extends BaseActivity implements NoteDetailsContract.View {

    public static final String INTENT_KEY_NOTE_ID = "intent_key_note_id";

    @BindView(R.id.toolbar_note_details)
    Toolbar toolbar;
    @BindView(R.id.ll_note_info)
    LinearLayout llNoteInfo;
    @BindView(R.id.tv_note_title)
    TextView tvNoteTitle;
    @BindView(R.id.tv_note_text)
    TextView tvNoteText;
    @BindView(R.id.pb_note_details)
    ProgressBar pbNoteDetails;

    private NoteDetailsContract.UserActionsListener noteDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        noteDetailsPresenter = new NoteDetailsPresenter(
                this,
                new NotesRepositoryImpl(NotesApplication.appDatabase)
        );

        Integer noteId = getIntent().getIntExtra(INTENT_KEY_NOTE_ID, -1);

        noteDetailsPresenter.getNote(noteId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setProgressIndicator(boolean active) {
        pbNoteDetails.setVisibility(active ? View.VISIBLE : View.GONE);
        llNoteInfo.setVisibility(active ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showNote(Note note) {
        tvNoteTitle.setText(note.getTitle());
        tvNoteText.setText(note.getText());
    }

    @Override
    public void showMissingNote() {
        tvNoteTitle.setText("");
        tvNoteText.setText("No data");
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}