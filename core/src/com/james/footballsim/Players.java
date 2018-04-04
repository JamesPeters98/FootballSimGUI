package com.james.footballsim;

import java.util.*;

/**
 * Created by James on 08/02/2018.
 */
public class Players {

    HashMap<Integer,Player> players;

    public Players(){
        players = new HashMap<>();
    }

    public void add(Player player){
        players.put(player.id,player);
    }

    public void remove(Player player){
        //System.out.println("Players: "+players.size());
        players.remove(player.id);
        //System.out.println("Players after remove: "+players.size());
    }

    public List<Player> getList(){
        return new ArrayList<>(players.values());
    }


    public List<Player> getPlayers(int position){
        List<Player> subPlayers = new ArrayList<>();
        for(Player player : players.values()){
            if(player.type == position){
                subPlayers.add(player);
            }
        }
        return subPlayers;
    }


}
