package com.pishgamanasia.self2.Helper;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.pishgamanasia.self2.Interface.CallBack;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by parsa on 2014-12-03.
 */
public class SoapHelper {

    String NAMESPACE;
    String METHOD_NAME;
    String URL;
    String SOAP_ACTION1;
    Context context;


    public SoapHelper(Context context,String namespace, String methodName, String url, String soapAction) {
        this.context=context;
        this.NAMESPACE = namespace;
        this.METHOD_NAME = methodName;
        this.URL = url;
        this.SOAP_ACTION1 = soapAction;
    }

    public void SendRequestToServer(final ArrayList<String> names, final ArrayList<String> values, final CallBack<JSONObject> callback) {


        new AsyncTaskLoader<JSONObject>(context) {

            @Override
            public JSONObject loadInBackground() {

                try {

                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                    for (int i = 0; i < names.size(); i++) {
                        request.addProperty(names.get(i), values.get(i));
                    }

                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.setOutputSoapObject(request);
                    envelope.dotNet = true;

                    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                    //this is the actual part that will call the webservice
                    androidHttpTransport.call(SOAP_ACTION1, envelope);


                    // log send and receive data
                    LogHelper log = new LogHelper(context);
                    log.InsertLog(new PersianCalendar().getGregorianDateTime(), envelope.bodyOut.toString(), envelope.bodyIn.toString());

                    // Get the SoapResult from the envelope body.
                    SoapObject result = (SoapObject) envelope.bodyIn;

                    if (result != null) {
                        // Extract JSON Result From Response
                        JSONObject jsonReceivedData = new JSONObject(result.getProperty(0).toString());
                        return (jsonReceivedData);

                    } else {
                        return null;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
        }

    }



    @Override
    public void deliverResult(JSONObject data) {

        if (data != null) {

            callback.onSuccess(data);
            super.deliverResult(data);

        } else {

            callback.onError("Server Response Not Valid");
        }
    }
        }.forceLoad();

    }

    public void SendRequestToServer(final SoapObject sObj, final CallBack<JSONObject> callback) {


        new AsyncTaskLoader<JSONObject>(context) {

            @Override
            public JSONObject loadInBackground() {

                try {

                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                    request.addSoapObject(sObj);

                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.setOutputSoapObject(request);
                    envelope.dotNet = true;

                    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                    //this is the actual part that will call the webservice
                    androidHttpTransport.call(SOAP_ACTION1, envelope);


                    // log send and receive data
                    LogHelper log = new LogHelper(context);
                    log.InsertLog(new PersianCalendar().getGregorianDateTime(), envelope.bodyOut.toString(), envelope.bodyIn.toString());

                    // Get the SoapResult from the envelope body.
                    SoapObject result = (SoapObject) envelope.bodyIn;

                    if (result != null) {
                        // Extract JSON Result From Response
                        JSONObject jsonReceivedData = new JSONObject(result.getProperty(0).toString());
                        return (jsonReceivedData);

                    } else {
                        return null;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }



            @Override
            public void deliverResult(JSONObject data) {

                if (data != null) {

                    callback.onSuccess(data);
                    super.deliverResult(data);

                } else {

                    callback.onError("Server Response Not Valid");
                }
            }
        }.forceLoad();

    }
}
