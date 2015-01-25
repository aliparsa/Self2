package com.pishgamanasia.self2.Helper;

/**
 * Created by parsa on 2015-01-25.
 */
public class StringHelper {


    public static String commaSeparator(String number){

        Double d = Double.parseDouble(number);
        String str = String.format("%,d", d.intValue());

        return " "+str+" ";
    }
}
