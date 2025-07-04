package com.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CalculatorController {

    @FXML
    private TextField display;

    private double firstOperand = 0;
    private String operator = "";
    private boolean waitingForOperand = false;

    @FXML
    private void numberPressed(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String number = clickedButton.getText();

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

    @FXML
    private void decimalPressed(ActionEvent event) {
        if (waitingForOperand) {
            display.setText("0.");
            waitingForOperand = false;
        } else if (!display.getText().contains(".")) {
            display.setText(display.getText() + ".");
        }
    }

    @FXML
    private void setOperator(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String clickedOperator = clickedButton.getText();

        if (!operator.isEmpty() && !waitingForOperand) {
            calculate(null);
        }

        firstOperand = Double.parseDouble(display.getText());
        operator = clickedOperator;
        waitingForOperand = true;
    }

    @FXML
    private void calculate(ActionEvent event) {
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

    @FXML
    private void clearAll(ActionEvent event) {
        display.setText("0");
        firstOperand = 0;
        operator = "";
        waitingForOperand = false;
    }

    @FXML
    private void toggleSign(ActionEvent event) {
        double currentValue = Double.parseDouble(display.getText());
        currentValue = -currentValue;

        if (currentValue == (long) currentValue) {
            display.setText(String.valueOf((long) currentValue));
        } else {
            display.setText(String.valueOf(currentValue));
        }
    }

    @FXML
    private void percent(ActionEvent event) {
        double currentValue = Double.parseDouble(display.getText());
        currentValue = currentValue / 100;

        if (currentValue == (long) currentValue) {
            display.setText(String.valueOf((long) currentValue));
        } else {
            display.setText(String.valueOf(currentValue));
        }
    }
}