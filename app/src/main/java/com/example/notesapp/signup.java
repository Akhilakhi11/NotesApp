package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class signup extends AppCompatActivity {
    @BindView(R.id.signupemail)
    EditText msignupemail;
    @BindView(R.id.signuppassword)
    EditText msignuppassword;
    @BindView(R.id.signup)
    RelativeLayout msignup;
    @BindView(R.id.gotologin)
    TextView mgotologin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();


        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this,MainActivity.class);
                startActivity(intent);
            }
        });
        
        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                String mail = msignupemail.getText().toString().trim();
                String password = msignuppassword.getText().toString().trim();
                
                if (mail.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(signup.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }
                else if (password.length()<7)
                {
                    Toast.makeText(signup.this, "Password Should Greater than 7 Digits", Toast.LENGTH_SHORT).show();
                }
                else
                {
                     //firebase
                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(signup.this, "Resistration Successfull", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            }
                            else
                            {
                                Toast.makeText(signup.this, "Failed To Register", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
            }
        });

    }

    //mail verification
    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    Toast.makeText(signup.this, "Verification Email is Send, Verify and Log In Again", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(signup.this,MainActivity.class));
                }
            });
        }
        else
        {
            Toast.makeText(signup.this, "Failed To Send Verification Email", Toast.LENGTH_SHORT).show();
        }
    }


}