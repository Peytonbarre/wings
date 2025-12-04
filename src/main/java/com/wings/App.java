package com.wings;

import com.wings.controller.ConsoleMenu;

//mvn clean package
//mvn test
//mvn exec:java

public class App 
{
    public static void main( String[] args )
    {
        ConsoleMenu menu = new ConsoleMenu();
        menu.run();
    }
}
