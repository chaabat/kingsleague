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

public class PlayerDisplay {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerDisplay.class);

    private PlayerController playerController;
    private TeamController teamController;
    private TournamentController tournamentController;
    private GameController gameController;
    private Scanner scanner;

    public PlayerDisplay() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        playerController = context.getBean(PlayerController.class);
        teamController = context.getBean(TeamController.class);
        tournamentController = context.getBean(TournamentController.class);
        gameController = context.getBean(GameController.class);
        
        // Initialize the scanner
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int choice = ValidatorUtil.validateInteger("Enter your choice: ", 1, 5);
            switch (choice) {
                case 1:
                    handlePlayerMenu();
                    break;
                case 2:
                    handleTeamMenu();
                    break;
                case 3:
                    handleTournamentMenu();
                    break;
                case 4:
                    handleGameMenu();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        // Close the scanner when exiting
        closeScanner();
    }

    private void printMainMenu() {
        System.out.println("\n===== E-Sports Tournament Management System =====");
        System.out.println("1. Player Management");
        System.out.println("2. Team Management");
        System.out.println("3. Tournament Management");
        System.out.println("4. Game Management");
        System.out.println("5. Exit");
    }

    private void handlePlayerMenu() {
        boolean back = false;
        while (!back) {
            printPlayerMenu();
            int choice = ValidatorUtil.validateInteger("Enter your choice: ", 1, 5);
            switch (choice) {
                case 1:
                    listAllPlayers();
                    break;
                case 2:
                    addNewPlayer();
                    break;
                case 3:
                    updatePlayer();
                    break;
                case 4:
                    deletePlayer();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printPlayerMenu() {
        System.out.println("\n----- Player Management -----");
        System.out.println("1. List all players");
        System.out.println("2. Add new player");
        System.out.println("3. Update player");
        System.out.println("4. Delete player");
        System.out.println("5. Back to main menu");
    }

    private void listAllPlayers() {
        List<Player> players = playerController.getAllPlayers();
        if (players.isEmpty()) {
            System.out.println("No players found.");
        } else {
            System.out.println("Players:");
            for (Player player : players) {
                System.out.println(player.getUsername() + " (Age: " + player.getAge() + ")");
            }
        }
    }

    private void addNewPlayer() {
        System.out.println("Enter player details:");
        String username = ValidatorUtil.validateString("Username (3-20 characters): ", 20);
        int age = ValidatorUtil.validateInteger("Age (1-120): ", 1, 120);
        Player player = new Player();
        try {
            player.setUsername(username);
            player.setAge(age);
            playerController.addPlayer(player);
            System.out.println("Player added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updatePlayer() {
        String username = ValidatorUtil.validateString("Enter player username to update: ", 20);
        try {
            Optional<Player> player = playerController.getPlayerByUsername(username);
            if (!player.isPresent()) {
                System.out.println("Player not found.");
                return;
            }
            System.out.println("Current player details:");
            System.out.println("Username: " + player.get().getUsername());
            System.out.println("Age: " + player.get().getAge());
            System.out.println("Enter new details (press enter to keep current value):");
            String newUsername = ValidatorUtil.validateString("New username (3-20 characters): ", 20);
            if (!newUsername.isEmpty()) {
                player.get().setUsername(newUsername);
            }
            String ageStr = ValidatorUtil.validateString("New age (1-120): ", 3);
            if (!ageStr.isEmpty()) {
                player.get().setAge(Integer.parseInt(ageStr));
            }
            playerController.updatePlayer(player.orElse(null));
            System.out.println("Player updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void deletePlayer() {
        String username = ValidatorUtil.validateString("Enter player username to delete: ", 20);
        playerController.deletePlayerByUsername(username);
        System.out.println("Player deleted successfully.");
    }
    private void handleTeamMenu() {
        boolean back = false;
        while (!back) {
            printTeamMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    listAllTeams();
                    break;
                case 2:
                    addNewTeam();
                    break;
                case 3:
                    updateTeam();
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
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printTeamMenu() {
        System.out.println("\n----- Team Management -----");
        System.out.println("1. List all teams");
        System.out.println("2. Add new team");
        System.out.println("3. Update team");
        System.out.println("4. Delete team");
        System.out.println("5. Add player to team");
        System.out.println("6. Remove player from team");
        System.out.println("7. Back to main menu");
    }

    private void listAllTeams() {
        List<Team> teams = teamController.getAllTeams();
        if (teams.isEmpty()) {
            System.out.println("No teams found.");
        } else {
            System.out.println("Teams:");
            for (Team team : teams) {
                System.out.println(team.getName() + " (Ranking: " + team.getRanking() + ")");
            }
        }
    }

    private void addNewTeam() {
        System.out.println("Enter team details:");
        String name = getValidStringInput("Name: ", 3, 50);
        Team team = new Team();
        try {
            team.setName(name);
            teamController.addTeam(team);
            System.out.println("Team added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateTeam() {
        String name = getValidStringInput("Enter team name to update: ", 3, 50);
        try {
            Optional<Team> team = teamController.getTeamByName(name);
            if (!team.isPresent()) {
                System.out.println("Team not found.");
                return;
            }
            System.out.println("Current team details:");
            System.out.println("Name: " + team.get().getName());
            System.out.println("Enter new details (press enter to keep current value):");
            String newName = getValidStringInput("New name (3-50 characters): ", 3, 50, true);
            if (!newName.isEmpty()) {
                team.get().setName(newName);
            }
            teamController.updateTeam(team.orElse(null));
            System.out.println("Team updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteTeam() {
        String name = getStringInput("Enter team name to delete: ");
        teamController.deleteTeamByName(name);
        System.out.println("Team deleted successfully.");
    }

    private void addPlayerToTeam() {
        String teamName = getStringInput("Enter team name: ");
        String playerUsername = getStringInput("Enter player username to add: ");
        try {
            teamController.addPlayerToTeam(teamName, playerUsername);
            System.out.println("Player added to team successfully.");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Failed to add player to team: {}", e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void removePlayerFromTeam() {
        String teamName = getStringInput("Enter team name: ");
        String playerUsername = getStringInput("Enter player username to remove: ");
        teamController.removePlayerFromTeam(teamName, playerUsername);
        System.out.println("Player removed from team successfully.");
    }

    private void handleTournamentMenu() {
        boolean back = false;
        while (!back) {
            printTournamentMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    listAllTournaments();
                    break;
                case 2:
                    addNewTournament();
                    break;
                case 3:
                    updateTournament();
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
                    changeTournamentStatus();
                    break;
                case 8:
                    cancelTournament();
                    break;
                case 9:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printTournamentMenu() {
        System.out.println("\n----- Tournament Management -----");
        System.out.println("1. List all tournaments");
        System.out.println("2. Add new tournament");
        System.out.println("3. Update tournament");
        System.out.println("4. Delete tournament");
        System.out.println("5. Add team to tournament");
        System.out.println("6. Remove team from tournament");
        System.out.println("7. Change tournament status");
        System.out.println("8. Cancel tournament");
        System.out.println("9. Back to main menu");
    }

    private void listAllTournaments() {
        List<Tournament> tournaments = tournamentController.getAllTournaments();
        if (tournaments.isEmpty()) {
            System.out.println("No tournaments found.");
        } else {
            System.out.println("Tournaments:");
            for (Tournament tournament : tournaments) {
                System.out.println(tournament.getName() + " (Status: " + tournament.getStatut() + ")");
            }
        }
    }

    private void addNewTournament() {
        System.out.println("Enter tournament details:");
        String title = getValidStringInput("Title: ", 3, 100);
        String startDateStr = getValidStringInput("Start Date (yyyy-MM-dd): ", 10, 10);
        String endDateStr = getValidStringInput("End Date (yyyy-MM-dd): ", 10, 10);
        
        // List available games and let the user choose one
        List<Game> games = gameController.getAllGames();
        if (games.isEmpty()) {
            System.out.println("No games available. Please create a game first.");
            return;
        }
        
        System.out.println("Available games:");
        for (int i = 0; i < games.size(); i++) {
            System.out.println((i + 1) + ". " + games.get(i).getName());
        }
        
        int gameChoice = getValidIntInput("Choose a game (1-" + games.size() + "): ", 1, games.size());
        Game selectedGame = games.get(gameChoice - 1);
        
        Tournament tournament = new Tournament();
        try {
            tournament.setName(title);
            tournament.setDateStart(java.sql.Date.valueOf(startDateStr));
            tournament.setEndDate(java.sql.Date.valueOf(endDateStr));
            tournament.setGame(selectedGame);
            tournamentController.addTournament(tournament);
            System.out.println("Tournament added successfully.");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Failed to add tournament: {}", e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateTournament() {
        String name = getValidStringInput("Enter tournament title to update: ", 3, 100);
        try {
            Optional<Tournament> tournamentOptional = tournamentController.getTournamentByName(name);
            if (!tournamentOptional.isPresent()) {
                System.out.println("Tournament not found.");
                return;
            }
            Tournament tournament = tournamentOptional.get();
            System.out.println("Current tournament details:");
            System.out.println("Title: " + tournament.getName());
            System.out.println("Start Date: " + tournament.getDateStart());
            System.out.println("End Date: " + tournament.getEndDate());
            System.out.println("Status: " + tournament.getStatut());
            System.out.println("Enter new details (press enter to keep current value):");
            String newTitle = getValidStringInput("New title (3-100 characters): ", 3, 100, true);
            if (!newTitle.isEmpty()) {
                tournament.setName(newTitle);
            }
            String newStartDateStr = getValidStringInput("New Start Date (yyyy-MM-dd): ", 10, 10, true);
            if (!newStartDateStr.isEmpty()) {
                tournament.setDateStart(java.sql.Date.valueOf(newStartDateStr));
            }
            String newEndDateStr = getValidStringInput("New End Date (yyyy-MM-dd): ", 10, 10, true);
            if (!newEndDateStr.isEmpty()) {
                tournament.setEndDate(java.sql.Date.valueOf(newEndDateStr));
            }
            tournamentController.updateTournament(tournament);
            System.out.println("Tournament updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteTournament() {
        String title = getStringInput("Enter tournament title to delete: ");
        tournamentController.deleteTournamentByName(title);
        System.out.println("Tournament deleted successfully.");
    }

    private void addTeamToTournament() {
        String tournamentTitle = getStringInput("Enter tournament title: ");
        String teamName = getStringInput("Enter team name to add: ");
        tournamentController.addTeamToTournament(tournamentTitle, teamName);
        System.out.println("Team added to tournament successfully.");
    }

    private void removeTeamFromTournament() {
        String tournamentTitle = getStringInput("Enter tournament title: ");
        String teamName = getStringInput("Enter team name to remove: ");
        tournamentController.removeTeamFromTournament(tournamentTitle, teamName);
        System.out.println("Team removed from tournament successfully.");
    }

    private void changeTournamentStatus() {
        String tournamentName = getStringInput("Enter tournament title: ");
        System.out.println("Available statuses:");
        for (TournamentStatut status : TournamentStatut.values()) {
            System.out.println(status.ordinal() + ": " + status);
        }
        int statusChoice = getIntInput("Enter new status (0-3): ");
        TournamentStatut newStatus = TournamentStatut.values()[statusChoice];
        tournamentController.changeStatut(tournamentName, newStatus);
        System.out.println("Tournament status updated successfully.");
    }

    private void cancelTournament() {
        String tournamentTitle = getStringInput("Enter tournament title to cancel: ");
        tournamentController.cancelTournament(tournamentTitle);
        System.out.println("Tournament cancelled successfully.");
    }

    private void handleGameMenu() {
        boolean back = false;
        while (!back) {
            printGameMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    listAllGames();
                    break;
                case 2:
                    addNewGame();
                    break;
                case 3:
                    updateGame();
                    break;
                case 4:
                    deleteGame();
                    break;
                case 5:
                    addTeamToGame();
                    break;
                case 6:
                    removeTeamFromGame();
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printGameMenu() {
        System.out.println("\n----- Game Management -----");
        System.out.println("1. List all games");
        System.out.println("2. Add new game");
        System.out.println("3. Update game");
        System.out.println("4. Delete game");
        System.out.println("5. Add team to game");
        System.out.println("6. Remove team from game");
        System.out.println("7. Back to main menu");
    }

    private void listAllGames() {
        List<Game> games = gameController.getAllGames();
        if (games.isEmpty()) {
            System.out.println("No games found.");
        } else {
            System.out.println("Games:");
            for (Game game : games) {
                System.out.println(game.getName() + " (Difficulty: " + game.getDifficulty() + ")");
            }
        }
    }

    private void addNewGame() {
        System.out.println("Enter game details:");
        String name = getValidStringInput("Name: ", 3, 50);
        int difficulty = getValidIntInput("Difficulty (1-10): ", 1, 10);
        int averageMatchDuration = getValidIntInput("Average Match Duration (minutes): ", 1, 300);
        Game game = new Game();
        try {
            game.setName(name);
            game.setDifficulty(difficulty);
            game.setDurationAverageMatch(averageMatchDuration);
            gameController.addGame(game);
            System.out.println("Game added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateGame() {
        System.out.print("Enter game name: ");
        String gameName = scanner.nextLine();
        Optional<Game> gameOptional = gameController.getGameByName(gameName);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            System.out.print("Enter new difficulty (1-10): ");
            int difficulty = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter new average match duration (in minutes): ");
            int durationAverageMatch = Integer.parseInt(scanner.nextLine());
            
            game.setDifficulty(difficulty);
            game.setDurationAverageMatch(durationAverageMatch);
            
            gameController.updateGame(game);
            System.out.println("Game updated successfully.");
        } else {
            System.out.println("Game not found.");
        }
    }

    private void deleteGame() {
        String name = getStringInput("Enter game name to delete: ");
        gameController.deleteGameByName(name);
        System.out.println("Game deleted successfully.");
    }

    private void addTeamToGame() {
        System.out.print("Enter game name: ");
        String gameName = scanner.nextLine();
        System.out.print("Enter team name to add: ");
        String teamName = scanner.nextLine();

        try {
            if (gameController == null) {
                LOGGER.error("GameController is null");
                throw new IllegalStateException("GameController is not initialized");
            }
            gameController.addTeamToGame(gameName, teamName);
            System.out.println("Team added to game successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
            LOGGER.error("Failed to add team to game: {}", e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error: An unexpected error occurred while adding the team to the game.");
            LOGGER.error("Failed to add team to game", e);
        } catch (Exception e) {
            System.out.println("Error: An unexpected error occurred. Please try again later.");
            LOGGER.error("Unexpected error while adding team to game", e);
        }
    }

    private void removeTeamFromGame() {
        String gameName = getStringInput("Enter game name: ");
        String teamName = getStringInput("Enter team name to remove: ");
        gameController.removeTeamFromGame(gameName, teamName);
        System.out.println("Team removed from game successfully.");
    }

    public void updateTournamentStatuses() {
        tournamentController.updateTournamentStatut();
        System.out.println("Tournament statuses updated successfully.");
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return this.scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(this.scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private String getValidStringInput(String prompt, int minLength, int maxLength) {
        return getValidStringInput(prompt, minLength, maxLength, false);
    }

    private String getValidStringInput(String prompt, int minLength, int maxLength, boolean allowEmpty) {
        while (true) {
            System.out.print(prompt);
            String input = this.scanner.nextLine().trim();
            if (allowEmpty && input.isEmpty()) {
                return input;
            }
            if (input.length() >= minLength && input.length() <= maxLength) {
                return input;
            }
            System.out.println("Invalid input. Please enter a string between " + minLength + " and " + maxLength + " characters.");
        }
    }

    private int getValidIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = Integer.parseInt(this.scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    // Make sure to close the scanner when it's no longer needed
    public void closeScanner() {
        if (this.scanner != null) {
            this.scanner.close();
        }
    }
}
