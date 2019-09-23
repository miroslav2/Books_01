package com.example.books_0_00_1.castomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.books_0_00_1.R;

import java.util.ArrayList;

public class BoxAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater layoutInflater;
    private ArrayList<Book_item> objects;

    public BoxAdapter(Context context, ArrayList<Book_item> book_items){
        this.ctx = context;
        this.objects = book_items;
        this.layoutInflater = (LayoutInflater) ctx
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = layoutInflater.inflate(R.layout.item, parent, false);

            Book_item book_item = getBook_item(position);

            ((TextView) view.findViewById(R.id.tv_name)).setText(book_item.getName());
            ((TextView) view.findViewById(R.id.tv_author)).setText(book_item.getAuthor());
            ((TextView) view.findViewById(R.id.tv_genr)).setText(book_item.getGenr());
            ((TextView) view.findViewById(R.id.tv_year)).setText(book_item.getYear().toString());
            ((TextView) view.findViewById(R.id.tv_description)).setText(book_item.getDescription());

        }
        return view;
    }

    private Book_item getBook_item(int position){
        return ((Book_item) getItem(position));
    }

}
// lol