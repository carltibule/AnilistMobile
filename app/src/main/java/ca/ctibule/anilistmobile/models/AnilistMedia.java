package ca.ctibule.anilistmobile.models;

import org.jsoup.Jsoup;

import java.util.ArrayList;

import ca.ctibule.AnilistMobile.type.MediaFormat;
import ca.ctibule.AnilistMobile.type.MediaSeason;
import ca.ctibule.AnilistMobile.type.MediaStatus;

public class AnilistMedia {
    private int anilistId;
    private int malId;
    private MediaFormat mediaFormat;
    private MediaStatus mediaStatus;
    private String description;
    private String startDate;
    private String endDate;
    private MediaSeason mediaSeason;
    private int episodes;
    private int duration;
    private int chapters;
    private int volumes;
    private String countryOfOrigin;
    private boolean isLicensed;
    private String hashtag;
    private int updatedAt;
    private int averageScore;
    private int meanScore;
    private boolean isAdult;

    // Associated objects
    public MediaTitle title;
    public MediaTrailer trailer;
    public MediaImage image;
    public AiringEpisode nextAiringEpisode;

    // Associated arrays
    private ArrayList<String> genres;

    public AnilistMedia(){
        anilistId = 0;
        malId = 0;
        mediaFormat = null;
        mediaStatus = null;
        description = "";
        startDate = "";
        endDate = "";
        mediaSeason = null;
        episodes = 0;
        duration = 0;
        chapters = 0;
        volumes = 0;
        countryOfOrigin = "";
        isLicensed = false;
        hashtag = "";
        updatedAt = 0;
        averageScore = 0;
        meanScore = 0;
        isAdult = false;

        // Initialize associated models
        title = new MediaTitle();
        trailer = new MediaTrailer();
        image = new MediaImage();
        nextAiringEpisode = new AiringEpisode();

        // Initialize associated arrays
        genres = new ArrayList<>();
    }

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

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
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

    public int getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(int averageScore) {
        this.averageScore = averageScore;
    }

    public int getMeanScore() {
        return meanScore;
    }

    public void setMeanScore(int meanScore) {
        this.meanScore = meanScore;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void addGenre(String genre){
        this.genres.add(genre);
    }
}

