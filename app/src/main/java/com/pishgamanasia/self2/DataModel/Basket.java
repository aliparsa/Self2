package com.pishgamanasia.self2.DataModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pishgamanasia.self2.Helper.PersianCalendar;
import com.pishgamanasia.self2.R;

/**
 * Created by parsa on 2015-01-24.
 */
public class Basket {

    MenuFood menuFood;

    String date;
    private int count;
    private double price;

    public Basket(MenuFood menuFood){
        this.menuFood = menuFood;

        PersianCalendar pc = new PersianCalendar();
        date = pc.getPersianWeekDayStr() + " " + pc.getIranianDate();
        count = 0;
        price = menuFood.calculatePrice();
    }


    public View getView(Context context, View oldView) {
        if (oldView == null || !(oldView.getTag() instanceof Basket)) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            oldView = inflater.inflate(R.layout.item_basket, null);
            Holder holder = new Holder();
            oldView.setTag(holder);
            getItem(holder, oldView);
            return oldView;
        } else {          Holder holder = (Holder) oldView.getTag();
            getItem(holder, oldView);
            return oldView;      }
    }

    private void getItem(Holder holder, View view) {
        holder.basket = this;  if (holder.date == null)
            holder.date = (TextView) view.findViewById(R.id.date);

        if (holder.count == null)
            holder.count = (TextView) view.findViewById(R.id.count);

        if (holder.price == null)
            holder.price = (TextView) view.findViewById(R.id.price);

        if (holder.mealcaption == null)
            holder.mealcaption = (TextView) view.findViewById(R.id.mealcaption);

        if (holder.foodcaption == null)
            holder.foodcaption = (TextView) view.findViewById(R.id.foodcaption);

        if (holder.planningcaption == null)
            holder.planningcaption = (TextView) view.findViewById(R.id.planningcaption);

        holder.date.setText(this.getDate());
        holder.count.setText(this.getCount());
        holder.price.setText(this.getPrice() + "");
        holder.mealcaption.setText(this.menuFood.getMealCaption());
        holder.foodcaption.setText(this.menuFood.getFoodCaption());
        holder.planningcaption.setText(this.menuFood.getPlanningCaption());
    }

    public class Holder {
        TextView date;
        TextView count;
        TextView price;
        TextView mealcaption;
        TextView foodcaption;
        TextView planningcaption;

        Basket basket;
    }





    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
