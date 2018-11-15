package ca.ctibule.anilistmobile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ctibule.anilistmobile.models.AnilistMedia;
import ca.ctibule.anilistmobile.models.MediaEpisode;
import ca.ctibule.anilistmobile.tasks.DownloadImageTask;

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

                    if(media.image.getMediumCoverImage() != null){
                        imageLink = media.image.getMediumCoverImage();
                    }
                    else{
                        imageLink = media.image.getLargeCoverImage();
                    }

                    new DownloadImageTask(imgCoverImage).execute(imageLink);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(lblTitle != null){
                if(media.title.getRomaji() != null){
                    lblTitle.setText(media.title.getRomaji());
                }
                else{
                    lblTitle.setText(media.title.getEnglish());
                }
            }

            if(lblDescription != null){
                lblDescription.setText(media.getShortDescription());
            }

            if(lblEpisodesAndDuration != null){
                String episodeCount = "";
                String duration = "";

                if(media.getEpisodeCount() < 1){
                    episodeCount = "?";
                }
                else{
                    episodeCount = String.valueOf(media.getEpisodeCount());
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
                            MediaEpisode.getCountdownFormat(media.nextAiringEpisode.getTimeUntilAiring()));
                }

                lblNextEpisode.setText(nextEpisodeLabel);
            }
        }

        return v;
    }
}
