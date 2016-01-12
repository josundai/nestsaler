package com.creal.nestsaler.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.creal.nestsaler.R;

public class ErrorAdapter extends ArrayAdapter<String> {
    public ErrorAdapter(Context context, int resource) {
        super(context, resource, new String[]{"nothing"});
    }
    public ErrorAdapter(Context context) {
        super(context, 0, new String[]{"nothing"});
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(R.layout.view_list_item_error, parent, false);
    }
}