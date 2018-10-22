package ca.ctibule.anilistmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private static final String ANILIST_API_URL= "https://graphql.anilist.co";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder().serverUrl(ANILIST_API_URL).okHttpClient(okHttpClient).build();
    }
}
