package ca.ctibule.anilistmobile.models;

public class MediaImage {
    private String extraLargeCoverImage;
    private String largeCoverImage;
    private String mediumCoverImage;
    private String bannerImage;

    public MediaImage(){
        this.extraLargeCoverImage = "";
        this.largeCoverImage = "";
        this.mediumCoverImage = "";
        this.bannerImage = "";
    }

    public String getExtraLargeCoverImage() {
        return extraLargeCoverImage;
    }

    public void setExtraLargeCoverImage(String extraLargeCoverImage) {
        this.extraLargeCoverImage = extraLargeCoverImage;
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
}
