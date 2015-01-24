package com.pishgamanasia.self2.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.pishgamanasia.self2.Interface.IListViewItem;

import java.util.List;
import java.util.Objects;

/**
 * Created by ashkan on 11/15/2014.
 */
public class ListViewObjectAdapter<T> extends ArrayAdapter<T> {




    private Context context;
    private List<T> items;

    public ListViewObjectAdapter(Context context, List<T> items) {
        super(context, 0);
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(items.get(position) instanceof IListViewItem) {
            IListViewItem item = (IListViewItem) items.get(position);
            return item.getView(context, view);
        }

        return new View(context);
    }

    public void setSelectedItem(int position){

        for (int i = 0; i < items.size(); i++) {
            ( (IListViewItem) items.get(i)).setSelected(false);
        }

        try {
            ((IListViewItem) items.get(position)).setSelected(true);
        }catch (Exception e){

        }

    }


}