package editor;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import flow.Transition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectionMenuController implements Initializable {



    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXButton dishesButton;
    @FXML
    private JFXButton categoriesButton;
    @FXML
    private ImageView dishesImage;
    @FXML
    private ImageView categoriesImage;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private AnchorPane rigthPane;

    @FXML
    private JFXSpinner spinner;

    private Transition transition;



    @Override
    public void initialize(URL url, ResourceBundle rb){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        rootPane.setPrefWidth(width);
        rootPane.setPrefHeight(height);

        System.out.println(width);

        AnchorPane.setLeftAnchor(rigthPane,width/2);
        AnchorPane.setRightAnchor(leftPane,width/2);

        leftPane.setPrefHeight(height);
        rigthPane.setPrefHeight(height);
        leftPane.setPrefWidth(width/2);
        rigthPane.setPrefWidth(width/2);

        spinner.setVisible(false);
        categoriesButton.setTextFill(Paint.valueOf("white"));
        dishesButton.setTextFill(Paint.valueOf("black"));
        dishesButton.setOnMouseEntered(e->{

            dishesImage.setY(dishesImage.getY()-50);

        });

        dishesButton.setOnMouseExited(e->{

            dishesImage.setY(dishesImage.getY()+50);

        });
        categoriesButton.setOnMouseEntered(e->{

            categoriesImage.setY(categoriesImage.getY()+30);
        });

        categoriesButton.setOnMouseExited(e->{

            categoriesImage.setY(categoriesImage.getY()-30);
        });


        categoriesButton.setOnAction(e->{

           changeScreen("/editor/CategoriesScreen.fxml");

        });

        dishesButton.setOnAction(e->{

           changeScreen("/editor/DishesScreen.fxml");

        });


        transition = new Transition();
        new SlideInDown(leftPane).play();
        new SlideInUp(rigthPane).play();
        transition.makeFadeInTransition(rootPane, 600);


    }

    private void changeScreen(String url){

        transition = new Transition();
        categoriesButton.setVisible(false);
        categoriesImage.setVisible(false);
        dishesImage.setVisible(false);
        dishesButton.setVisible(false);
        spinner.setVisible(true);
        new SlideOutDown(rigthPane).play();
        new SlideOutUp(leftPane).play();

        transition.changeScene(rootPane,600, url);
    }



}
