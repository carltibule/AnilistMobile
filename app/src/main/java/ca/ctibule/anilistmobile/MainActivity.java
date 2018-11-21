package ca.ctibule.anilistmobile;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

import ca.ctibule.AnilistMobile.MediaQuery;
import ca.ctibule.AnilistMobile.type.MediaSeason;
import ca.ctibule.anilistmobile.layout_adapters.AnilistMediaAdapter;
import ca.ctibule.anilistmobile.layout_adapters.MediaAdapter;
import ca.ctibule.anilistmobile.models.AnilistMedia;
import okhttp3.OkHttpClient;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private static final String ANILIST_API_URL= "https://graphql.anilist.co";
    private ArrayList<AnilistMedia> mediaList;
    private static boolean hasNextPage;
    ProgressBar progressBar;

    private RecyclerView lstMedia;
    private RecyclerView.Adapter mediaAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate necesary variables;
        mediaList = new ArrayList<AnilistMedia>();
        hasNextPage = true;
        progressBar = findViewById(R.id.progressBar);

        lstMedia = findViewById(R.id.lst_media);

        QueryAnilistAPITask queryAnilistAPITask = new QueryAnilistAPITask();
        queryAnilistAPITask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnu_appbar, menu);
        return true;
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
                        anilistMedia.title.setRomaji(medium.title().romaji());
                    }

                    if(medium.title().english() != null){
                        anilistMedia.title.setEnglish(medium.title().english());
                    }

                    if(medium.title().native_() != null){
                        anilistMedia.title.setNativeLanguage(medium.title().native_());
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

                    if(medium.episodes() != null){
                        anilistMedia.setEpisodeCount(medium.episodes());
                    }

                    if(medium.duration() != null){
                        anilistMedia.setDuration(medium.duration());
                    }

                    if(medium.coverImage().large() != null){
                        anilistMedia.image.setLargeCoverImage(medium.coverImage().large());
                    }

                    if(medium.coverImage().medium() != null){
                        anilistMedia.image.setMediumCoverImage(medium.coverImage().medium());
                    }

                    if(medium.nextAiringEpisode() != null){
                        anilistMedia.nextAiringEpisode.setAiringAt(medium.nextAiringEpisode().airingAt());
                        anilistMedia.nextAiringEpisode.setTimeUntilAiring(medium.nextAiringEpisode().timeUntilAiring());
                        anilistMedia.nextAiringEpisode.setEpisode(medium.nextAiringEpisode().episode());
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
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
                ApolloClient apolloClient = ApolloClient.builder().serverUrl(ANILIST_API_URL)
                        .okHttpClient(okHttpClient)
                        .build();

                // Retrieve media from Anilist API
                int page = 0;

                while(hasNextPage){
                    page += 1;
                    getMediaFromAnilistAPI(apolloClient, 2018, MediaSeason.FALL, page);
                    Thread.sleep(3000);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //Remove progress bar
            progressBar.setVisibility(View.GONE);

            layoutManager = new LinearLayoutManager(getOuter());
            lstMedia.setLayoutManager(layoutManager);

            mediaAdapter = new MediaAdapter(getOuter(), mediaList);
            lstMedia.setAdapter(mediaAdapter);
        }
    }


}
