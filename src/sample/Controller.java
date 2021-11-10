package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javafx.event.ActionEvent;

public class Controller {

    private Scene scene;
    private Stage stage;
    private Parent root;

    @FXML
    private Button singleplayerBtn;

    @FXML
    private Button multiplayerBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Button quitBtn;

    @FXML
    public void openSettings(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("settingsScene.fxml"));
        Main.window.setScene(new Scene(root, 500, 400));
        Main.window.setFullScreen(SettingsController.fullscreen);
    }

    @FXML
    public void quitGame(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }


    @FXML
    public void startMultiplayer(ActionEvent event) throws IOException {

        MultiplayerController multiplayerController = new MultiplayerController();

        multiplayerController.initializeBoard(event);

    }



    @FXML
    public void startSingleplayer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("singleplayerScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
