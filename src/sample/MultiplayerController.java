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
    public chessSquare[][] chessBoard;


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
                chessBoard[i][j].isEmpty = true;
            }
        }

        // assign full status to the rest of the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 2; j++) {
                chessBoard[i][j].isEmpty = false;
                chessBoard[i][j].chessPiece.isWhiteTeam = false;
                chessBoard[i][j].chessPiece.isDead = false;
            }
            for (int j = 6; j < 8; j++) {
                chessBoard[i][j].isEmpty = false;
                chessBoard[i][j].chessPiece.isWhiteTeam = true;
                chessBoard[i][j].chessPiece.isDead = false;
            }
        }

        // assign pawn positions
        for (int i = 0; i < 8; i++) {
            chessBoard[1][i].chessPiece.name = (new StringBuilder()).append("Pawn").append(i).toString();
            chessBoard[6][i].chessPiece.name = (new StringBuilder()).append("Pawn").append(i).toString();
        }

        // assign the rest of the pieces' positions
        chessBoard[0][0].chessPiece.name = "rook";
        chessBoard[0][1].chessPiece.name = "horse";
        chessBoard[0][2].chessPiece.name = "bishop";
        chessBoard[0][3].chessPiece.name = "king";
        chessBoard[0][4].chessPiece.name = "queen";
        chessBoard[0][5].chessPiece.name = "bishop";
        chessBoard[0][6].chessPiece.name = "horse";
        chessBoard[0][7].chessPiece.name = "rook";

        chessBoard[7][0].chessPiece.name = "rook";
        chessBoard[7][1].chessPiece.name = "horse";
        chessBoard[7][2].chessPiece.name = "bishop";
        chessBoard[7][3].chessPiece.name = "king";
        chessBoard[7][4].chessPiece.name = "queen";
        chessBoard[7][5].chessPiece.name = "bishop";
        chessBoard[7][6].chessPiece.name = "horse";
        chessBoard[7][7].chessPiece.name = "rook";

        gameState = "whiteTurn";
    }

    @FXML
    void selectPiece(ActionEvent event, double xCoord, double yCoord) throws IOException {
        //System.out.println("["+xCoord+", "+yCoord+"]");

        xCoord -= 20;
        yCoord -= 20;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.println(chessBoard[i][j].chessPiece.name);
            }
        }
        if (xCoord < 535 && yCoord < 535) {
            int squareXCoord = (int) xCoord / 67;
            int squareYCoord = (int) yCoord / 67;
            System.out.println(squareXCoord + "," + squareYCoord);
            System.out.println(chessBoard[squareXCoord][squareYCoord].chessPiece.name);
        }

    }
}
