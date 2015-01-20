package com.pishgamanasia.self2.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.pishgamanasia.self2.Interface.IListViewItem;
import com.pishgamanasia.self2.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashkan on 12/3/2014.
 */
public class LogHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "db.db";


    // TABLES
    private static final String TABLE_LOG = "log";

    // TABLE_LOG_FIELDS
    private static final String LOG_ID = "LOG_ID";
    private static final String LOG_DATE = "LOG_DATE";
    private static final String LOG_REQUEST = "LOG_REQUEST";
    private static final String LOG_RESPONSE = "LOG_RESPONSE";

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_LOG_TABLE =
                "CREATE TABLE " + TABLE_LOG + "("
                        + LOG_ID + " INTEGER PRIMARY KEY,"
                        + LOG_DATE + " TEXT,"
                        + LOG_REQUEST + " TEXT,"
                        + LOG_RESPONSE + " TEXT"
                        + ")";
        db.execSQL(CREATE_LOG_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }




    public LogHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void InsertLog(String date, String request, String response){

        request = request.replace("\"","'");
        response = response.replace("\"","'");

        ContentValues values = new ContentValues();
        values.put(LOG_DATE, date);
        values.put(LOG_REQUEST, request);
        values.put(LOG_RESPONSE, response);
        getWritableDatabase().insert(TABLE_LOG, null, values);
    }

    public List<Log> getAllLog(int count){

        SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOG + " ORDER BY " + LOG_ID + " DESC" + " LIMIT " + count , null);
        ArrayList<Log> logs = new ArrayList<Log>();


        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {

                    int id = cursor.getInt(cursor.getColumnIndex(LOG_ID));
                    String date = cursor.getString(cursor.getColumnIndex(LOG_DATE));
                    String request = cursor.getString(cursor.getColumnIndex(LOG_REQUEST));
                    String response = cursor.getString(cursor.getColumnIndex(LOG_RESPONSE));

                    Log log = new Log(id, date, request, response);

                    logs.add(log);

                } while (cursor.moveToNext());

            }
        }
        return logs;
    }




    public class Log implements IListViewItem{

        private int id;
        private String date;
        private String request;
        private String response;

        public Log(int id, String date, String request, String response) {

            this.id = id;
            this.request = request;
            this.response = response;
            this.date = date;

        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }


        public View getView(Context context, View oldView) {

            if (oldView == null || !(oldView.getTag() instanceof Log)) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                oldView = inflater.inflate(R.layout.item_log, null);
                Holder holder = new Holder();
                oldView.setTag(holder);
                getItem(holder, oldView);
                return oldView;
            } else {
                Holder holder = (Holder) oldView.getTag();
                getItem(holder, oldView);
                return oldView;
            }
        }

        @Override
        public void setSelected(boolean flag) {

        }

        private void getItem(Holder holder, View view) {

            holder.log = this;

            if (holder.request == null)
                holder.request = (TextView) view.findViewById(R.id.request);

            if (holder.response == null)
                holder.response = (TextView) view.findViewById(R.id.response);

            if (holder.date == null)
                holder.date = (TextView) view.findViewById(R.id.date);

            holder.request.setText(getRequest());

            try {
                holder.response.setText(JsonFormatter.format(getResponse()));
            } catch (JSONException e) {
                e.printStackTrace();
                holder.response.setText(getResponse());
            }

            holder.date.setText(getDate());
        }


        public class Holder {
            TextView request;
            TextView response;
            TextView date;

            Log log;
        }

    }

}
