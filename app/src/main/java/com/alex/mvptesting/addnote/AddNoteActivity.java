package com.alex.mvptesting.addnote;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.alex.mvptesting.R;
import com.alex.mvptesting.activities.BaseActivity;
import com.alex.mvptesting.application.NotesApplication;
import com.alex.mvptesting.data.repository.NotesRepositoryImpl;
import com.alex.mvptesting.data.source.local.NoteLocalDataSource;

import butterknife.BindView;

public class AddNoteActivity extends BaseActivity implements AddNoteContract.View {

    @BindView(R.id.toolbar_add_note)
    Toolbar toolbar;
    @BindView(R.id.et_note_title)
    EditText etNoteTitle;
    @BindView(R.id.et_note_text)
    EditText etNoteText;

    private AddNoteContract.UserActionsListener addNotePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        addNotePresenter = new AddNotePresenter(
                this,
                new NotesRepositoryImpl(
                        new NoteLocalDataSource(NotesApplication.appDatabase.noteDao())
                )
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
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
                addNotePresenter.saveNote(getNoteTitle(), getNoteText());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showEmptyNoteError() {
        Toast.makeText(this, R.string.empty_note_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeAddNoteActivity() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showError(String message) {
//        Toast.makeText(this, R.string.error_occurred_try_later_msg, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String getNoteTitle() {
        return etNoteTitle.getText().toString();
    }

    private String getNoteText() {
        return etNoteText.getText().toString();
    }
}