package com.james.footballsim;

import com.james.footballsim.Simulator.League;
import com.james.footballsim.Simulator.LeagueStats;
import com.james.footballsim.Simulator.Team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Info implements Serializable {

    //Everything in this class gets stored.
    public Info(){

    }

    public HashMap<Integer, League> leagues;
    public List<Team> teams;
    public int teamId = -1;
    public int division;
    public int round;
    public boolean seasonRunning = false;

    public LeagueStats getTeamLeagueStats(){
        //Gdx.app.log("Info", "teamid: "+teamId);
        return leagues.get(division).getLeagueStats().get(teamId);
    }

    public int getTeamPosition(){
        ArrayList<LeagueStats> leagueStatsArray = new ArrayList<>(FootballSim.info.leagues.get(division).getLeagueStats().values());
        if(leagueStatsArray.size()==0) return 0;
        Utils.sortArray(leagueStatsArray);
        for(int i = 0; i <leagueStatsArray.size();i++) {
            LeagueStats stats = leagueStatsArray.get(i);
            if(stats.team!=null) {
                if (stats.team.id == teamId) return i + 1;
            } else {
                return 0;
            }
        }
        return 0;
    }

}
