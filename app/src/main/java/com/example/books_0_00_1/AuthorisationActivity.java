package com.example.books_0_00_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.books_0_00_1.castomAdapter.Book_item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthorisationActivity extends AppCompatActivity implements View.OnClickListener {

    protected FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText et_email;
    private EditText et_password;

    private Button btn_signed_in;
    private Button btn_registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    StartActivity();
                } else {
                    // User is signed out
                }
            }
        };

        et_email = (EditText) findViewById(R.id.et_E_mail);
        et_password = (EditText) findViewById(R.id.et_Password);

        btn_signed_in = (Button) findViewById(R.id.btn_signed_in);
        btn_registration = (Button) findViewById(R.id.btn_registration);

        btn_signed_in.setOnClickListener(this);
        btn_registration.setOnClickListener(this);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            StartActivity();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_signed_in){
            if((et_email.getText().length() != 0) && (et_password.getText().length() != 0)) {
                signing(et_email.getText().toString(), et_password.getText().toString());
            } else {
                Toast.makeText(AuthorisationActivity.this, "Authorisation not successful", Toast.LENGTH_LONG).show();
            }
        } else if (view.getId() == R.id.btn_registration){
            if ((et_email.getText().length() != 0) && (et_password.getText().length() != 0)) {
                registration(et_email.getText().toString(), et_password.getText().toString());
            } else {
                Toast.makeText(AuthorisationActivity.this, "Registration not successful", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void signing(final String email, final String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AuthorisationActivity.this, "Authorisation successful", Toast.LENGTH_LONG).show();
                    StartActivity();
                } else {
                    Toast.makeText(AuthorisationActivity.this, "Authorisation not successful", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void registration(final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AuthorisationActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                    StartActivity();
                } else {
                    Toast.makeText(AuthorisationActivity.this, "Registration not successful", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void StartActivity(){
        Intent intent = new Intent(AuthorisationActivity.this, ReadActivity.class);
        startActivity(intent);
    }
}
//
