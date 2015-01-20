package com.pishgamanasia.self2.Interface;

import android.content.Context;
import android.view.View;

/**
 * Created by ashkan on 11/15/2014.
 */
public interface IListViewItem {

    View getView(Context context, View oldView);
    void setSelected(boolean flag);
}
