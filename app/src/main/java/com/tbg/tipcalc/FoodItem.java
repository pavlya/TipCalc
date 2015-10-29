package com.tbg.tipcalc;

/**
 * Created by Pavlya on 10/29/2015.
 */
public class FoodItem {
    private float value;

    public FoodItem(){
        this.value = 0;
    }

    public FoodItem(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
