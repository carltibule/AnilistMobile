package ca.ctibule.anilistmobile.models;

import ca.ctibule.AnilistMobile.MediaQueryByIdQuery;

public class MediaStreamingEpisode {
    private String title;
    private String thumbnail;
    private String url;
    private String site;

    public MediaStreamingEpisode(){
        this.title = "";
        this.thumbnail = "";
        this.url = "";
        this.site = "";
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
