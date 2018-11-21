package ca.ctibule.anilistmobile.models;

public class MediaTrailer {
    private String id;
    private String site;

    public MediaTrailer(){
        this.id = "";
        this.site = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
