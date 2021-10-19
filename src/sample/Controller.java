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
        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
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

        root = FXMLLoader.load(getClass().getResource("gameScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Multiplayer Chess");

        // for input stream, put the directory of you chessBackground picture in your local machine
        InputStream stream = new FileInputStream("src/sample/data/chessBackground.jpg");
        Image boardImage = new Image(stream);
        ImageView imageView = new ImageView();
        imageView.setImage(boardImage);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setFitWidth(575);
        imageView.setPreserveRatio(true);
        root = setRoot(imageView);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        MultiplayerController multiplayerController = new MultiplayerController();

        multiplayerController.initializeBoard();

        imageView.setOnMouseClicked(e -> {
            double xCoord = e.getX();
            double yCoord = e.getY();
            try {
                multiplayerController.selectPiece(event, xCoord, yCoord);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    public Parent setRoot(ImageView imageView) throws IOException {
        //rook
        InputStream rookStream = new FileInputStream("src/sample/data/purple pieces/rook.png");
        Image rook = new Image(rookStream);
        ImageView rookView = new ImageView();
        rookView.setImage(rook);
        rookView.setMouseTransparent(true);
        rookView.setX(-110);
        rookView.setY(-40);

        //knight
        InputStream knightStream = new FileInputStream("src/sample/data/purple pieces/knight.png");
        Image knight = new Image(knightStream);
        ImageView knightView = new ImageView();
        knightView.setImage(knight);
        knightView.setMouseTransparent(true);
        knightView.setX(-100);
        knightView.setY(-40);

        //bishop
        InputStream bishopStream = new FileInputStream("src/sample/data/purple pieces/bishop.png");
        Image bishop = new Image(bishopStream);
        ImageView bishopView = new ImageView();
        bishopView.setImage(bishop);
        bishopView.setMouseTransparent(true);
        bishopView.setX(-100);
        bishopView.setY(-40);

        //king
        InputStream kingStream = new FileInputStream("src/sample/data/purple pieces/king.png");
        Image king = new Image(kingStream);
        ImageView kingView = new ImageView();
        kingView.setImage(king);
        kingView.setMouseTransparent(true);
        kingView.setX(160);
        kingView.setY(-40);

        //queen
        InputStream queenStream = new FileInputStream("src/sample/data/purple pieces/queen.png");
        Image queen = new Image(queenStream);
        ImageView queenView = new ImageView();
        queenView.setImage(queen);
        queenView.setMouseTransparent(true);
        queenView.setX(290);
        queenView.setY(-40);

        //bishop2
        ImageView bishop2View = new ImageView();
        bishop2View.setImage(bishop);
        bishop2View.setMouseTransparent(true);
        bishop2View.setX(100);
        bishop2View.setY(-40);

        //knight2
        ImageView knight2View = new ImageView();
        knight2View.setImage(knight);
        knight2View.setMouseTransparent(true);
        knight2View.setX(230);
        knight2View.setY(-40);

        //rook2
        ImageView rook2View = new ImageView();
        rook2View.setImage(rook);
        rook2View.setMouseTransparent(true);
        rook2View.setX(360);
        rook2View.setY(-40);

        return new Group(imageView,rookView,knightView,bishopView,kingView,queenView,bishop2View,knight2View,rook2View);
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
