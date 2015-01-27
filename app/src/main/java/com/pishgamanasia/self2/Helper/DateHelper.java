package com.pishgamanasia.self2.Helper;

import com.pishgamanasia.self2.DataModel.DateItem;
import com.pishgamanasia.self2.DataModel.YearMonthItem;


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


    public static List<YearMonthItem> getYearsBefore( PersianCalendar date, int yearsBefore){

        ArrayList<YearMonthItem> dates = new ArrayList<YearMonthItem>();

        int pYear = date.getIranianYear();

        for (int i = yearsBefore; i >= 0; i--) {

            PersianCalendar newDate = new PersianCalendar();
            newDate.setIranianDate((pYear-i),1,1);

            dates.add(new YearMonthItem(newDate, YearMonthItem.Type.year));
        }

        return dates;
    }


    public static List<YearMonthItem> getMonthsOfYear( PersianCalendar date){

        ArrayList<YearMonthItem> dates = new ArrayList<YearMonthItem>();


        int pYear = date.getIranianYear();

        for (int i = 1; i <= 12; i++) {

            PersianCalendar newDate = new PersianCalendar();
            newDate.setIranianDate(pYear, i, 1);

            dates.add(new YearMonthItem(newDate, YearMonthItem.Type.month));
        }

        return dates;
    }
}
