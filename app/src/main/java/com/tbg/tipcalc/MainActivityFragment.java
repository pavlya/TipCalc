package com.tbg.tipcalc;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private TextView tvTotalValue;
    private TextView tvBillValue;
    private TextView tvTipValue;
    private TextView tvTipPercent;
    private SeekBar percentSeekBar;
    private ListView lvItems;
    private ArrayList<FoodItem> foodItems;
    private TipAdapter tipAdapter;
    private ArrayAdapter<String> testAdapter;
    private final static int MAX_ITEMS_COUNT = 4;
    private List<String> testStrings;
    private EditText etAmount;
    private ImageButton ibAdd;
    private DecimalFormat decimalFormat;
    private int maxItemPriceLength;
    private String itemPriceLongValue;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_main, container, false);
        initViews(parentView);

        foodItems = new ArrayList<>();
        updateAdapter();
        etAmount.addTextChangedListener(new TipTextWatcher());
        updateTipPercentView();
        // Using to format values
        decimalFormat = new DecimalFormat("#.00");
        itemPriceLongValue = getResources().getString(R.string.item_price_length);
        return parentView;
    }

    private void initViews(View parentView) {
        tvTotalValue = (TextView) parentView.findViewById(R.id.tv_summary);
        tvBillValue = (TextView)parentView.findViewById(R.id.tv_bill);
        tvTipValue = (TextView)parentView.findViewById(R.id.tv_tip_value);
        percentSeekBar = (SeekBar)parentView.findViewById(R.id.sbar_percent);
        percentSeekBar.setOnSeekBarChangeListener(new PercentSeekBarChangeListener());
        lvItems = (ListView)parentView.findViewById(R.id.lv_items);
        etAmount = (EditText)parentView.findViewById(R.id.et_amount);
        tvTipPercent = (TextView)parentView.findViewById(R.id.tv_tip_percent);
        ibAdd = (ImageButton)parentView.findViewById(R.id.ib_addItem);
        ibAdd.setOnClickListener(new AddButtonClickListener());

        AdView mAdView = (AdView) parentView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
        addEmptyFoodItem();
        updateAdapter();
        Log.d("MyApp", "MainActivityFragment.addArrayItemAndUpdateArrayAdapter");
    }

    public void addArrayItemAndUpdateArrayAdapter(float price) {
        addEmptyFoodItem(price);
        updateAdapter();
    }

    private void updateAdapter() {
        tipAdapter = new TipAdapter(getActivity(),
                    R.layout.food_item, foodItems, this);
        lvItems.setAdapter(tipAdapter);
        calculatePercent();
    }

    private void addEmptyFoodItem(){
        foodItems.add(new FoodItem());
    }
    private void addEmptyFoodItem(float price){
        foodItems.add(new FoodItem(price));
    }

    public void removeItem(int position) {
        foodItems.remove(position);
        updateAdapter();
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
        for(FoodItem foodItem : foodItems){
            totalCheck += foodItem.getValue();
        }

        totalTip = (totalCheck * percentSeekBar.getProgress()) /100;
        totalToPay = totalCheck + totalTip;

        if(totalToPay <= 0){
            tvTotalValue.setText("0");
            tvTipValue.setText("0");
            tvBillValue.setText("0");
        } else {
            // Format values using decimal format
            tvTotalValue.setText(String.valueOf(decimalFormat.format(totalToPay)));
            tvBillValue.setText(String.valueOf(decimalFormat.format(totalCheck)));
            tvTipValue.setText(String.valueOf(decimalFormat.format(totalTip)));
        }
    }

    private void updateFoodItemsList() {

    }

    public void updateItemPrice(){
        // TODO iterate over all items in list view and get the value of the item
        calculatePercent();

    }

    private class AddButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String numberString = etAmount.getText().toString();
            float price;
            if(numberString != null && numberString.length() >0){
                price = Float.valueOf(numberString);
                maxItemPriceLength = 6;
                if(numberString.length() > maxItemPriceLength){
                    Toast.makeText(getContext(), itemPriceLongValue, Toast.LENGTH_SHORT ).show();
                }
                else if(price > 0){
                    addArrayItemAndUpdateArrayAdapter(price);
                    // empty EditText view
                    etAmount.setText("");
                }
            }

        }
    }

    private class TipTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
