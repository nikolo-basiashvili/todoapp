package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class todoList extends AppCompatActivity implements FirestoreAdapter.OnListItemClicked {

    private FirestoreAdapter adapter;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);


        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.todoAdapterList);

        Intent i = getIntent();
        email = i.getStringExtra("userEmail");

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(3)
                .build();

        Query query = fireStore.collection("todos")
                .orderBy("isDone")
                .orderBy("title")
                .whereEqualTo("authorEmail", email);
        FirestorePagingOptions<ToDo> options = new FirestorePagingOptions.Builder<ToDo>()
                .setQuery(query, config, snapshot -> {
                    ToDo t = snapshot.toObject(ToDo.class);
                    String id = snapshot.getId();
                    assert t != null;
                    t.setUid(id);
                    t.setIfDone(snapshot.getBoolean("isDone"));
                    return t;
                })
                .build();

        adapter = new FirestoreAdapter(options, this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void goBackFromTodoList(View view){
        Intent i = new Intent(this, Profile.class);
        startActivity(i);
    }

    @Override
    public void onItemClicked(DocumentSnapshot t, int position) {
        Log.i("CHECK", "onItemClicked: ID: " + t.getId());

        Intent i = new Intent(this, TodoDetail.class);
        i.putExtra("uid", t.getId());
        i.putExtra("title", Objects.requireNonNull(t.get("title")).toString());
        i.putExtra("content", Objects.requireNonNull(t.get("content")).toString());
        i.putExtra("email", Objects.requireNonNull(t.get("authorEmail")).toString());
        i.putExtra("isDone", Boolean.parseBoolean(Objects.requireNonNull(t.get("isDone")).toString()));
        startActivity(i);
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }
}

