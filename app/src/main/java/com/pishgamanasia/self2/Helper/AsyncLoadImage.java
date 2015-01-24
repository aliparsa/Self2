package com.pishgamanasia.self2.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.LruCache;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class AsyncLoadImage extends AsyncTask<String, String, Bitmap> {


    private static int imageNumber = 0;


    static int cacheSizeInKB = 1024 * 20;
    static LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(cacheSizeInKB) {

        @Override
        protected void entryRemoved(boolean evicted, String key, Bitmap old, Bitmap newBitmap) {
        }

        /**re ite
         * Measure item size in kilobytes rather than units,
         * which is more practical for a bitmap cache
         */
        @Override
        protected int sizeOf(String key, Bitmap value) {
            final int bitmapSize = value.getByteCount() / 1024;
            return bitmapSize == 0 ? 1 : bitmapSize;
        }
    };


    DiskLruImageCache cacheDisk;

    private String imageUrl;
    private ProgressCallBack<Bitmap> callback;

    public AsyncLoadImage(Context context, String imageUrl, ProgressCallBack<Bitmap> callback) {

        this.imageUrl = imageUrl;
        this.callback = callback;

        //cacheDisk = new DiskLruImageCache(context, "uniqueCache", cacheSizeInKB, Bitmap.CompressFormat.JPEG, 300);

    }

    /**
     * Before starting background thread Show Progress Bar Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Downloading file in background thread
     */
    @Override
    protected Bitmap doInBackground(String... f_url) {
        int count;
        try {

            Bitmap image = cache.get(imageUrl);

            if(image != null){
                return image;
            }else {

                URL url = new URL(imageUrl);
                URLConnection connection = url.openConnection();
                connection.connect();
                // this will be useful so that you can show a tipical 0-100% progress
                // bar
                int lengthOfFile = connection.getContentLength();

                if(lengthOfFile == 0){

                   return null;
                }

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream
                String path = Environment.getExternalStorageDirectory().toString();
                File file = new File(path, "download" + (imageNumber++) + ".jpg");
                OutputStream output = new FileOutputStream(file);


                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));


                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

                image = BitmapFactory.decodeFile(file.getPath());
                cache.put(imageUrl, image);

                file.delete();

                return image;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {

        callback.onProgress(Integer.parseInt(values[0]), 0, null);
    }

    /**
     * After completing background task Dismiss the progress dialog
     * *
     */
    @Override
    protected void onPostExecute(Bitmap file) {

        if (file == null) {
            callback.onError("on error in response handler");
        } else {
            callback.onSuccess(file, imageUrl);
        }

    }


    public interface ProgressCallBack<T> {

        public void onSuccess(T result, String url);

        public void onError(String errorMessage);

        public void onProgress(int done, int total, T result);


    }

}