package com.pishgamanasia.self2.DataModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.pishgamanasia.self2.Interface.IListViewItem;
import com.pishgamanasia.self2.R;

/**
 * Created by parsa on 2015-01-22.
 */
public class NoItem implements IListViewItem {


    public View getView(Context context, View oldView) {

        if (oldView == null || !(oldView.getTag() instanceof NoItem)) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            oldView = inflater.inflate(R.layout.item_no_item, null);
            Holder holder = new Holder();
            oldView.setTag(holder);
            getItem(holder, oldView, context);
        //    holder.setFont(context);
            return oldView;
        } else {
            Holder holder = (Holder) oldView.getTag();
            getItem(holder, oldView, context);
            return oldView;
        }
    }

    @Override
    public void setSelected(boolean flag) {

    }

    private void getItem(final Holder holder, View view, Context context) {


    }


    public class Holder {


//        public void setFont(Context context){
//            FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT, name, Typeface.NORMAL);
//            FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT,code, Typeface.NORMAL);
//        }
    }
}


