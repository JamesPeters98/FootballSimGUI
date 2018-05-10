package com.james.footballsim.Simulator;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TeamUpdate {

    private String update;

    public String getUpdate(){
        return update;
    }

    TeamUpdate injury(Player player){
        update = player.getMatchName()+" is injured for "+player.injuryLength+" week[s].";
        return this;
    }

    TeamUpdate backFromInjury(Player player){
        update = player.getMatchName()+" is no longer injured.";
        return this;
    }

    TeamUpdate teamRating(Team team, int previous){
        NumberFormat format = new DecimalFormat("+#;-#");
        update = "New squad rating: "+team.getRating()+" ("+format.format(team.getRating()-previous)+")";
        return this;
    }

    TeamUpdate playerGrowth(Player player){
        update = player.getMatchName()+" just grew! ("+player.rating+")";
        return this;
    }

    TeamUpdate youthPromotion(Player player){
        update = "Youth player promoted! "+player.getMatchName()+" R:"+player.rating+" P:"+(player.rating+player.growth);
        return this;
    }
}
