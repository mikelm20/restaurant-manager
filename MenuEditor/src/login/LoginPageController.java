/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import com.jfoenix.controls.JFXSpinner;
import flow.Transition;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;


public class LoginPageController implements Initializable{

 
   
     @FXML
    private JFXPasswordField passwordText;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXTextField usernameText;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXSpinner spinner;

    
    @FXML
    private Label errorLabel;

    @FXML
    private AnchorPane topPane;

    private Transition transition;




    @FXML
    private void clearLabel()
    {
        errorLabel.setVisible(false);
    }


    @FXML
    private void handleButtonAction(ActionEvent event) {
        

        if(usernameText.getText().isEmpty() || passwordText.getText().isEmpty()){

            if(usernameText.getText().isEmpty() && passwordText.getText().isEmpty())
            {
                errorLabel.setText("Access denied: Empty credentials.");
                errorLabel.setTextFill(Color.web("#ff0000"));
                errorLabel.setVisible(true);
                new Pulse(errorLabel).setSpeed(10).setCycleCount(3).play();
                new Pulse(usernameText).setSpeed(10).setCycleCount(3).play();
                new Pulse(passwordText).setSpeed(10).setCycleCount(3).play();

            }


            else if(usernameText.getText().isEmpty()) {
                errorLabel.setText("Access denied: Username is empty.");
                errorLabel.setTextFill(Color.web("#ff0000"));
                errorLabel.setVisible(true);
                new Pulse(errorLabel).setSpeed(10).setCycleCount(3).play();
                new Pulse(usernameText).setSpeed(10).setCycleCount(3).play();
            }

            else if(passwordText.getText().isEmpty()) {
                errorLabel.setText("Access denied: Password is empty.");
                errorLabel.setTextFill(Color.web("#ff0000"));
                errorLabel.setVisible(true);
                new Pulse(errorLabel).setSpeed(10).setCycleCount(3).play();
                new Pulse(passwordText).setSpeed(10).setCycleCount(3).play();
            }


        }
        else {
            usernameText.setVisible(false);
            passwordText.setVisible(false);
            loginButton.setVisible(false);
            errorLabel.setVisible(false);
            spinner.setVisible(true);
            String user = usernameText.getText();
            String pass = passwordText.getText();
            LoginBuisness login = new LoginBuisness();
            boolean valid = login.getLogin(user, pass);

            if (!valid) {
                errorLabel.setText("Access denied: Wrong password or username.");
                errorLabel.setTextFill(Color.web("#ff0000"));
                spinner.setVisible(false);
                usernameText.setVisible(true);
                passwordText.setVisible(true);
                loginButton.setVisible(true);
                errorLabel.setVisible(true);
                new Pulse(errorLabel).setSpeed(10).setCycleCount(3).play();


            } else if (valid) {
                transition = new Transition();
                transition.changeScene(rootPane, 600, "/editor/SelectionMenu.fxml");

            }
        }



    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        rootPane.setPrefWidth(width);
        rootPane.setPrefHeight(height);

        ImageView logo = new ImageView(new Image("/Resources/welcome.png"));
        logo.maxHeight(60);
        logo.maxWidth(280);
        logo.setFitHeight(60);
        logo.setFitWidth(280);
        logo.setLayoutY(20);
        topPane.getChildren().addAll(logo);

        transition = new Transition();
        spinner.setVisible(false);
        rootPane.setOpacity(0);
        new SlideInLeft(topPane).setSpeed(0.8).play();
        new FadeIn(usernameText).setSpeed(0.5).play();
        new FadeIn(passwordText).setSpeed(0.5).play();
        new FadeIn(loginButton).setSpeed(0.4).play();
        transition.makeFadeInTransition(rootPane, 600);

    }


    

}
