package ca.ctibule.anilistmobile.models;

public class MediaRanking {
    private int rank;
    private String context;

    public MediaRanking(){
        this.rank = 0;
        this.context = "";
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
