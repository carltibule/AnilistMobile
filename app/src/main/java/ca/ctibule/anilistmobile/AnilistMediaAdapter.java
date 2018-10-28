package ca.ctibule.anilistmobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import ca.ctibule.anilistmobile.models.AiringEpisode;
import ca.ctibule.anilistmobile.models.AnilistMedia;

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
            TextView lblEpisodesAndDuration = v.findViewById(R.id.lbl_episodes_and_duration);
            TextView lblNextEpisode = v.findViewById(R.id.lbl_next_episode);

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
                if(media.titles.getRomaji() != null){
                    lblTitle.setText(media.titles.getRomaji());
                }
                else{
                    lblTitle.setText(media.titles.getEnglish());
                }
            }

            if(lblDescription != null){
                lblDescription.setText(media.getShortDescription());
            }

            if(lblEpisodesAndDuration != null){
                String episodeCount = "";
                String duration = "";

                if(media.getEpisodes() < 1){
                    episodeCount = "?";
                }
                else{
                    episodeCount = String.valueOf(media.getEpisodes());
                }

                if(media.getDuration() < 1){
                    duration = "?";
                }
                else{
                    duration = String.valueOf(media.getDuration());
                }

                lblEpisodesAndDuration.setText(String.format("%s episodes x %s mins", episodeCount, duration));
            }

            if(lblNextEpisode != null){
                String nextEpisodeLabel = "";

                if(media.nextAiringEpisode.getTimeUntilAiring() == 0){
                    nextEpisodeLabel = String.format("Airing on %s", media.getStartDate());
                }
                else{
                    nextEpisodeLabel = String.format("Episode %d airing in %s",
                            media.nextAiringEpisode.getEpisode(),
                            AiringEpisode.getCountdownFormat(media.nextAiringEpisode.getTimeUntilAiring()));
                }

                lblNextEpisode.setText(nextEpisodeLabel);
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
