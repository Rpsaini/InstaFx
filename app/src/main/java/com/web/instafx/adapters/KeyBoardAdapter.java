package com.web.instafx.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.web.instafx.R;

import java.util.ArrayList;

public class KeyBoardAdapter extends BaseAdapter {

    private AppCompatActivity context;
    private final ArrayList gridValues;

    public KeyBoardAdapter(AppCompatActivity context, ArrayList artistData) {

        this.context = context;
        this.gridValues = artistData;

    }

    @Override
    public int getCount() {
        return gridValues.size() ;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 10;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView = null;

        if (convertView == null) {

            gridView = new View(context);
            gridView = inflater.inflate(R.layout.keyboard_grid_layout, null);


        } else {

            gridView = (View) convertView;
        }

        TextView txt_gridtext=gridView.findViewById(R.id.txt_gridtext);
        ImageView back_button=gridView.findViewById(R.id.back_button);
//        DefaultConstatnts.setFont(context,txt_gridtext);
        if(position==10)
        {
            back_button.setImageResource(Integer.parseInt(gridValues.get(position).toString()));
            back_button.setVisibility(View.VISIBLE);
//            txt_gridtext.setTextColor(context.getResources().getColor(R.color.dark_red_color));
        }
        else
        {
            txt_gridtext.setText(gridValues.get(position)+"");
            back_button.setVisibility(View.GONE);
            txt_gridtext.setTextColor(context.getResources().getColor(R.color.black));
        }


        return gridView;
    }

}


