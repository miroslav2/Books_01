package com.example.books_0_00_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class WaitActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef_1, myRef_2;
    private String name, author, genr, description;
    private int year;
    private DataSnapshot ds;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        mAuth = FirebaseAuth.getInstance();

        myRef_1 = FirebaseDatabase.getInstance().getReference();
        myRef_2 = FirebaseDatabase.getInstance().getReference();

        user = mAuth.getCurrentUser();

        myRef_2.child(user.getUid());

        myRef_2.setValue("AllBooks");

        myRef_2.child("AllBooks");

        myRef_1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                myRef_2.push().setValue(t);
                myRef_2.child(String.valueOf(t));
                dataSnapshot.child(String.valueOf(t));
                ds = dataSnapshot;
                name = String.valueOf(dataSnapshot.child("Name").getValue());
                myRef_2.setValue("Name");
                myRef_2.child("Name").setValue(name);
                myRef_2 = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("AllBooks").child(String.valueOf(t));
                dataSnapshot = ds;
                author = String.valueOf(dataSnapshot.child("Author").getValue());
                myRef_2.setValue("Author");
                myRef_2.child("Author").setValue(author);
                myRef_2 = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("AllBooks").child(String.valueOf(t));
                dataSnapshot = ds;
                genr = String.valueOf(dataSnapshot.child("Genr").getValue());
                myRef_2.setValue("Genr");
                myRef_2.child("Genr").setValue(genr);
                myRef_2 = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("AllBooks").child(String.valueOf(t));
                dataSnapshot = ds;
                description = String.valueOf(dataSnapshot.child("Description").getValue());
                myRef_2.setValue("Description");
                myRef_2.child("Description").setValue(description);
                myRef_2 = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("AllBooks").child(String.valueOf(t));
                dataSnapshot = ds;
                year = (int) dataSnapshot.child("Year").getValue();
                myRef_2.setValue("Year");
                myRef_2.child("Year").setValue(year);
                myRef_2 = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("AllBooks").child(String.valueOf(t));

                myRef_2.setValue("Id");
                myRef_2.child("Id").setValue(t);
                myRef_2 = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("AllBooks").child(String.valueOf(t));

                myRef_2.setValue("Like");
                myRef_2.child("Like").setValue(false);
                myRef_2 = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("AllBooks");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        Intent intent = new Intent(WaitActivity.this, ReadActivity.class);
        startActivity(intent);
    }
}
