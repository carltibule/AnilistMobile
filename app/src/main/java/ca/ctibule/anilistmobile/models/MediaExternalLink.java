package ca.ctibule.anilistmobile.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MediaExternalLink implements Parcelable {
    private String site;
    private String url;

    public MediaExternalLink(){
        this.site = "";
        this.url = "";
    }

    public MediaExternalLink(Parcel source){
        this.site = source.readString();
        this.url = source.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.site);
        dest.writeString(this.url);
    }

    public static final Creator<MediaExternalLink> CREATOR = new Creator<MediaExternalLink>() {
        @Override
        public MediaExternalLink createFromParcel(Parcel source) {
            return new MediaExternalLink(source);
        }

        @Override
        public MediaExternalLink[] newArray(int size) {
            return new MediaExternalLink[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
