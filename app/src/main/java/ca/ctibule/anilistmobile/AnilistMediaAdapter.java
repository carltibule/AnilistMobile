package ca.ctibule.anilistmobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class AnilistMediaAdapter extends ArrayAdapter<AnilistMedia> {
    private ArrayList<AnilistMedia> anilistMedia = new ArrayList<AnilistMedia>();

    public AnilistMediaAdapter(@NonNull Context context, int resource, @NonNull ArrayList<AnilistMedia> anilistMedia) {
        super(context, resource, anilistMedia);
        this.anilistMedia = anilistMedia;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if(v == null){
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.lyt_media, null);
        }

        AnilistMedia media = anilistMedia.get(position);

        if(media != null){
            ImageView imgCoverImage = v.findViewById(R.id.img_cover_image);
            TextView lblTitle = v.findViewById(R.id.lbl_title);
            TextView lblDescription = v.findViewById(R.id.lbl_description);

            if(imgCoverImage != null){
                try{
                    String imageLink = null;

                    if(media.getMediumCoverImage() != null){
                        imageLink = media.getMediumCoverImage();
                    }
                    else{
                        imageLink = media.getLargeCoverImage();
                    }

                    new DownloadImageTask(imgCoverImage).execute(imageLink);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(lblTitle != null){
                if(media.getRomajiTitle() != null){
                    lblTitle.setText(media.getRomajiTitle());
                }
                else{
                    lblTitle.setText(media.getEnglishTitle());
                }
            }

            if(lblDescription != null){
                lblDescription.setText(media.getShortDescription());
            }
        }

        return v;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{
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
}
