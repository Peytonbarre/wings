package com.wings;

import java.sql.SQLException;

import com.wings.controller.ConsoleMenu;
import com.wings.database.DatabaseInitializer;
import com.wings.repository.*;
import com.wings.repository.impl.*;
import com.wings.service.BirdingService;
//mvn clean package
//mvn test
//mvn exec:java

public class App 
{
    public static void main( String[] args )
    {
        try{
            DatabaseInitializer.initialize();

            UserRepository userRepo = new UserRepositoryImpl();
            BirdRepository birdRepo = new BirdRepositoryImpl();
            SpottedBirdRepository spottedBirdRepo = new SpottedBirdRepositoryImpl();
            FriendshipRepository friendshipRepo = new FriendshipRepositoryImpl();

            BirdingService birdingService = new BirdingService(userRepo, birdRepo, spottedBirdRepo, friendshipRepo);

            ConsoleMenu menu = new ConsoleMenu(birdingService);
            menu.run();

        } catch(SQLException e){
            System.out.println("Database Error: " + e);
            e.printStackTrace();
        }
    }
}
