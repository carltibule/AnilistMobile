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
import java.util.ArrayList;

import ca.ctibule.AnilistMobile.MediaQuery;
import ca.ctibule.AnilistMobile.type.CustomType;
import ca.ctibule.AnilistMobile.type.MediaSeason;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private static final String ANILIST_API_URL= "https://graphql.anilist.co";
    private ArrayList<AnilistMedia> mediaList = new ArrayList<AnilistMedia>();

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

        for(AnilistMedia media: mediaList){
            Log.d("GraphQL", "Anilist ID: " + String.valueOf(media.getAnilistId()));
            Log.d("GraphQL", "MAL ID: " + String.valueOf(media.getMalId()));
            Log.d("GraphQL", "Romaji: " + media.getRomajiTitle());
            Log.d("GraphQL", "English: " + media.getEnglishTitle());
            Log.d("GraphQL", "Native: " + media.getNativeTitle());
            Log.d("GraphQL", "MediaType: " + media.getMediaType().rawValue());
            Log.d("GraphQL", "MediaFormat: " + media.getMediaFormat().rawValue());
            Log.d("GraphQL", "MediaStatus: " + media.getMediaStatus().rawValue());
            Log.d("GraphQL", "Description: " + media.getDescription());
            Log.d("GraphQL", "StartDate: " + media.getStartDate());
            Log.d("GraphQL", "EndDate: " + media.getEndDate());
            Log.d("GraphQL", "Season: " + media.getMediaSeason().rawValue());
            Log.d("GraphQL", "Episodes: " + media.getEpisodes());
            Log.d("GraphQL", "Duration: " + media.getDuration());
            Log.d("GraphQL", "Chapters: " + media.getChapters());
            Log.d("GraphQL", "Volumes: " + media.getVolumes());
            Log.d("GraphQL", "CountryOfOrigin: " + media.getCountryOfOrigin());
            Log.d("GraphQL", "IsLicensed: " + media.isLicensed());
            Log.d("GraphQL", "Source: " + media.getMediaSource().rawValue());
            Log.d("GraphQL", "Hashtag: " + media.getHashtag());
            Log.d("GraphQL", "TrailerID: " + media.getTrailerId());
            Log.d("GraphQL", "UpdatedAt: " + media.getUpdatedAt());
            Log.d("GraphQL", "LargeCoverImage: " + media.getLargeCoverImage());
            Log.d("GraphQL", "MediumCoverImage: " + media.getMediumCoverImage());
            Log.d("GraphQL", "BannerImage: " + media.getBannerImage());
            Log.d("GraphQL", "AverageScore: " + media.getAverageScore());
            Log.d("GraphQL", "MeanScore: " + media.getMeanScore());
            Log.d("GraphQL", "IsAdult: " + media.isAdult());
            Log.d("GraphQL", "--------------------------------------------------");
        }
    }
}
