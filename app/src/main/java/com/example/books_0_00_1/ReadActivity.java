package com.example.books_0_00_1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.books_0_00_1.castomAdapter.Book_item;
import com.example.books_0_00_1.castomAdapter.BoxAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_all_books;
    private Button btn_books_for_me;
    private Button btn_my_books;
    private byte location;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private ListView lv_books;
    private ArrayList<Book_item> books_name;
    private ArrayList<Book_item> my_books_name;
    private Book_item my_book;
    private String name, author, genr, description;
    private Integer year, id;
    private BoxAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        btn_all_books = (Button) findViewById(R.id.btn_all_books);
        btn_my_books = (Button) findViewById(R.id.btn_my_books);

        btn_all_books.setOnClickListener(this);
        btn_my_books.setOnClickListener(this);

        books_name = new ArrayList<>();

        lv_books = (ListView) findViewById(R.id.lv_books);

        location = 0;

        mAuth = FirebaseAuth.getInstance();

        myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("AllBooks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Create_Books_Name(ds);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        UpdateUI(books_name);
    }

    @Override
    public void onClick(View view) {
        if ((view.getId() == R.id.btn_all_books) && (location != 0)){
            myRef = FirebaseDatabase.getInstance().getReference().child("AllBooks");
            myRef.child("AllBooks").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        Create_Books_Name(ds);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
            UpdateUI(books_name);
            location = 0;
        } else if ((view.getId() == R.id.btn_my_books) && (location != 2)){

            UpdateUI(my_books_name);
            location = 2;
        }
    }

    private void Create_Books_Name (DataSnapshot _ds){
        name = _ds.child("Name").getValue(String.class);

        author = _ds.child("Author").getValue(String.class);

        genr = _ds.child("Genr").getValue(String.class);

        description = _ds.child("Description").getValue(String.class);

        year = _ds.child("Year").getValue(Integer.class);

        id = _ds.child("Id").getValue(Integer.class);

        books_name.add(new Book_item(name, author, genr, description, id, year));
    }

    private void UpdateUI(ArrayList<Book_item> books){
        adapter = new BoxAdapter(this, books);
        lv_books.setAdapter(adapter);
    }
}
