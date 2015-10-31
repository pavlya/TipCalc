package com.tbg.tipcalc;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private TextView textViewHello;
    private EditText etAmount;
    private TextView tvResult;
    private SeekBar percentSeekBar;
    private ListView lvItems;
    private ArrayList wordsList;
    private ArrayList<FoodItem> foodItems;
    private TipAdapter tipAdapter;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_main, container, false);
        textViewHello = (TextView) parentView.findViewById(R.id.tv_hello);
        etAmount = (EditText) parentView.findViewById(R.id.et_amount);
        tvResult = (TextView) parentView.findViewById(R.id.tv_summary);
        percentSeekBar = (SeekBar)parentView.findViewById(R.id.sbar_percent);
        percentSeekBar.setOnSeekBarChangeListener(new PercentSeekBarChangeListener());
//        etAmount.addTextChangedListener(new TipTextWatcher());
        lvItems = (ListView)parentView.findViewById(R.id.lv_items);
        foodItems = new ArrayList<>();
        // add first entry
        foodItems.add(new FoodItem());
        tipAdapter = new TipAdapter(getActivity(),
                R.layout.food_item, foodItems, this);
        lvItems.setAdapter(tipAdapter);
        tipAdapter = new TipAdapter(getActivity(),R.layout.food_item, foodItems, this );
        return parentView;
    }

    public void addArrayItemAndUpdateArrayAdapter() {
        addFoodItem();
//        tipAdapter.clear();
//        tipAdapter.addAll(foodItems);
//        tipAdapter.notifyDataSetChanged();
        tipAdapter = new TipAdapter(getActivity(),
                R.layout.food_item, foodItems, this);
        lvItems.setAdapter(tipAdapter);
    }

    private void addFoodItem(){
        foodItems.add(new FoodItem());
    }
    class TipTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            calculatePercent();
        }

    }

    private class PercentSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            calculatePercent();
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
//        if(etAmount.getText().length() > 0) {
//            float number = Float.parseFloat(String.valueOf(etAmount.getText()));
//            float percent = 1 + (float) percentSeekBar.getProgress() / 100;
//
//            float summary = number * percent;
//            tvResult.setText("You should pay: " + summary);
//        } else{
//            tvResult.setText("Enter the check value.");
//        }

        float totalCheck = 0;
        float totalToPay = 0;
        float totalTip = 0;
        for(FoodItem item: foodItems){
            totalCheck += item.getValue();
        }
        totalTip = totalCheck * ((float)percentSeekBar.getProgress() /100);
        totalToPay = totalCheck + totalTip;
        if(totalToPay <= 0){
            tvResult.setText("Enter rhe check value");
        } else {
            tvResult.setText("You should pay: " + totalToPay);
        }
    }

    public void updateItemPrice(int itemPosition, float price){
        this.foodItems.set(itemPosition, new FoodItem(price));
        calculatePercent();
    }
}
