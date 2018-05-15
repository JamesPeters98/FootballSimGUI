package uk.co.codeecho.fixture.generator;

import java.util.*;

import com.james.footballsim.Utils;
import com.james.footballsim.Simulator.Team;

public class FixtureGenerator {

    public List<List<Fixture<Integer>>> getFixtures(HashMap<Integer,Team> teamMap, boolean includeReverseFixtures) {

        List<Integer> teams = new ArrayList<>(teamMap.keySet());
        int numberOfTeams = teams.size();

        // If odd number of teams add a "ghost".
        boolean ghost = false;
        if (numberOfTeams % 2 != 0) {
            numberOfTeams++;
            ghost = true;
        }

        // Generate the fixtures using the cyclic algorithm.
        int totalRounds = numberOfTeams - 1;
        int matchesPerRound = numberOfTeams / 2;
        List<List<Fixture<Integer>>> rounds = new LinkedList<List<Fixture<Integer>>>();

        for (int round = 0; round < totalRounds; round++) {
            LinkedList<Fixture<Integer>> fixtures = new LinkedList<Fixture<Integer>>();
            for (int match = 0; match < matchesPerRound; match++) {
                int home = (round + match) % (numberOfTeams - 1);
                int away = (numberOfTeams - 1 - match + round) % (numberOfTeams - 1);
                // Last team stays in the same place while the others
                // rotate around it.
                if (match == 0) {
                    away = numberOfTeams - 1;
                }
                fixtures.add(new Fixture<Integer>(teams.get(home), teams.get(away)));
            }
            //Utils.putChosenTeamAtTopOfArray(fixtures, teamId);
            rounds.add(fixtures);
        }
        
        Utils.randomiseArray(rounds);

        // Interleave so that home and away games are fairly evenly dispersed.
        List<List<Fixture<Integer>>> interleaved = new LinkedList<List<Fixture<Integer>>>();

        int evn = 0;
        int odd = (numberOfTeams / 2);
        for (int i = 0; i < rounds.size(); i++) {
            if (i % 2 == 0) {
                interleaved.add(rounds.get(evn++));
            } else {
                interleaved.add(rounds.get(odd++));
            }
        }

        rounds = interleaved;

        // Last team can't be away for every game so flip them
        // to home on odd leagueGames.
        for (int roundNumber = 0; roundNumber < rounds.size(); roundNumber++) {
            if (roundNumber % 2 == 1) {
                Fixture fixture = rounds.get(roundNumber).get(0);
                rounds.get(roundNumber).set(0, new Fixture(fixture.getAwayTeam(), fixture.getHomeTeam()));
            }
        }
        
        if(includeReverseFixtures){
            List<List<Fixture<Integer>>> reverseFixtures = new LinkedList<List<Fixture<Integer>>>();
            for(List<Fixture<Integer>> round: rounds){
                List<Fixture<Integer>> reverseRound = new LinkedList<Fixture<Integer>>();
                for(Fixture<Integer> fixture: round){
                    reverseRound.add(new Fixture<Integer>(fixture.getAwayTeam(), fixture.getHomeTeam()));
                }
                reverseFixtures.add(reverseRound);
            }
            Collections.shuffle(reverseFixtures);
            rounds.addAll(reverseFixtures);
        }

        return rounds;
    }

}
