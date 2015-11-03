package com.tbg.tipcalc;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private TextView tvResult;
    private TextView tvTipPercent;
    private SeekBar percentSeekBar;
    private ListView lvItems;
    private ArrayList<FoodItem> foodItems;
    private TipAdapter tipAdapter;
    private ArrayAdapter<String> testAdapter;
    private final static int MAX_ITEMS_COUNT = 4;
    private List<String> testStrings;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_main, container, false);
        tvResult = (TextView) parentView.findViewById(R.id.tv_summary);
        percentSeekBar = (SeekBar)parentView.findViewById(R.id.sbar_percent);
        percentSeekBar.setOnSeekBarChangeListener(new PercentSeekBarChangeListener());
        lvItems = (ListView)parentView.findViewById(R.id.lv_items);
        foodItems = new ArrayList<>();
        // add first entry
        foodItems.add(new FoodItem());
        foodItems.add(new FoodItem());
        testStrings = new ArrayList<>();
        testStrings.add("Hello");
        tvTipPercent = (TextView)parentView.findViewById(R.id.tv_tip_percent);
        // some tricks with layouts
//        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        RelativeLayout relativeLayout = (RelativeLayout)parentView.findViewById(R.id.rl_list_container);
        View linearLayout = parentView.findViewById(R.id.ll_results);
        lvItems.addFooterView(linearLayout);
        tipAdapter = new TipAdapter(getActivity(),
                R.layout.food_item, foodItems, this);
//        testAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, testStrings);
//        lvItems.setAdapter(testAdapter);
//        lvItems.setAdapter(tipAdapter);
        updateTipPercentView();
        return parentView;
    }

    private void updateTipPercentView() {
        String percentString = percentSeekBar.getProgress() + " %";
        tvTipPercent.setText(percentString);
    }

    public void testAddArrayItemAndUpdateArrayAdapter() {
        testStrings.add("Hello");
        testAdapter.notifyDataSetChanged();
    }

    public void addArrayItemAndUpdateArrayAdapter() {
        addFoodItem();
        tipAdapter = new TipAdapter(getActivity(),
                    R.layout.food_item, foodItems, this);
        lvItems.setAdapter(tipAdapter);
        Log.d("MyApp", "MainActivityFragment.addArrayItemAndUpdateArrayAdapter");
    }

    private void addFoodItem(){
        foodItems.add(new FoodItem());
    }

    private class PercentSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            calculatePercent();
            updateTipPercentView();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    public void calculatePercent() {
        // TODO iterate over all items in adapter and calculate overall tip value
        // TODO implement textWatcher in adapter

        float totalCheck = 0;
        float totalToPay = 0;
        float totalTip = 0;

        if(totalToPay <= 0){
            tvResult.setText("Enter rhe check value");
        } else {
            tvResult.setText("You should pay: " + totalToPay);
        }
    }

    private void updateFoodItemsList() {

    }

    public void updateItemPrice(){
        // TODO iterate over all items in list view and get the value of the item
        calculatePercent();

    }
}
