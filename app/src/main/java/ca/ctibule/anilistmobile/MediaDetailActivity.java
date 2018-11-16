package ca.ctibule.anilistmobile;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;

import ca.ctibule.AnilistMobile.MediaByIdQuery;
import ca.ctibule.AnilistMobile.type.CustomType;
import ca.ctibule.anilistmobile.models.AnilistMedia;
import ca.ctibule.anilistmobile.models.MediaEpisode;
import ca.ctibule.anilistmobile.tasks.DownloadImageTask;
import okhttp3.OkHttpClient;

public class MediaDetailActivity extends AppCompatActivity implements MediaDetailSummaryFragment.OnFragmentInteractionListener,
EpisodesFragment.OnFragmentInteractionListener, ExternalLinksFragment.OnFragmentInteractionListener{

    private static final String ANILIST_API_URL= "https://graphql.anilist.co";
    private AnilistMedia media;
    private FragmentPagerAdapter adapterViewPager;
    ProgressBar mediaDetailProgressBar;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_detail);

        media = new AnilistMedia();
        mediaDetailProgressBar = findViewById(R.id.mediaDetailProgressBar);

        QueryAPIByIdTask queryAPIByIdTask = new QueryAPIByIdTask();
        queryAPIByIdTask.execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnu_appbar, menu);
        return true;
    }

    private MediaDetailActivity getOuter(){
        return this;
    }

    private void getMedia(ApolloClient apolloClient, int anilistId){
        // Query API using Id
        apolloClient.query(MediaByIdQuery.builder().id(anilistId).build()).enqueue(new ApolloCall.Callback<MediaByIdQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<MediaByIdQuery.Data> response) {
                // Get media from response
                MediaByIdQuery.Media mediaFromQuery = response.data().Media();

                // Get Id out of the query
                media.setAnilistId(mediaFromQuery.id());

                if(mediaFromQuery.idMal() != null){
                    media.setMalId(mediaFromQuery.idMal());
                }

                // Get general information associated with the media
                media.setMediaType(mediaFromQuery.type());
                media.setMediaFormat(mediaFromQuery.format());
                media.setMediaStatus(mediaFromQuery.status());
                media.setMediaSeason(mediaFromQuery.season());

                if(mediaFromQuery.description() != null){
                    media.setDescription(mediaFromQuery.description());
                }

                if(mediaFromQuery.startDate().year() != null && mediaFromQuery.startDate().month() != null &&
                        mediaFromQuery.startDate().day() != null){
                    media.setStartDate(mediaFromQuery.startDate().year(), mediaFromQuery.startDate().month(),
                            mediaFromQuery.startDate().day());
                }

                if(mediaFromQuery.endDate().year() != null && mediaFromQuery.endDate().month() != null &&
                        mediaFromQuery.endDate().day() != null){
                    media.setStartDate(mediaFromQuery.endDate().year(), mediaFromQuery.endDate().month(),
                            mediaFromQuery.endDate().day());
                }

                if(mediaFromQuery.episodes() != null){
                    media.setEpisodeCount(mediaFromQuery.episodes());
                }

                if(mediaFromQuery.volumes() != null){
                    media.setVolumes(mediaFromQuery.volumes());
                }

                if(mediaFromQuery.chapters() != null){
                    media.setChapters(mediaFromQuery.chapters());
                }

                if(mediaFromQuery.countryOfOrigin() != null){
                    media.setCountryOfOrigin(mediaFromQuery.countryOfOrigin());
                }

                if(mediaFromQuery.isLicensed() != null){
                    media.setLicensed(mediaFromQuery.isLicensed());
                }

                if(mediaFromQuery.hashtag() != null){
                    media.setHashtag(mediaFromQuery.hashtag());
                }

                if(mediaFromQuery.updatedAt() != null){
                    media.setUpdatedAt(mediaFromQuery.updatedAt());
                }

                if(mediaFromQuery.isAdult() != null){
                    media.setAdult(mediaFromQuery.isAdult());
                }

                if(mediaFromQuery.siteUrl() != null){
                    media.setSiteUrl(mediaFromQuery.siteUrl());
                }

                // Retrieve the titles associated with the media
                if(mediaFromQuery.title().romaji() != null){
                    media.title.setRomaji(mediaFromQuery.title().romaji());
                }

                if(mediaFromQuery.title().english() != null){
                    media.title.setEnglish(mediaFromQuery.title().english());
                }

                if(mediaFromQuery.title().native_() != null){
                    media.title.setNativeLanguage(mediaFromQuery.title().native_());
                }

                // Retrieve the images associated with the media
                if(mediaFromQuery.coverImage().extraLarge() != null){
                    media.image.setExtraLargeCoverImage(mediaFromQuery.coverImage().extraLarge());
                }

                if(mediaFromQuery.coverImage().large() != null){
                    media.image.setLargeCoverImage(mediaFromQuery.coverImage().large());
                }

                if(mediaFromQuery.coverImage().medium() != null){
                    media.image.setMediumCoverImage(mediaFromQuery.coverImage().medium());
                }

                if(mediaFromQuery.bannerImage() != null){
                    media.image.setBannerImage(mediaFromQuery.bannerImage());
                }

                if(mediaFromQuery.streamingEpisodes() != null){
                    for(MediaByIdQuery.StreamingEpisode streamingEpisode: mediaFromQuery.streamingEpisodes()){
                        MediaEpisode episode = new MediaEpisode();

                        episode.setTitle(streamingEpisode.title());
                        episode.setUrl(streamingEpisode.url());
                        episode.setThumbnail(streamingEpisode.thumbnail());
                        episode.setSite(streamingEpisode.site());

                        media.addMediaEpisode(episode);
                    }
                }

                // Reverse the order of objects in the collection
                Collections.reverse(media.getEpisodeCollection());

                if(mediaFromQuery.airingSchedule().nodes() != null){
                    for(MediaByIdQuery.Node node : mediaFromQuery.airingSchedule().nodes()){
                        MediaEpisode episode;
                        boolean isInCollection;

                        // Check if the episode can be retrieved from the collection
                        if(node.episode() - 1 >= media.getEpisodeCollection().size()){
                            episode = new MediaEpisode();
                            isInCollection = false;
                        }
                        else{
                            episode = media.getEpisode(node.episode() - 1);
                            isInCollection = true;
                        }

                        // Add more attributes
                        if(node.airingAt() != 0){
                            episode.setAiringAt(node.airingAt());
                        }

                        if(node.timeUntilAiring() != 0){
                            episode.setTimeUntilAiring(node.timeUntilAiring());
                        }

                        if(node.episode() != 0){
                            episode.setEpisode(node.episode());
                        }

                        // Get the episode back in the collection
                        if(isInCollection){
                            media.getEpisodeCollection().set(node.episode() - 1, episode);
                        }
                        else{
                            media.addMediaEpisode(episode);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                e.printStackTrace();
            }
        });
    }
    private class QueryAPIByIdTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            mediaDetailProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                CustomTypeAdapter<String> countryCodeAdapter = new CustomTypeAdapter<String>() {
                    @Override
                    public String decode(@NotNull CustomTypeValue value) {
                        try {
                            return value.value.toString();
                        }
                        catch (Exception e){
                            throw e;
                        }
                    }

                    @NotNull
                    @Override
                    public CustomTypeValue encode(@NotNull String value) {
                        return new CustomTypeValue.GraphQLString(value);
                    }
                };

                // Build apollo client
                OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                ApolloClient apolloClient = ApolloClient.builder().serverUrl(ANILIST_API_URL)
                        .addCustomTypeAdapter(CustomType.COUNTRYCODE, countryCodeAdapter)
                        .okHttpClient(okHttpClient).build();

                // Get anilist id
                int anilistId = getIntent().getIntExtra("AnilistID", 0);
                getMedia(apolloClient, anilistId);

                // Pause
                Thread.sleep(5000);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            //Remove progress bar
            mediaDetailProgressBar.setVisibility(View.GONE);

            ImageView imgDetailCoverImage = findViewById(R.id.img_detail_cover_image);
            TextView lblDetailTitle = findViewById(R.id.lbl_detail_title);
            TextView lblDetailDescription = findViewById(R.id.lbl_detail_description);

            if(lblDetailTitle != null){
                if(media.title.getRomaji() != null){
                    lblDetailTitle.setText(media.title.getRomaji());
                }
                else{
                    lblDetailTitle.setText(media.title.getEnglish());
                }
            }

            if(lblDetailDescription != null){
                lblDetailDescription.setText(media.getDescription());
            }

            if(imgDetailCoverImage != null){
                String coverImageLink = null;

                if(media.image.getMediumCoverImage() != null){
                    coverImageLink = media.image.getMediumCoverImage();
                }
                else{
                    coverImageLink = media.image.getLargeCoverImage();
                }

                new DownloadImageTask(imgDetailCoverImage).execute(coverImageLink);
            }

            // Send the object to fragment

            // Load the rest of the UI
            ViewPager viewPager = findViewById(R.id.viewPager);
            adapterViewPager = new PagerAdapter(getOuter(), getSupportFragmentManager(), media);
            viewPager.setAdapter(adapterViewPager);

            TabLayout tabLayout = findViewById(R.id.tabLayout);
            tabLayout.setupWithViewPager(viewPager);


        }
    }

    public static class PagerAdapter extends FragmentPagerAdapter{
        private static int NUM_ITEMS = 3;
        private Context context;
        private AnilistMedia media;

        public PagerAdapter(Context context, FragmentManager fragmentManager, AnilistMedia media){
            super(fragmentManager);
            this.context = context;
            this.media = media;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int i) {
            switch(i){
                case 0:
                    MediaDetailSummaryFragment mediaDetailSummaryFragment = new MediaDetailSummaryFragment();
                    Bundle detailsBundle = new Bundle();
                    detailsBundle.putParcelable("media", media);
                    mediaDetailSummaryFragment.setArguments(detailsBundle);
                    return mediaDetailSummaryFragment;
                case 1:
                    EpisodesFragment episodesFragment = new EpisodesFragment();
                    Bundle episodesBundle = new Bundle();
                    episodesBundle.putParcelableArrayList("episodes", media.getEpisodeCollection());
                    episodesFragment.setArguments(episodesBundle);
                    return episodesFragment;
                case 2:
                    return new ExternalLinksFragment();
                default:
                    return null;
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return context.getString(R.string.summary);
                case 1:
                    return context.getString(R.string.episodeCount);
                case 2:
                    return context.getString(R.string.links);
                default:
                    return null;
            }
        }
    }
}
