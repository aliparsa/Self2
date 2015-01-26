package com.pishgamanasia.self2.DataModel;

/**
 * Created by parsa on 2015-01-25.
 */
public class Food {

    private String caption;
    private int count;


    public Food(String caption, int count) {
        this.caption = caption;
        this.count = count;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
