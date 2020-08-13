package com.triplegamaappsinc.downloadimage1;

import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImage extends AsyncTask<Integer, Integer, Integer> {

    Context context;
    String imageUrl;
    WeakReference<MainActivity> weakReference;

    public DownloadImage(Context context, String imageUrl, MainActivity mainActivity) {
        this.context = context;
        this.imageUrl = imageUrl;
        weakReference =new WeakReference<MainActivity>(mainActivity);
    }

    @Override
    protected Integer doInBackground(Integer... integers) {

        try {
            URL url = new URL(imageUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();

            int fileLength = httpURLConnection.getContentLength();
            System.out.println("LL: "+fileLength);

            InputStream inputStream = new BufferedInputStream(url.openStream());


//            File file = new File(context.getExternalFilesDir(null)+File.separator+"Downloads");
            String data1 = String.format(context.getExternalFilesDir(null).getAbsolutePath()+"/Downloads/");
            System.out.println("AB: "+data1);

            FileOutputStream stream = new FileOutputStream(data1+ "Nature123.jpg");


            byte[] data = new byte[5024];
            long total = 0;
            int count;
            while ((count = inputStream.read(data)) != -1) {
                total += count;
                // publishing the progress....
                publishProgress((int) (total * 100 / fileLength));
                stream.write(data, 0, count);
            }

            stream.flush();
            stream.close();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity mainActivity = weakReference.get();
        if(mainActivity == null || mainActivity.isFinishing())
        {
            return;
        }
        mainActivity.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        MainActivity mainActivity = weakReference.get();
        if(mainActivity == null || mainActivity.isFinishing())
        {
            return;
        }
        mainActivity.progressBar.setProgress(values[0]);
        mainActivity.textView.setText(Integer.toString(values[0])+"%");
//        Toast.makeText(mainActivity, ""+(values[0]), Toast.LENGTH_SHORT).show();
        System.out.println("PP: "+values[0]);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
//        Toast.makeText(context, "Downloading Completed", Toast.LENGTH_LONG).show();
    }
}
