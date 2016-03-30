package com.example.mauro.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mauro on 20/03/2016.
 */
public class ItemAdapter extends ArrayAdapter<Task> {
    private LayoutInflater inflater;

    //Constructor
    public ItemAdapter(Context context, int resource, List<Task> task) {
        super(context, resource, task);

        //Initialize the layout inflater
        inflater = LayoutInflater.from(getContext());
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder;

        if (row == null){

            row = inflater.inflate(R.layout.row_item, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.populateRow(getItem(position));

        return row;
    }

}

