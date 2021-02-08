package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import static android.provider.SyncStateContract.Helpers.update;

public class TodoDetail extends AppCompatActivity {

    private Button doneBtn;
    private String email;
    private String uid;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        Intent i = getIntent();
        db = FirebaseFirestore.getInstance();

        String title = i.getStringExtra("title");
        String content = i.getStringExtra("content");
        email = i.getStringExtra("email");
        uid = i.getStringExtra("uid");
        boolean isDone = i.getBooleanExtra("isDone", false);

        TextView titleView = findViewById(R.id.title);
        titleView.setText(title);
        TextView contentView = findViewById(R.id.content);
        contentView.setText(content);
        doneBtn = findViewById(R.id.doneBtn);

        if(isDone) {
            doneBtn.setVisibility(View.GONE);
        }

    }

    public void goBackFromDetail(View view){
        Intent i = new Intent(this, todoList.class);
        i.putExtra("userEmail", email);
        startActivity(i);
    }

    public void setTodoDone(View view){
        Map<String, Object> todoDone = new HashMap<>();
        todoDone.put("isDone", true);

        db.collection("todos").document(uid)
                .update(todoDone)
                .addOnSuccessListener(aVoid -> Log.i("INFO", "onSuccess: changed"))
                .addOnFailureListener(e -> Log.i("INFO", "onFailure: not changed"));
        doneBtn.setBackgroundColor(Color.GRAY);
        Toast.makeText(this, "TODO updated", Toast.LENGTH_LONG).show();
        goBackFromDetail(view);
    }

    public void deleteTodo(View view){
        db.collection("todos").document(uid)
                .delete()
                .addOnSuccessListener(aVoid -> Log.i("INFO", "onSuccess: deleted"))
                .addOnFailureListener(e -> Log.i("INFO", "onFailure: not deleted"));
        Toast.makeText(this, "TODO deleted", Toast.LENGTH_LONG).show();
        goBackFromDetail(view);
    }
}