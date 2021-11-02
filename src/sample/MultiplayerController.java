package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class MultiplayerController {

    private Scene scene;
    private Stage stage;
    private Parent root;
    private String gameState;
    public chessSquare[][] chessBoard = new chessSquare[8][8];
    private ImageView imageView;

    public class chessSquare {
        Boolean isEmpty;
        chessPiece chessPiece;
    }

    public class chessPiece {
        String name;
        Boolean isWhiteTeam;
        Boolean isDead;
    }

    @FXML
    void initializeBoard(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("gameScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Multiplayer Chess");

        // for input stream, put the directory of you chessBackground picture in your local machine
        InputStream stream = new FileInputStream("src/sample/data/chessBackground.jpg");
        Image boardImage = new Image(stream);
        imageView = new ImageView();
        imageView.setImage(boardImage);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setFitWidth(575);
        imageView.setPreserveRatio(true);
        root = Controller.setRoot(imageView);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // make the middle of the chess board empty
        for (int i = 0; i < 8; i++) {
            for (int j = 2; j < 6; j++) {
                chessBoard[j][i] = new chessSquare();
                chessBoard[j][i].chessPiece = new chessPiece();
                chessBoard[j][i].isEmpty = true;
            }
        }

        // assign full status to the rest of the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 2; j++) {
                chessBoard[j][i] = new chessSquare();
                chessBoard[j][i].chessPiece = new chessPiece();
                chessBoard[j][i].isEmpty = false;
                chessBoard[j][i].chessPiece.isWhiteTeam = false;
                chessBoard[j][i].chessPiece.isDead = false;
            }
            for (int j = 6; j < 8; j++) {
                chessBoard[j][i] = new chessSquare();
                chessBoard[j][i].chessPiece = new chessPiece();
                chessBoard[j][i].isEmpty = false;
                chessBoard[j][i].chessPiece.isWhiteTeam = true;
                chessBoard[j][i].chessPiece.isDead = false;
            }
        }

        // assign pawn positions
        for (int i = 0; i < 8; i++) {
            chessBoard[i][1].chessPiece.name = (new StringBuilder()).append("Pawn").append(i).toString();
            chessBoard[i][6].chessPiece.name = (new StringBuilder()).append("Pawn").append(i).toString();
        }

        // assign the rest of the pieces' positions
        chessBoard[0][0].chessPiece.name = "rook";
        chessBoard[1][0].chessPiece.name = "horse";
        chessBoard[2][0].chessPiece.name = "bishop";
        chessBoard[3][0].chessPiece.name = "king";
        chessBoard[4][0].chessPiece.name = "queen";
        chessBoard[5][0].chessPiece.name = "bishop";
        chessBoard[6][0].chessPiece.name = "horse";
        chessBoard[7][0].chessPiece.name = "rook";

        chessBoard[0][7].chessPiece.name = "rook";
        chessBoard[1][7].chessPiece.name = "horse";
        chessBoard[2][7].chessPiece.name = "bishop";
        chessBoard[3][7].chessPiece.name = "king";
        chessBoard[4][7].chessPiece.name = "queen";
        chessBoard[5][7].chessPiece.name = "bishop";
        chessBoard[6][7].chessPiece.name = "horse";
        chessBoard[7][7].chessPiece.name = "rook";

        gameState = "pendingWhite";

        selectPiece(event);

    }


    @FXML
    void selectPiece(ActionEvent event) throws IOException {
        int coordinates[] = new int[2];
        imageView.setOnMouseClicked(e -> {
            double xCoord = e.getX();
            double yCoord = e.getY();
            xCoord -= 20;
            yCoord -= 20;

            if (xCoord < 535 && yCoord < 535) {
                coordinates[0] = (int) xCoord / 67;
                coordinates[1] = (int) yCoord / 67;
            }
            try {
                makeMove(event, coordinates);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }


    int [] score(chessSquare[][] chessBoard){

        int [] score = new int[2];
        // first element is white score
        // second element is black score
        score[0] = 0;
        score[1] = 1;

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (!chessBoard[i][j].isEmpty){
                    if (!chessBoard[i][j].chessPiece.isDead){
                        if (chessBoard[i][j].chessPiece.isWhiteTeam){
                            score[0] ++;
                        }
                        else{
                            score[1] ++;
                        }
                    }
                }

            }
        }
        return score;
    }

    @FXML
    void makeMove(ActionEvent event, int[] coordinates) throws IOException {
        int xCoord = coordinates[0];
        int yCoord = coordinates[1];
        if (gameState == "pendingWhite") {

        }
    }
}
