package com.pishgamanasia.self2.DataModel;

/**
 * Created by parsa on 2015-01-24.
 */
public class Basket {
    String date;
    String PlanningId;
    String FoodId;
    int Count;

    public Basket(String date, String planningId, String foodId, int count) {
        this.date = date;
        PlanningId = planningId;
        FoodId = foodId;
        Count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlanningId() {
        return PlanningId;
    }

    public void setPlanningId(String planningId) {
        PlanningId = planningId;
    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
