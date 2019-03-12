package flow;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;


public class Transition {

    private StackPane rootPane;

    private void makeFadeOut(StackPane rootPane, int ms, String url) {
        this.rootPane = rootPane;
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(ms));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event) -> {
            loadNextScene(url);
        });
        fadeTransition.play();

    }


    private void loadNextScene(String url) {

        try {
            Parent nextView;
            nextView = (StackPane) FXMLLoader.load(getClass().getResource(url));

            Scene newScene = new Scene(nextView);
            Stage curStage = (Stage) rootPane.getScene().getWindow();

            curStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void makeFadeInTransition(StackPane rootPane, int ms) {
        this.rootPane = rootPane;
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(ms));
        fadeTransition.setNode(this.rootPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public void changeScene(StackPane rootPane, int ms, String url) {


    PauseTransition pt = new PauseTransition(Duration.seconds(1.2));
        pt.setOnFinished((
    ActionEvent event1)-> {
        makeFadeOut(rootPane, ms, url);
    });
        pt.play();

    }

}
