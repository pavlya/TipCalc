package com.tbg.tipcalc;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pavlya on 30/10/2015.
 */
public class TipAdapter extends ArrayAdapter<FoodItem> {
    private ArrayList<FoodItem> items;
    private MainActivityFragment activityFragment;
    public TipAdapter(Context context, int resource) {
        super(context, resource);
    }

    public TipAdapter(Context context, int textViewResourceId, ArrayList<FoodItem> items, MainActivityFragment mainActivityFragment){
        super(context, textViewResourceId, items);
        this.items = items;
        this.activityFragment = mainActivityFragment;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View myView = convertView;
        if(myView == null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.food_item, null);
        }


        FoodItem item = items.get(position);
        if (item != null){
            ImageButton btnRemove = (ImageButton)myView.findViewById(R.id.ib_remove);
            TextView tvItemPrice = (TextView)myView.findViewById(R.id.tv_item_price);
            tvItemPrice.setText(String.valueOf(item.getValue()));
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityFragment.removeItem(position);
                }
            });
//            etPrice.addTextChangedListener(new TipAdapTerTextWatcher());
        }

        return myView;
    }

}
