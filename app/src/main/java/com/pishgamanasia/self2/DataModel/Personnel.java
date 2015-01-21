package com.pishgamanasia.self2.DataModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.pishgamanasia.self2.Interface.IListViewItem;
import com.pishgamanasia.self2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by parsa on 2014-12-06.
 */
public class Personnel implements IListViewItem {

    int id;
    String name;
    String family;
    String nationalNo;
    String imageUrl;
    double finalCridit;
    String code;


    public Personnel(int id, String name, String family, String code, double finalCridit) {
        this.id = id;
        this.name = name;
        this.family = family;
        this.code = code;
        this.finalCridit=finalCridit;

    }

    public Personnel(int id, String name, String family, String nationalNo, String imageUrl) {
        this.id = id;
        this.name = name;
        this.family = family;
        this.nationalNo = nationalNo;
        this.imageUrl = imageUrl;
    }

    public static ArrayList<Personnel> getArrayFromJson(String personnelJson) {
        ArrayList<Personnel> itemlist = null;
        try {

            JSONArray jsonArray = new JSONArray(personnelJson);
            itemlist = new ArrayList<Personnel>();
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.getJSONObject(i);

                int id = obj.getInt("Id");
                String name = obj.getString("Name");
                String family= obj.getString("Family");
                String nationalNo= obj.getString("NationalNo");
                String imageUrl= obj.getString("ImageUrl");
                Personnel personnel = new Personnel(id, name, family, nationalNo, imageUrl);
                itemlist.add(personnel);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return itemlist;
    }

    public View getView(Context context, View oldView) {

        if (oldView == null || !(oldView.getTag() instanceof Personnel)) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            oldView = inflater.inflate(R.layout.item_personnel, null);
            Holder holder = new Holder();
            oldView.setTag(holder);
            getItem(holder, oldView, context);
            //holder.setFont(context);
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

        holder.personnel = this;

        if (holder.name == null)
            holder.name = (TextView) view.findViewById(R.id.personnelName);

        if (holder.code == null)
            holder.code = (TextView) view.findViewById(R.id.personnelCode);

        if (holder.image == null)
            holder.image = (ImageView) view.findViewById(R.id.personnelImage);


        holder.name.setText(getName() + " " + getFamily());
        holder.code.setText("کد ملی: "+getNationalNo());



    }


    public class Holder {
        TextView name;
        TextView code;
        ImageView image;
        Personnel personnel;

//        public void setFont(Context context){
//            FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT,name, Typeface.NORMAL);
//            FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT,code, Typeface.NORMAL);
//        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getNationalNo() {
        return nationalNo;
    }

    public void setNationalNo(String nationalNo) {
        this.nationalNo = nationalNo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
