import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {




        Parent root = FXMLLoader.load(getClass().getResource("splashscreen/Welcome.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
       // stage.setFullScreen(true);
        // stage.setFullScreenExitHint("");
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
