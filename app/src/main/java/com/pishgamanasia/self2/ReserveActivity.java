package com.pishgamanasia.self2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pishgamanasia.self2.Adapter.ListViewObjectAdapter;
import com.pishgamanasia.self2.DataModel.Basket;
import com.pishgamanasia.self2.DataModel.DateItem;
import com.pishgamanasia.self2.DataModel.MenuFood;
import com.pishgamanasia.self2.DataModel.NoItem;
import com.pishgamanasia.self2.DataModel.Personnel;
import com.pishgamanasia.self2.DataModel.Reserve;
import com.pishgamanasia.self2.Helper.DateHelper;
import com.pishgamanasia.self2.Helper.PersianCalendar;
import com.pishgamanasia.self2.Helper.Webservice;
import com.pishgamanasia.self2.Interface.CallBack;

import java.util.ArrayList;
import java.util.List;


public class ReserveActivity extends Activity {

    private Context context;
//    private ServerCardResponse serverResponse;
    private Button buttonTahvil;
    ListView dateLV;
    String cardId;

    TextView personnelNameTxt;
    TextView personnelNNTxt;
    TextView personnelCredit;
    private TextView personnelNameTxt2;
    private TextView personnelNNTxt2;
    private TextView personnelCredit2;
    private TextView personnelCredit3;

    ListView reserv_sabad;

    private Button btnSabad;
    private Button btnReserve;

    ListViewObjectAdapter sabadAdapter;
    ListViewObjectAdapter reserveAdapter;
    ListView lvFoodMenu ;
    ArrayList<MenuFood> basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        context=this;
        dateLV = (ListView) findViewById(R.id.datelistView);
        cardId = getIntent().getStringExtra("cardId");

        reserv_sabad = (ListView) findViewById(R.id.listViewReserveSabad);

        btnSabad = (Button) findViewById(R.id.btn_sabad);
        btnReserve = (Button) findViewById(R.id.btn_reserve);

        basket = new ArrayList<MenuFood>();

        lvFoodMenu = (ListView) findViewById(R.id.listViewMenuFood);

        btnSabad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActiveTab(1);
            }
        });

        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActiveTab(2);
            }
        });

        // add food to basket
        lvFoodMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MenuFood menuFood = (MenuFood)((MenuFood.Holder) view.getTag()).menufood;
                basket.add(menuFood);
                Toast.makeText(context,"به سبد خرید افزوده شد",Toast.LENGTH_SHORT).show();
                //TODO Ashkan please Put your Function right Here
            }
        });
        reserv_sabad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (view.getTag() instanceof Reserve.Holder){
                    final Reserve reserve = (Reserve)((Reserve.Holder) view.getTag()).reserve;
                    if (reserve.isShowCancel()==false){
                     //   Toast.makeText(context,"لغو این رزرو امکان پذیر نیست",Toast.LENGTH_LONG).show();
                        return;
                    }
                    new AlertDialog.Builder(context)
                            .setTitle("توجه")
                            .setMessage("آیا این رزرو لغو شود ؟")
                            .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete

                                    final ProgressDialog progDialog = ProgressDialog.show(context, "تبادل داده با سرور","کمی صبر کنید", true);
                                    progDialog.show();
                                    Webservice.CancelReserve(context,reserve.getId()+"",new CallBack() {
                                        @Override
                                        public void onSuccess(Object result) {
                                            progDialog.dismiss();
                                            if (reserv_sabad!=null && reserv_sabad.getAdapter() instanceof ListViewObjectAdapter){

                                                ((ListViewObjectAdapter)reserv_sabad.getAdapter()).removeItem(reserve);

                                                ((ListViewObjectAdapter)reserv_sabad.getAdapter()).notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onError(String errorMessage) {
                                            progDialog.dismiss();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

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
        final ProgressDialog progress;
        progress = ProgressDialog.show(this, "",
                "دریافت اطلاعات", true);


        personnelNameTxt = (TextView) findViewById(R.id.personnelName);
        personnelNNTxt = (TextView) findViewById(R.id.personnelNN);
        personnelCredit = (TextView) findViewById(R.id.personnelCredit);
        //
        personnelNameTxt2 = (TextView) findViewById(R.id.personnelName2);
        personnelNNTxt2 = (TextView) findViewById(R.id.personnelNN2);
        personnelCredit2 = (TextView) findViewById(R.id.personnelCredit2);
        personnelCredit3 = (TextView) findViewById(R.id.personnelCredit3);

        FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT, personnelNameTxt, Typeface.NORMAL);
        FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT, personnelNNTxt, Typeface.NORMAL);
        FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT, personnelCredit, Typeface.NORMAL);
        //
        FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT, personnelNameTxt2, Typeface.NORMAL);
        FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT, personnelNNTxt2, Typeface.NORMAL);
        FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT, personnelCredit2, Typeface.NORMAL);
        FontHelper.SetFont(context, FontHelper.Fonts.MAIN_FONT, personnelCredit3, Typeface.NORMAL);


        Webservice.GetPersonelInfo(context, cardId, new CallBack<Personnel>() {
            @Override
            public void onSuccess(Personnel result) {
                progress.dismiss();

                personnelNameTxt.setText(result.getName() +" "+ result.getFamily()
                +" ( " + result.getCode() + " )");

                personnelNNTxt.setText(result.getNationalNo());

                personnelCredit.setText(result.getFinalCridit()+ "");
               }

            @Override
            public void onError(String errorMessage) {
                progress.dismiss();
                Toast.makeText(context,"عملیات با خطا مواجه شد",Toast.LENGTH_LONG).show();
               // finish();
            }
        });
    }

    private void fillDateListView() {

        List<DateItem> dates = DateHelper.getDatesBeforeAndAfter(new PersianCalendar(), 10);
        final ListViewObjectAdapter adapter = new ListViewObjectAdapter(context,dates);
        dateLV.setAdapter(adapter);

        dateLV.setSelection(9);

        dateLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               adapter.setSelectedItem(i);
               adapter.notifyDataSetChanged();

               DateItem item = ((DateItem.Holder) view.getTag()).getDateItem();


               final ProgressDialog progress = ProgressDialog.show(context, "",
                       "دریافت اطلاعات", true);

               Webservice.GetMenuFoods(context, item.getDate().getGregorianDate(), cardId, new CallBack<ArrayList<MenuFood>>() {
                   @Override
                   public void onSuccess(ArrayList<MenuFood> result) {
                       progress.dismiss();

                       fillFoodMenu(result);
                   }

                   @Override
                   public void onError(String errorMessage) {
                       progress.dismiss();

                   }
               } );

               Webservice.GetReserves(context, item.getDate().getGregorianDate(), cardId, new CallBack<ArrayList<Reserve>>() {
                   @Override
                   public void onSuccess(ArrayList<Reserve> result) {

                       setReserved(result);
                   }

                   @Override
                   public void onError(String errorMessage) {

                   }
               } );
           }
       });

    }

    private void fillFoodMenu(ArrayList<MenuFood> menuFoods){


        if (menuFoods.size()<1){

            ArrayList<NoItem> noItems = new ArrayList<NoItem>();
            noItems.add(new NoItem());

            ListViewObjectAdapter adapter = new ListViewObjectAdapter(context,noItems);
            lvFoodMenu.setAdapter(adapter);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.view_not_valid);
            lvFoodMenu.startAnimation(animation);


            return;
        }

        ListViewObjectAdapter adapter = new ListViewObjectAdapter(context,menuFoods);
        lvFoodMenu.setAdapter(adapter);


    }

    private void setReserved(ArrayList<Reserve> reserves){



        if (reserves.size()<1){

            ArrayList<NoItem> noItems = new ArrayList<NoItem>();
            noItems.add(new NoItem());

            ListViewObjectAdapter adapter = new ListViewObjectAdapter(context,noItems);
            reserv_sabad.setAdapter(adapter);




            return;
        }

        reserveAdapter = new ListViewObjectAdapter(context,reserves);
        reserv_sabad.setAdapter(reserveAdapter);

    }


    void setActiveTab(int tabNum){

        if(tabNum == 1){//sabad

            btnSabad.setBackgroundColor(Color.parseColor("#ffe1e1e1"));
            btnReserve.setBackgroundColor(Color.parseColor("#ffffff"));

            reserv_sabad.setAdapter(null);

        }else{//reserve

            btnSabad.setBackgroundColor(Color.parseColor("#ffffff"));
            btnReserve.setBackgroundColor(Color.parseColor("#ffe1e1e1"));

            reserv_sabad.setAdapter(reserveAdapter);

        }

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
