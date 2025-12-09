package com.wings.controller;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;
import com.wings.models.User;
import com.wings.service.BirdingService;

public class ConsoleMenu {
    private Scanner scanner;
    private User currentUser;
    private BirdingService birdingService;
    
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";

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
        System.out.println("5. View My Profile");
        System.out.println("6. Log Out");

        String choice = scanner.nextLine();

        switch(choice) {
            case "1" -> handleSpotBird();
            case "2" -> handleViewMyBirds();
            case "3" -> handleViewLeaderboard();
            case "4" -> handleAddFriend();
            case "5" -> handleViewMyProfile();
            case "6" -> handleLogOut();
            default -> System.out.println("Input not recognized");
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
            default -> System.out.println("Input not recognized");
        }
    }

    private void consolePrint(String content){
        System.out.println(GREEN + "=========");
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
            System.out.println("Error logging in: " + e);
        } catch (IllegalArgumentException e) {
            System.out.println("User not found");
        }
    }

    private void handleSignup() {
        consolePrint("Welcome! What's your username?");
        String username = scanner.nextLine();

        try {
            birdingService.createUser(username);
        } catch (SQLException e) {
            System.out.println("Error creating user: " + e);
        } catch (IllegalArgumentException e) {
            System.out.println("Error signing up: " + e);
        }

        //TODO: Store user
    }

    private void handleQuit() {
        scanner.close();
        consolePrint("Goodbye!");
        System.exit(0);
    }

    private void handleSpotBird() {

    }

    private void handleViewMyBirds() { 

    }

    private void handleViewLeaderboard() {

    }

    private void handleAddFriend() {

    }

    private void handleViewMyProfile() {
        consolePrint(currentUser.toString());
    }

    private void handleLogOut() {
        consolePrint("Logging out...");
        currentUser = null;
    }
}
