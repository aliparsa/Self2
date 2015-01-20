package com.pishgamanasia.self2.Helper;

import android.widget.ListView;

import com.pishgamanasia.self2.DataModel.DateItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashkan on 1/20/2015.
 */
public class DateHelper {


    public static List<DateItem> getDatesBeforeAndAfter(PersianCalendar date, int daysCount){

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
}
