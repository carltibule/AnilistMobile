package ca.ctibule.anilistmobile;

import java.text.SimpleDateFormat;

public class AiringEpisode {
    long airingAt;
    long timeUntilAiring;
    int episode;

    public AiringEpisode(){
        this.airingAt = 0;
        this.timeUntilAiring = 0;
        this.episode = 0;
    }

    public long getAiringAt() {
        return airingAt;
    }

    public void setAiringAt(long airingAt) {
        this.airingAt = airingAt;
    }

    public long getTimeUntilAiring() {
        return timeUntilAiring;
    }

    public void setTimeUntilAiring(long timeUntilAiring) {
        this.timeUntilAiring = timeUntilAiring;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public static String getCountdownFormat(long epoch){
        SimpleDateFormat days = new SimpleDateFormat("d");
        SimpleDateFormat hours = new SimpleDateFormat("H");
        SimpleDateFormat minutes = new SimpleDateFormat("m");

        // Calculate precise day, Anilist values seem to be 1 day behind the actual epoch time given by the API
        int adjustedDays = Integer.parseInt(days.format(epoch)) - 1;

        // Strip days out if adjustedDay is below 1
        if(adjustedDays < 1){
            return String.format("%sh %sm", hours.format(epoch), minutes.format(epoch));
        }
        else{
            return String.format("%sd %sh %sm", days.format(epoch), hours.format(epoch), minutes.format(epoch));
        }
    }
}
