package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MultiplayerController {

    private Scene scene;
    private Stage stage;
    private Parent root;
    private String gameState;
    public chessSquare[][] chessBoard = new chessSquare[8][8];


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
    void initializeBoard() {

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

        gameState = "whiteTurn";
    }

    @FXML
    void selectPiece(ActionEvent event, double xCoord, double yCoord) throws IOException {
        //System.out.println("["+xCoord+", "+yCoord+"]");

        xCoord -= 20;
        yCoord -= 20;

        if (xCoord < 535 && yCoord < 535) {
            int squareXCoord = (int) xCoord / 67;
            int squareYCoord = (int) yCoord / 67;
            System.out.println(squareXCoord + "," + squareYCoord);
            System.out.println(chessBoard[squareXCoord][squareYCoord].chessPiece.name);
        }
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

}
