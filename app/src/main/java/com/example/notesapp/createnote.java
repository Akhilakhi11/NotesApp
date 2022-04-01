package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class createnote extends AppCompatActivity {

    @BindView(R.id.createtitleofnote)
    EditText mcreatetitleofnote;
    @BindView(R.id.createcontentofnote)
    EditText mcreatecontentofnote;
    @BindView(R.id.savenote)
    FloatingActionButton msavenote;
//    @BindView(R.id.visibilitynote)
//    FloatingActionButton mvisibilitynote;
    @BindView(R.id.backnote)
    FloatingActionButton mbacknote;
    @BindView(R.id.progressbarofcreatenote)
    ProgressBar mprogressbarofcreatenote;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);
        ButterKnife.bind(this);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore =FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mbacknote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(createnote.this,notesactivity.class));
            }
        });

        msavenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mcreatetitleofnote.getText().toString();
                String content = mcreatecontentofnote.getText().toString();
                if (title.isEmpty() || content.isEmpty())
                {
                    Toast.makeText(createnote.this, "Both fields are require", Toast.LENGTH_SHORT).show();
                }
                else
                {


                    mprogressbarofcreatenote.setVisibility(View.VISIBLE);

                    DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document();
                    Map<String, Object> note = new HashMap<>();
                    note.put("title",title);
                    note.put("content",content);


                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(createnote.this, "Note Created Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(createnote.this, notesactivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(createnote.this, "Failed To Create Note", Toast.LENGTH_SHORT).show();
                          //  startActivity(new Intent(createnote.this, notesactivity.class));

                        }
                    });

                }
            }
        });

    }
    }

