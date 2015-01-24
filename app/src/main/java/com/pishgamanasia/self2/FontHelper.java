package com.pishgamanasia.self2;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.Set;


/**
 * Created by ashkan on 9/1/2014.
 * Step 1
 * Create assets folder under main folder
 * Step 2
 * create fonts folder under assets folder
 * Step 3
 * copy your fonts in this folder and use them
 */
public class FontHelper {


    public static void SetFontNormal(Context context, Fonts font, TextView view) {

        SetFont(context, font, view, Typeface.NORMAL);

    }

    public static void SetFontBold(Context context, Fonts font, TextView view) {

        SetFont(context, font, view, Typeface.BOLD);

    }

    public static void SetFontItalic(Context context, Fonts font, TextView view) {

        SetFont(context, font, view, Typeface.ITALIC);

    }

    public static void SetFontBoldItalic(Context context, Fonts font, TextView view) {

        SetFont(context, font, view, Typeface.BOLD_ITALIC);

    }

    public static void SetFont(Context context, Fonts font, TextView view, int typeFace) {
        Typeface tf;

        switch (font) {
            case MAIN_FONT:
                tf = Typeface.createFromAsset(context.getAssets(), "fonts/BKOODB.TTF");
                view.setTypeface(tf, typeFace);
                break;
            case BKAMRAN:
                tf = Typeface.createFromAsset(context.getAssets(), "fonts/BKAMRAN.TTF");
                view.setTypeface(tf, typeFace);
                break;
            case BKOODAK:
                tf = Typeface.createFromAsset(context.getAssets(), "fonts/BKOODB.TTF");
                view.setTypeface(tf, typeFace);
                break;
        }
    }

    public static void SetFont(Context context, Fonts font, Button view, int typeFace) {
        Typeface tf;


        switch (font) {
            case MAIN_FONT:
                tf = Typeface.createFromAsset(context.getAssets(), "fonts/BKOODB.TTF");
                view.setTypeface(tf, typeFace);
                break;
            case BKAMRAN:
                tf = Typeface.createFromAsset(context.getAssets(), "fonts/BKAMRAN.TTF");
                view.setTypeface(tf, typeFace);
                break;
            case BKOODAK:
                tf = Typeface.createFromAsset(context.getAssets(), "fonts/BKOODB.TTF");
                view.setTypeface(tf, typeFace);
                break;
        }
    }


    public enum Fonts {

        MAIN_FONT,
        BKAMRAN,
        BKOODAK
    }

}