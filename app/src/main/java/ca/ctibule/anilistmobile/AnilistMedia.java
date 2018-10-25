package ca.ctibule.anilistmobile;

import ca.ctibule.AnilistMobile.type.MediaFormat;
import ca.ctibule.AnilistMobile.type.MediaSeason;
import ca.ctibule.AnilistMobile.type.MediaSource;
import ca.ctibule.AnilistMobile.type.MediaStatus;
import ca.ctibule.AnilistMobile.type.MediaType;

public class AnilistMedia {
    private int anilistId;
    private int malId;
    private String romajiTitle;
    private String englishTitle;
    private String nativeTitle;
    private MediaType mediaType;
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
    private MediaSource mediaSource;
    private String hashtag;
    private String trailerId;
    private int updatedAt;
    private String largeCoverImage;
    private String mediumCoverImage;
    private String bannerImage;
    private int averageScore;
    private int meanScore;
    private boolean isAdult;


    public AnilistMedia(){
        anilistId = 0;
        malId = 0;
        romajiTitle = "";
        englishTitle = "";
        nativeTitle = "";
        mediaType = null;
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
        mediaSource = null;
        hashtag = "";
        trailerId = "";
        updatedAt = 0;
        largeCoverImage = "";
        mediumCoverImage = "";
        bannerImage = "";
        averageScore = 0;
        meanScore = 0;
        isAdult = false;
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

    public String getRomajiTitle() {
        return romajiTitle;
    }

    public void setRomajiTitle(String romajiTitle) {
        this.romajiTitle = romajiTitle;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public void setEnglishTitle(String englishTitle) {
        this.englishTitle = englishTitle;
    }

    public String getNativeTitle() {
        return nativeTitle;
    }

    public void setNativeTitle(String nativeTitle) {
        this.nativeTitle = nativeTitle;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public MediaSource getMediaSource() {
        return mediaSource;
    }

    public void setMediaSource(MediaSource mediaSource) {
        this.mediaSource = mediaSource;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(int updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLargeCoverImage() {
        return largeCoverImage;
    }

    public void setLargeCoverImage(String largeCoverImage) {
        this.largeCoverImage = largeCoverImage;
    }

    public String getMediumCoverImage() {
        return mediumCoverImage;
    }

    public void setMediumCoverImage(String mediumCoverImage) {
        this.mediumCoverImage = mediumCoverImage;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
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
}

