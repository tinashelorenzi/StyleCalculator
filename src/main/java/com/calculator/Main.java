package com.calculator;

import javafx.application.Application;

public class Main extends Application {

    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {
        // Launch the premium calculator instead
        PremiumCalculator premiumCalculator = new PremiumCalculator();
        premiumCalculator.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}