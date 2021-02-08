package com.example.todoapp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.DocumentSnapshot;

public class FirestoreAdapter extends FirestorePagingAdapter<ToDo, FirestoreAdapter.todosViewHolder> {

    private OnListItemClicked onListItemClicked;

    public FirestoreAdapter(@NonNull FirestorePagingOptions<ToDo> options, OnListItemClicked onListItemClicked) {
        super(options);
        this.onListItemClicked = onListItemClicked;
    }

    @NonNull
    @Override
    public todosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_row, parent, false);
        return new todosViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull todosViewHolder holder, int position, @NonNull ToDo model) {
        holder.listItemTitle.setText(model.getTitle());
        Log.i("TEST", "onBindViewHolder: ALOOOOOOOOOOO "
                + model.isDone() + " "
                + model.getAuthorEmail() + " "
                + model.getContent() + " "
                + model.getTitle() + " "
                + model.getUid());
        if(model.isDone()) {
            (holder.listItemTitle.getBackground()).setColorFilter(Color.parseColor("#95f5a2"), PorterDuff.Mode.SRC_IN);
        }else{
            (holder.listItemTitle.getBackground()).setColorFilter(Color.parseColor("#8fd6ff"), PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state){
        super.onLoadingStateChanged(state);
        switch (state) {
            case LOADED:
                Log.i("INFO", "onLoadingStateChanged: loaded");
                break;
            case ERROR:
                Log.i("INFO", "onLoadingStateChanged: error");
                break;
        }
    }

    public class todosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView listItemTitle;

        public todosViewHolder(@NonNull View itemView) {
            super(itemView);
            listItemTitle = itemView.findViewById(R.id.todoTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemClicked.onItemClicked(getItem(getAdapterPosition()),getAdapterPosition());
        }
    }

    public interface OnListItemClicked {
        void onItemClicked(DocumentSnapshot t, int position);
    }




}
