package ca.ctibule.anilistmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MediaDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_detail);
        Log.d("GraphQL", String.valueOf(getIntent().getIntExtra("AnilistID", 0)));
    }
}
