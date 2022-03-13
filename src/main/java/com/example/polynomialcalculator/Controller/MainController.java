package com.example.polynomialcalculator.Controller;

import com.example.polynomialcalculator.Model.Polynomial;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button additionButton, subtractionButton, multiplyButton, divisionButton, derivativeButton, integrationButton;
    @FXML
    private TextField firstPolynomialTextField, secondPolynomialTextField, resultTextField;

    public Polynomial firstPolynomial, secondPolynomial, resultPolynomial;

    public static void createAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Please insert the polynomials correctly!");
        alert.setContentText("Correct example: 3x^4-x^3+x^2+2x-1");
        alert.show();
    }

    public static void divisionWithZero(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("You cannot divide by 0!");
        alert.setContentText("Please correct the second polynomial.");
        alert.show();
    }

    public void getPolynomials(){
        firstPolynomial = new Polynomial(firstPolynomialTextField.getText());
        secondPolynomial = new Polynomial(secondPolynomialTextField.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        additionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getPolynomials();
                resultPolynomial = firstPolynomial.add(secondPolynomial);
                System.out.println(resultPolynomial.getStringPolynomial());
                resultTextField.setText(resultPolynomial.getStringPolynomial());
            }
        });

        subtractionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getPolynomials();
                resultPolynomial = firstPolynomial.subtract(secondPolynomial);
                System.out.println(resultPolynomial.getStringPolynomial());
                resultTextField.setText(resultPolynomial.getStringPolynomial());
            }
        });

        multiplyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getPolynomials();
                resultPolynomial = firstPolynomial.multiply(secondPolynomial);
                System.out.println(resultPolynomial.getStringPolynomial());
                resultTextField.setText(resultPolynomial.getStringPolynomial());
            }
        });

        divisionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getPolynomials();
                ArrayList<Polynomial> resultPolynomial = firstPolynomial.divide(secondPolynomial);
                if(resultPolynomial == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Wrong input!");
                    alert.setContentText("Please enter a higher degree for the first polynomial!");

                    alert.showAndWait();
                }
                else resultTextField.setText("Quotient: " + resultPolynomial.get(0).getStringPolynomial() +
                                             " Remainder: " + resultPolynomial.get(1).getStringPolynomial());
            }
        });

        derivativeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getPolynomials();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation!");
                alert.setHeaderText("Select one polynomial");
                alert.setContentText("Please choose which polynomial you would like to derivate.");

                ButtonType buttonTypeOne = new ButtonType("First Polynomial");
                ButtonType buttonTypeTwo = new ButtonType("Second Polynomial");

                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeOne){
                    resultTextField.setText(firstPolynomial.derivative().getStringPolynomial());
                } else if (result.get() == buttonTypeTwo) {
                    resultTextField.setText(secondPolynomial.derivative().getStringPolynomial());
                }
            }
        });

        integrationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getPolynomials();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation!");
                alert.setHeaderText("Select one polynomial");
                alert.setContentText("Please choose which polynomial you would like to integrate.");

                ButtonType buttonTypeOne = new ButtonType("First Polynomial");
                ButtonType buttonTypeTwo = new ButtonType("Second Polynomial");

                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeOne){
                    resultTextField.setText(firstPolynomial.integrate().getStringPolynomial());
                } else if (result.get() == buttonTypeTwo) {
                    resultTextField.setText(secondPolynomial.integrate().getStringPolynomial());
                }
            }
        });
    }
}
