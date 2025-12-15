package com.wings.controller;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.wings.models.Bird;
import com.wings.models.SpottedBird;
import com.wings.models.User;
import com.wings.service.BirdingService;

public class ConsoleMenu {
    private Scanner scanner;
    private User currentUser;
    private BirdingService birdingService;
    
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";

    public ConsoleMenu(BirdingService birdingService) {
        this.scanner = new Scanner(System.in);
        this.birdingService = birdingService;
        this.currentUser = null;
    }

    public void run() {
        while(true) {
            if(currentUser == null){
                showLoginMenu();
            }else{
                showMainMenu();
            }
        }
    }

    private void showMainMenu() {
        System.out.println("===Main Menu===");
        System.out.println("1. Spot Bird");
        System.out.println("2. View My Birds");
        System.out.println("3. View Leaderboard");
        System.out.println("4. Add Friend");
        System.out.println("5. View Friends");
        System.out.println("6. View My Profile");
        System.out.println("7. Log Out");

        String choice = scanner.nextLine();

        switch(choice) {
            case "1" -> handleSpotBird();
            case "2" -> handleViewMyBirds();
            case "3" -> handleViewLeaderboard();
            case "4" -> handleAddFriend();
            case "5" -> handleViewFriends();
            case "6" -> handleViewMyProfile();
            case "7" -> handleLogOut();
            default -> consoleError("Input not recognized");
        }
    }

    private void showLoginMenu() {
        System.out.println("===Wings===");
        System.out.println("1. Login");
        System.out.println("2. Signup");
        System.out.println("3. Quit");

        String choice = scanner.nextLine();
        switch(choice) {
            case "1" -> handleLogin();
            case "2" -> handleSignup();
            case "3" -> handleQuit();
            default -> consoleError("Input not recognized");
        }
    }

    private void consolePrint(String content){
        System.out.println(GREEN + "=========");
        System.out.println(content);
        System.out.println("=========" + RESET);    
    }

    private void consoleError(String content) {
        System.out.println(RED + "=========");
        System.out.println(content);
        System.out.println("=========" + RESET);
    }

    private void handleLogin() {
        consolePrint("Enter username:");
        String username = scanner.nextLine();
        try {
            User user = birdingService.loginUser(username);
            this.currentUser = user;
            consolePrint("Logged in as " + username);
        } catch (SQLException e) {
            consoleError("Error logging in: " + e);
        } catch (IllegalArgumentException e) {
            consoleError("User not found");
        }
    }

    private void handleSignup() {
        consolePrint("Welcome! What's your username?");
        String username = scanner.nextLine();
        try {
            User user = birdingService.createUser(username);
            this.currentUser = user;
        } catch (SQLException e) {
            consoleError("Error creating user: " + e);
        } catch (IllegalArgumentException e) {
            consoleError("Error signing up: " + e);
        }
    }

    private void handleQuit() {
        scanner.close();
        consolePrint("Goodbye!");
        System.exit(0);
    }

    private void handleSpotBird() {
        try{
            List<Bird> allBirds = birdingService.getAllBirds();
            List<Bird> results = allBirds;

            while (true) {
                consolePrint("Search birds (press ENTER to skip)");
                String query = scanner.nextLine().toLowerCase();
                if(!query.isEmpty()){
                    results = allBirds.stream().filter(bird -> bird.getName().toLowerCase().contains(query)).collect(Collectors.toList());
                } else {
                    results = allBirds;
                }
                if(results.isEmpty()){
                    consoleError("No birds found");
                    continue;
                }
                System.out.println("=== RESULTS ===");
                for(int i = 0; i < results.size(); i++){
                    Bird bird = results.get(i);
                    System.out.printf("%d. %-30s | %s | Rarity: %.1f%n", i + 1, bird.getName(), bird.getHabitat(), bird.getRarity());
                }
                consolePrint("Select by number! (0 to search again)");
                int choice = Integer.parseInt(scanner.nextLine());
                if(choice == 0) {
                    continue;
                } else if (choice > 0 && choice <= results.size()) {
                    Bird selected = results.get(choice-1);
                    birdingService.spotBird(currentUser, selected.getBirdId());
                    System.out.println("✓ " + selected.getName() + " spotted!");
                    break;
                } else {
                    consoleError("Invalid selection");
                }
            }
        } catch(SQLException e) {
            consoleError("Error logging bird spotting: " + e);
        } catch (IllegalArgumentException e) {
            consoleError("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleViewMyBirds() { 
        try{
            List<SpottedBird> spottedBirds = birdingService.getMyBirds(currentUser);
            System.out.println("=== Bird Spottings ===");
            System.out.printf("%-5s | %-30s | %-20s | %-20s%n", "No.", "Bird Name", "Date Spotted", "Bird ID");
            for(int i = 0; i < spottedBirds.size(); i++){
                SpottedBird sb = spottedBirds.get(i);
                Bird bird = birdingService.getBirdById(sb.getBirdId());
                System.out.printf("%-5d | %-30s | %-20s | %-20s%n", i + 1, bird.getName(), sb.getDateSpotted().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), sb.getBirdId().toString().substring(0, 8) + "..."
            );
            }
        } catch (SQLException e) {
            consoleError("Error getting bird spottings: " + e);
        }
    }

    private void handleViewLeaderboard() {
        try{
            Map<User, Integer> leaderBoardUnsorted = birdingService.getLeaderboard();
            System.out.println("=== Bird Spottings ===");
            System.out.printf("%-5s | %-30s | %-20s | %-20s%n", "No.", "Username", "Birds Spotted", "Streak");
            AtomicInteger i = new AtomicInteger(1);
            leaderBoardUnsorted.forEach((user, count) -> {
                try {
                    System.out.printf("%-5d | %-30s | %-20s | %-20s%n", i.getAndIncrement(), user.getUsername(), count, birdingService.getCurrentStreak(user.getUserId()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            consoleError("Error getting leaderboard: " + e);
        }
    }

    private void handleAddFriend() {
        consolePrint("What's your friend's username?");
        String friend = scanner.nextLine();
        try{
            birdingService.addFriend(currentUser, friend);
        } catch (SQLException e) {
            consoleError("Error adding friend: " + e);
        } catch (IllegalArgumentException e) {
            consoleError("Error: " + e);
        }
    }

    private void handleViewFriends() {
        System.out.println("=== Friends ===");
            System.out.printf("%-30s | %-20s | %-20s%n", "Username", "Birds Spotted", "Streak");
            // TODO friend since
            try {
                List<User> friends = birdingService.getFriends(currentUser.getUserId());
                for(User friend : friends){
                    System.out.printf("%-30s | %-20s | %-20s%n", friend.getUsername(), birdingService.getTotalBirdsSpotted(friend), birdingService.getCurrentStreak(friend.getUserId()));
                }
            } catch (SQLException e) {
                consoleError("Error: " + e);
            }
            
    }

    private void handleViewMyProfile() {
        try {
            int currentStreak = birdingService.getCurrentStreak(currentUser.getUserId());
            int birdsSpotted = birdingService.getTotalBirdsSpotted(currentUser);
            consolePrint(currentUser.getUsername() + '\n' 
                        + "Birds Spotted: " +  birdsSpotted + '\n'
                        + "Daily Streak: " + currentStreak);
        } catch (SQLException e) {
            consoleError("Error: " + e);
        }
    }

    private void handleLogOut() {
        consolePrint("Logging out...");
        currentUser = null;
    }
}
