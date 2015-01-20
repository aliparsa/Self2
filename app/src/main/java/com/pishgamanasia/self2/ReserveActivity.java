package com.pishgamanasia.self2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.pishgamanasia.self2.Adapter.ListViewObjectAdapter;
import com.pishgamanasia.self2.DataModel.DateItem;
import com.pishgamanasia.self2.DataModel.Personnel;
import com.pishgamanasia.self2.Helper.DateHelper;
import com.pishgamanasia.self2.Helper.PersianCalendar;
import com.pishgamanasia.self2.Helper.TimerHelper;
import com.pishgamanasia.self2.Helper.Webservice;
import com.pishgamanasia.self2.Interface.CallBack;

import java.util.List;


public class ReserveActivity extends Activity {

    private Context context;
//    private ServerCardResponse serverResponse;
    private Button buttonTahvil;
    ListView dateLV;
String cardId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        context=this;
        dateLV = (ListView) findViewById(R.id.datelistView);
        cardId = getIntent().getStringExtra("cardId");

        fillPersonnelInfo();
        fillDateListView();



//        try {
//            context=this;
//
//            String cardId = getIntent().getStringExtra("cardId");
//
//            final ProgressDialog dialog = ProgressDialog.show(context, "دریافت اطلاعات",
//                    "کمی صبر کنید", true);
//            dialog.show();
//            Webservice.sendCard(context,cardId,new CallBack<ServerCardResponse>() {
//                @Override
//                public void onSuccess(ServerCardResponse result) {
//                    dialog.dismiss();
//                    // TODO Do Something
//                    serverResponse = result;
//                    showInformation(result);
//
//                }
//
//                @Override
//                public void onError(String errorMessage) {
//
//                    dialog.dismiss();
//                    finish();
//                    msgUser(errorMessage);
//                }
//            });
//
//
//
//             buttonTahvil = (Button) findViewById(R.id.buttonTahvil);
//            FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT,buttonTahvil, Typeface.NORMAL);
//            buttonTahvil.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    String reserveId="";
//                    for (Reserve reserve:serverResponse.getReserves()){
//                        reserveId+=reserve.getId() + " *";
//                    }
//                    reserveId = reserveId.substring(0, reserveId.length() - 1);
//                    //reserveId+="]";
//
//                    Webservice.sendTahvil(context, reserveId,new CallBack() {
//                        @Override
//                        public void onSuccess(Object result) {
//                            finish();
//                            msgUser("تحویل شد");
//                        }
//
//                        @Override
//                        public void onError(String errorMessage) {
//                            msgUser("سرور نتوانست داده را ذخیره کند");
//                            finish();
//                        }
//                    });
//                }
//            });
//
//
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
    }

    private void fillPersonnelInfo() {
        Webservice.GetPersonelInfo(context, cardId, new CallBack<Personnel>() {
            @Override
            public void onSuccess(Personnel result) {


            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    private void fillDateListView() {

        List<DateItem> dates = DateHelper.getDatesBeforeAndAfter(new PersianCalendar(), 10);
        final ListViewObjectAdapter adapter = new ListViewObjectAdapter(context,dates);
        dateLV.setAdapter(adapter);
        dateLV.setSelection(9);

        TimerHelper.timerFactory(1000,1,new TimerHelper.TimerFunction() {
            @Override
            public void tick() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dateLV.setItemChecked(11, true);
                    }
                });
            }
        });

        dateLV.setItemChecked(11, true);


        dateLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               dateLV.setItemChecked(i, true);

           }
       });

    }

//    private void showInformation(ServerCardResponse result) {
//
//        boolean haveReserve = checkReserve(result.getReserves());
//
//        if (!haveReserve){
//            buttonTahvil.setBackgroundColor(Color.RED);
//            buttonTahvil.setText("خروج");
//            buttonTahvil.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    finish();
//                }
//            });
//        }
//
//        ListView lvPersonnels = (ListView) findViewById(R.id.listView);
//        ListView lvReserve = (ListView) findViewById(R.id.listView2);
//        ListView lvTotal = (ListView) findViewById(R.id.listView3);
//
//        ListViewObjectAdapter adapterPersonnel = new ListViewObjectAdapter(context, result.getPersonnels());
//        lvPersonnels.setAdapter(adapterPersonnel);
//
//        ListViewObjectAdapter adapterReserve = new ListViewObjectAdapter(context, result.getReserves());
//        lvReserve.setAdapter(adapterReserve);
//
//
//
//        ArrayList<Food> newFoods = new ArrayList<Food>();
//
//        for (Reserve reserve:result.getReserves()) {
//            ArrayList<Food> foods = reserve.getFoods();
//
//            if(reserve.getDeliveryStatus().equals("1"))
//                continue;
//
//            for (Food food : foods) {
//
//                Food newFood = new Food(food.getId(), food.getCaption(), food.getCount());
//
//                boolean set = false;
//                for (Food oldFood:newFoods){
//
//                    if(oldFood.getId() == food.getId()){
//                        oldFood.setCount(oldFood.getCount() + newFood.getCount());
//                        set = true;
//                    }
//                }
//
//                if(!set){
//                    newFoods.add(newFood);
//                }
//            }
//        }
//
//        ListViewObjectAdapter adapterTotal = new ListViewObjectAdapter(context, newFoods);
//        lvTotal.setAdapter(adapterTotal);
//
//    }
//
//    private boolean checkReserve(ArrayList<Reserve> reserves) {
//        boolean haveReserve =false;
//        for (Reserve reserve:reserves){
//            if (reserve.getDeliveryStatus().equals("0")){
//                haveReserve=true;
//            }
//        }
//        return haveReserve;
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.delivery, menu);
//        return true;
//    }
//
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
//    private void msgUser(String errMessage) {
//        Toast.makeText(context, errMessage, Toast.LENGTH_SHORT).show();
//    }

}
