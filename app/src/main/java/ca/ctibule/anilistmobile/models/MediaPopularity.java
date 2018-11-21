package ca.ctibule.anilistmobile.models;

import java.util.ArrayList;

public class MediaPopularity {
    private int averageScore;
    private int meanScore;
    private int anilistFollowers;
    private int trending;
    private ArrayList<MediaRanking> rankings;

    public MediaPopularity(){
        this.averageScore = 0;
        this.meanScore = 0;
        this.anilistFollowers = 0;
        this.trending = 0;
        this.rankings = new ArrayList<>();
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

    public int getAnilistFollowers() {
        return anilistFollowers;
    }

    public void setAnilistFollowers(int anilistFollowers) {
        this.anilistFollowers = anilistFollowers;
    }

    public int getTrending() {
        return trending;
    }

    public void setTrending(int trending) {
        this.trending = trending;
    }

    public ArrayList<MediaRanking> getRankings() {
        return rankings;
    }

    public void addRanking(MediaRanking ranking){
        this.rankings.add(ranking);
    }
}
