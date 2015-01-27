package com.pishgamanasia.self2.DataModel;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pishgamanasia.self2.FontHelper;
import com.pishgamanasia.self2.Helper.PersianCalendar;
import com.pishgamanasia.self2.Interface.IListViewItem;
import com.pishgamanasia.self2.R;

/**
 * Created by ashkan on 1/27/2015.
 */
public class YearMonthItem implements IListViewItem {




    public enum Type{

        year,
        month
    }

    private PersianCalendar date;
    private String name;
    private Type type;

    private boolean selected;

    public YearMonthItem(PersianCalendar date, Type type) {

        this.date = date;
        this.type = type;
    }


    public View getView(Context context, View oldView) {
        if (oldView == null || !(oldView.getTag() instanceof YearMonthItem)) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            oldView = inflater.inflate(R.layout.item_yearmonth, null);
            Holder holder = new Holder();
            oldView.setTag(holder);
            getItem(context, holder, oldView);
            return oldView;
        } else {
            Holder holder = (Holder) oldView.getTag();
            getItem(context, holder, oldView);
            return oldView;      }
    }

    @Override
    public void setSelected(boolean flag) {
        selected = flag;
    }

    private void getItem(Context context, Holder holder, View view) {
        holder.yearmonthitem = this;

        if (holder.name == null) {
            holder.name = (TextView) view.findViewById(R.id.name);
            FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, holder.name);
        }

        holder.name.setText(getName());


        if (type == Type.year && !isSelected())
            view.setBackgroundColor(Color.TRANSPARENT);
        else if(type == Type.year) {
            view.setBackgroundResource(R.drawable.date_item_selected);
        }
    }

    public class Holder {
        TextView id;
        TextView name;

        private YearMonthItem yearmonthitem;

        public YearMonthItem getYearmonthitem() {
            return yearmonthitem;
        }
    }


    public boolean isSelected() {
        return selected;
    }


    public String getName() {
        if(type == Type.year){
            return date.getIranianYear() + "";
        }
        if(type == Type.month){
            return date.getPersianMonthNameStr();
        }
        return "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public PersianCalendar getDate() {
        return date;
    }

    public void setDate(PersianCalendar date) {
        this.date = date;
    }
}
