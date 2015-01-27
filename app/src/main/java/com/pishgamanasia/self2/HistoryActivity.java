package com.pishgamanasia.self2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.pishgamanasia.self2.Adapter.ListViewObjectAdapter;
import com.pishgamanasia.self2.DataModel.NoItem;
import com.pishgamanasia.self2.DataModel.ReserveHistory;
import com.pishgamanasia.self2.DataModel.YearMonthItem;
import com.pishgamanasia.self2.Helper.DateHelper;
import com.pishgamanasia.self2.Helper.PersianCalendar;
import com.pishgamanasia.self2.Helper.Webservice;
import com.pishgamanasia.self2.Interface.CallBack;
import com.pishgamanasia.self2.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends Activity {


    private Context context;
    ListView yearListview;
    ListView mountListview;
    private LinearLayout monthll;
    String cardId;
    ListView reserveHistotyListview;
    private ListViewObjectAdapter yearAdapter;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        context= this;

        // init
        yearListview = (ListView) findViewById(R.id.sallistView);
        mountListview = (ListView) findViewById(R.id.mahListview);
        monthll = (LinearLayout) findViewById(R.id.monthll);
        reserveHistotyListview= (ListView) findViewById(R.id.HistoryListView);

        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // lets start
        fillYearListVIew();
        cardId = getIntent().getStringExtra("cardId");

    }


    private void fillYearListVIew(){

        List<YearMonthItem> years = DateHelper.getYearsBefore(new PersianCalendar(), 5);
        yearAdapter = new ListViewObjectAdapter(context,years);
        yearListview.setAdapter(yearAdapter);

        yearListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PersianCalendar persianCalendar = ((YearMonthItem.Holder) view.getTag()).getYearmonthitem().getDate();
                fillMonthListVIew(persianCalendar);

                yearAdapter.setSelectedItem(i);
                yearAdapter.notifyDataSetChanged();
            }
        });

    }




    private void fillMonthListVIew(PersianCalendar date){

        List<YearMonthItem> months = DateHelper.getMonthsOfYear(date);
        final ListViewObjectAdapter adapter = new ListViewObjectAdapter(context,months);
        mountListview.setAdapter(adapter);



        mountListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                monthll.setVisibility(View.GONE);
                PersianCalendar persianCalendar = ((YearMonthItem.Holder) view.getTag()).getYearmonthitem().getDate();

                final ProgressDialog progDialog = ProgressDialog.show(context, "تبادل داده با سرور", "کمی صبر کنید", true);
                progDialog.show();

                Webservice.GetHistory(context,persianCalendar.getGregorianDate(),cardId,new CallBack<ArrayList<ReserveHistory>>() {
                    @Override
                    public void onSuccess(ArrayList<ReserveHistory> result) {
                        progDialog.dismiss();

                        if (result.size()<1){
                            ArrayList<NoItem> noItems = new ArrayList<NoItem>();
                            noItems.add(new NoItem());
                            ListViewObjectAdapter adapter = new ListViewObjectAdapter(context,noItems);
                            reserveHistotyListview.setAdapter(adapter);
                            Animation animation = AnimationUtils.loadAnimation(context, R.anim.view_not_valid);
                            reserveHistotyListview.startAnimation(animation);
                            return;
                        }


                        ListViewObjectAdapter adapter1 = new ListViewObjectAdapter(context,result);
                        reserveHistotyListview.setAdapter(adapter1);

                    }

                    @Override
                    public void onError(String errorMessage) {
                        progDialog.dismiss();
                    }
                });

            }
        });

        monthll.setVisibility(View.VISIBLE);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
