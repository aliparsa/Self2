package com.pishgamanasia.self2.DataModel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pishgamanasia.self2.FontHelper;
import com.pishgamanasia.self2.Helper.PersianCalendar;
import com.pishgamanasia.self2.Interface.IListViewItem;
import com.pishgamanasia.self2.R;

/**
 * Created by parsa on 2015-01-20.
 */
public class DateItem implements IListViewItem {
    PersianCalendar date;
    private boolean isSelected = false;

    public DateItem(PersianCalendar date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return date.toString();
    }

    public View getView(Context context, View oldView) {

        if (oldView == null || !(oldView.getTag() instanceof DateItem)) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            oldView = inflater.inflate(R.layout.item_date, null);
            Holder holder = new Holder();
            oldView.setTag(holder);
            getItem(holder, oldView);
            holder.setFont(context);
            return oldView;
        } else {

            Holder holder = (Holder) oldView.getTag();
            getItem(holder, oldView);
            return oldView;
        }
    }

    @Override
    public void setSelected(boolean flag) {
        this.isSelected = flag;
    }

    private void getItem(Holder holder, View view) {

        holder.dateItem = this;

        if (holder.dateName == null)
            holder.dateName = (TextView) view.findViewById(R.id.dateName);

        if (holder.dateNumber == null)
            holder.dateNumber = (TextView) view.findViewById(R.id.dateNumber);

        holder.dateName.setText(date.getPersianWeekDayStr());
        holder.dateNumber.setText(date.getIranianDate());


        //if (isSelected)
       //     view.setBackgroundColor(Color.parseColor("#FF5FB0FF"));
        //else
        view.setBackgroundColor(Color.TRANSPARENT);

        if (new PersianCalendar().getGregorianDate().equals(date.getGregorianDate())) {
            holder.dateName.setTextColor(Color.parseColor("#FF06880D"));
            holder.dateNumber.setTextColor(Color.parseColor("#FF06880D"));
        }
       // else
        //    view.setBackgroundColor(Color.TRANSPARENT);


    }


    public class Holder {
        TextView dateName;
        TextView dateNumber;

        DateItem dateItem;

        public void setFont(Context context) {
            FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT, dateName, Typeface.NORMAL);
            FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT, dateNumber, Typeface.NORMAL);

        }
    }
}
