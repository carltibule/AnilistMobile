package ca.ctibule.anilistmobile.models;

public class MediaExternalLink {
    private String site;
    private String url;

    public MediaExternalLink(){
        site = "";
        url = "";
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
