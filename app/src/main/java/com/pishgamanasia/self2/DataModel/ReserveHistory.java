package com.pishgamanasia.self2.DataModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pishgamanasia.self2.R;

/**
 * Created by ashkan on 1/27/2015.
 */
public class ReserveHistory {


        String restaurant;
        String planning;
        String meal;
        String date;
        String paymentType;
        String foods;
        String deliveryStatus;


    public ReserveHistory(String restaurant, String planning, String meal, String date, String paymentType, String foods, String deliveryStatus) {
        this.restaurant = restaurant;
        this.planning = planning;
        this.meal = meal;
        this.date = date;
        this.paymentType = paymentType;
        this.foods = foods;
        this.deliveryStatus = deliveryStatus;
    }

    public View getView(Context context, View oldView) {
        if (oldView == null || !(oldView.getTag() instanceof ReserveHistory)) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            oldView = inflater.inflate(R.layout.item_reservehistory, null);
            Holder holder = new Holder();
            oldView.setTag(holder);
            getItem(holder, oldView);
            return oldView;
        } else {          Holder holder = (Holder) oldView.getTag();
            getItem(holder, oldView);
            return oldView;      }
    }

    private void getItem(Holder holder, View view) {
        holder.reservehistory = this;

        if (holder.restaurant == null)
            holder.restaurant = (TextView) view.findViewById(R.id.restaurant);

        if (holder.planning == null)
            holder.planning = (TextView) view.findViewById(R.id.planning);

        if (holder.meal == null)
            holder.meal = (TextView) view.findViewById(R.id.meal);

        if (holder.date == null)
            holder.date = (TextView) view.findViewById(R.id.date);

        if (holder.paymenttype == null)
            holder.paymenttype = (TextView) view.findViewById(R.id.paymenttype);

        if (holder.foods == null)
            holder.foods = (TextView) view.findViewById(R.id.foods);

        if (holder.deliverystatus == null)
            holder.deliverystatus = (TextView) view.findViewById(R.id.deliverystatus);


        holder.restaurant.setText(this.getRestaurant());
        holder.planning.setText(this.getPlanning());
        holder.meal.setText(this.getMeal());
        holder.date.setText(this.getDate());
        holder.paymenttype.setText(this.getPaymentType());
        holder.foods.setText(this.getFoods());
        holder.deliverystatus.setText(this.getDeliveryStatus());
    }

    public class Holder {
        TextView restaurant;
        TextView planning;
        TextView meal;
        TextView date;
        TextView paymenttype;
        TextView foods;
        TextView deliverystatus;

        ReserveHistory reservehistory;
    }





    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getPlanning() {
        return planning;
    }

    public void setPlanning(String planning) {
        this.planning = planning;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getFoods() {
        return foods;
    }

    public void setFoods(String foods) {
        this.foods = foods;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
