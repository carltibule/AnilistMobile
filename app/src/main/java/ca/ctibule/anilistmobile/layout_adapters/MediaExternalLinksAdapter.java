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

import org.w3c.dom.Text;

import java.util.ArrayList;

import ca.ctibule.anilistmobile.R;
import ca.ctibule.anilistmobile.models.MediaEpisode;
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
            TextView lblLink = v.findViewById(R.id.lbl_link);

            if(imgLinkIcon != null){
                imgLinkIcon.setImageResource(R.mipmap.internet_icon);
            }

            if(lblSiteName != null){
                lblSiteName.setText(externalLink.getSite());
            }

            if(lblLink != null){
                lblLink.setText(externalLink.getUrl());
            }
        }

        return v;
    }
}
