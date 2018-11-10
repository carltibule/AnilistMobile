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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;

import org.jetbrains.annotations.NotNull;

import ca.ctibule.AnilistMobile.MediaByIdQuery;
import ca.ctibule.AnilistMobile.type.CustomType;
import ca.ctibule.anilistmobile.models.AnilistMedia;
import ca.ctibule.anilistmobile.tasks.DownloadImageTask;
import okhttp3.OkHttpClient;

public class MediaDetailActivity extends AppCompatActivity implements MediaDetailSummaryFragment.OnFragmentInteractionListener,
EpisodesFragment.OnFragmentInteractionListener, ExternalLinksFragment.OnFragmentInteractionListener{

    private static final String ANILIST_API_URL= "https://graphql.anilist.co";
    private AnilistMedia media;
    private FragmentPagerAdapter adapterViewPager;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_detail);

        media = new AnilistMedia();

        QueryAPIByIdTask queryAPIByIdTask = new QueryAPIByIdTask();
        queryAPIByIdTask.execute();

        ViewPager viewPager = findViewById(R.id.viewPager);
        adapterViewPager = new PagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnu_appbar, menu);
        return true;
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

                if(mediaFromQuery.title().romaji() != null){
                    media.title.setRomaji(mediaFromQuery.title().romaji());
                }

                if(mediaFromQuery.title().english() != null){
                    media.title.setEnglish(mediaFromQuery.title().english());
                }

                if(mediaFromQuery.title().native_() != null){
                    media.title.setNativeLanguage(mediaFromQuery.title().native_());
                }

                if(mediaFromQuery.description() != null){
                    media.setDescription(mediaFromQuery.description());
                }

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
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                e.printStackTrace();
            }
        });
    }
    private class QueryAPIByIdTask extends AsyncTask<Void, Void, Void>{
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
        }
    }

    public static class PagerAdapter extends FragmentPagerAdapter{
        private static int NUM_ITEMS = 3;
        private Context context;

        public PagerAdapter(Context context, FragmentManager fragmentManager){
            super(fragmentManager);
            this.context = context;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int i) {
            switch(i){
                case 0:
                    return new MediaDetailSummaryFragment();
                case 1:
                    return new EpisodesFragment();
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
                    return context.getString(R.string.episodes);
                case 2:
                    return context.getString(R.string.links);
                default:
                    return null;
            }
        }
    }
}
