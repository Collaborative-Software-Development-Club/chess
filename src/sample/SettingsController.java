package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class SettingsController {

    @FXML
    private Button aiDifficultyBtn;

    @FXML
    private Button musicBtn;

    @FXML
    private Button fullscreenBtn;

    @FXML
    private Button backBtn;

    @FXML
    void aiDifficulty(javafx.event.ActionEvent event) throws IOException {

    }

    //Music files
    final Media alternativeMusic = new Media(
            new File("src/sample/data/alternativeMusic.mp3").toURI().toString());
    final Media defaultMusic = new Media(new File("src/sample/data/defaultMusic.mp3").toURI().toString());

    MediaPlayer alternativeMusicPlayer = new MediaPlayer(alternativeMusic);
    MediaPlayer defaultMusicPlayer = new MediaPlayer(defaultMusic);
    boolean isDefaultPlaying = true;

    @FXML
    void changeMusic(javafx.event.ActionEvent event) {
        Main.musicPlayer.stop();
        alternativeMusicPlayer.stop();
        defaultMusicPlayer.stop();

        //Changes music to alternative music
        if (isDefaultPlaying) {
            isDefaultPlaying = false;
            alternativeMusicPlayer.play();
        } else {
            isDefaultPlaying = true;
            defaultMusicPlayer.play();
        }
    }

    static boolean fullscreen = false;
    @FXML
    void fullscreen(javafx.event.ActionEvent event) throws IOException {
        Main.window.setFullScreen(!fullscreen);
        fullscreen = !fullscreen;
    }

    @FXML
    void back(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
        Main.window.setScene(new Scene(root,500,400));
        Main.window.setFullScreen(fullscreen);
    }
}
