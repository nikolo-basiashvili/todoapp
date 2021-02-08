package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class newTodo extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);
        db = FirebaseFirestore.getInstance();
    }

    public void goBackFromNewTodo(View view) {
        Intent i = new Intent(this, Profile.class);
        startActivity(i);
    }

    public void submitTodo(View view){
        Intent i = getIntent();
        String email = i.getStringExtra("userEmail");
        EditText titleView = findViewById(R.id.todoTitle);
        EditText contentView = findViewById(R.id.todoContent);
        String title = titleView.getText().toString();
        String content = contentView.getText().toString();
        if(title.length() == 0 || content.length() == 0) {
            Toast.makeText(this, "Please fill both title and content of your todo.", Toast.LENGTH_SHORT).show();
        }else {
            ToDo newToDo = new ToDo(title, content, email, false, null);
            Map<String, Object> todo = new HashMap<>();
            todo.put("title", newToDo.getTitle());
            todo.put("content", newToDo.getContent());
            todo.put("isDone", newToDo.isDone());
            todo.put("authorEmail", newToDo.getAuthorEmail());
            todo.put("uid", null);

            db.collection("todos")
                    .add(todo)
                    .addOnSuccessListener(documentReference -> Log.i("INFO", "user added with ID:" + documentReference.getId()))
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("INFO", "Error! couldn't add user.");
                        }
                    });
            Toast.makeText(this, "TODO Added", Toast.LENGTH_LONG).show();
            goBackFromNewTodo(view);
        }
    }
}