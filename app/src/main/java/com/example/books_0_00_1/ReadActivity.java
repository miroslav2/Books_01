package com.example.books_0_00_1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReadActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btn_search, btn_sort;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private ListView lv_books;
    private ArrayList<Book_item> books_name, books_name_search;
   // private Book_item my_book;
    private String name, author, genr, description;
    private Integer year, id, log;
    private BoxAdapter adapter;
    private EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        btn_search = (ImageButton) findViewById(R.id.btn_search);
        btn_sort = (ImageButton) findViewById(R.id.btn_sort);
        et_search = (EditText) findViewById(R.id.et_search);

        btn_search.setOnClickListener(this);
        btn_sort.setOnClickListener(this);

        books_name = new ArrayList<>();
        books_name_search = new ArrayList<>();

        log = 0;

        lv_books = (ListView) findViewById(R.id.lv_books);

        mAuth = FirebaseAuth.getInstance();

        myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("AllBooks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Create_Books_Name(ds);
                }
                log = 1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        UpdateUI(books_name);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_search && log == 1){
            books_name_search.clear();
            for (Book_item s : books_name) {
                if (s.getName().contains(et_search.getText())) {
                    books_name_search.add(s);
                }
            }
            UpdateUI(books_name_search);
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

    public void sort() {
        Collections.sort(books_name, new Comparator<Book_item>() {
            @Override
            public int compare(Book_item book_item, Book_item t1) {
               return book_item.getName().compareToIgnoreCase(t1.getName());
            }
        });
    }
}
