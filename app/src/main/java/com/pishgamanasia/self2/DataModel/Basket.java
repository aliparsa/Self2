package com.pishgamanasia.self2.DataModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pishgamanasia.self2.FontHelper;
import com.pishgamanasia.self2.Helper.AsyncLoadImage;
import com.pishgamanasia.self2.Helper.PersianCalendar;
import com.pishgamanasia.self2.Helper.SettingHelper;
import com.pishgamanasia.self2.Interface.IListViewItem;
import com.pishgamanasia.self2.R;

import java.util.ArrayList;

/**
 * Created by parsa on 2015-01-24.
 */
public class Basket implements IListViewItem{

    MenuFood menuFood;

    String date;
    private int count;
    private double price;

    public Basket(MenuFood menuFood){
        this.menuFood = menuFood;

        PersianCalendar pc = new PersianCalendar(menuFood.getDate());
        date = pc.getPersianWeekDayStr() + " " + pc.getIranianDate();
        count = 1;
        price = menuFood.calculatePrice();
    }


    public View getView(Context context, View oldView) {
        if (oldView == null || !(oldView.getTag() instanceof Basket)) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            oldView = inflater.inflate(R.layout.item_basket, null);
            Holder holder = new Holder();
            oldView.setTag(holder);
            getItem(context,holder, oldView);
            return oldView;
        } else {
            Holder holder = (Holder) oldView.getTag();
            holder.image.setImageBitmap(null);
            getItem(context, holder, oldView);
            return oldView;      }
    }

    @Override
    public void setSelected(boolean flag) {

    }

    private void getItem(final Context context, final Holder holder, View view) {
        holder.basket = this;  if (holder.date == null)
            holder.date = (TextView) view.findViewById(R.id.date);

        if (holder.count == null)
            holder.count = (TextView) view.findViewById(R.id.count);

        if (holder.price == null)
            holder.price = (TextView) view.findViewById(R.id.price);

       // if (holder.mealcaption == null)
       //     holder.mealcaption = (TextView) view.findViewById(R.id.mealcaption);

        if (holder.foodcaption == null)
            holder.foodcaption = (TextView) view.findViewById(R.id.foodcaption);

        if (holder.planningcaption == null)
            holder.planningcaption = (TextView) view.findViewById(R.id.planningcaption);

        if (holder.image == null)
            holder.image = (ImageView) view.findViewById(R.id.image);

        holder.date.setText(this.getDate() + "      (" + this.menuFood.getMealCaption() + ")");
        holder.count.setText(this.getCount()+"");
        holder.price.setText((this.getPrice() * this.getCount())+ " ريال");
        //holder.mealcaption.setText(this.menuFood.getMealCaption());
        holder.foodcaption.setText(this.menuFood.getFoodCaption());
        holder.planningcaption.setText(this.menuFood.getPlanningCaption()+ " (" +
        this.menuFood.getRestaurant() + " )");

        FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, holder.date);
        FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, holder.count);
        FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, holder.price);
        //FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, holder.mealcaption);
        FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, holder.foodcaption);
        FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, holder.planningcaption);

        if(getImage() != null && !getImage().equals("null") && !getImage().equals("")){

            AsyncLoadImage loader = new AsyncLoadImage(context, new SettingHelper(context).getOption("serverAddress")+ "/" +  getImage(), new AsyncLoadImage.ProgressCallBack<Bitmap>() {
                @Override
                public void onSuccess(Bitmap result, String imageUrl) {

                    if((new SettingHelper(context).getOption("serverAddress")+ "/" + getImage()).equals(imageUrl))
                        holder.image.setImageBitmap(result);
                    else
                        holder.image.setImageBitmap(null);
                }

                @Override
                public void onError(String errorMessage) {
                    holder.image.setImageResource(R.drawable.ic_steak);
                }

                @Override
                public void onProgress(int done, int total, Bitmap result) {

                }
            });


            loader.execute();
        }else{

            holder.image.setImageResource(R.drawable.ic_steak);
        }
    }

    public String getImage() {
        return menuFood.getImage();
    }

    public class Holder {
        TextView date;
        TextView count;
        TextView price;
        //TextView mealcaption;
        TextView foodcaption;
        TextView planningcaption;
        ImageView image;

         public Basket basket;
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


    public MenuFood getMenuFood() {
        return menuFood;
    }


}
