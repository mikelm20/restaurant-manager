package splashscreen;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.util.ResourceBundle;

import flow.Transition;
import animatefx.animation.*;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import login.LoginBuisness;

import javax.persistence.Persistence;

public class WelcomeController implements Initializable {

   @FXML
    private ImageView imageLogo;

    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane backPane;

    @FXML
    private Text text;

    private Transition transition;
    private String nextScene;
    


    @FXML
    void handleClick(MouseEvent event) {

        transition = new Transition();
        new ZoomOut(text).setSpeed(0.5).play();
        new ZoomOutLeft(imageLogo).setSpeed(0.5).play();
        new SlideOutLeft(backPane).setSpeed(1).play();
        transition.changeScene(rootPane, 600, nextScene);

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        LoginBuisness lb = new LoginBuisness();
        new FadeIn(rootPane).setSpeed(0.7).play();
        new SlideInRight(backPane).setSpeed(1).play();
        new ZoomInUp(imageLogo).setSpeed(0.5).play();
        new ZoomIn(text).setSpeed(0.25).play();
        if(lb.isFirstLogin())
            nextScene="/signup/SignUpPage.fxml";
        else
           nextScene="/login/LoginPage.fxml";



    }




    
}
