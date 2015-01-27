package com.pishgamanasia.self2.Helper;

import com.pishgamanasia.self2.DataModel.DateItem;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashkan on 1/20/2015.
 */
public class DateHelper {



    public static List<DateItem> getDatesBeforeAndAfter( PersianCalendar date, int daysCount){

        ArrayList<DateItem> dates = new ArrayList<DateItem>();

        date.previousDay(daysCount);

        for (int i = 0; i < daysCount * 2 + 1; i++) {

            PersianCalendar newDate = new PersianCalendar(
                    date.getGregorianYear(),
                    date.getGregorianMonth(),
                    date.getGregorianDay());

            dates.add(new DateItem(newDate));
            date.nextDay();
        }

        return dates;
    }


    public static List<DateItem> getYearsBefore( PersianCalendar date, int yearsBefore){

        ArrayList<DateItem> dates = new ArrayList<DateItem>();


        for (int i = 1; i <= yearsBefore; i++) {

            PersianCalendar newDate = new PersianCalendar(
                    date.getGregorianYear() - i,
                    1,1);

            dates.add(new DateItem(newDate));
        }

        return dates;
    }


    public static List<DateItem> getMonthsOfYear( PersianCalendar date){

        ArrayList<DateItem> dates = new ArrayList<DateItem>();


        for (int i = 1; i <= 12; i++) {

            PersianCalendar newDate = new PersianCalendar(
                    date.getGregorianYear(),
                    i,1);

            dates.add(new DateItem(newDate));
        }

        return dates;
    }
}
