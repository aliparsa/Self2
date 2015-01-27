package com.pishgamanasia.self2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.pishgamanasia.self2.DataModel.YearMonthItem;
import com.pishgamanasia.self2.Helper.DateHelper;
import com.pishgamanasia.self2.Helper.PersianCalendar;
import com.pishgamanasia.self2.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }


    private void fillYearListVIew(){

        List<YearMonthItem> years = DateHelper.getYearsBefore(new PersianCalendar(), 5);



    }


    private void fillMonthListVIew(PersianCalendar date){

        List<YearMonthItem> months = DateHelper.getMonthsOfYear(date);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
