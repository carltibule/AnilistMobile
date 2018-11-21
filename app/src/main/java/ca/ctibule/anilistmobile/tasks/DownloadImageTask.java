package ca.ctibule.anilistmobile.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView imgCoverImage;

    public DownloadImageTask(ImageView imgCoverImage){
        this.imgCoverImage = imgCoverImage;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String urlDisplay = strings[0];
        Bitmap mIcon11 = null;

        try{
            InputStream inputStream = new URL(urlDisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(inputStream);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imgCoverImage.setImageBitmap(bitmap);
    }
}

