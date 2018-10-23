package ca.ctibule.anilistmobile;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import ca.ctibule.AnilistMobile.MediaQuery;
import ca.ctibule.AnilistMobile.type.MediaSeason;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private static final String ANILIST_API_URL= "https://graphql.anilist.co";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder().serverUrl(ANILIST_API_URL).okHttpClient(okHttpClient).build();

        apolloClient.query(
                MediaQuery.builder().page(1).season(MediaSeason.WINTER).year(2018).build()
        ).enqueue(new ApolloCall.Callback<MediaQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<MediaQuery.Data> response) {
                Log.d("GraphQL", response.toString());
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("GraphQL", e.getMessage());
            }
        });
    }
}
