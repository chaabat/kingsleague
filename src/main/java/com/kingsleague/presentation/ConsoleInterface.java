package com.kingsleague.presentation;

import com.kingsleague.controller.GameController;
import com.kingsleague.controller.PlayerController;
import com.kingsleague.controller.TeamController;
import com.kingsleague.controller.TournamentController;
import com.kingsleague.model.Game;
import com.kingsleague.model.Player;
import com.kingsleague.model.Team;
import com.kingsleague.model.Tournament;
import com.kingsleague.model.enums.TournamentStatut;
import com.kingsleague.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
import java.util.Set;

import javax.persistence.EntityNotFoundException;
 

public class ConsoleInterface {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleInterface.class);

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";

    // Emojis
    private static final String PLAYER = "🦸🏼‍♂️🦸🏻‍♀️";
    private static final String TEAM = "👫";
    private static final String GAME = "🕹️";
    private static final String TOURNAMENT = "🎖️🏆🎖️";
    private static final String SUCCESS = "✅";
    private static final String ERROR = "❌";
    private static final String WARNING = "⚠️";
    private static final String MENU = "📋";
    private static final String EXIT = "👋";
    private static final String EDIT = "✏️";
    private static final String DELETE = "🗑️";
    private static final String VIEW = "👀";
    private static final String ADDING = "➕";
    private static final String REMOVE = "➖";
    private static final String TIME = "⌚";
    private static final String STATUS = "🔄";
    

    private Scanner scanner;
    private PlayerController playerController;
    private TeamController teamController;
    private GameController gameController;
    private TournamentController tournamentController;

    public ConsoleInterface() {
        scanner = new Scanner(System.in);
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        playerController = context.getBean(PlayerController.class);
        teamController = context.getBean(TeamController.class);
        gameController = context.getBean(GameController.class);
        tournamentController = context.getBean(TournamentController.class);
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int choice = getIntInput(CYAN + "Enter your choice: " + RESET, 1, 5);
            switch (choice) {
                case 1:
                    handlePlayerMenu();
                    break;
                case 2:
                    handleTeamMenu();
                    break;
                case 3:
                    handleGameMenu();
                    break;
                case 4:
                    handleTournamentMenu();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    logger.error(RED + ERROR + " Invalid choice. Please try again." + RESET);
            }
        }
        logger.info(GREEN + EXIT + " Thank you for using the Kings League Tournament Management System!" + RESET);
    }

    private void printMainMenu() {
        logger.info("\n" + PURPLE + MENU + " --- Kings League  --- " + MENU + RESET);
        logger.info(BLUE + "1. " + PLAYER + " Player Management" + RESET);
        logger.info(BLUE + "2. " + TEAM + " Team Management" + RESET);
        logger.info(BLUE + "3. " + GAME + " Game Management" + RESET);
        logger.info(BLUE + "4. " + TOURNAMENT + " Tournament Management" + RESET);
        logger.info(BLUE + "5. " + EXIT + " Exit" + RESET);
    }

    private void handlePlayerMenu() {
        boolean back = false;
        while (!back) {
            logger.info("\n" + PURPLE + PLAYER + " --- Player Management --- " + PLAYER + RESET);
            logger.info(BLUE + "1. " + ADDING + " Create Player" + RESET);
            logger.info(BLUE + "2. " + VIEW + " View All Players" + RESET);
            logger.info(BLUE + "3. " + EDIT + " Edit Player" + RESET);
            logger.info(BLUE + "4. " + DELETE + " Delete Player" + RESET);
            logger.info(BLUE + "5. " + EXIT + " Back to Main Menu" + RESET);
            int choice = getIntInput(CYAN + "Enter your choice: " + RESET, 1, 5);
            switch (choice) {
                case 1:
                    createPlayer();
                    break;
                case 2:
                    viewAllPlayers();
                    break;
                case 3:
                    editPlayer();
                    break;
                case 4:
                    deletePlayer();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    logger.error(RED + ERROR + " Invalid choice. Please try again." + RESET);
            }
        }
    }

    private void createPlayer() {
        logger.info("\n" + YELLOW + ADDING + " Creating a new player:" + RESET);
        String username = getStringInput(CYAN + "Enter username: " + RESET, 3, 50);
        int age = getIntInput(CYAN + "Enter age: " + RESET, 13, 100);

        Player player = new Player();
        player.setUsername(username);
        player.setAge(age);

        try {
            playerController.createPlayer(player);
            logger.info(GREEN + SUCCESS + " Player created successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error creating player: " + e.getMessage() + RESET);
        }
    }

    private void viewAllPlayers() {
        List<Player> players = playerController.getAllPlayers();
        if (players.isEmpty()) {
            logger.info(YELLOW + WARNING + " No players found." + RESET);
        } else {
            logger.info("\n" + BLUE + VIEW + " All Players:" + RESET);
            for (Player player : players) {
                logger.info(CYAN + "Username: " + player.getUsername()
                        + ", Age: " + player.getAge()
                        + ", Team: " + (player.getTeam() != null ? player.getTeam().getName() : "N/A") + RESET);
            }
        }
    }

    private void editPlayer() {
        String username = getStringInput(CYAN + "Enter the username of the player to edit: " + RESET);
        Optional<Player> playerOpt = playerController.getPlayerByUsername(username);
        if (!playerOpt.isPresent()) {
            logger.error(RED + ERROR + " Player not found." + RESET);
            return;
        }
        Player player = playerOpt.get();

        logger.info(YELLOW + EDIT + " Editing player: " + username + RESET);
        String newUsername = getStringInput(CYAN + "Enter new username (3-50 characters, press enter to keep current): " + RESET);
        if (!newUsername.isEmpty()) {
            player.setUsername(newUsername);
        }

        int newAge = getIntInput(CYAN + "Enter new age (13-100, press enter to keep current): " + RESET, 13, 100);
        if (newAge != -1) {
            player.setAge(newAge);
        }

        try {
            playerController.updatePlayer(player);
            logger.info(GREEN + SUCCESS + " Player updated successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error updating player: " + e.getMessage() + RESET);
        }
    }

    private void deletePlayer() {
        String username = getStringInput(CYAN + "Enter the username of the player to delete: " + RESET);
        try {
            playerController.deletePlayerByUsername(username);
            logger.info(GREEN + SUCCESS + " Player deleted successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error deleting player: " + e.getMessage() + RESET);
        }
    }

    private void handleTeamMenu() {
        boolean back = false;
        while (!back) {
            logger.info("\n" + PURPLE + TEAM + " --- Team Management --- " + TEAM + RESET);
            logger.info(BLUE + "1. " + ADDING + " Create Team" + RESET);
            logger.info(BLUE + "2. " + VIEW + " View All Teams" + RESET);
            logger.info(BLUE + "3. " + EDIT + " Edit Team" + RESET);
            logger.info(BLUE + "4. " + DELETE + " Delete Team" + RESET);
            logger.info(BLUE + "5. " + ADDING + " Add Player to Team" + RESET);
            logger.info(BLUE + "6. " + REMOVE + " Remove Player from Team" + RESET);
            logger.info(BLUE + "7. " + EXIT + " Back to Main Menu" + RESET);
            int choice = getIntInput(CYAN + "Enter your choice: " + RESET, 1, 7);
            switch (choice) {
                case 1:
                    createTeam();
                    break;
                case 2:
                    viewAllTeams();
                    break;
                case 3:
                    editTeam();
                    break;
                case 4:
                    deleteTeam();
                    break;
                case 5:
                    addPlayerToTeam();
                    break;
                case 6:
                    removePlayerFromTeam();
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    logger.error(RED + ERROR + " Invalid choice. Please try again." + RESET);
            }
        }
    }

    private void createTeam() {
        logger.info("\n" + YELLOW + ADDING + " Creating a new team:" + RESET);
        String name = getStringInput(CYAN + "Enter team name: " + RESET, 3, 50);
        int ranking = getIntInput(CYAN + "Enter team ranking: " + RESET, 1, 1000);

        Team team = new Team();
        team.setName(name);
        team.setRanking(ranking);

        try {
            teamController.createTeam(team);
            logger.info(GREEN + SUCCESS + " Team created successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error creating team: " + e.getMessage() + RESET);
        }
    }

    private void viewAllTeams() {
        List<Team> teams = teamController.getAllTeams();
        if (teams.isEmpty()) {
            logger.info(YELLOW + WARNING + " No teams found." + RESET);
        } else {
            logger.info("\n" + BLUE + VIEW + " All Teams:" + RESET);
            for (Team team : teams) {
                logger.info(CYAN + "Name: " + team.getName()
                        + ", Ranking: " + team.getRanking()
                        + ", Players: " + team.getPlayers().size() + RESET);
            }
        }
    }

    private void editTeam() {
        String name = getStringInput(CYAN + "Enter the name of the team to edit: " + RESET);
        Optional<Team> teamOpt = teamController.getTeamByName(name);
        if (!teamOpt.isPresent()) {
            logger.error(RED + ERROR + " Team not found." + RESET);
            return;
        }
        Team team = teamOpt.get();

        logger.info(YELLOW + EDIT + " Editing team: " + name + RESET);
        String newName = getStringInput(CYAN + "Enter new team name (3-50 characters, press enter to keep current): " + RESET);
        if (!newName.isEmpty()) {
            // Update the team name using the existing updateTeam method
            team.setName(newName);
            try {
                teamController.updateTeam(team);
                logger.info(GREEN + SUCCESS + " Team name updated successfully." + RESET);
            } catch (IllegalArgumentException e) {
                logger.error(RED + ERROR + " Error updating team name: " + e.getMessage() + RESET);
            }
        }

        int newRanking = getIntInput(CYAN + "Enter new team ranking (1-1000, press enter to keep current): " + RESET, 1, 1000);
        if (newRanking != -1) {
            team.setRanking(newRanking);
        }

        try {
            teamController.updateTeam(team);
            logger.info(GREEN + SUCCESS + " Team updated successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error updating team: " + e.getMessage() + RESET);
        }
    }

    private void deleteTeam() {
        System.out.println("Enter the name of the team to delete: ");
        String teamName = scanner.nextLine();
        try {
            teamController.deleteTeamByName(teamName);
            System.out.println("Team deleted successfully.");
        } catch (EntityNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred while deleting the team: " + e.getMessage());
        }
    }

    private void addPlayerToTeam() {
        String teamName = getStringInput(CYAN + "Enter team name: " + RESET);
        String playerUsername = getStringInput(CYAN + "Enter player username: " + RESET);

        try {
            teamController.addPlayerToTeam(teamName, playerUsername);
            logger.info(GREEN + SUCCESS + " Player added to the team successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error adding player to team: " + e.getMessage() + RESET);
        }
    }

    private void removePlayerFromTeam() {
        String teamName = getStringInput(CYAN + "Enter team name: " + RESET);
        String playerUsername = getStringInput(CYAN + "Enter player username: " + RESET);

        try {
            teamController.removePlayerFromTeam(teamName, playerUsername);
            logger.info(GREEN + SUCCESS + " Player removed from the team successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error removing player from team: " + e.getMessage() + RESET);
        }
    }

    private void handleGameMenu() {
        boolean back = false;
        while (!back) {
            logger.info("\n" + PURPLE + GAME + " --- Game Management --- " + GAME + RESET);
            logger.info(BLUE + "1. " + ADDING + " Create Game" + RESET);
            logger.info(BLUE + "2. " + VIEW + " View All Games" + RESET);
            logger.info(BLUE + "3. " + EDIT + " Edit Game" + RESET);
            logger.info(BLUE + "4. " + DELETE + " Delete Game" + RESET);
            logger.info(BLUE + "5. " + EXIT + " Back to Main Menu" + RESET);
            int choice = getIntInput(CYAN + "Enter your choice: " + RESET, 1, 5);
            switch (choice) {
                case 1:
                    createGame();
                    break;
                case 2:
                    viewAllGames();
                    break;
                case 3:
                    editGame();
                    break;
                case 4:
                    deleteGame();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    logger.error(RED + ERROR + " Invalid choice. Please try again." + RESET);
            }
        }
    }

    private void createGame() {
        logger.info("\n" + YELLOW + ADDING + " Creating a new game:" + RESET);
        String name = getStringInput(CYAN + "Enter game name: " + RESET, 3, 50);
        int difficulty = getIntInput(CYAN + "Enter game difficulty: " + RESET, 1, 10);
        int averageMatchDuration = getIntInput(CYAN + "Enter average match duration in minutes: " + RESET, 1, 180);

        Game game = new Game();
        game.setName(name);
        game.setDifficulty(difficulty);
        game.setAverageMatchDuration(averageMatchDuration);

        try {
            gameController.createGame(game);
            logger.info(GREEN + SUCCESS + " Game created successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error creating game: " + e.getMessage() + RESET);
        }
    }

    private void viewAllGames() {
        List<Game> games = gameController.getAllGames();
        if (games.isEmpty()) {
            logger.info(YELLOW + WARNING + " No games found." + RESET);
        } else {
            logger.info("\n" + BLUE + VIEW + " All Games:" + RESET);
            for (Game game : games) {
                logger.info(CYAN + "Name: " + game.getName()
                        + ", Difficulty: " + game.getDifficulty()
                        + ", Average Match Duration: " + game.getAverageMatchDuration() + " minutes" + RESET);
            }
        }
    }

    private void editGame() {
        String title = getStringInput(CYAN + "Enter the name of the game to edit: " + RESET);
        Optional<Game> gameOpt = gameController.getGameByTitle(title);
        if (!gameOpt.isPresent()) {
            logger.error(RED + ERROR + " Game not found." + RESET);
            return;
        }
        Game game = gameOpt.get();

        logger.info(YELLOW + EDIT + " Editing game: " + title + RESET);
        String newName = getStringInput(CYAN + "Enter new game name (3-50 characters, press enter to keep current): " + RESET);
        if (!newName.isEmpty()) {
            game.setName(newName);
        }

        int newDifficulty = getIntInput(CYAN + "Enter new game difficulty (1-10, press enter to keep current): " + RESET, 1, 10);
        if (newDifficulty != -1) {
            game.setDifficulty(newDifficulty);
        }

        int newAverageMatchDuration = getIntInput(CYAN + "Enter new average match duration in minutes (1-180, press enter to keep current): " + RESET, 1, 180);
        if (newAverageMatchDuration != -1) {
            game.setAverageMatchDuration(newAverageMatchDuration);
        }

        try {
            gameController.updateGame(game);
            logger.info(GREEN + SUCCESS + " Game updated successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error updating game: " + e.getMessage() + RESET);
        }
    }

    private void deleteGame() {
        String name = getStringInput(CYAN + "Enter the name of the game to delete: " + RESET);
        try {
            gameController.deleteGameByName(name);
            logger.info(GREEN + SUCCESS + " Game deleted successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error deleting game: " + e.getMessage() + RESET);
        }
    }

    private void handleTournamentMenu() {
        boolean back = false;
        while (!back) {
            logger.info("\n" + PURPLE + TOURNAMENT + " --- Tournament Management --- " + TOURNAMENT + RESET);
            logger.info(BLUE + "1. " + ADDING + " Create Tournament" + RESET);
            logger.info(BLUE + "2. " + VIEW + " View All Tournaments" + RESET);
            logger.info(BLUE + "3. " + EDIT + " Edit Tournament" + RESET);
            logger.info(BLUE + "4. " + DELETE + " Delete Tournament" + RESET);
            logger.info(BLUE + "5. " + ADDING + " Add Team to Tournament" + RESET);
            logger.info(BLUE + "6. " + REMOVE + " Remove Team from Tournament" + RESET);
            logger.info(BLUE + "7. " + ADDING + " Add Game to Tournament" + RESET);
            logger.info(BLUE + "8. " + STATUS + " Change Tournament Status" + RESET);
            logger.info(BLUE + "9. " + TIME + " Calculate Estimated Duration" + RESET);
            logger.info(BLUE + "10. " + EXIT + " Back to Main Menu" + RESET);
            int choice = getIntInput(CYAN + "Enter your choice: " + RESET, 1, 10);
            switch (choice) {
                case 1:
                    createTournament();
                    break;
                case 2:
                    viewAllTournaments();
                    break;
                case 3:
                    editTournament();
                    break;
                case 4:
                    deleteTournament();
                    break;
                case 5:
                    addTeamToTournament();
                    break;
                case 6:
                    removeTeamFromTournament();
                    break;
                case 7:
                    addGameToTournament();
                    break;
                case 8:
                    changeTournamentStatus();
                    break;
                case 9:
                    calculateEstimatedDuration();
                    break;
                case 10:
                    back = true;
                    break;
                default:
                    logger.error(RED + ERROR + " Invalid choice. Please try again." + RESET);
            }
        }
    }

    private void createTournament() {
        logger.info("\n" + YELLOW + ADDING + " Creating a new tournament:" + RESET);
        String title = getStringInput(CYAN + "Enter tournament title: " + RESET, 1, 100);
        Date startDate = getDateInput(CYAN + "Enter start date (yyyy-MM-dd): " + RESET);
        Date endDate = getDateInput(CYAN + "Enter end date (yyyy-MM-dd): " + RESET);

        if (endDate.before(startDate)) {
            logger.error(RED + ERROR + " End date cannot be before start date. Please try again." + RESET);
            return;
        }

        int spectatorCount = getIntInput(CYAN + "Enter spectator count: " + RESET, 0, Integer.MAX_VALUE);
        int matchBreakTime = getIntInput(CYAN + "Enter match break time in minutes: " + RESET, 0, Integer.MAX_VALUE);
        int ceremonyTime = getIntInput(CYAN + "Enter ceremony time in minutes: " + RESET, 0, Integer.MAX_VALUE);

        Tournament tournament = new Tournament();
        tournament.setTitle(title);
        tournament.setStartDate(startDate);
        tournament.setEndDate(endDate);
        tournament.setSpectatorCount(spectatorCount);
        tournament.setMatchBreakTime(matchBreakTime);
        tournament.setCeremonyTime(ceremonyTime);
        tournament.setStatus(TournamentStatut.SCHEDULED);

        try {
            tournamentController.createTournament(tournament);
            logger.info(GREEN + SUCCESS + " Tournament created successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error creating tournament: " + e.getMessage() + RESET);
        }
    }

    private void viewAllTournaments() {
        List<Tournament> tournaments = tournamentController.getAllTournaments();
        if (tournaments.isEmpty()) {
            logger.info(YELLOW + WARNING + " No tournaments found." + RESET);
        } else {
            logger.info("\n" + BLUE + VIEW + " All Tournaments:" + RESET);
            for (Tournament tournament : tournaments) {
                Set<Team> teams = tournament.getTeams();
                int estimatedDuration = tournamentController.calculateEstimatedDuration(tournament.getTitle());
                logger.info(CYAN + "Title: " + tournament.getTitle()
                        + ", Game: " + (tournament.getGame() != null ? tournament.getGame().getName() : "N/A")
                        + ", Status: " + tournament.getStatus()
                        + ", Teams: " + teams.size()
                        + " (" + String.join(", ", teams.stream().map(Team::getName).toArray(String[]::new)) + ")"
                        + ", Estimated Duration: " + estimatedDuration + " minutes" + RESET);
            }
        }
    }

    private void editTournament() {
        String title = getStringInput(CYAN + "Enter the title of the tournament to edit: " + RESET);
        Optional<Tournament> tournamentOpt = tournamentController.getTournamentByTitle(title);
        if (!tournamentOpt.isPresent()) {
            logger.error(RED + ERROR + " Tournament not found." + RESET);
            return;
        }
        Tournament tournament = tournamentOpt.get();

        logger.info(YELLOW + EDIT + " Editing tournament: " + title + RESET);
        String newTitle = getStringInput(CYAN + "Enter new tournament title (1-100 characters, press enter to keep current): " + RESET);
        if (!newTitle.isEmpty()) {
            tournament.setTitle(newTitle);
        }

        String startDatePrompt = CYAN + "Enter new start date (yyyy-MM-dd, press enter to keep current): " + RESET;
        String endDatePrompt = CYAN + "Enter new end date (yyyy-MM-dd, press enter to keep current): " + RESET;

        Date newStartDate = getOptionalDateInput(startDatePrompt, tournament.getStartDate());
        Date newEndDate = getOptionalDateInput(endDatePrompt, tournament.getEndDate());

        if (newEndDate.before(newStartDate)) {
            logger.error(RED + ERROR + " End date cannot be before start date. Changes not applied." + RESET);
        } else {
            tournament.setStartDate(newStartDate);
            tournament.setEndDate(newEndDate);
        }

        int newSpectatorCount = getIntInput(CYAN + "Enter new spectator count (press enter to keep current): " + RESET, 0, Integer.MAX_VALUE);
        if (newSpectatorCount != -1) {
            tournament.setSpectatorCount(newSpectatorCount);
        }

        int newMatchBreakTime = getIntInput(CYAN + "Enter new match break time in minutes (press enter to keep current): " + RESET, 0, Integer.MAX_VALUE);
        if (newMatchBreakTime != -1) {
            tournament.setMatchBreakTime(newMatchBreakTime);
        }

        int newCeremonyTime = getIntInput(CYAN + "Enter new ceremony time in minutes (press enter to keep current): " + RESET, 0, Integer.MAX_VALUE);
        if (newCeremonyTime != -1) {
            tournament.setCeremonyTime(newCeremonyTime);
        }

        try {
            tournamentController.updateTournament(tournament);
            logger.info(GREEN + SUCCESS + " Tournament updated successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error updating tournament: " + e.getMessage() + RESET);
        }
    }

    private void deleteTournament() {
        String title = getStringInput(CYAN + "Enter tournament title to delete: " + RESET);
        try {
            tournamentController.deleteTournamentByTitle(title);
            logger.info(GREEN + SUCCESS + " Tournament deleted successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error deleting tournament: " + e.getMessage() + RESET);
        }
    }

    private void addTeamToTournament() {
        String tournamentTitle = getStringInput(CYAN + "Enter tournament title: " + RESET);
        String teamName = getStringInput(CYAN + "Enter team name: " + RESET);

        try {
            tournamentController.addTeamToTournament(tournamentTitle, teamName);
            logger.info(GREEN + SUCCESS + " Team added to the tournament successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error adding team to tournament: " + e.getMessage() + RESET);
        }
    }

    private void removeTeamFromTournament() {
        String tournamentTitle = getStringInput(CYAN + "Enter tournament title: " + RESET);
        String teamName = getStringInput(CYAN + "Enter team name: " + RESET);

        try {
            tournamentController.removeTeamFromTournament(tournamentTitle, teamName);
            logger.info(GREEN + SUCCESS + " Team removed from the tournament successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error removing team from tournament: " + e.getMessage() + RESET);
        }
    }

    private void addGameToTournament() {
        String tournamentTitle = getStringInput(CYAN + "Enter tournament title: " + RESET);
        String gameName = getStringInput(CYAN + "Enter game name: " + RESET);

        try {
            tournamentController.addGameToTournament(tournamentTitle, gameName);
            logger.info(GREEN + SUCCESS + " Game added to the tournament successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error adding game to tournament: " + e.getMessage() + RESET);
        }
    }

    private void changeTournamentStatus() {
        String title = getStringInput(CYAN + "Enter tournament title: " + RESET);
        TournamentStatut status = getTournamentStatusInput(CYAN + "Enter new status (PLANNED, ONGOING, COMPLETED): " + RESET);

        try {
            tournamentController.changeStatus(title, status);
            logger.info(GREEN + SUCCESS + " Tournament status changed successfully." + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error changing tournament status: " + e.getMessage() + RESET);
        }
    }

    private void calculateEstimatedDuration() {
        String title = getStringInput(CYAN + "Enter tournament title: " + RESET);

        try {
            int estimatedDuration = tournamentController.calculateEstimatedDuration(title);
            logger.info(CYAN + "Estimated Duration: " + estimatedDuration + " minutes" + RESET);
        } catch (IllegalArgumentException e) {
            logger.error(RED + ERROR + " Error calculating estimated duration: " + e.getMessage() + RESET);
        }
    }

    private String getStringInput(String prompt, int minLength, int maxLength) {
        logger.info(prompt);
        String input = scanner.nextLine();
        while (input.length() < minLength || input.length() > maxLength) {
            logger.error(RED + ERROR + " Invalid input length. Please try again." + RESET);
            logger.info(prompt);
            input = scanner.nextLine();
        }
        return input;
    }

    private int getIntInput(String prompt, int min, int max) {
        logger.info(prompt);
        int input;
        try {
            input = Integer.parseInt(scanner.nextLine());
            while (input < min || input > max) {
                logger.error(RED + ERROR + " Invalid input range. Please try again." + RESET);
                logger.info(prompt);
                input = Integer.parseInt(scanner.nextLine());
            }
        } catch (NumberFormatException e) {
            logger.error(RED + ERROR + " Invalid input type. Please try again." + RESET);
            return getIntInput(prompt, min, max);
        }
        return input;
    }

    private String getStringInput(String prompt) {
        logger.info(prompt);
        return scanner.nextLine();
    }

    private TournamentStatut getTournamentStatusInput(String prompt) {
        logger.info(prompt);
        String input = scanner.nextLine().toUpperCase();
        while (!input.equals("PLANNED") && !input.equals("ONGOING") && !input.equals("COMPLETED")) {
            logger.error(RED + ERROR + " Invalid status. Please try again." + RESET);
            logger.info(prompt);
            input = scanner.nextLine().toUpperCase();
        }
        return TournamentStatut.valueOf(input);
    }

    private Date getOptionalDateInput(String prompt, Date currentDate) {
        String dateStr = getStringInput(prompt);
        if (dateStr.isEmpty()) {
            return currentDate;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        while (true) {
            try {
                Date inputDate = dateFormat.parse(dateStr);
                Date today = new Date();
                if (inputDate.before(today)) {
                    logger.error(RED + ERROR + " Date must be present or in the future. Please try again." + RESET);
                    dateStr = getStringInput(prompt);
                    if (dateStr.isEmpty()) {
                        return currentDate;
                    }
                } else {
                    return inputDate;
                }
            } catch (ParseException e) {
                logger.error(RED + ERROR + " Invalid date format. Please use yyyy-MM-dd or press enter to keep current." + RESET);
                dateStr = getStringInput(prompt);
                if (dateStr.isEmpty()) {
                    return currentDate;
                }
            }
        }
    }

    private Date getDateInput(String prompt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        while (true) {
            String dateStr = getStringInput(prompt);
            try {
                Date inputDate = dateFormat.parse(dateStr);
                Date today = new Date();
                if (inputDate.before(today)) {
                    logger.error(RED + ERROR + " Date must be present or in the future. Please try again." + RESET);
                } else {
                    return inputDate;
                }
            } catch (ParseException e) {
                logger.error(RED + ERROR + " Invalid date format. Please use yyyy-MM-dd." + RESET);
            }
        }
    }

}
