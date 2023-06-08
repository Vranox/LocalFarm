package com.example.localfarm.adapteur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.localfarm.R;

public class ProfileAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final int[] icons;

    public ProfileAdapter(Context context, String[] values, int[] icons) {
        super(context, R.layout.list_profile, values);
        this.context = context;
        this.values = values;
        this.icons = icons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_profile, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(values[position]);
        imageView.setImageResource(icons[position]);
        return rowView;
    }
}
