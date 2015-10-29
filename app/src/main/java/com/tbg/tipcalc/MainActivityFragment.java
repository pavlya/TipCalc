package com.tbg.tipcalc;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    private ArrayAdapter arrayAdapter;
    private Button btnTest;
    private ArrayList<FoodItem> foodItems;
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
        etAmount.addTextChangedListener(new TipTextWatcher());
        lvItems = (ListView)parentView.findViewById(R.id.lv_items);
        wordsList = new ArrayList();
        wordsList.add("Hello");
        wordsList.add("Hello");
        wordsList.add("Hello");

        foodItems = new ArrayList<>();
        // add first entry
        foodItems.add(new FoodItem());
        arrayAdapter = new ArrayAdapter<FoodItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, foodItems);
        lvItems.setAdapter(arrayAdapter);
        btnTest = (Button) parentView.findViewById(R.id.btn_test);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordsList.add("Hello");
                arrayAdapter.notifyDataSetChanged();
            }
        });
        return parentView;
    }

    public void addFoodItem(){
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

    private void calculatePercent() {
        if(etAmount.getText().length() > 0) {
            float number = Float.parseFloat(String.valueOf(etAmount.getText()));
            float percent = 1 + (float) percentSeekBar.getProgress() / 100;

            float summary = number * percent;
            tvResult.setText("You should pay: " + summary);
        } else{
            tvResult.setText("Enter the check value.");
        }
    }
}
