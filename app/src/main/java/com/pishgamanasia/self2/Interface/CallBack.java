package com.pishgamanasia.self2.Interface;

/**
 * Created by aliparsa on 8/10/2014.
 */

public interface CallBack<T> {

    public void onSuccess(T result);

    public void onError(String errorMessage);


}
