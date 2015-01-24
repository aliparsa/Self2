package com.pishgamanasia.self2.DataModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pishgamanasia.self2.Interface.IListViewItem;
import com.pishgamanasia.self2.R;

/**
 * Created by parsa on 2015-01-21.
 */
public class Reserve implements IListViewItem {

    private int id;
    private String planningCaption;
    private String meal;
    private String restaurant;
    private String food;
    private boolean delivered;
    private boolean showCancel;
    private boolean showPrint;
    private boolean showPoll;

    public Reserve(int id, String planningCaption, String meal, String restaurant, String food, boolean delivered, boolean showCancel, boolean showPrint, boolean showPoll) {
        this.id = id;
        this.planningCaption = planningCaption;
        this.meal = meal;
        this.restaurant = restaurant;
        this.food = food;
        this.delivered = delivered;
        this.showCancel = showCancel;
        this.showPrint = showPrint;
        this.showPoll = showPoll;
    }



    @Override
    public void setSelected(boolean flag) {

    }


    public View getView(Context context, View oldView) {
        if (oldView == null || !(oldView.getTag() instanceof Reserve)) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            oldView = inflater.inflate(R.layout.item_reserve, null);
            Holder holder = new Holder();
            oldView.setTag(holder);
            getItem(holder, oldView);
            return oldView;
        } else {          Holder holder = (Holder) oldView.getTag();
            getItem(holder, oldView);
            return oldView;      }
    }

    private void getItem(Holder holder, View view) {
        holder.reserve = this;

//        if (holder.id == null)
//            holder.id = (TextView) view.findViewById(R.id.id);

        if (holder.planningCaption == null)
            holder.planningCaption = (TextView) view.findViewById(R.id.planningCaption);

        if (holder.meal == null)
            holder.meal = (TextView) view.findViewById(R.id.meal);

        if (holder.restaurant == null)
            holder.restaurant = (TextView) view.findViewById(R.id.restaurant);

        if (holder.food == null)
            holder.food = (TextView) view.findViewById(R.id.food);


        if (holder.deleteReserve == null)
            holder.deleteReserve = (TextView) view.findViewById(R.id.delete);
//
//        if (holder.showcancel == null)
//            holder.showcancel = (TextView) view.findViewById(R.id.showcancel);
//
//        if (holder.showprint == null)
//            holder.showprint = (TextView) view.findViewById(R.id.showprint);
//
//        if (holder.showpoll == null)
//            holder.showpoll = (TextView) view.findViewById(R.id.showpoll);

//        holder.id.setText(this.getId()+"");
        holder.planningCaption.setText(this.getPlanningCaption());
        holder.meal.setText(this.getMeal());
        holder.restaurant.setText(this.getRestaurant());
        holder.food.setText(this.getFood());

        if (isShowCancel()){
            holder.deleteReserve.setVisibility(View.VISIBLE);
        }else{
            holder.deleteReserve.setVisibility(View.GONE);
        }
//        holder.delivered.setText(this.isDelivered()+"");
//        holder.showcancel.setText(this.isShowCancel()+"");
//        holder.showprint.setText(this.isShowPrint()+"");
//        holder.showpoll.setText(this.isShowPoll()+"");
    }

    public class Holder {
        //TextView id;
        TextView planningCaption;
        TextView meal;
        TextView restaurant;
        TextView food;
        TextView deleteReserve;

        //TextView delivered;
       // TextView showcancel;
        //TextView showprint;
      //  TextView showpoll;

        public Reserve reserve;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlanningCaption() {
        return planningCaption;
    }

    public void setPlanningCaption(String planningCaption) {
        this.planningCaption = planningCaption;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public boolean isShowCancel() {
        return showCancel;
    }

    public void setShowCancel(boolean showCancel) {
        this.showCancel = showCancel;
    }

    public boolean isShowPrint() {
        return showPrint;
    }

    public void setShowPrint(boolean showPrint) {
        this.showPrint = showPrint;
    }

    public boolean isShowPoll() {
        return showPoll;
    }

    public void setShowPoll(boolean showPoll) {
        this.showPoll = showPoll;
    }
}
