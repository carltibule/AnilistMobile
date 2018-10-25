package ca.ctibule.anilistmobile;

public class AnilistMedia {
    private int anilistId;
    private int malId;
    private String romajiTitle;
    private String englishTitle;
    private String nativeTitle;

    public AnilistMedia(){
        anilistId = 0;
        malId = 0;
        romajiTitle = "";
        englishTitle = "";
        nativeTitle = "";
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
}
