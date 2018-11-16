package ca.ctibule.anilistmobile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.ctibule.AnilistMobile.type.MediaType;
import ca.ctibule.anilistmobile.models.AnilistMedia;

public class MediaDetailSummaryFragment extends Fragment {

    OnFragmentInteractionListener mListener;
    private AnilistMedia media;

    public MediaDetailSummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

        media = this.getArguments().getParcelable("media");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_media_detail_summary, container, false);

        // Check if controls exist
        TextView lblRomaji = rootView.findViewById(R.id.lbl_romaji);
        TextView lblEnglish = rootView.findViewById(R.id.lbl_english);
        TextView lblNative = rootView.findViewById(R.id.lbl_native);
        TextView lblType = rootView.findViewById(R.id.lbl_type);
        TextView lblFormat = rootView.findViewById(R.id.lbl_format);
        TextView lblCountry = rootView.findViewById(R.id.lbl_country);
        TextView lblIsLicensed = rootView.findViewById(R.id.lbl_is_licensed);
        TextView lblIsAdult = rootView.findViewById(R.id.lbl_is_adult);
        TextView lblHashtag = rootView.findViewById(R.id.lbl_hashtag);
        TextView lblSiteUrl = rootView.findViewById(R.id.lbl_site_url);

        if(lblRomaji != null){
            lblRomaji.setText(media.title.getRomaji());
        }

        if(lblEnglish != null){
            lblEnglish.setText(media.title.getEnglish());
        }

        if(lblNative != null){
            lblNative.setText(media.title.getNativeLanguage());
        }

        if(lblType != null){
            lblType.setText(media.getMediaType().name());
        }

        if(lblFormat != null){
            lblFormat.setText(media.getMediaFormat().name());
        }

        if(lblCountry != null){
            lblCountry.setText(media.getCountryOfOrigin());
        }

        if(lblIsLicensed != null){
            lblIsLicensed.setText(String.valueOf(media.isLicensed()));
        }

        if(lblIsAdult != null){
            lblIsAdult.setText(String.valueOf(media.isAdult()));
        }

        if(lblHashtag != null){
            lblHashtag.setText(String.valueOf(media.getHashtag()));
        }

        if(lblSiteUrl != null){
            lblSiteUrl.setText(media.getSiteUrl());
        }

        // Disable the appropriate group of details depending on its type
        ConstraintLayout grpMangaDetails = rootView.findViewById(R.id.grp_manga_details);
        ConstraintLayout grpAnimeDetails = rootView.findViewById(R.id.grp_anime_details);

        if(media.getMediaType().equals(MediaType.ANIME)){
            grpMangaDetails.setVisibility(View.GONE);

            TextView lblSeason = rootView.findViewById(R.id.lbl_season);
            TextView lblStartDate = rootView.findViewById(R.id.lbl_start_date);
            TextView lblEndDate = rootView.findViewById(R.id.lbl_end_date);
            TextView lblEpisodesAndDuration = rootView.findViewById(R.id.lbl_episodes_and_duration);

            if(lblSeason != null){
                lblSeason.setText(media.getMediaSeason().name());
            }

            if(lblStartDate != null){
                lblStartDate.setText(media.getStartDate());
            }

            if(lblEndDate != null){
                lblEndDate.setText(media.getEndDate());
            }

            if(lblEpisodesAndDuration != null){
                int episodeCount = media.getEpisodeCount();
                int duration = media.getDuration();

                if(episodeCount == 0 && duration == 0){
                    lblEpisodesAndDuration.setText("");
                }
                else if(episodeCount == 0){
                    lblEpisodesAndDuration.setText(String.format("%d mins.", duration));
                }
                else if(duration == 0){
                    lblEpisodesAndDuration.setText(String.format("%d eps.", episodeCount));
                }
                else {
                    lblEpisodesAndDuration.setText(String.format("%s eps. x %d mins.", media.getEpisodeCount(), media.getDuration()));
                }
            }
        }
        else{
            grpAnimeDetails.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
