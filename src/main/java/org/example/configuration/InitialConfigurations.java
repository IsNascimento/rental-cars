package org.example.configuration;

public class InitialConfigurations {

    public static void initiateServer() {
        new DatabaseConnection().createDataBase();
    }
}
