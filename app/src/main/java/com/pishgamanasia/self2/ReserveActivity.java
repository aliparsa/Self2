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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.pishgamanasia.self2.Helper.StringHelper;
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

    private LinearLayout btnSabad;
    private LinearLayout btnReserve;

    private TextView txtSabad;

    private Button btnSendReserve;

    ListViewObjectAdapter sabadAdapter;
    ListViewObjectAdapter reserveAdapter;
    ListViewObjectAdapter dateAdapter;
    ListView lvFoodMenu;
    ArrayList<MenuFood> selectedFoods;
    int lastSelectedDayIndex = 0;
    private ImageView imgSabad;
    private ArrayList<MenuFood> globalMenuFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        context = this;
        dateLV = (ListView) findViewById(R.id.datelistView);
        cardId = getIntent().getStringExtra("cardId");

        reserv_sabad = (ListView) findViewById(R.id.listViewReserveSabad);

        btnSabad = (LinearLayout) findViewById(R.id.btn_sabad);
        btnReserve = (LinearLayout) findViewById(R.id.btn_reserve);
        btnSendReserve = (Button) findViewById(R.id.btn_send_reserve);

        txtSabad = (TextView) findViewById(R.id.txtSabad);
        imgSabad = (ImageView) findViewById(R.id.imgSabad);

        selectedFoods = new ArrayList<MenuFood>();

        FontHelper.SetFontNormal(context, FontHelper.Fonts.MAIN_FONT, btnSendReserve);

        lvFoodMenu = (ListView) findViewById(R.id.listViewMenuFood);


        btnSendReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedFoods.size() > 0) {
                    final ProgressDialog progDialog = ProgressDialog.show(context, "تبادل داده با سرور", "کمی صبر کنید", true);
                    progDialog.show();

                    String json = MenuFood.getJsonFromArrayList(selectedFoods);
                    Webservice.AddReserve(context, json, cardId, new CallBack<String>() {
                        @Override
                        public void onSuccess(String result) {
                            progDialog.dismiss();
                            Toast.makeText(context, " رزرو با موفقیت انجام شد", Toast.LENGTH_SHORT).show();

                            LoadAndFillMenuFood(selectedFoods.get(selectedFoods.size() - 1).getDate(), cardId);
                            selectedFoods.clear();
                            refreshBasketListView();
                            setActiveTab(2);
                            sabadAdapter.notifyDataSetChanged();

                            txtSabad.setText("سبد خرید");
                            imgSabad.setImageResource(R.drawable.ic_shopping_cart_dis);

                            setCredit(result);
                            //fillPersonnelInfo(false);
                        }

                        @Override
                        public void onError(String errorMessage) {
                            progDialog.dismiss();
                            Toast.makeText(context, " موفقیت آمیز نبود", Toast.LENGTH_SHORT).show();

                        }
                    });
                } else {
                    Toast.makeText(context, "سبد خرید خالی است", Toast.LENGTH_LONG).show();
                }
            }
        });
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
                MenuFood menuFood = (MenuFood) ((MenuFood.Holder) view.getTag()).menufood;

                if (menuFood.getCount() < menuFood.getMaxReserveCount()) {
                    menuFood.setCount(menuFood.getCount() + 1);
                } else {
                    Toast.makeText(context, "حداکثر تداد مجاز رزرو شده است", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (menuFood.isShowReserveButton() == false) {
                    Toast.makeText(context, "این مورد قابل رزرو نیست", Toast.LENGTH_SHORT).show();
                    return;
                }
                selectedFoods.add(menuFood);
                //Toast.makeText(context,"به سبد خرید افزوده شد",Toast.LENGTH_SHORT).show();

                txtSabad.setText("سبد خرید" + " (" + selectedFoods.size() + ")");
                imgSabad.setImageResource(R.drawable.ic_shopping_cart);

                refreshBasketListView();
            }
        });


        reserv_sabad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });

        fillPersonnelInfo(true);
        fillDateListView();


    }

    private void setCredit(String result) {
        personnelCredit.setText(StringHelper.commaSeparator(result));

    }

    private void getAndFillPersonnelInfo(String cardId) {

    }

    private void refreshBasketListView() {

        List<Basket> baskets = new ArrayList<Basket>();


        double price = 0;

        for (MenuFood menufood : selectedFoods) {

            Basket newBasket = new Basket(menufood);
            boolean flag = false;

            price += menufood.getPayedPrice();

            for (Basket basket : baskets) {

                if (basket.getMenuFood().equals(newBasket.getMenuFood())) {
                    basket.setCount(basket.getCount() + 1);
                    flag = true;
                    break;
                }
            }

            if (!flag)
                baskets.add(newBasket);
        }

        sabadAdapter = new ListViewObjectAdapter(context, baskets);


        btnSendReserve.setText("ثبت رزرو" + "     با مبلغ  : " + StringHelper.commaSeparator(price + "") + " ريال");

        setActiveTab(1);
    }

    private void fillPersonnelInfo(final boolean isShowProgress) {

        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("دریافت اطلاعات");
        progress.setIndeterminate(true);


        if (isShowProgress)
            progress.show();

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


                if (isShowProgress)
                    progress.dismiss();


                personnelNameTxt.setText(result.getName() + " " + result.getFamily()
                        + " ( " + result.getCode() + " )");

                personnelNNTxt.setText(result.getNationalNo());

                setCredit(result.getFinalCridit() + "");
            }

            @Override
            public void onError(String errorMessage) {

                if (isShowProgress)
                    progress.dismiss();

                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void fillDateListView() {

        List<DateItem> dates = DateHelper.getDatesBeforeAndAfter(new PersianCalendar(), 10);
        dateAdapter = new ListViewObjectAdapter(context, dates);
        dateLV.setAdapter(dateAdapter);

        dateLV.setSelection(9);

        dateLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                lastSelectedDayIndex = i;
                DateItem item = ((DateItem.Holder) view.getTag()).getDateItem();
                LoadAndFillMenuFood(item.getDate().getGregorianDate(), cardId);

            }
        });

    }

    private void fillFoodMenu(ArrayList<MenuFood> menuFoods) {


        if (menuFoods.size() < 1) {

            ArrayList<NoItem> noItems = new ArrayList<NoItem>();
            noItems.add(new NoItem());

            ListViewObjectAdapter adapter = new ListViewObjectAdapter(context, noItems);
            lvFoodMenu.setAdapter(adapter);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.view_not_valid);
            lvFoodMenu.startAnimation(animation);


            return;
        }

        ListViewObjectAdapter adapter = new ListViewObjectAdapter(context, menuFoods);
        lvFoodMenu.setAdapter(adapter);


    }

    private void setReserved(ArrayList<Reserve> reserves) {


        if (reserves.size() < 1) {

            ArrayList<NoItem> noItems = new ArrayList<NoItem>();
            noItems.add(new NoItem());

            Animation animation = AnimationUtils.loadAnimation(context, R.anim.view_not_valid);
            reserv_sabad.startAnimation(animation);

            reserveAdapter = new ListViewObjectAdapter(context, noItems);
            setActiveTab(2);
            return;
        }

        reserveAdapter = new ListViewObjectAdapter(context, reserves);
        setActiveTab(2);

    }


    void setActiveTab(int tabNum) {

        if (tabNum == 1) {//sabad

            // btnSabad.setBackgroundColor(Color.parseColor("#ffe1e1e1"));
            btnSabad.setBackgroundResource(R.drawable.tab_drawable);
            btnReserve.setBackgroundColor(Color.parseColor("#ffffff"));
            btnSendReserve.setVisibility(View.VISIBLE);
            reserv_sabad.setAdapter(sabadAdapter);


        } else {//reserve

            btnSabad.setBackgroundColor(Color.parseColor("#ffffff"));
            btnReserve.setBackgroundResource(R.drawable.tab_drawable);
            btnSendReserve.setVisibility(View.GONE);
            reserv_sabad.setAdapter(reserveAdapter);

        }

    }


    public void LoadAndFillMenuFood(String date, String cardId) {

        dateAdapter.setSelectedItem(lastSelectedDayIndex);
        dateAdapter.notifyDataSetChanged();

        final ProgressDialog progress = ProgressDialog.show(context, "",
                "دریافت اطلاعات", true);
        Webservice.GetMenuFoods(context, date, cardId, new CallBack<ArrayList<MenuFood>>() {
            @Override
            public void onSuccess(ArrayList<MenuFood> result) {
                progress.dismiss();
                globalMenuFoods = result;
                fillFoodMenu(result);
            }

            @Override
            public void onError(String errorMessage) {
                progress.dismiss();
            }
        });

        Webservice.GetReserves(context, date, cardId, new CallBack<ArrayList<Reserve>>() {
            @Override
            public void onSuccess(ArrayList<Reserve> result) {

                setReserved(result);
            }

            @Override
            public void onError(String errorMessage) {

                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

}
