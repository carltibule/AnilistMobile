package ca.ctibule.anilistmobile.layout_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ctibule.anilistmobile.R;
import ca.ctibule.anilistmobile.models.MediaExternalLink;

public class MediaExternalLinksAdapter extends ArrayAdapter<MediaExternalLink> {
    private ArrayList<MediaExternalLink> externalLinks = new ArrayList<>();

    public MediaExternalLinksAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MediaExternalLink> externalLinks){
        super(context, resource, externalLinks);
        this.externalLinks = externalLinks;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if(v == null){
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.lyt_external_links, null);
        }

        MediaExternalLink externalLink = this.externalLinks.get(position);

        if(externalLink != null){
            ImageView imgLinkIcon = v.findViewById(R.id.img_link_icon);
            TextView lblSiteName = v.findViewById(R.id.lbl_site_name);

            if(imgLinkIcon != null){

                if(externalLink.getSite().equalsIgnoreCase("twitter")){
                    imgLinkIcon.setImageResource(R.drawable.twitter);
                }
                else if(externalLink.getSite().equalsIgnoreCase("crunchyroll")){
                    imgLinkIcon.setImageResource(R.drawable.crunchyroll);
                }
                else if(externalLink.getSite().equalsIgnoreCase("funimation")){
                    imgLinkIcon.setImageResource(R.drawable.funimation);
                }
                else if(externalLink.getSite().equalsIgnoreCase("wakanim")){
                    imgLinkIcon.setImageResource(R.drawable.wakanim);
                }
                else{
                    imgLinkIcon.setImageResource(R.drawable.internet);
                }

            }

            if(lblSiteName != null){
                lblSiteName.setText(externalLink.getSite());
            }
        }

        return v;
    }
}
