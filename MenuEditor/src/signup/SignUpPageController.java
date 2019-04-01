/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signup;

import animatefx.animation.*;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTextField;
import flow.Transition;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import login.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class SignUpPageController implements Initializable {

    @FXML
    private Button singupButton;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confpassword;

    @FXML
    private TextField username;
    
    @FXML
    private StackPane rootPane;
    
    @FXML
    private Label errorLabel;

    @FXML
    private AnchorPane leftPane;

    private Transition transition;

    @FXML
    private JFXColorPicker colorPicker;

    @FXML
    private JFXColorPicker colorSecundarioPicker;
    @FXML
    private AnchorPane topPane;

    @FXML
    private Label title;
    @FXML
    private Label title2;
    @FXML
    private Label w_letter;

    @FXML
    private JFXTextField restaurantName;
    
    @FXML
    void goLogin(ActionEvent event){

        transition = new Transition();
        errorLabel.setVisible(false);
        new SlideOutDown(leftPane).play();
        new FadeOut(username).play();
        new FadeOut(password).play();
        new FadeOut(confpassword).play();
        new FadeOut(singupButton).play();
        new FadeOutRightBig(topPane).play();
        new FadeOut(colorPicker).play();
        transition.changeScene(rootPane,600,"/login/LoginPage.fxml");


    }

    @FXML
    void onHandlerClick(ActionEvent event) {
        
        int code;
        String error = null;
        LoginData regisUser = new LoginData();
        regisUser.setUsername(username.getText());
        regisUser.setPassword(password.getText());
        regisUser.setColor("#"+Integer.toHexString(colorPicker.getValue().hashCode()).substring(0, 6));
        regisUser.setColorSecundario("#"+Integer.toHexString(colorSecundarioPicker.getValue().hashCode()).substring(0, 6));
        regisUser.setNombre(restaurantName.getText());
        
        LoginBuisness persister = new LoginBuisness();
        code = persister.persistLoginData(regisUser,confpassword.getText());
        
        
       switch(code){
            case 1: error="Username alredy taken";
                     break;
            case 2: error="Empty labels";
                    break;
            case 3: error="Passwords doesn't match";
                    break;
        }
        
        if(code == 0) {
            this.goLogin(event);
        }
        
        else{
            
            errorLabel.setText(error);
            errorLabel.setTextFill(Color.web("#ff0000"));
            errorLabel.setOpacity(1);
            new Pulse(errorLabel).setSpeed(10).setCycleCount(3).play();
            
        }

    }

    @FXML
    private void clearLabel()
    {
        errorLabel.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ImageView logo = new ImageView(new Image("/Resources/logo.png"));
        logo.setFitHeight(50);
        logo.setFitWidth(130);
        logo.setLayoutY(520);
        logo.setLayoutX(8);
        leftPane.getChildren().add(logo);
        transition = new Transition();
        rootPane.setOpacity(0);
        errorLabel.setOpacity(0);
        new FadeInUpBig(leftPane).setSpeed(0.8).play();
        new FadeIn(username).setSpeed(0.3).play();
        new FadeIn(password).setSpeed(0.3).play();
        new FadeIn(confpassword).setSpeed(0.3).play();
        new FadeIn(singupButton).setSpeed(0.3).play();
        new SlideInLeft(topPane).setSpeed(0.8).play();
        transition.makeFadeInTransition(rootPane,600);

    }




    

    
}
