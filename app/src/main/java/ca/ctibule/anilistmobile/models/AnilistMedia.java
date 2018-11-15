package ca.ctibule.anilistmobile.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.jsoup.Jsoup;

import java.util.ArrayList;

import ca.ctibule.AnilistMobile.type.MediaFormat;
import ca.ctibule.AnilistMobile.type.MediaSeason;
import ca.ctibule.AnilistMobile.type.MediaStatus;
import ca.ctibule.AnilistMobile.type.MediaType;

public class AnilistMedia implements Parcelable{
    private int anilistId;
    private int malId;
    private MediaType mediaType;
    private MediaFormat mediaFormat;
    private MediaStatus mediaStatus;
    private String description;
    private String startDate;
    private String endDate;
    private MediaSeason mediaSeason;
    private int episodeCount;
    private int duration;
    private int chapters;
    private int volumes;
    private String countryOfOrigin;
    private boolean isLicensed;
    private String hashtag;
    private int updatedAt;
    private boolean isAdult;
    private String siteUrl;

    // Associated objects
    public MediaTitle title;
    public MediaTrailer trailer;
    public MediaImage image;
    public MediaEpisode nextAiringEpisode;
    public MediaRanking mediaRanking;

    // Associated arrays
    private ArrayList<String> genres;
    private ArrayList<String> tags;
    private ArrayList<MediaExternalLink> externalLinks;
    private ArrayList<MediaEpisode> episodes;

    public AnilistMedia(){
        anilistId = 0;
        malId = 0;
        mediaType = null;
        mediaFormat = null;
        mediaStatus = null;
        description = "";
        startDate = "";
        endDate = "";
        mediaSeason = null;
        episodeCount = 0;
        duration = 0;
        chapters = 0;
        volumes = 0;
        countryOfOrigin = "";
        isLicensed = false;
        hashtag = "";
        updatedAt = 0;
        isAdult = false;
        siteUrl = "";
        // Initialize associated models
        title = new MediaTitle();
        trailer = new MediaTrailer();
        image = new MediaImage();
        nextAiringEpisode = new MediaEpisode();
        mediaRanking = new MediaRanking();

        // Initialize associated arrays
        genres = new ArrayList<>();
        tags = new ArrayList<>();
        externalLinks = new ArrayList<>();
        episodes = new ArrayList<>();
    }

    public AnilistMedia(Parcel source){
        anilistId = source.readInt();
        malId = source.readInt();
        mediaType = MediaType.valueOf(source.readString());
        mediaFormat = MediaFormat.valueOf(source.readString());
        mediaStatus = MediaStatus.valueOf(source.readString());
        mediaSeason = MediaSeason.valueOf(source.readString());
        description = source.readString();
        startDate = source.readString();
        endDate = source.readString();
        episodeCount = source.readInt();
        duration = source.readInt();
        volumes = source.readInt();
        chapters = source.readInt();
        countryOfOrigin = source.readString();
        isLicensed = (boolean)source.readValue(Boolean.class.getClassLoader());
        hashtag = source.readString();
        updatedAt = source.readInt();
        isAdult = (boolean)source.readValue(boolean.class.getClassLoader());
        siteUrl = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(anilistId);
        dest.writeInt(malId);
        dest.writeString(mediaType.name());
        dest.writeString(mediaFormat.name());
        dest.writeString(mediaStatus.name());
        dest.writeString(mediaSeason.name());
        dest.writeString(description);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeInt(episodeCount);
        dest.writeInt(duration);
        dest.writeInt(volumes);
        dest.writeInt(chapters);
        dest.writeString(countryOfOrigin);
        dest.writeValue(isLicensed);
        dest.writeString(hashtag);
        dest.writeInt(updatedAt);
        dest.writeValue(isAdult);
        dest.writeString(siteUrl);
    }

    public static final Creator<AnilistMedia> CREATOR = new Creator<AnilistMedia>() {
        @Override
        public AnilistMedia createFromParcel(Parcel source) {
            return new AnilistMedia(source);
        }

        @Override
        public AnilistMedia[] newArray(int size) {
            return new AnilistMedia[size];
        }
    };

    public int getAnilistId() {
        return anilistId;
    }

    public void setAnilistId(int anilistId) {
        this.anilistId = anilistId;
    }

    public int getMalId() {
        return malId;
    }

    public void setMalId(int malId) {
        this.malId = malId;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public MediaFormat getMediaFormat() {
        return mediaFormat;
    }

    public void setMediaFormat(MediaFormat mediaFormat) {
        this.mediaFormat = mediaFormat;
    }

    public MediaStatus getMediaStatus() {
        return mediaStatus;
    }

    public void setMediaStatus(MediaStatus mediaStatus) {
        this.mediaStatus = mediaStatus;
    }

    public String getShortDescription(){
        if(this.description.length() > 150){
            return String.format("%s...", this.description.substring(0, 150));
        }
        else{
            return this.description;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Jsoup.parse(description).text();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(int year, int month, int day) {
        this.startDate = String.format("%d-%d-%d", year, month, day);
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(int year, int month, int day) {
        this.endDate = String.format("%d-%d-%d", year, month, day);
    }

    public MediaSeason getMediaSeason() {
        return mediaSeason;
    }

    public void setMediaSeason(MediaSeason mediaSeason) {
        this.mediaSeason = mediaSeason;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getChapters() {
        return chapters;
    }

    public void setChapters(int chapters) {
        this.chapters = chapters;
    }

    public int getVolumes() {
        return volumes;
    }

    public void setVolumes(int volumes) {
        this.volumes = volumes;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public boolean isLicensed() {
        return isLicensed;
    }

    public void setLicensed(boolean licensed) {
        isLicensed = licensed;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(int updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void addGenre(String genre){
        this.genres.add(genre);
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void addTag(String tag){
        this.tags.add(tag);
    }

    public ArrayList<MediaExternalLink> getExternalLinks() {
        return externalLinks;
    }

    public void addExternalLink(MediaExternalLink externalLink){
        this.externalLinks.add(externalLink);
    }

    public ArrayList<MediaEpisode> getEpisodes() {
        return episodes;
    }

    public void addMediaEpisode(MediaEpisode mediaEpisode){
        this.episodes.add(mediaEpisode);
    }
}

