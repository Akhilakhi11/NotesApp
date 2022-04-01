package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class notedetails extends AppCompatActivity {


    @BindView(R.id.titleofnotedetail)
    TextView mtitleofnotedetail;
    @BindView(R.id.contentofnotedetail)
    TextView mcontentofnotedetail;
    @BindView(R.id.gotoeditnote)
    FloatingActionButton mgotoeditnote;
    @BindView(R.id.gotonotesactivity)
    FloatingActionButton mgotoenotesactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notedetails);
        ButterKnife.bind(this);

        Intent data = getIntent();

        mgotoenotesactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(notedetails.this,notesactivity.class);
                startActivity(intent1);
            }
        });

        mgotoeditnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),editnoteactivity.class);
                intent.putExtra("title",data.getStringExtra("title"));
                intent.putExtra("content",data.getStringExtra("content"));
                intent.putExtra("noteId",data.getStringExtra("noteId"));
                view.getContext().startActivity(intent);
            }
        });

        mcontentofnotedetail.setText(data.getStringExtra("content"));
        mtitleofnotedetail.setText(data.getStringExtra("title"));
    }
}