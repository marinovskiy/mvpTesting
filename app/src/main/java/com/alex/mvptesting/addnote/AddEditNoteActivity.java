package com.alex.mvptesting.addnote;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alex.mvptesting.R;
import com.alex.mvptesting.BaseActivity;
import com.alex.mvptesting.application.NotesApplication;
import com.alex.mvptesting.data.repository.NotesRepositoryImpl;
import com.alex.mvptesting.data.source.local.NoteLocalDataSource;
import com.alex.mvptesting.entities.Note;

import butterknife.BindView;

public class AddEditNoteActivity extends BaseActivity implements AddEditNoteContract.View {

    public static final String INTENT_KEY_NOTE_ID = "intent_key_note_id";

    @BindView(R.id.toolbar_add_edit_note)
    Toolbar toolbar;
    @BindView(R.id.ll_note_data)
    LinearLayout llNoteData;
    @BindView(R.id.et_note_title)
    EditText etNoteTitle;
    @BindView(R.id.et_note_text)
    EditText etNoteText;
    @BindView(R.id.pb_add_edit_note)
    ProgressBar pbAddEditNote;

    private AddEditNoteContract.Presenter presenter;

    private Integer noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        presenter = new AddEditNotePresenter(
                new NotesRepositoryImpl(
                        new NoteLocalDataSource(NotesApplication.appDatabase.noteDao())
                )
        );
        presenter.attach(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        noteId = getIntent().getIntExtra(INTENT_KEY_NOTE_ID, -1);

        if (noteId != -1) {
            presenter.getNote(noteId);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
            case R.id.action_add_note:
                if (noteId == -1) {
                    presenter.saveNote(getNoteTitle(), getNoteText());
                } else {
                    presenter.updateNote(noteId, getNoteTitle(), getNoteText());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setProgressIndicator(boolean active) {
        pbAddEditNote.setVisibility(active ? View.VISIBLE : View.GONE);
        llNoteData.setVisibility(active ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showEmptyNoteError() {
        Toast.makeText(this, R.string.empty_note_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeAddEditNoteActivity() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMissingNote() {
        etNoteTitle.setEnabled(false);
        etNoteText.setEnabled(false);

        etNoteText.setText("No data");
    }

    @Override
    public void showNote(Note note) {
        etNoteTitle.setText(note.getTitle());
        etNoteText.setText(note.getText());
    }

    private String getNoteTitle() {
        return etNoteTitle.getText().toString();
    }

    private String getNoteText() {
        return etNoteText.getText().toString();
    }
}