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
    private int pickedXCoord;
    private int pickedYCoord;


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
                chessBoard[i][j] = new chessSquare();
                chessBoard[i][j].chessPiece = new chessPiece();
                chessBoard[i][j].isEmpty = true;
            }
        }

        // assign full status to the rest of the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 2; j++) {
                chessBoard[i][j] = new chessSquare();
                chessBoard[i][j].chessPiece = new chessPiece();
                chessBoard[i][j].isEmpty = false;
                chessBoard[i][j].chessPiece.isWhiteTeam = false;
                chessBoard[i][j].chessPiece.isDead = false;
            }
            for (int j = 6; j < 8; j++) {
                chessBoard[i][j] = new chessSquare();
                chessBoard[i][j].chessPiece = new chessPiece();
                chessBoard[i][j].isEmpty = false;
                chessBoard[i][j].chessPiece.isWhiteTeam = true;
                chessBoard[i][j].chessPiece.isDead = false;
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

        gameState = "pendingWhite"; // white piece needs to be picked

        startGame(event);

    }

    // executes
    @FXML
    void startGame(ActionEvent event) throws IOException {
        int coordinates[] = new int[2];
        // processes pixel coordinates and turns them into chess board coordinates
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
                makeMove(event, coordinates); // executes with every mouse click
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
    void makeMove(ActionEvent event, int[] coordinates) throws IOException { // executes with every mouse click
        int xCoord = coordinates[0];
        int yCoord = coordinates[1];
        if (gameState == "pendingWhite") { // pendingWhite is when you have to pick a white chessPiece
            pickWhite(xCoord, yCoord);
        } else if (gameState == "pendingBlack") { // same as above but for black chess piece
            pickBlack(xCoord, yCoord);
        } else if (gameState == "whitePick") { // once the white piece has been picked, pick where to put it
            whitePickSpot(xCoord, yCoord);
        } else if (gameState == "blackPick") { // same as above
            blackPickSpot(xCoord, yCoord);
        }
    }

    @FXML
    void pickWhite(int xCoord, int yCoord) {
        // if a white chess piece is selected, then the state is changed and its coordinates are saved
        if (!chessBoard[xCoord][yCoord].isEmpty && chessBoard[xCoord][yCoord].chessPiece.isWhiteTeam) {
            gameState = "whitePick";
            pickedXCoord = xCoord;
            pickedYCoord = yCoord;
        }
    }

    void pickBlack(int xCoord, int yCoord) throws IOException {

        // is the piece black, if so change game state to blackPick

        if ((!chessBoard[xCoord][yCoord].isEmpty) && (!chessBoard[xCoord][yCoord].chessPiece.isWhiteTeam)){
                gameState = "blackPick";
                pickedXCoord = xCoord;
                pickedYCoord = yCoord;
        }
    }


    void blackPickSpot(int xCoord, int yCoord) throws IOException {

        if (chessBoard[pickedXCoord][pickedYCoord].chessPiece.name.equals("pawn")){

            if (xCoord == pickedXCoord && yCoord > -1 && yCoord < 8 && (yCoord == 1 + xCoord || yCoord == 2 + xCoord)) {
                gameState = "pendingWhite";
            }

        }
        else if (chessBoard[pickedXCoord][pickedYCoord].chessPiece.name.equals("rook")){



        }
        else if (chessBoard[pickedXCoord][pickedYCoord].chessPiece.name.equals("bishop")){

        }
        else if (chessBoard[pickedXCoord][pickedYCoord].chessPiece.name.equals("queen")){

        }
        else if (chessBoard[pickedXCoord][pickedYCoord].chessPiece.name.equals("king")){

        }
    }

    @FXML
    void whitePickSpot(int xCoord, int yCoord) {
            String pieceName = chessBoard[pickedXCoord][pickedYCoord].chessPiece.name;
            if((chessBoard[xCoord][yCoord].isEmpty || !chessBoard[xCoord][yCoord].chessPiece.isWhiteTeam)){
              if (pieceName.equals("bishop")) {
                if (!((Math.abs(xCoord - pickedXCoord) == Math.abs(yCoord - pickedYCoord)))){
                    gameState = "pendingBlack";
                }
            } else if (pieceName.equals("pawn")) {

            }
          }

        System.out.println(gameState);
    }

}


