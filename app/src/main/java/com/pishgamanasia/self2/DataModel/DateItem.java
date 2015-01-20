package com.pishgamanasia.self2.DataModel;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pishgamanasia.self2.R;

/**
 * Created by parsa on 2015-01-20.
 */
public class DateItem {
    String date;


    public DateItem(String date) {
        this.date = date;
    }


    public View getView(Context context, View oldView) {

        if (oldView == null || !(oldView.getTag() instanceof DateItem)) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            oldView = inflater.inflate(R.layout.item_date, null);
            Holder holder = new Holder();
            oldView.setTag(holder);
            getItem(holder, oldView);
            //holder.setFont(context);
            return oldView;
        } else {
            Holder holder = (Holder) oldView.getTag();
            getItem(holder, oldView);
            return oldView;
        }
    }

    private void getItem(Holder holder, View view) {

        holder.dateItem = this;

        if (holder.date == null)
            holder.date = (TextView) view.findViewById(R.id.food_name);

        holder.date.setText(date);

    }


    public class Holder {
        TextView date;

        DateItem dateItem;

//        public void setFont(Context context){
//            FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT, name, Typeface.NORMAL);
//            FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT,count, Typeface.NORMAL);
//        }
    }
}
