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
    private Book_item book;
    private Book_item my_book;
    private String name, author, genr, description;
    private int year, id;
    private boolean like;
    private DataSnapshot ds;
    private BoxAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        btn_all_books = (Button) findViewById(R.id.btn_all_books);
        btn_books_for_me = (Button) findViewById(R.id.btn_books_for_me);
        btn_my_books = (Button) findViewById(R.id.btn_my_books);

        btn_all_books.setOnClickListener(this);
        btn_books_for_me.setOnClickListener(this);
        btn_my_books.setOnClickListener(this);

        books_name = new ArrayList<>();

        lv_books = (ListView) findViewById(R.id.lv_books);

        location = 0;

        mAuth = FirebaseAuth.getInstance();

        myRef = FirebaseDatabase.getInstance().getReference();

        user = mAuth.getCurrentUser();

        myRef.child(user.getUid());
        myRef.child("AllBooks");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                ds = dataSnapshot.child(String.valueOf(t));

                name = String.valueOf(ds.child("Name").getValue());

                author = String.valueOf(ds.child("Author").getValue());

                genr = String.valueOf(ds.child("Genr").getValue());

                description = String.valueOf(ds.child("Description").getValue());

                year = (int) ds.child("Year").getValue();

                like = (boolean) ds.child("Like").getValue();

                id = (int) ds.child("Id").getValue();

                books_name.add(book = new Book_item(name, author, genr, description, like, id, year));

                UpdateUI(books_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    @Override
    public void onClick(View view) {
        if ((view.getId() == R.id.btn_all_books) && (location != 0)){
            myRef = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("AllBooks");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                    ds = dataSnapshot.child(String.valueOf(t));

                    name = String.valueOf(ds.child("Name").getValue());

                    author = String.valueOf(ds.child("Author").getValue());

                    genr = String.valueOf(ds.child("Genr").getValue());

                    description = String.valueOf(ds.child("Description").getValue());

                    year = (int) ds.child("Year").getValue();

                    like = (boolean) ds.child("Like").getValue();

                    id = (int) ds.child("Id").getValue();

                    books_name.add(book = new Book_item(name, author, genr, description, like, id, year));

                    UpdateUI(books_name);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });

            location = 0;
        } else if ((view.getId() == R.id.btn_books_for_me) && (location != 1)){
        } else if ((view.getId() == R.id.btn_my_books) && (location != 2)){
            for (Book_item bookItem: adapter.get_Like()){
                if(bookItem.getLike()){
                    my_books_name.add(my_book = new Book_item(bookItem.getName(),bookItem.getAuthor(),bookItem.getGenr(),bookItem.getDescription(),bookItem.getLike(), bookItem.getId(),bookItem.getYear()));
                    myRef = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("AllBooks").child(String.valueOf(bookItem.getId())).child("Like");
                    myRef.setValue(true);
                }
            }
            UpdateUI(my_books_name);
            location = 2;
        }
    }

    private void UpdateUI(ArrayList<Book_item> books){
        adapter = new BoxAdapter(this, books);
        lv_books.setAdapter(adapter);
    }
}
