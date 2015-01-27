package com.pishgamanasia.self2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pishgamanasia.self2.Adapter.ListViewObjectAdapter;
import com.pishgamanasia.self2.DataModel.YearMonthItem;
import com.pishgamanasia.self2.Helper.DateHelper;
import com.pishgamanasia.self2.Helper.PersianCalendar;
import com.pishgamanasia.self2.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends Activity {


    private Context context;
    ListView yearListview;
    ListView mountListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        context= this;

        // init
        yearListview = (ListView) findViewById(R.id.sallistView);
        mountListview = (ListView) findViewById(R.id.mahListview);

        // lets start
        fillYearListVIew();
    }


    private void fillYearListVIew(){

        List<YearMonthItem> years = DateHelper.getYearsBefore(new PersianCalendar(), 5);
        ListViewObjectAdapter adapter = new ListViewObjectAdapter(context,years);
        yearListview.setAdapter(adapter);
        yearListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PersianCalendar persianCalendar = ((YearMonthItem.Holder) view.getTag()).getYearmonthitem().getDate();
                fillMonthListVIew(persianCalendar);
            }
        });

    }




    private void fillMonthListVIew(PersianCalendar date){

        List<YearMonthItem> months = DateHelper.getMonthsOfYear(date);
        ListViewObjectAdapter adapter = new ListViewObjectAdapter(context,months);
        mountListview.setAdapter(adapter);


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
