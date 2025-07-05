#!/bin/bash

# Compile the project
./mvnw clean compile

# Run the JavaFX application with native access enabled to suppress warnings
java --enable-native-access=javafx.graphics --module-path /Library/Java/JavaFX/lib --add-modules javafx.controls,javafx.fxml -cp target/classes com.calculator.Main 