package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage window;
    static MediaPlayer musicPlayer;
    static Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
        window.setTitle("Chess");
        window.setScene(new Scene(root,500,400));
        window.show();

    }

    public static void main(String[] args) { launch(args); }
}
