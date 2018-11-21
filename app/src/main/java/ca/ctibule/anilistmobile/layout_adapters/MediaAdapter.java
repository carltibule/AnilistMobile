package ca.ctibule.anilistmobile.layout_adapters;

import android.content.Intent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ctibule.anilistmobile.MediaDetailActivity;
import ca.ctibule.anilistmobile.R;
import ca.ctibule.anilistmobile.models.AnilistMedia;
import ca.ctibule.anilistmobile.models.MediaEpisode;
import ca.ctibule.anilistmobile.tasks.DownloadImageTask;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder>{
    public class MediaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imgCoverImage;
        public TextView lblTitle;
        public TextView lblNextEpisode;
        public TextView lblEpisodesAndDuration;
        public TextView lblDescription;

        public MediaViewHolder(View itemView){
            super(itemView);

            itemView.setOnClickListener(this);

            imgCoverImage = itemView.findViewById(R.id.img_cover_image);
            lblTitle = itemView.findViewById(R.id.lbl_title);
            lblNextEpisode = itemView.findViewById(R.id.lbl_next_episode);
            lblEpisodesAndDuration = itemView.findViewById(R.id.lbl_episodes_and_duration);
            lblDescription = itemView.findViewById(R.id.lbl_description);
        }

        @Override
        public void onClick(View v) {
            if(onEntryClickListener != null){
                onEntryClickListener.onEntryClick(v, getLayoutPosition());
            }
        }
    }

    private ArrayList<AnilistMedia> mediaCollection;
    private Context context;

    public MediaAdapter(Context context, ArrayList<AnilistMedia> mediaCollection){
        this.mediaCollection = mediaCollection;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return this.mediaCollection.size();
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lyt_media, viewGroup, false);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder mediaViewHolder, int i) {
        AnilistMedia media = this.mediaCollection.get(i);

        if(mediaViewHolder.imgCoverImage != null){
            String imageLink = null;

            if(media.image.getMediumCoverImage() != null){
                imageLink = media.image.getMediumCoverImage();
            }
            else{
                imageLink = media.image.getLargeCoverImage();
            }

            new DownloadImageTask(mediaViewHolder.imgCoverImage).execute(imageLink);
        }

        if(mediaViewHolder.lblTitle != null){
            if(media.title.getRomaji() != null){
                mediaViewHolder.lblTitle.setText(media.title.getRomaji());
            }
            else{
                mediaViewHolder.lblTitle.setText(media.title.getEnglish());
            }
        }

        if(mediaViewHolder.lblDescription != null){
            mediaViewHolder.lblDescription.setText(media.getShortDescription());
        }

        if(mediaViewHolder.lblEpisodesAndDuration != null){
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

            mediaViewHolder.lblEpisodesAndDuration.setText(String.format("%s episodes x %s mins", episodeCount, duration));
        }

        if(mediaViewHolder.lblNextEpisode != null){
            String nextEpisodeLabel = "";

            if(media.nextAiringEpisode.getTimeUntilAiring() == 0){
                nextEpisodeLabel = String.format("Airing on %s", media.getStartDate());
            }
            else{
                nextEpisodeLabel = String.format("Episode %d airing in %s",
                        media.nextAiringEpisode.getEpisode(),
                        MediaEpisode.getCountdownFormat(media.nextAiringEpisode.getTimeUntilAiring()));
            }

            mediaViewHolder.lblNextEpisode.setText(nextEpisodeLabel);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private OnEntryClickListener onEntryClickListener;

    public interface OnEntryClickListener{
        void onEntryClick(View view, int position);
    }

    public void setOnEntryClickListener(OnEntryClickListener onEntryClickListener){
        this.onEntryClickListener = onEntryClickListener;
    }
}
