package ca.ctibule.anilistmobile.models;

import java.util.ArrayList;

public class MediaTitle {
    private String romaji;
    private String nativeLanguage;
    private String english;
    private ArrayList<String> alternatives;

    public MediaTitle(){
        this.romaji = "";
        this.nativeLanguage = "";
        this.english = "";
        alternatives = new ArrayList<>();
    }

    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public ArrayList<String> getAlternatives() {
        return alternatives;
    }

    public void addAlternative(String alternative){
        this.alternatives.add(alternative);
    }
}
