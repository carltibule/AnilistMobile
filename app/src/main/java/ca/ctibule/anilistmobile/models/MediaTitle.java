package ca.ctibule.anilistmobile.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MediaTitle implements Parcelable {
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

    public MediaTitle(Parcel source){
        romaji = source.readString();
        nativeLanguage = source.readString();
        english = source.readString();
        alternatives = source.readArrayList(String.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(romaji);
        dest.writeString(nativeLanguage);
        dest.writeString(english);
        dest.writeList(alternatives);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MediaTitle> CREATOR = new Creator<MediaTitle>() {
        @Override
        public MediaTitle createFromParcel(Parcel source) {
            return new MediaTitle(source);
        }

        @Override
        public MediaTitle[] newArray(int size) {
            return new MediaTitle[size];
        }
    };

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
