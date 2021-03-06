package ca.ctibule.anilistmobile.layout_adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ctibule.anilistmobile.R;
import ca.ctibule.anilistmobile.models.MediaEpisode;
import ca.ctibule.anilistmobile.tasks.DownloadImageTask;

public class MediaEpisodesAdapter extends ArrayAdapter<MediaEpisode> {

    private ArrayList<MediaEpisode> mediaEpisodes = new ArrayList<>();

    public MediaEpisodesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MediaEpisode> episodes){
        super(context, resource, episodes);
        this.mediaEpisodes = episodes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if(v == null){
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.lyt_episode_list_view, null);
        }

        MediaEpisode episode = mediaEpisodes.get(position);

        if(episode != null){
            ImageView imgThumbnail = v.findViewById(R.id.img_thumbnail);
            TextView lblEpisodeTitle = v.findViewById(R.id.lbl_episode_title);
            TextView lblWatchOn = v.findViewById(R.id.lbl_watch_on);
            TextView lblAiringDate = v.findViewById(R.id.lbl_airing_date);

            try{
                if(imgThumbnail != null){
                    String imageLink = null;

                    if(episode.getThumbnail() != null){
                        imageLink = episode.getThumbnail();
                    }

                    new DownloadImageTask(imgThumbnail).execute(imageLink);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            if(lblEpisodeTitle != null){
                if(episode.getTitle() != ""){
                    lblEpisodeTitle.setText(episode.getTitle());
                }
                else{
                    lblEpisodeTitle.setText(String.format("Episode %s", episode.getEpisode()));
                }
            }

            if(lblWatchOn != null){
                if(episode.getSite() != "") {
                    lblWatchOn.setText(String.format("Watch on: %s", episode.getSite()));
                }
            }

            if(lblAiringDate != null){
                if(episode.getAiringAt() != 0){
                    lblAiringDate.setText(String.format("Airing Date: %d", episode.getAiringAt()));
                }
            }
        }

        return v;
    }
}
