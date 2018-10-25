package ca.ctibule.anilistmobile;

import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.exception.ApolloParseException;
import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ca.ctibule.AnilistMobile.MediaQuery;
import ca.ctibule.AnilistMobile.type.CustomType;
import ca.ctibule.AnilistMobile.type.MediaSeason;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private static final String ANILIST_API_URL= "https://graphql.anilist.co";
    private ArrayList<AnilistMedia> mediaList;
    private static boolean hasNextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate necesary variables;
        mediaList = new ArrayList<AnilistMedia>();
        hasNextPage = true;

        QueryAnilistAPITask queryAnilistAPITask = new QueryAnilistAPITask();
        queryAnilistAPITask.execute();
    }

    private MainActivity getOuter(){
        return MainActivity.this;
    }

    private void getMediaFromAnilistAPI(ApolloClient apolloClient, int year, MediaSeason season, int page){
        apolloClient.query(MediaQuery.builder().year(year).season(season).page(page).build()).enqueue(new ApolloCall.Callback<MediaQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<MediaQuery.Data> response) {
                hasNextPage = response.data().Page().pageInfo().hasNextPage();

                for(MediaQuery.Medium medium : response.data().Page().media()){
                    AnilistMedia anilistMedia = new AnilistMedia();

                    anilistMedia.setAnilistId(medium.id());

                    if(medium.idMal() != null){
                        anilistMedia.setMalId(medium.idMal());
                    }

                    if(medium.title().romaji() != null){
                        anilistMedia.setRomajiTitle(medium.title().romaji());
                    }

                    if(medium.title().english() != null){
                        anilistMedia.setEnglishTitle(medium.title().english());
                    }

                    if(medium.title().native_() != null){
                        anilistMedia.setNativeTitle(medium.title().native_());
                    }

                    if(medium.type() != null){
                        anilistMedia.setMediaType(medium.type());
                    }

                    if(medium.format() != null){
                        anilistMedia.setMediaFormat(medium.format());
                    }

                    if(medium.status() != null){
                        anilistMedia.setMediaStatus(medium.status());
                    }

                    if(medium.description() != null){
                        anilistMedia.setDescription(medium.description());
                    }

                    if(medium.startDate().year() != null && medium.startDate().month() != null && medium.startDate().day() != null){
                        anilistMedia.setStartDate(medium.startDate().year(), medium.startDate().month(), medium.startDate().day());
                    }

                    if(medium.endDate().year() != null && medium.endDate().month() != null && medium.endDate().day() != null){
                        anilistMedia.setEndDate(medium.endDate().year(), medium.endDate().month(), medium.endDate().day());
                    }

                    if(medium.season() != null){
                        anilistMedia.setMediaSeason(medium.season());
                    }

                    if(medium.episodes() != null){
                        anilistMedia.setEpisodes(medium.episodes());
                    }

                    if(medium.duration() != null){
                        anilistMedia.setDuration(medium.duration());
                    }

                    if(medium.chapters() != null){
                        anilistMedia.setChapters(medium.chapters());
                    }

                    if(medium.volumes() != null){
                        anilistMedia.setVolumes(medium.volumes());
                    }

                    if(medium.countryOfOrigin() != null){
                        anilistMedia.setCountryOfOrigin(medium.countryOfOrigin());
                    }

                    if(medium.isLicensed() != null){
                        anilistMedia.setLicensed(medium.isLicensed());
                    }

                    if(medium.source() != null){
                        anilistMedia.setMediaSource(medium.source());
                    }

                    if(medium.hashtag() != null){
                        anilistMedia.setHashtag(medium.hashtag());
                    }

                    if(medium.trailer() != null && medium.trailer().id() != null){
                        anilistMedia.setTrailerId(medium.trailer().id());
                    }

                    if(medium.updatedAt() != null){
                        anilistMedia.setUpdatedAt(medium.updatedAt());
                    }

                    if(medium.coverImage().large() != null){
                        anilistMedia.setLargeCoverImage(medium.coverImage().large());
                    }

                    if(medium.coverImage().medium() != null){
                        anilistMedia.setMediumCoverImage(medium.coverImage().medium());
                    }

                    if(medium.bannerImage() != null){
                        anilistMedia.setBannerImage(medium.bannerImage());
                    }

                    if(medium.averageScore() != null){
                        anilistMedia.setAverageScore(medium.averageScore());
                    }

                    if(medium.meanScore() != null){
                        anilistMedia.setMeanScore(medium.meanScore());
                    }

                    if(medium.isAdult() != null){
                        anilistMedia.setAdult(medium.isAdult());
                    }

                    mediaList.add(anilistMedia);
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("GraphQL", "Failure " + e.getMessage());
            }
        });
    }

    private class QueryAnilistAPITask extends AsyncTask<Void, Void, Void>{
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

                OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                ApolloClient apolloClient = ApolloClient.builder().serverUrl(ANILIST_API_URL)
                        .okHttpClient(okHttpClient)
                        .addCustomTypeAdapter(CustomType.COUNTRYCODE, countryCodeAdapter)
                        .build();

                // Retrieve media from Anilist API
                int page = 0;

                while(hasNextPage){
                    page += 1;
                    getMediaFromAnilistAPI(apolloClient, 2018, MediaSeason.FALL, page);
                    Thread.sleep(15000);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ListView lstViewAnime = findViewById(R.id.list_view_anime);
            AnilistMediaAdapter anilistMediaAdapter = new AnilistMediaAdapter(getOuter(), R.layout.lyt_media, mediaList);
            lstViewAnime.setAdapter(anilistMediaAdapter);
        }
    }
}
