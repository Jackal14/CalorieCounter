package com.example.caloriecounter.model;

import java.io.Serializable;

//Class that contains the data for what defines a food

public class Food implements Serializable {

    private String foodName, recordDate;
    private int calories, foodId;
    private static final long serialVersionUID = 10L;


    public Food() {
    }

    public Food(String foodName, int calories) {
        this.foodName = foodName;
        this.calories = calories;
    }

    public Food(String foodName, String recordDate, int calories, int foodId) {
        this.foodName = foodName;
        this.recordDate = recordDate;
        this.calories = calories;
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public static long getSerialVersionID() {
        return serialVersionUID;
    }
}
