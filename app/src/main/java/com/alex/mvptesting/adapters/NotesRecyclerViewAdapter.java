package com.alex.mvptesting.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex.mvptesting.R;
import com.alex.mvptesting.entities.Note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesRecyclerViewAdapter extends
        RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteViewHolder> {

    private OnItemClickListener onItemClickListener;

    private List<Note> notes = new ArrayList<>();

    public NotesRecyclerViewAdapter(List<Note> notes) {
        this.notes = notes;
    }

    public void updateNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.bindNote(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes != null ? notes.size() : 0;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_note_title)
        TextView tvNoteTitle;
        @BindView(R.id.tv_note_short_text)
        TextView tvNoteShortText;

        NoteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        }

        void bindNote(Note note) {
            if (note != null) {
                tvNoteTitle.setText(note.getTitle());
                tvNoteShortText.setText(note.getText());
            }
        }
    }

    public List<Note> getNotes() {
        return notes != null ? notes : Collections.emptyList();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}