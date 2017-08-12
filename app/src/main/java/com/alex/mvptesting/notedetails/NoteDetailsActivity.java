package com.alex.mvptesting.notedetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.mvptesting.BaseActivity;
import com.alex.mvptesting.R;
import com.alex.mvptesting.addnote.AddEditNoteActivity;
import com.alex.mvptesting.application.NotesApplication;
import com.alex.mvptesting.data.repository.NotesRepositoryImpl;
import com.alex.mvptesting.data.source.local.NoteLocalDataSource;
import com.alex.mvptesting.dialogs.MyDialogFragment;
import com.alex.mvptesting.entities.Note;

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

    private NoteDetailsContract.Presenter presenter;

    private Integer noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        presenter = new NoteDetailsPresenter(
                new NotesRepositoryImpl(
                        new NoteLocalDataSource(NotesApplication.appDatabase.noteDao())
                )
        );
        presenter.attach(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        noteId = getIntent().getIntExtra(INTENT_KEY_NOTE_ID, -1);

        presenter.getNote(noteId);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case RequestCodes.REQUEST_CODE_EDIT_NOTE:
////                    presenter.getNote(noteId);
//                    break;
//            }
//        }
//    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
            case R.id.action_edit_note:
                Intent editNoteIntent = new Intent(this, AddEditNoteActivity.class);
                editNoteIntent.putExtra(AddEditNoteActivity.INTENT_KEY_NOTE_ID, noteId);
                startActivity(editNoteIntent);
                return true;
            case R.id.action_delete_note:
                showDeleteNoteDialog();
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
    public void showDeletedSuccessfulMessageAndCloseNoteDetailsActivity() {
        Toast.makeText(this, "Note has been deleted", Toast.LENGTH_SHORT).show();

        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showMissingNote() {
        tvNoteTitle.setText("");
        tvNoteText.setText(R.string.no_data);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showDeleteNoteDialog() {
        MyDialogFragment myDialogFragment = MyDialogFragment.newInstance(
                getString(R.string.want_to_delete_note),
                getString(R.string.yes),
                getString(R.string.no),
                new MyDialogFragment.OnClickListener() {
                    @Override
                    public void onPositiveButtonClick() {
                        presenter.deleteNoteById(noteId);
                    }

                    @Override
                    public void onNegativeButtonClick() {
                        // just ignore
                    }
                }
        );

        getSupportFragmentManager().beginTransaction()
                .add(myDialogFragment, MyDialogFragment.TAG)
                .commitAllowingStateLoss();
    }
}