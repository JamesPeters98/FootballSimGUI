package com.james.footballsim.Simulator;

public class LeagueTypes {

    public static LeagueType PREMIER_DIVISION = new LeagueType() {
        @Override
        public String getName() {
            return "Premier Division";
        }

        @Override
        public void addTeams(League league) {
            league.addTeam(Teams.CHELSEA);
            league.addTeam(Teams.LIVERPOOL);
            league.addTeam(Teams.MAN_CITY);
            league.addTeam(Teams.TOTTENHAM);
            league.addTeam(Teams.ARSENAL);
            league.addTeam(Teams.MAN_UNITED);
            league.addTeam(Teams.EVERTON);
            league.addTeam(Teams.SOUTHAMPTON);
            league.addTeam(Teams.CRYS_PALACE);
            league.addTeam(Teams.BOURNEMOUTH);
            league.addTeam(Teams.LEICESTER);
            league.addTeam(Teams.SWANSEA);
            league.addTeam(Teams.WEST_BROM);
            league.addTeam(Teams.WEST_HAM);
            league.addTeam(Teams.BURNLEY);
            league.addTeam(Teams.HUDDERSFIELD);
            league.addTeam(Teams.NEWCASTLE);
            league.addTeam(Teams.WATFORD);
            league.addTeam(Teams.STOKE);
            league.addTeam(Teams.BRIGHTON);
        }

        @Override
        public int getFixtureLength() {
            return 38;
        }

        @Override
        public boolean hasPlayOffs() {
            return false;
        }

        @Override
        public int divisionPosition() {
            return 1;
        }

        @Override
        public boolean lastDivision() {
            return false;
        }
    };

    public static LeagueType SECOND_DIVISION = new LeagueType() {
        @Override
        public String getName() {
            return "Second Division";
        }

        @Override
        public void addTeams(League league) {
            league.addTeam(Teams.WOLVES);
            league.addTeam(Teams.CARDIFF);
            league.addTeam(Teams.FULHAM);
            league.addTeam(Teams.ASTON_VILLA);
            league.addTeam(Teams.MIDDLESBROUGH);
            league.addTeam(Teams.DERBY);
            league.addTeam(Teams.PRESTON_NORTH_END);
            league.addTeam(Teams.MILLWALL);
            league.addTeam(Teams.BRENTFORD);
            league.addTeam(Teams.SHEFFIELD_UNITED);
            league.addTeam(Teams.BRISTOL);
            league.addTeam(Teams.IPSWICH);
            league.addTeam(Teams.LEEDS);
            league.addTeam(Teams.NORWICH);
            league.addTeam(Teams.SHEFFIELD_WED);
            league.addTeam(Teams.QPR);
            league.addTeam(Teams.NOTTINGHAM);
            league.addTeam(Teams.HULL);
            league.addTeam(Teams.BIRMINGHAM);
            league.addTeam(Teams.READING);
            league.addTeam(Teams.BOLTON);
            league.addTeam(Teams.BARNSLEY);
            league.addTeam(Teams.BURTON);
            league.addTeam(Teams.SUNDERLAND);
        }

        @Override
        public int getFixtureLength() {
            return 49;
        }

        @Override
        public boolean hasPlayOffs() {
            return true;
        }

        @Override
        public int divisionPosition() {
            return 2;
        }

        @Override
        public boolean lastDivision() {
            return true;
        }
    };

}
