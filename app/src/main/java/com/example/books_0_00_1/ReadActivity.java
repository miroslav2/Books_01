package com.example.books_0_00_1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.books_0_00_1.castomAdapter.Book_item;
import com.example.books_0_00_1.castomAdapter.BoxAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ReadActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btn_search;
    private Spinner sp_sort;
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
    private ArrayAdapter<String> arrayAdapter;
    private String[] sort_spinner = {"По названию (А --> Я)",
            "По названию (Я --> А)",
            "По году выхода (по возрастанию)",
            "По году выхода (по убыванию)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        btn_search = (ImageButton) findViewById(R.id.btn_search);
        et_search = (EditText) findViewById(R.id.et_search);
        sp_sort = (Spinner) findViewById(R.id.sp_sort);

        btn_search.setOnClickListener(this);

        books_name = new ArrayList<>();
        books_name_search = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,sort_spinner);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp_sort.setAdapter(arrayAdapter);

        sp_sort.setPrompt("Сортировка");

        log = 0;

        lv_books = (ListView) findViewById(R.id.lv_books);

        mAuth = FirebaseAuth.getInstance();

        myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("AllBooks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    createBooksName(ds);
                }
                log = 1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        sp_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                   sort_name_A_to_Z();
                } else if(position == 1){
                    sort_name_Z_to_A();
                } else if(position == 2){
                    sort_Year_1_to_10();
                } else if(position == 3){
                    sort_Year_10_to_1();
                }
                updateUI(books_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        updateUI(books_name);
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
            updateUI(books_name_search);
        }


    }

    private void createBooksName(DataSnapshot _ds){
        name = _ds.child("Name").getValue(String.class);

        author = _ds.child("Author").getValue(String.class);

        genr = _ds.child("Genr").getValue(String.class);

        description = _ds.child("Description").getValue(String.class);

        year = _ds.child("Year").getValue(Integer.class);

        id = _ds.child("Id").getValue(Integer.class);

        books_name.add(new Book_item(name, author, genr, description, id, year));
    }

    private void updateUI(ArrayList<Book_item> books){
        adapter = new BoxAdapter(this, books);
        lv_books.setAdapter(adapter);
    }

    public void sort_name_A_to_Z() {
        Collections.sort(books_name, new Comparator<Book_item>() {
            @Override
            public int compare(Book_item book_item, Book_item t1) {
                return book_item.getName().compareToIgnoreCase(t1.getName());
            }
        });
    }
    public void sort_name_Z_to_A() {
        Collections.sort(books_name, new Comparator<Book_item>() {
            @Override
            public int compare(Book_item t1, Book_item book_item) {
                return book_item.getName().compareToIgnoreCase(t1.getName());
            }
        });
    }
    public void sort_Year_1_to_10() {
        Collections.sort(books_name, new Comparator<Book_item>() {
            @Override
            public int compare(Book_item book_item, Book_item t1) {
                return book_item.getYear().compareTo(t1.getYear());
            }
        });
    }
    public void sort_Year_10_to_1() {
        Collections.sort(books_name, new Comparator<Book_item>() {
            @Override
            public int compare(Book_item t1, Book_item book_item) {
                return book_item.getYear().compareTo(t1.getYear());
            }
        });
    }
}
