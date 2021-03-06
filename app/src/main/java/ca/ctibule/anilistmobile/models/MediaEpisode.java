package ca.ctibule.anilistmobile.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MediaEpisode implements Parcelable {
    private long airingAt;
    private long timeUntilAiring;
    private int episode;
    private String title;
    private String thumbnail;
    private String url;
    private String site;

    public MediaEpisode(){
        this.airingAt = 0;
        this.timeUntilAiring = 0;
        this.episode = 0;
        this.title = "";
        this.thumbnail = "";
        this.url = "";
        this.site = "";

    }

    public MediaEpisode(Parcel source){
        airingAt = source.readLong();
        timeUntilAiring = source.readLong();
        episode = source.readInt();
        title = source.readString();
        thumbnail = source.readString();
        url = source.readString();
        site = source.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(airingAt);
        dest.writeLong(timeUntilAiring);
        dest.writeInt(episode);
        dest.writeString(title);
        dest.writeString(thumbnail);
        dest.writeString(url);
        dest.writeString(site);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MediaEpisode> CREATOR = new Creator<MediaEpisode>() {
        @Override
        public MediaEpisode createFromParcel(Parcel source) {
            return new MediaEpisode(source);
        }

        @Override
        public MediaEpisode[] newArray(int size) {
            return new MediaEpisode[size];
        }
    };

    public long getAiringAt() {
        return airingAt;
    }

    public void setAiringAt(long airingAt) {
        this.airingAt = airingAt;
    }

    public long getTimeUntilAiring() {
        return timeUntilAiring;
    }

    public void setTimeUntilAiring(long timeUntilAiring) {
        this.timeUntilAiring = timeUntilAiring;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public static String getCountdownFormat(long epoch){
        Date epochInDate = new Date(epoch * 1000);
        SimpleDateFormat days = new SimpleDateFormat("d");
        SimpleDateFormat hours = new SimpleDateFormat("H");
        SimpleDateFormat minutes = new SimpleDateFormat("m");

        // Calculate precise day, Anilist values seem to be 1 day behind the actual epoch time given by the API
        int adjustedDays = Integer.parseInt(days.format(epochInDate)) - 1;

        // Strip days out if adjustedDay is below 1
        if(adjustedDays < 1){
            return String.format("%sh %sm", hours.format(epochInDate), minutes.format(epochInDate));
        }
        else{
            return String.format("%sd %sh %sm", adjustedDays, hours.format(epochInDate), minutes.format(epochInDate));
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
