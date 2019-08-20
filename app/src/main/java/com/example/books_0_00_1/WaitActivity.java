package com.example.books_0_00_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.books_0_00_1.castomAdapter.Book_item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

public class WaitActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef_1, myRef_2;
    private String name, author, genr, description;
    private int year, id;
    private boolean like;
    private DataSnapshot ds;
    private FirebaseUser user;
    private Map<Integer, Book_item> map_1;
    private Map<String, Map<Integer, Book_item>> map_2;
    private Book_item book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        mAuth = FirebaseAuth.getInstance();

        myRef_1 = FirebaseDatabase.getInstance().getReference();
        myRef_2 = FirebaseDatabase.getInstance().getReference();

        user = mAuth.getCurrentUser();

        myRef_1.child("AllBooks");
        myRef_2.child(user.getUid());

        myRef_1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                ds = dataSnapshot.child(String.valueOf(t));

                name = String.valueOf(ds.child("Name").getValue());

                author = String.valueOf(ds.child("Author").getValue());

                genr = String.valueOf(ds.child("Genr").getValue());

                description = String.valueOf(ds.child("Description").getValue());

                year = (int) ds.child("Year").getValue();

                id = (int) ds.child("Id").getValue();

                like = false;

                book = new Book_item(name, author, genr, description, like, id, year);
                map_1.put(id, book);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        map_2.put("AllBooks", map_1);

        myRef_2.setValue(map_2);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(WaitActivity.this, ReadActivity.class);
        startActivity(intent);
    }
}
