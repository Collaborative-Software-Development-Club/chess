package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    static Stage window;
    static String musicPath;
    static MediaPlayer musicPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //loads default music
        musicPath = "src/sample/data/defaultMusic.mp3";
        Media music = new Media(new File(musicPath).toURI().toString());
        musicPlayer = new MediaPlayer(music);
        musicPlayer.play();

        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
        window.setTitle("Chess");
        window.setScene(new Scene(root));
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
