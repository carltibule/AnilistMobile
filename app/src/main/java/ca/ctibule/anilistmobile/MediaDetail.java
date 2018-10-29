package ca.ctibule.anilistmobile;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import ca.ctibule.AnilistMobile.MediaByIdQuery;
import ca.ctibule.AnilistMobile.type.CustomType;
import ca.ctibule.anilistmobile.models.AnilistMedia;
import okhttp3.OkHttpClient;

public class MediaDetail extends AppCompatActivity {

    private static final String ANILIST_API_URL= "https://graphql.anilist.co";
    static AnilistMedia media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_detail);

        QueryAPIByIdTask queryAPIByIdTask = new QueryAPIByIdTask();
        queryAPIByIdTask.execute();
    }

    private void getMedia(ApolloClient apolloClient, int anilistId){
        media = new AnilistMedia();

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
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("GraphQL", media.title.getRomaji());
            Log.d("GraphQL", media.title.getEnglish());

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
        }
    }
}
