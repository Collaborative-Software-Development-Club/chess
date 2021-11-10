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

    public static Parent setRoot(ImageView imageView) throws IOException {
        //purple rook
        InputStream rookStream = new FileInputStream("src/sample/data/purple pieces/rook.png");
        Image rook = new Image(rookStream);
        ImageView rookView = new ImageView();
        rookView.setImage(rook);
        rookView.setMouseTransparent(true);
        rookView.setX(-110);
        rookView.setY(-40);

        //purple knight
        InputStream knightStream = new FileInputStream("src/sample/data/purple pieces/knight.png");
        Image knight = new Image(knightStream);
        ImageView knightView = new ImageView();
        knightView.setImage(knight);
        knightView.setMouseTransparent(true);
        knightView.setX(-100);
        knightView.setY(-40);

        //purple bishop
        InputStream bishopStream = new FileInputStream("src/sample/data/purple pieces/bishop.png");
        Image bishop = new Image(bishopStream);
        ImageView bishopView = new ImageView();
        bishopView.setImage(bishop);
        bishopView.setMouseTransparent(true);
        bishopView.setX(-100);
        bishopView.setY(-40);

        //purple king
        InputStream kingStream = new FileInputStream("src/sample/data/purple pieces/king.png");
        Image king = new Image(kingStream);
        ImageView kingView = new ImageView();
        kingView.setImage(king);
        kingView.setMouseTransparent(true);
        kingView.setX(160);
        kingView.setY(-40);

        //purple queen
        InputStream queenStream = new FileInputStream("src/sample/data/purple pieces/queen.png");
        Image queen = new Image(queenStream);
        ImageView queenView = new ImageView();
        queenView.setImage(queen);
        queenView.setMouseTransparent(true);
        queenView.setX(290);
        queenView.setY(-40);

        //purple bishop2
        ImageView bishop2View = new ImageView();
        bishop2View.setImage(bishop);
        bishop2View.setMouseTransparent(true);
        bishop2View.setX(100);
        bishop2View.setY(-40);

        //purple knight2
        ImageView knight2View = new ImageView();
        knight2View.setImage(knight);
        knight2View.setMouseTransparent(true);
        knight2View.setX(230);
        knight2View.setY(-40);

        //purple rook2
        ImageView rook2View = new ImageView();
        rook2View.setImage(rook);
        rook2View.setMouseTransparent(true);
        rook2View.setX(360);
        rook2View.setY(-40);

        //purple pawn
        InputStream pawnStream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn = new Image(pawnStream);
        ImageView pawnView = new ImageView();
        pawnView.setImage(pawn);
        pawnView.setMouseTransparent(true);
        pawnView.setX(-100);
        pawnView.setY(25);

        //purple pawn2
        InputStream pawn2Stream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn2 = new Image(pawn2Stream);
        ImageView pawn2View = new ImageView();
        pawn2View.setImage(pawn2);
        pawn2View.setMouseTransparent(true);
        pawn2View.setX(-170);
        pawn2View.setY(25);

        //purple pawn3
        InputStream pawn3Stream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn3 = new Image(pawn3Stream);
        ImageView pawn3View = new ImageView();
        pawn3View.setImage(pawn3);
        pawn3View.setMouseTransparent(true);
        pawn3View.setX(-30);
        pawn3View.setY(25);

        //purple pawn4
        InputStream pawn4Stream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn4 = new Image(pawn4Stream);
        ImageView pawn4View = new ImageView();
        pawn4View.setImage(pawn4);
        pawn4View.setMouseTransparent(true);
        pawn4View.setX(40);
        pawn4View.setY(25);

        //purple pawn5
        InputStream pawn5Stream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn5 = new Image(pawn5Stream);
        ImageView pawn5View = new ImageView();
        pawn5View.setImage(pawn5);
        pawn5View.setMouseTransparent(true);
        pawn5View.setX(100);
        pawn5View.setY(25);

        //purple pawn6
        InputStream pawn6Stream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn6 = new Image(pawn6Stream);
        ImageView pawn6View = new ImageView();
        pawn6View.setImage(pawn6);
        pawn6View.setMouseTransparent(true);
        pawn6View.setX(170);
        pawn6View.setY(25);

        //purple pawn7
        InputStream pawn7Stream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn7 = new Image(pawn7Stream);
        ImageView pawn7View = new ImageView();
        pawn7View.setImage(pawn7);
        pawn7View.setMouseTransparent(true);
        pawn7View.setX(-300);
        pawn7View.setY(25);

        //purple pawn8
        InputStream pawn8Stream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn8 = new Image(pawn8Stream);
        ImageView pawn8View = new ImageView();
        pawn8View.setImage(pawn8);
        pawn8View.setMouseTransparent(true);
        pawn8View.setX(-240);
        pawn8View.setY(25);

        //blue rook
        InputStream blueRookStream = new FileInputStream("src/sample/data/blue pieces/rook.png");
        Image blueRook = new Image(blueRookStream);
        ImageView blueRookView = new ImageView();
        blueRookView.setImage(blueRook);
        blueRookView.setMouseTransparent(true);
        blueRookView.setX(-110);
        blueRookView.setY(490);

        //blue knight
        InputStream blueKnightStream = new FileInputStream("src/sample/data/blue pieces/knight.png");
        Image blueKnight = new Image(blueKnightStream);
        ImageView blueKnightView = new ImageView();
        blueKnightView.setImage(blueKnight);
        blueKnightView.setMouseTransparent(true);
        blueKnightView.setX(-110);
        blueKnightView.setY(490);

        //blue bishop
        InputStream blueBishopStream = new FileInputStream("src/sample/data/blue pieces/bishop.png");
        Image blueBishop = new Image(blueBishopStream);
        ImageView blueBishopView = new ImageView();
        blueBishopView.setImage(blueBishop);
        blueBishopView.setMouseTransparent(true);
        blueBishopView.setX(-110);
        blueBishopView.setY(490);

        return new Group(imageView,rookView,knightView,bishopView,kingView,queenView,bishop2View,knight2View,
        rook2View,pawnView,pawn2View,pawn3View,pawn4View,pawn5View,pawn6View,pawn7View,pawn8View,
        blueRookView,blueKnightView,blueBishopView);
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
