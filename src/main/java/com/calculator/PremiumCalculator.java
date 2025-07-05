package com.calculator;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class PremiumCalculator extends Application {

    private TextField display;
    private double firstOperand = 0;
    private String operator = "";
    private boolean waitingForOperand = false;
    private Timeline pulseAnimation;

    @Override
    public void start(Stage primaryStage) {
        // Create the main container with display and buttons
        VBox content = createMainContainer();

        // Create nearly opaque background
        Rectangle backgroundRect = new Rectangle(380, 680);
        backgroundRect.setFill(Color.rgb(30, 30, 30, 0.95));
        backgroundRect.setArcWidth(30);
        backgroundRect.setArcHeight(30);
        backgroundRect.setStroke(Color.rgb(255, 255, 255, 0.2));
        backgroundRect.setStrokeWidth(1.5);

        // StackPane as root: background at bottom, content on top
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundRect, content);

        // Create scene with opaque background for visibility
        Scene scene = new Scene(root, 380, 680);
        scene.setFill(Color.rgb(30, 30, 30)); // Opaque background

        // Apply custom CSS for ultra-modern look
        scene.getStylesheets().add(createPremiumCSS());

        // Configure stage with modern window styling
        primaryStage.initStyle(StageStyle.DECORATED); // Use normal window for now
        primaryStage.setTitle("Premium Calculator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // Show with fade-in animation
        primaryStage.show();
        animateWindowAppearance(content);
    }

    private VBox createMainContainer() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        // Create display with premium styling
        display = createPremiumDisplay();

        // Create button grid with advanced animations
        GridPane buttonGrid = createPremiumButtonGrid();

        // Add components to root
        root.getChildren().addAll(display, buttonGrid);
        return root;
    }

    private TextField createPremiumDisplay() {
        TextField display = new TextField("0");
        display.setEditable(false);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setPrefHeight(100);
        display.setMaxWidth(320);

        // Premium typography
        display.setFont(Font.font("SF Pro Display", FontWeight.LIGHT, 48));

        // More visible display background
        display.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.8);" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.3);" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-width: 1;" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 10);"
        );

        return display;
    }

    private GridPane createPremiumButtonGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);
        grid.setMaxWidth(320);

        // Button configuration: text, row, col, colspan, style
        String[][] buttons = {
                {"C", "0", "0", "1", "clear"},
                {"±", "0", "1", "1", "function"},
                {"%", "0", "2", "1", "function"},
                {"÷", "0", "3", "1", "operator"},

                {"7", "1", "0", "1", "number"},
                {"8", "1", "1", "1", "number"},
                {"9", "1", "2", "1", "number"},
                {"×", "1", "3", "1", "operator"},

                {"4", "2", "0", "1", "number"},
                {"5", "2", "1", "1", "number"},
                {"6", "2", "2", "1", "number"},
                {"−", "2", "3", "1", "operator"},

                {"1", "3", "0", "1", "number"},
                {"2", "3", "1", "1", "number"},
                {"3", "3", "2", "1", "number"},
                {"+", "3", "3", "1", "operator"},

                {"0", "4", "0", "2", "number"},
                {".", "4", "2", "1", "number"},
                {"=", "4", "3", "1", "equals"}
        };

        for (String[] btnData : buttons) {
            Button btn = createPremiumButton(btnData[0], btnData[4]);

            int row = Integer.parseInt(btnData[1]);
            int col = Integer.parseInt(btnData[2]);
            int colspan = Integer.parseInt(btnData[3]);

            if (colspan > 1) {
                GridPane.setColumnSpan(btn, colspan);
            }

            grid.add(btn, col, row);
        }

        return grid;
    }

    private Button createPremiumButton(String text, String type) {
        Button btn = new Button(text);
        btn.setPrefSize(70, 70);
        btn.setMaxSize(70, 70);
        btn.setFont(Font.font("SF Pro Display", FontWeight.MEDIUM, 24));

        // Base styling for all buttons
        String baseStyle =
                "-fx-background-radius: 35;" +
                        "-fx-border-radius: 35;" +
                        "-fx-cursor: hand;" +
                        "-fx-font-weight: 500;";

        // Type-specific styling with more visible backgrounds
        switch (type) {
            case "number":
                btn.setStyle(baseStyle +
                        "-fx-background-color: rgba(255, 255, 255, 0.5);" +
                        "-fx-text-fill: white;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.2);" +
                        "-fx-border-width: 1;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0, 0, 5);");
                break;
            case "operator":
                btn.setStyle(baseStyle +
                        "-fx-background-color: rgba(255,107,107,0.7);" +
                        "-fx-text-fill: white;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255,107,107,0.4), 15, 0, 0, 5);");
                break;
            case "function":
                btn.setStyle(baseStyle +
                        "-fx-background-color: rgba(255,255,255,0.7);" +
                        "-fx-text-fill: white;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.3);" +
                        "-fx-border-width: 1;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0, 0, 5);");
                break;
            case "clear":
                btn.setStyle(baseStyle +
                        "-fx-background-color: rgba(255,71,87,0.7);" +
                        "-fx-text-fill: white;" +
                        "-fx-effect: dropshadow(gaussian, rgba(255,71,87,0.4), 15, 0, 0, 5);");
                break;
            case "equals":
                btn.setStyle(baseStyle +
                        "-fx-background-color: rgba(46,213,115,0.7);" +
                        "-fx-text-fill: white;" +
                        "-fx-effect: dropshadow(gaussian, rgba(46,213,115,0.4), 15, 0, 0, 5);");
                break;
        }

        // Add premium animations
        addPremiumButtonAnimations(btn, type);

        // Set action
        btn.setOnAction(e -> handleButtonClick(text));

        return btn;
    }

    private void addPremiumButtonAnimations(Button btn, String type) {
        // Hover effect with glow
        btn.setOnMouseEntered(e -> {
            btn.setEffect(new Glow(0.3));
            btn.setScaleX(1.05);
            btn.setScaleY(1.05);
        });

        btn.setOnMouseExited(e -> {
            btn.setEffect(null);
            btn.setScaleX(1.0);
            btn.setScaleY(1.0);
        });

        // Click animation with ripple effect
        btn.setOnMousePressed(e -> {
            playClickAnimation(btn);
        });
    }

    private void playClickAnimation(Button btn) {
        // Create ripple effect
        Circle ripple = new Circle(5);
        ripple.setFill(Color.rgb(255, 255, 255, 0.3));
        ripple.setCenterX(btn.getWidth() / 2);
        ripple.setCenterY(btn.getHeight() / 2);

        // Create a container for the ripple effect
        StackPane rippleContainer = new StackPane();
        rippleContainer.getChildren().add(ripple);
        rippleContainer.setMouseTransparent(true);

        // Add ripple container to button's parent
        if (btn.getParent() instanceof Pane) {
            Pane parent = (Pane) btn.getParent();
            parent.getChildren().add(rippleContainer);
            
            // Position the ripple container over the button
            rippleContainer.setLayoutX(btn.getLayoutX() + btn.getWidth() / 2 - 5);
            rippleContainer.setLayoutY(btn.getLayoutY() + btn.getHeight() / 2 - 5);
        }

        // Animate ripple
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), ripple);
        scaleTransition.setToX(10);
        scaleTransition.setToY(10);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), ripple);
        fadeTransition.setToValue(0);

        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeTransition);
        parallelTransition.setOnFinished(e -> {
            if (btn.getParent() instanceof Pane) {
                Pane parent = (Pane) btn.getParent();
                parent.getChildren().remove(rippleContainer);
            }
        });
        parallelTransition.play();
    }

    private Color getButtonGlowColor(String type) {
        switch (type) {
            case "operator":
                return Color.rgb(255, 107, 107, 0.6);
            case "clear":
                return Color.rgb(255, 71, 87, 0.6);
            case "equals":
                return Color.rgb(46, 213, 115, 0.6);
            default:
                return Color.rgb(255, 255, 255, 0.3);
        }
    }

    private LinearGradient createGlassMorphismGradient() {
        return new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(255, 255, 255, 0.1)),
                new Stop(1, Color.rgb(255, 255, 255, 0.05)));
    }

    private String createPremiumCSS() {
        return getClass().getResource("/styles.css").toExternalForm();
    }

    private void makeWindowDraggable(VBox root, Stage stage) {
        root.setOnMousePressed(e -> {
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - e.getSceneX());
                stage.setY(event.getScreenY() - e.getSceneY());
            });
        });
    }

    private void animateWindowAppearance(VBox root) {
        root.setOpacity(0);
        root.setScaleX(0.8);
        root.setScaleY(0.8);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), root);
        fadeTransition.setToValue(1);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(800), root);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);

        ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, scaleTransition);
        parallelTransition.play();
    }

    private void handleButtonClick(String text) {
        switch (text) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                numberPressed(text);
                break;
            case ".":
                decimalPressed();
                break;
            case "+":
            case "−":
            case "×":
            case "÷":
                setOperator(text);
                break;
            case "=":
                calculate();
                break;
            case "C":
                clearAll();
                break;
            case "±":
                toggleSign();
                break;
            case "%":
                percent();
                break;
        }
        animateDisplayChange();
    }

    private void animateDisplayChange() {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), display);
        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);
        scaleTransition.play();
    }

    private void numberPressed(String number) {
        if (waitingForOperand) {
            display.setText(number);
            waitingForOperand = false;
        } else {
            if (display.getText().equals("0")) {
                display.setText(number);
            } else {
                display.setText(display.getText() + number);
            }
        }
    }

    private void decimalPressed() {
        if (waitingForOperand) {
            display.setText("0.");
            waitingForOperand = false;
        } else if (!display.getText().contains(".")) {
            display.setText(display.getText() + ".");
        }
    }

    private void setOperator(String op) {
        if (!operator.isEmpty() && !waitingForOperand) {
            calculate();
        }

        firstOperand = Double.parseDouble(display.getText());
        operator = op;
        waitingForOperand = true;
    }

    private void calculate() {
        if (operator.isEmpty() || waitingForOperand) {
            return;
        }

        double secondOperand = Double.parseDouble(display.getText());
        double result = 0;

        switch (operator) {
            case "+":
                result = firstOperand + secondOperand;
                break;
            case "−":
                result = firstOperand - secondOperand;
                break;
            case "×":
                result = firstOperand * secondOperand;
                break;
            case "÷":
                if (secondOperand != 0) {
                    result = firstOperand / secondOperand;
                } else {
                    display.setText("Error");
                    return;
                }
                break;
        }

        if (result == (long) result) {
            display.setText(String.valueOf((long) result));
        } else {
            display.setText(String.valueOf(result));
        }

        operator = "";
        waitingForOperand = true;
    }

    private void clearAll() {
        display.setText("0");
        firstOperand = 0;
        operator = "";
        waitingForOperand = false;
    }

    private void toggleSign() {
        double currentValue = Double.parseDouble(display.getText());
        currentValue = -currentValue;

        if (currentValue == (long) currentValue) {
            display.setText(String.valueOf((long) currentValue));
        } else {
            display.setText(String.valueOf(currentValue));
        }
    }

    private void percent() {
        double currentValue = Double.parseDouble(display.getText());
        currentValue = currentValue / 100;

        if (currentValue == (long) currentValue) {
            display.setText(String.valueOf((long) currentValue));
        } else {
            display.setText(String.valueOf(currentValue));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
