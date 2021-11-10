package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
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
    private Image boardImage;
    private int pickedXCoord;
    private int pickedYCoord;


    public class chessSquare {
        Boolean isEmpty;
        chessPiece chessPiece;
    }

    public class chessPiece {
        String name;
        Boolean isBlueTeam;
        Boolean isDead;
    }

    @FXML
    void initializeBoard(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("gameScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Multiplayer Chess");
        stage.setWidth(575);

        // for input stream, put the directory of you chessBackground picture in your local machine
        InputStream stream = new FileInputStream("src/sample/data/gameBackground.jpg");
        boardImage = new Image(stream);
        imageView = new ImageView();
        imageView.setImage(boardImage);
        imageView.setX(0);
        imageView.setY(0);
        imageView.setFitWidth(575);
        imageView.setFitHeight(575);
        imageView.setPreserveRatio(false);


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
                chessBoard[i][j].chessPiece.isBlueTeam = false;
                chessBoard[i][j].chessPiece.isDead = false;
            }
            for (int j = 6; j < 8; j++) {
                chessBoard[i][j] = new chessSquare();
                chessBoard[i][j].chessPiece = new chessPiece();
                chessBoard[i][j].isEmpty = false;
                chessBoard[i][j].chessPiece.isBlueTeam = true;
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

        root = setRoot(imageView); // adds chess piece images to board
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        gameState = "pickBlue"; // blue piece needs to be picked

        startGame(event);

    }

    // executes
    @FXML
    void startGame(ActionEvent event) throws IOException {
        int coordinates[] = new int[2];
        // processes pixel coordinates and turns them into chess board coordinates
        imageView.setOnMouseClicked(e -> {
            double xCoord = e.getX() / imageView.getFitWidth(); // relative coordinates to image view
            double yCoord = e.getY() / imageView.getFitHeight();
            double boardXCoord = (xCoord - .0365) / (.965 - .0365); // relative coordinates to board
            double boardYCoord = (yCoord - .0365) / (.965 - .0365);

            // if relative coordinates to board fall within 0% and 100%, they are converted to chess square
            // coordinates
            if (boardXCoord <= 1 && boardXCoord >= 0 && boardYCoord <= 1 && boardYCoord >= 0) {
                coordinates[0] = (int) (boardXCoord * 8);
                coordinates[1] = (int) (boardYCoord * 8);

                try {
                    makeMove(event, coordinates); // executes with every mouse click
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    int [] score(chessSquare[][] chessBoard){

        int [] score = new int[2];
        // first element is blue score
        // second element is purple score
        score[0] = 0;
        score[1] = 1;

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (!chessBoard[i][j].isEmpty){
                    if (!chessBoard[i][j].chessPiece.isDead){
                        if (chessBoard[i][j].chessPiece.isBlueTeam){
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
        if (gameState == "pickBlue") { // pickBlue is when you have to pick a blue chessPiece
            pickBlue(xCoord, yCoord);
        } else if (gameState == "pickPurple") { // same as above but for purple chess piece
            pickPurple(xCoord, yCoord);
        } else if (gameState == "bluePickSpot") { // once the blue piece has been picked, pick where to put it
            bluePickSpot(xCoord, yCoord);
        } else if (gameState == "purplePickSpot") { // same as above
            purplePickSpot(xCoord, yCoord);
        }
    }

    @FXML
    void pickBlue(int xCoord, int yCoord) {
        // if a blue chess piece is selected, then the state is changed and its coordinates are saved
        if (!chessBoard[xCoord][yCoord].isEmpty && chessBoard[xCoord][yCoord].chessPiece.isBlueTeam) {
            gameState = "bluePickSpot";
            pickedXCoord = xCoord;
            pickedYCoord = yCoord;
        }
    }

    void pickPurple(int xCoord, int yCoord) throws IOException {

        // is the piece purple, if so change game state to purplePickSpot

        if ((!chessBoard[xCoord][yCoord].isEmpty) && (!chessBoard[xCoord][yCoord].chessPiece.isBlueTeam)){
                gameState = "purplePickSpot";
                pickedXCoord = xCoord;
                pickedYCoord = yCoord;
        }
    }


    void purplePickSpot(int xCoord, int yCoord) throws IOException {

        if (chessBoard[pickedXCoord][pickedYCoord].chessPiece.name.equals("pawn")){

            if (xCoord == pickedXCoord && yCoord > -1 && yCoord < 8 && (yCoord == 1 + xCoord || yCoord == 2 + xCoord)) {
                gameState = "pickBlue";
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
    void bluePickSpot(int xCoord, int yCoord) {
            String pieceName = chessBoard[pickedXCoord][pickedYCoord].chessPiece.name;
              if (pieceName.equals("bishop")) {
                  boolean noSpaceBetween = true;
                if (((Math.abs(xCoord - pickedXCoord) == Math.abs(yCoord - pickedYCoord))) && (chessBoard[xCoord][yCoord].isEmpty || !chessBoard[xCoord][yCoord].chessPiece.isBlueTeam)){
                    for(int xCheck = xCoord; xCheck < pickedXCoord; xCheck++) {
                        int yCheck = Math.abs(xCoord - pickedXCoord) + pickedYCoord;
                        if(!chessBoard[xCheck][yCheck].isEmpty) {
                            noSpaceBetween = false;
                        }
                    }
                }
                if(noSpaceBetween) {
                    gameState = "pickPurple";
                }
            } else if (pieceName.equals("rook")) {
                  if ((chessBoard[xCoord][yCoord].isEmpty || !chessBoard[xCoord][yCoord].chessPiece.isBlueTeam)) {
                      boolean noSpaceBetween = true;
                      if(xCoord == pickedXCoord) {
                          for(int yCheck = yCoord; yCheck < pickedYCoord; yCheck++) {
                              if(!chessBoard[xCoord][yCheck].isEmpty) {
                                  noSpaceBetween = false;
                              }
                          }
                      } else if (yCoord == pickedYCoord){
                          for(int xCheck = xCoord; xCheck < pickedXCoord; xCheck++) {
                              if(!chessBoard[xCheck][yCoord].isEmpty) {
                                  noSpaceBetween = false;
                              }
                          }
                      }

                      if(noSpaceBetween) {
                          gameState = "pickPurple";
                      }
                  }
            }
        System.out.println(gameState);
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

}


