package com.alex.mvptesting.settings;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.alex.mvptesting.BaseActivity;
import com.alex.mvptesting.R;
import com.alex.mvptesting.application.NotesApplication;
import com.alex.mvptesting.data.repository.NotesRepositoryImpl;
import com.alex.mvptesting.data.source.local.NoteLocalDataSource;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements SettingsContract.View {

    @BindView(R.id.toolbar_settings)
    Toolbar toolbar;

    private SettingsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        presenter = new SettingsPresenter(
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
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                presenter.closeSettingsActivity();//TODO onBackPressed?
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.ll_delete_all_notes)
    public void onViewClicked() {
        presenter.deleteAllNotes();
    }

    @Override
    public void closeSettingsActivity() {
        finish();
    }

    @Override
    public void allNoteHaveBeenDeleted() {
        Toast.makeText(this, "All notes have been deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorDeletingNotes(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}