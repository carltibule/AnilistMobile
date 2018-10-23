package ca.ctibule.anilistmobile;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.exception.ApolloParseException;
import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;

import ca.ctibule.AnilistMobile.MediaQuery;
import ca.ctibule.AnilistMobile.type.CustomType;
import ca.ctibule.AnilistMobile.type.MediaSeason;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private static final String ANILIST_API_URL= "https://graphql.anilist.co";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        apolloClient.query(MediaQuery.builder().year(2018).season(MediaSeason.FALL).page(1).build()).enqueue(new ApolloCall.Callback<MediaQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<MediaQuery.Data> response) {
                for(MediaQuery.Medium medium : response.data().Page().media()){
                    Log.d("GraphQL", medium.title().romaji());
                    Log.d("GraphQL", medium.description());
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d("GraphQL", "Failure " + e.getMessage());
            }
        });
    }
}
