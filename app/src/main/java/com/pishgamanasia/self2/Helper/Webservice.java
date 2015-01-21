package com.pishgamanasia.self2.Helper;

import android.content.Context;

import com.pishgamanasia.self2.DataModel.Personnel;
import com.pishgamanasia.self2.Interface.CallBack;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;


/**
 * Created by aliparsa on 8/9/2014.
 */


public class Webservice {

    private static final int RESULT_OK =100;
    private static final int RESULT_ERROR =101;


    public static void GetPersonelInfo(Context context,final String cardNo, final CallBack<Personnel> callback) {

        try {
            SettingHelper setting = new SettingHelper(context);
            String SERVER_ADDRESS = setting.getOption("serverAddress");
            if (SERVER_ADDRESS==null)
                SERVER_ADDRESS="http://192.168.0.11:6061";

            final String NAMESPACE = SERVER_ADDRESS+"/Areas/Buffet/Service/";
            final String METHOD_NAME = "GetPersonelInfo";
            final String URL = SERVER_ADDRESS+"/areas/buffet/service/webserviceAndroid.asmx?op=GetPersonelInfo";
            final String SOAP_ACTION =SERVER_ADDRESS+ "/Areas/Buffet/Service/GetPersonelInfo";

            SoapHelper soapHelper = new SoapHelper(context,NAMESPACE, METHOD_NAME, URL, SOAP_ACTION);

            ArrayList<String> names = new ArrayList<String>();
            ArrayList<String> values = new ArrayList<String>();

            names.add("cardNo");
            values.add(cardNo);


            soapHelper.SendRequestToServer(names,values, new CallBack<JSONObject>() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {

                        int resultCode = resultCode = result.getInt("ResultCode");

                        switch (resultCode) {
                            case RESULT_OK: {
                               /* String token = result.getString("token");
                                String name = result.getString("name");
                                int resturantId = result.getInt("restaurantId");
                                String resturantName = result.getString("restaurantName");
                                String deliverPersonel = result.getString("deliverPersonel");
                                String meal = result.getString("meal");
                                ArrayList<Meal> meals= Meal.getArrayFromJson(meal);
                                */

                                int id = result.getInt("PersonelId");
                                String firstName = result.getString("FName");
                                String lastName = result.getString("LName");
                                double finalCredit = result.getDouble("finalcredit");
                                String code = result.getString("Code");


                                callback.onSuccess(new Personnel(id,firstName,lastName,code,finalCredit));

                                break;
                            }
                            case RESULT_ERROR: {
                                callback.onError(result.getString("ErrorMessage"));
                                break;
                            }
                            default: {
                                callback.onError("server response is not valid ");
                                break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    callback.onError(errorMessage);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //-----------------------------------------------------------------------------
//    public static void GetMenuFoods(Context context,final String date, final String cardNo, final CallBack callback) {
//
//        try {
//            SettingHelper setting = new SettingHelper(context);
//            String SERVER_ADDRESS = setting.getOption("serverAddress");
//            if (SERVER_ADDRESS==null)
//                SERVER_ADDRESS="http://192.168.0.11:6061";
//
//            final String NAMESPACE = SERVER_ADDRESS+"/Areas/Buffet/Service/";
//            final String METHOD_NAME = "DeliveryByCode";
//            final String URL = SERVER_ADDRESS+"/areas/buffet/service/webserviceAndroid.asmx?op=DeliveryByCode";
//            final String SOAP_ACTION =SERVER_ADDRESS+ "/Areas/Buffet/Service/GetStep1";
//
//            SoapHelper soapHelper = new SoapHelper(context,NAMESPACE, METHOD_NAME, URL, SOAP_ACTION);
//
//            ArrayList<String> names = new ArrayList<String>();
//            ArrayList<String> values = new ArrayList<String>();
//
//            names.add("date");
//            values.add(date);
//
//            names.add("cardNo");
//            values.add(cardNo);
//
//
//            soapHelper.SendRequestToServer(names,values, new CallBack<JSONObject>() {
//                @Override
//                public void onSuccess(JSONObject result) {
//                    try {
//
//                        int resultCode = resultCode = result.getInt("ResultCode");
//
//                        switch (resultCode) {
//                            case RESULT_OK: {
//                               /* String token = result.getString("token");
//                                String name = result.getString("name");
//                                int resturantId = result.getInt("restaurantId");
//                                String resturantName = result.getString("restaurantName");
//                                String deliverPersonel = result.getString("deliverPersonel");
//                                String meal = result.getString("meal");
//                                ArrayList<Meal> meals= Meal.getArrayFromJson(meal);
//                                callback.onSuccess(new LoginInfo(token, name, resturantId, resturantName, deliverPersonel,meals));
//                                */
//                                break;
//                            }
//                            case RESULT_ERROR: {
//                                callback.onError(result.getString("ErrorMessage"));
//                                break;
//                            }
//                            default: {
//                                callback.onError("server response is not valid ");
//                                break;
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(String errorMessage) {
//                    callback.onError(errorMessage);
//                }
//            });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //-----------------------------------------------------------------------------
//    public static void GetReserves(Context context,final String date, final String cardNo, final CallBack callback) {
//
//        try {
//            SettingHelper setting = new SettingHelper(context);
//            String SERVER_ADDRESS = setting.getOption("serverAddress");
//            if (SERVER_ADDRESS==null)
//                SERVER_ADDRESS="http://192.168.0.11:6061";
//
//            final String NAMESPACE = SERVER_ADDRESS+"/Areas/Buffet/Service/";
//            final String METHOD_NAME = "GetReserves";
//            final String URL = SERVER_ADDRESS+"/areas/buffet/service/webserviceAndroid.asmx?op=GetReserves";
//            final String SOAP_ACTION =SERVER_ADDRESS+ "/Areas/Buffet/Service/GetStep1";
//
//            SoapHelper soapHelper = new SoapHelper(context,NAMESPACE, METHOD_NAME, URL, SOAP_ACTION);
//
//            ArrayList<String> names = new ArrayList<String>();
//            ArrayList<String> values = new ArrayList<String>();
//
//            names.add("date");
//            values.add(date);
//
//            names.add("cardNo");
//            values.add(cardNo);
//
//            soapHelper.SendRequestToServer(names,values, new CallBack<JSONObject>() {
//                @Override
//                public void onSuccess(JSONObject result) {
//                    try {
//
//                        int resultCode = resultCode = result.getInt("ResultCode");
//
//                        switch (resultCode) {
//                            case RESULT_OK: {
//                                String token = result.getString("token");
//                                String name = result.getString("name");
//                                int resturantId = result.getInt("restaurantId");
//                                String resturantName = result.getString("restaurantName");
//                                String deliverPersonel = result.getString("deliverPersonel");
//                                String meal = result.getString("meal");
//                                ArrayList<Meal> meals= Meal.getArrayFromJson(meal);
//                                callback.onSuccess(new LoginInfo(token, name, resturantId, resturantName, deliverPersonel,meals));
//                                break;
//                            }
//                            case RESULT_ERROR: {
//                                callback.onError("نام و یا کلمه عبور اشتباه است");
//                                break;
//                            }
//                            default: {
//                                callback.onError("server response is not valid ");
//                                break;
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(String errorMessage) {
//                    callback.onError(errorMessage);
//                }
//            });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    //-----------------------------------------------------------------------------
//    public static void DeliveryByCode(Context context,final String code, final String type, final String deviceId, final CallBack callback) {
//
//        try {
//            SettingHelper setting = new SettingHelper(context);
//            String SERVER_ADDRESS = setting.getOption("serverAddress");
//            if (SERVER_ADDRESS==null)
//                SERVER_ADDRESS="http://192.168.0.11:6061/";
//
//            final String NAMESPACE = SERVER_ADDRESS+"/Areas/Buffet/Service/";
//            final String METHOD_NAME = "DeliveryByCode";
//            final String URL = SERVER_ADDRESS+"/areas/buffet/service/webserviceAndroid.asmx?op=DeliveryByCode";
//            final String SOAP_ACTION =SERVER_ADDRESS+ "/Areas/Buffet/Service/GetStep1";
//
//            SoapHelper soapHelper = new SoapHelper(context,NAMESPACE, METHOD_NAME, URL, SOAP_ACTION);
//
//            ArrayList<String> names = new ArrayList<String>();
//            ArrayList<String> values = new ArrayList<String>();
//
//            names.add("code");
//            values.add(code);
//
//            names.add("type");
//            values.add(type);
//
//            names.add("deviceId");
//            values.add(deviceId);
//
//            soapHelper.SendRequestToServer(names,values, new CallBack<JSONObject>() {
//                @Override
//                public void onSuccess(JSONObject result) {
//                    try {
//
//                        int resultCode = resultCode = result.getInt("ResultCode");
//
//                        switch (resultCode) {
//                            case RESULT_OK: {
//                                String token = result.getString("token");
//                                String name = result.getString("name");
//                                int resturantId = result.getInt("restaurantId");
//                                String resturantName = result.getString("restaurantName");
//                                String deliverPersonel = result.getString("deliverPersonel");
//                                String meal = result.getString("meal");
//                                ArrayList<Meal> meals= Meal.getArrayFromJson(meal);
//                                callback.onSuccess(new LoginInfo(token, name, resturantId, resturantName, deliverPersonel,meals));
//                                break;
//                            }
//                            case RESULT_ERROR: {
//                                callback.onError("نام و یا کلمه عبور اشتباه است");
//                                break;
//                            }
//                            default: {
//                                callback.onError("server response is not valid ");
//                                break;
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(String errorMessage) {
//                    callback.onError(errorMessage);
//                }
//            });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //-----------------------------------------------------------------------------
//    public static void AddReserve(Context context,final String code, final String type, final String deviceId, final CallBack callback) {
//
//        try {
//            SettingHelper setting = new SettingHelper(context);
//            String SERVER_ADDRESS = setting.getOption("serverAddress");
//            if (SERVER_ADDRESS==null)
//                SERVER_ADDRESS="http://192.168.0.11:6061";
//
//            final String NAMESPACE = SERVER_ADDRESS+"/Areas/Buffet/Service/";
//            final String METHOD_NAME = "AddReserve";
//            final String URL = SERVER_ADDRESS+"/areas/buffet/service/webserviceAndroid.asmx?op=AddReserve";
//            final String SOAP_ACTION =SERVER_ADDRESS+ "/Areas/Buffet/Service/AddReserve";
//
//            SoapHelper soapHelper = new SoapHelper(context,NAMESPACE, METHOD_NAME, URL, SOAP_ACTION);
//
//            SoapObject sObj = new SoapObject(NAMESPACE, METHOD_NAME);
//
//            //for
//            SoapObject resObj = new SoapObject("","");
//
//            resObj.addProperty("Id","65465");
//            resObj.addProperty("Date","2015/01/19T12:10:00");
//            resObj.addProperty("DateShamsi","1393/01/19T12:10:00");
//            resObj.addProperty("PlanningId","654312");
//            resObj.addProperty("PayedPrice","5461651");
//            resObj.addProperty("FoodId","651654");
//            resObj.addProperty("MealId","4565");
//            resObj.addProperty("RestaurantId","65456");
//            resObj.addProperty("FoodType","1");
//            resObj.addProperty("FoodCaption","fgfdgs");
//            resObj.addProperty("Count","1");
//            resObj.addProperty("TotalPrice","654131");
//
//            sObj.addSoapObject(resObj);
//            //end for
//
//            soapHelper.SendRequestToServer(sObj, new CallBack<JSONObject>() {
//                @Override
//                public void onSuccess(JSONObject result) {
//                    try {
//
//                        int resultCode = resultCode = result.getInt("ResultCode");
//
//                        switch (resultCode) {
//                            case RESULT_OK: {
//                                String token = result.getString("token");
//                                String name = result.getString("name");
//                                int resturantId = result.getInt("restaurantId");
//                                String resturantName = result.getString("restaurantName");
//                                String deliverPersonel = result.getString("deliverPersonel");
//                                String meal = result.getString("meal");
//                                ArrayList<Meal> meals= Meal.getArrayFromJson(meal);
//                                callback.onSuccess(new LoginInfo(token, name, resturantId, resturantName, deliverPersonel,meals));
//                                break;
//                            }
//                            case RESULT_ERROR: {
//                                callback.onError("نام و یا کلمه عبور اشتباه است");
//                                break;
//                            }
//                            default: {
//                                callback.onError("server response is not valid ");
//                                break;
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(String errorMessage) {
//                    callback.onError(errorMessage);
//                }
//            });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    //-----------------------------------------------------------------------------
//    public static void Login(Context context,final String username, final String password, final String deviceId, final CallBack<LoginInfo> callback) {
//
//        try {
//            SettingHelper setting = new SettingHelper(context);
//            String SERVER_ADDRESS = setting.getOption("serverAddress");
//            if (SERVER_ADDRESS==null)
//                SERVER_ADDRESS="http://192.168.0.11:6061/";
//
//            final String NAMESPACE = SERVER_ADDRESS+"/Areas/Buffet/Service/";
//            final String METHOD_NAME = "GetStep1";
//            final String URL = SERVER_ADDRESS+"/areas/buffet/service/webserviceAndroid.asmx?op=GetStep1";
//            final String SOAP_ACTION =SERVER_ADDRESS+ "/Areas/Buffet/Service/GetStep1";
//
//            SoapHelper soapHelper = new SoapHelper(context,NAMESPACE, METHOD_NAME, URL, SOAP_ACTION);
//
//            ArrayList<String> names = new ArrayList<String>();
//            ArrayList<String> values = new ArrayList<String>();
//
//            names.add("username");
//            values.add(username);
//
//            names.add("password");
//            values.add(password);
//
//            names.add("deviceId");
//            values.add(deviceId);
//
//            soapHelper.SendRequestToServer(names,values, new CallBack<JSONObject>() {
//                @Override
//                public void onSuccess(JSONObject result) {
//                    try {
//
//                        int resultCode = resultCode = result.getInt("ResultCode");
//
//                        switch (resultCode) {
//                            case RESULT_OK: {
//                                String token = result.getString("token");
//                                String name = result.getString("name");
//                                int resturantId = result.getInt("restaurantId");
//                                String resturantName = result.getString("restaurantName");
//                                String deliverPersonel = result.getString("deliverPersonel");
//                                String meal = result.getString("meal");
//                                ArrayList<Meal> meals= Meal.getArrayFromJson(meal);
//                                callback.onSuccess(new LoginInfo(token, name, resturantId, resturantName, deliverPersonel,meals));
//                                break;
//                            }
//                            case RESULT_ERROR: {
//                                callback.onError("نام و یا کلمه عبور اشتباه است");
//                                break;
//                            }
//                            default: {
//                                callback.onError("server response is not valid ");
//                                break;
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(String errorMessage) {
//                    callback.onError(errorMessage);
//                }
//            });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    //--------------------------------------------------------------------------
//    public static void sendCard(Context context,final String cardNo, final CallBack<ServerCardResponse> callback) {
//
//        try {
//            SettingHelper setting = new SettingHelper(context);
//            String SERVER_ADDRESS = setting.getOption("serverAddress");
//
//            final String NAMESPACE = SERVER_ADDRESS+"/Areas/Buffet/Service/";
//            final String METHOD_NAME = "GetStep2";
//            final String URL = SERVER_ADDRESS+"/areas/buffet/service/webserviceAndroid.asmx?op=GetStep2";
//            final String SOAP_ACTION =SERVER_ADDRESS+ "/Areas/Buffet/Service/GetStep2";
//
//            SoapHelper soapHelper = new SoapHelper(context,NAMESPACE, METHOD_NAME, URL, SOAP_ACTION);
//
//            ArrayList<String> names = new ArrayList<String>();
//            ArrayList<String> values = new ArrayList<String>();
//
//            names.add("token");
//            values.add(AccountHelper.getInstant(context).getToken());
//
//            names.add("cardNo");
//            values.add(cardNo);
//
//
//            soapHelper.SendRequestToServer(names,values, new CallBack<JSONObject>() {
//                @Override
//                public void onSuccess(JSONObject result) {
//                    try {
//
//                        int resultCode = resultCode = result.getInt("ResultCode");
//
//                        switch (resultCode) {
//                            case RESULT_OK: {
//
//                                ArrayList<Personnel> personnels = Personnel.getArrayFromJson(result.getString("Personels"));
//                                ArrayList<Reserve> reserves = Reserve.getArrayFromJson(result.getString("Reserves"));
//                                String message = result.getString("Message");
//                                String status = result.getString("Status");
//                                callback.onSuccess(new ServerCardResponse(personnels,reserves,message,status));
//                                break;
//                            }
//
//                            default: {
//                                callback.onError("server response is not valid ");
//                                break;
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(String errorMessage) {
//                    callback.onError(errorMessage);
//                }
//            });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    //-------------------------------------------------------------------------------
//    public static void sendTahvil(Context context,String strJsonListReserve,final CallBack callback){
//
//        try {
//
//            SettingHelper setting = new SettingHelper(context);
//            String SERVER_ADDRESS = setting.getOption("serverAddress");
//
//            final String NAMESPACE = SERVER_ADDRESS+"/Areas/Buffet/Service/";
//            final String METHOD_NAME = "GetStep3";
//            final String URL = SERVER_ADDRESS+"/areas/buffet/service/webserviceAndroid.asmx?op=GetStep3";
//            final String SOAP_ACTION =SERVER_ADDRESS+ "/Areas/Buffet/Service/GetStep3";
//
//            SoapHelper soapHelper = new SoapHelper(context,NAMESPACE, METHOD_NAME, URL, SOAP_ACTION);
//
//            ArrayList<String> names = new ArrayList<String>();
//            ArrayList<String> values = new ArrayList<String>();
//
//            names.add("token");
//            values.add(AccountHelper.getInstant(context).getToken());
//
//            names.add("listReserveId");
//            values.add(strJsonListReserve);
//
//
//            soapHelper.SendRequestToServer(names,values, new CallBack<JSONObject>() {
//                @Override
//                public void onSuccess(JSONObject result) {
//                    try {
//
//                        int resultCode = resultCode = result.getInt("ResultCode");
//
//                        switch (resultCode) {
//                            case RESULT_OK: {
//                                callback.onSuccess(null);
//                                break;
//                            }
//                            case RESULT_ERROR: {
//                                callback.onError("server return error");
//                                break;
//                            }
//
//                            default: {
//                                callback.onError("server response is not valid ");
//                                break;
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(String errorMessage) {
//
//                }
//            });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//    //-------------------------------------------------------------------------------

}