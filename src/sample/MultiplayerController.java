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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
    private boolean enPassBlue[][] = {
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false}
    };
    private boolean enPassPurple[][] = {
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false}
    };


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

            //System.out.println(xCoord);
            //System.out.println(yCoord);

            //System.out.println(chessBoard[pickedXCoord][pickedYCoord].chessPiece.name);
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

    @FXML
    void makeMove(ActionEvent event, int[] coordinates) throws IOException { // executes with every mouse click
        int xCoord = coordinates[0];
        int yCoord = coordinates[1];
        if (gameState == "pickBlue") { // pickBlue is when you have to pick a blue chessPiece
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    enPassBlue[i][j] = false;
                }
            }
            pickBlue(xCoord, yCoord);
        } else if (gameState == "pickPurple") { // same as above but for purple chess piece
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    enPassPurple[i][j] = false;
                }
            }
            pickPurple(xCoord, yCoord);
        } else if (gameState == "bluePickSpot") { // once the blue piece has been picked, pick where to put it
            bluePickSpot(xCoord, yCoord);
            updateImage();
        } else if (gameState == "purplePickSpot") { // same as above
            purplePickSpot(xCoord, yCoord);
            updateImage();
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

        String pieceName = chessBoard[pickedXCoord][pickedYCoord].chessPiece.name;
        boolean noSpaceBetween = true;
        if (pieceName.equals("bishop")) {
            if (((Math.abs(xCoord - pickedXCoord) == Math.abs(yCoord - pickedYCoord))) && (chessBoard[xCoord][yCoord].isEmpty || chessBoard[xCoord][yCoord].chessPiece.isBlueTeam)){
                int yCheck = pickedYCoord;
                if(pickedXCoord > xCoord) {
                    for (int xCheck = pickedXCoord - 1; xCheck > xCoord; xCheck--) {
                        if(yCoord < pickedYCoord) {
                            yCheck--;
                        } else if(yCoord > pickedYCoord) {
                            yCheck++;
                        }
                        if (!chessBoard[xCheck][yCheck].isEmpty) {
                            noSpaceBetween = false;
                        }
                    }
                } else {
                    for (int xCheck = pickedXCoord + 1; xCheck < xCoord; xCheck++) {
                        if(yCoord < pickedYCoord) {
                            yCheck--;
                        } else if(yCoord > pickedYCoord){
                            yCheck++;
                        }
                        if (!chessBoard[xCheck][yCheck].isEmpty) {
                            noSpaceBetween = false;
                        }
                    }
                }
                if(noSpaceBetween) {
                    gameState = "pickBlue";
                    chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                    chessBoard[xCoord][yCoord].isEmpty = false;
                    chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = false;
                    chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                    chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                }
            }
        } else if (pieceName.equals("rook")) {
            if ((chessBoard[xCoord][yCoord].isEmpty || chessBoard[xCoord][yCoord].chessPiece.isBlueTeam)) {
                if(xCoord == pickedXCoord) {
                    if(pickedYCoord < yCoord) {
                        for (int yCheck = pickedYCoord + 1; yCheck < yCoord; yCheck++) {
                            if (!chessBoard[xCoord][yCheck].isEmpty) {
                                noSpaceBetween = false;
                            }
                        }
                    } else {
                        for (int yCheck = pickedYCoord - 1; yCheck > yCoord; yCheck--) {
                            if (!chessBoard[xCoord][yCheck].isEmpty) {
                                noSpaceBetween = false;
                            }
                        }
                    }

                    if(noSpaceBetween) {
                        gameState = "pickBlue";
                        chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                        chessBoard[xCoord][yCoord].isEmpty = false;
                        chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = false;
                        chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                        chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                    }
                } else if (yCoord == pickedYCoord){
                    if(pickedXCoord < xCoord) {
                        for (int xCheck = pickedXCoord + 1; xCheck < xCoord; xCheck++) {
                            if (!chessBoard[xCheck][yCoord].isEmpty) {
                                noSpaceBetween = false;
                            }
                        }
                    } else {
                        for (int xCheck = pickedXCoord - 1; xCheck > xCoord; xCheck--) {
                            if (!chessBoard[xCheck][yCoord].isEmpty) {
                                noSpaceBetween = false;
                            }
                        }
                    }
                    if(noSpaceBetween) {
                        gameState = "pickBlue";
                        chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                        chessBoard[xCoord][yCoord].isEmpty = false;
                        chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = false;
                        chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                        chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                    }
                }
            }
        } else if(pieceName.contains("Pawn")) {
            if ((chessBoard[xCoord][yCoord].isEmpty) && xCoord == pickedXCoord) {
                if (pickedYCoord != 1) {
                    if(yCoord - pickedYCoord == 1) {
                        gameState = "pickBlue";
                        chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                        chessBoard[xCoord][yCoord].isEmpty = false;
                        chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = false;
                        chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                        chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                    }
                } else {
                    if(yCoord - pickedYCoord == 2 || yCoord - pickedYCoord == 1) {
                        gameState = "pickBlue";
                        chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                        chessBoard[xCoord][yCoord].isEmpty = false;
                        chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = false;
                        chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                        chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;

                        if(yCoord - pickedYCoord == 2) {
                            enPassPurple[xCoord][yCoord] = true;
                        }
                    }
                }
            } else if ((yCoord - pickedYCoord == 1 && Math.abs(xCoord - pickedXCoord) == 1) && chessBoard[xCoord][yCoord].isEmpty && chessBoard[xCoord][yCoord - 1].chessPiece.isBlueTeam && enPassBlue[xCoord][yCoord - 1]) {
                gameState = "pickPurple";
                chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                chessBoard[xCoord][yCoord].isEmpty = false;
                chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = false;
                chessBoard[xCoord][yCoord - 1].chessPiece.name = null;
                chessBoard[xCoord][yCoord - 1].isEmpty = true;
                chessBoard[xCoord][yCoord - 1].chessPiece.isBlueTeam = false;
                chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                chessBoard[pickedXCoord][pickedYCoord].chessPiece.isBlueTeam = false;
            } else if (!chessBoard[xCoord][yCoord].isEmpty && chessBoard[xCoord][yCoord].chessPiece.isBlueTeam) {
                if(yCoord - pickedYCoord == 1 && Math.abs(xCoord - pickedXCoord) == 1) {
                    gameState = "pickBlue";
                    chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                    chessBoard[xCoord][yCoord].isEmpty = false;
                    chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = false;
                    chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                    chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                }
            }
        } else if(pieceName.equals("queen")) {
            if ((chessBoard[xCoord][yCoord].isEmpty || chessBoard[xCoord][yCoord].chessPiece.isBlueTeam)) {
                if(xCoord == pickedXCoord) {
                    if(pickedYCoord < yCoord) {
                        for (int yCheck = pickedYCoord + 1; yCheck < yCoord; yCheck++) {
                            if (!chessBoard[xCoord][yCheck].isEmpty) {
                                noSpaceBetween = false;
                            }
                        }
                    } else {
                        for (int yCheck = pickedYCoord - 1; yCheck > yCoord; yCheck--) {
                            if (!chessBoard[xCoord][yCheck].isEmpty) {
                                noSpaceBetween = false;
                            }
                        }
                    }
                    if(noSpaceBetween) {
                        gameState = "pickBlue";
                        chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                        chessBoard[xCoord][yCoord].isEmpty = false;
                        chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = false;
                        chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                        chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                    }
                } else if (yCoord == pickedYCoord){
                    if(pickedXCoord < xCoord) {
                        for (int xCheck = pickedXCoord + 1; xCheck < xCoord; xCheck++) {
                            if (!chessBoard[xCheck][yCoord].isEmpty) {
                                noSpaceBetween = false;
                            }
                        }
                    } else {
                        for (int xCheck = pickedXCoord - 1; xCheck > xCoord; xCheck--) {
                            if (!chessBoard[xCheck][yCoord].isEmpty) {
                                noSpaceBetween = false;
                            }
                        }
                    }
                    if(noSpaceBetween) {
                        gameState = "pickBlue";
                        chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                        chessBoard[xCoord][yCoord].isEmpty = false;
                        chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = false;
                        chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                        chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                    }
                }
            }

            if (((Math.abs(xCoord - pickedXCoord) == Math.abs(yCoord - pickedYCoord))) && (chessBoard[xCoord][yCoord].isEmpty || chessBoard[xCoord][yCoord].chessPiece.isBlueTeam)){
                int yCheck = pickedYCoord;
                if(pickedXCoord > xCoord) {
                    for (int xCheck = pickedXCoord - 1; xCheck > xCoord; xCheck--) {
                        if(yCoord < pickedYCoord) {
                            yCheck--;
                        } else if(yCoord > pickedYCoord) {
                            yCheck++;
                        }
                        if (!chessBoard[xCheck][yCheck].isEmpty) {
                            noSpaceBetween = false;
                        }
                    }
                } else {
                    for (int xCheck = pickedXCoord + 1; xCheck < xCoord; xCheck++) {
                        if(yCoord < pickedYCoord) {
                            yCheck--;
                        } else if(yCoord > pickedYCoord){
                            yCheck++;
                        }
                        if (!chessBoard[xCheck][yCheck].isEmpty) {
                            noSpaceBetween = false;
                        }
                    }
                }
                if(noSpaceBetween) {
                    gameState = "pickBlue";
                    chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                    chessBoard[xCoord][yCoord].isEmpty = false;
                    chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = false;
                    chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                    chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                }
            }
        } else if(pieceName.equals("king")) {
            if((Math.abs(xCoord - pickedXCoord) <= 1 && Math.abs(yCoord - pickedYCoord) <= 1 && (xCoord - pickedXCoord != 0 || yCoord - pickedYCoord != 0)) && (chessBoard[xCoord][yCoord].isEmpty || chessBoard[xCoord][yCoord].chessPiece.isBlueTeam)) {
                gameState = "pickBlue";
                chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                chessBoard[xCoord][yCoord].isEmpty = false;
                chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = false;
                chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
            }
        } else if(pieceName.equals("horse")) {
            if(((Math.abs(xCoord - pickedXCoord) == 2 && Math.abs(yCoord - pickedYCoord) == 1) || (Math.abs(yCoord - pickedYCoord) == 2 && Math.abs(xCoord - pickedXCoord) == 1)) && (chessBoard[xCoord][yCoord].isEmpty || chessBoard[xCoord][yCoord].chessPiece.isBlueTeam)) {
                gameState = "pickBlue";
                chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                chessBoard[xCoord][yCoord].isEmpty = false;
                chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = false;
                chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
            }

        }
        System.out.println(gameState);
    }

    @FXML
    void bluePickSpot(int xCoord, int yCoord) {
            String pieceName = chessBoard[pickedXCoord][pickedYCoord].chessPiece.name;
            boolean noSpaceBetween = true;
              if (pieceName.equals("bishop")) {
                if (((Math.abs(xCoord - pickedXCoord) == Math.abs(yCoord - pickedYCoord))) && (chessBoard[xCoord][yCoord].isEmpty || !chessBoard[xCoord][yCoord].chessPiece.isBlueTeam)){
                    int yCheck = pickedYCoord;
                    if(pickedXCoord > xCoord) {
                        for (int xCheck = pickedXCoord - 1; xCheck > xCoord; xCheck--) {
                            if(yCoord < pickedYCoord) {
                                yCheck--;
                            } else if(yCoord > pickedYCoord) {
                                yCheck++;
                            }
                            if (!chessBoard[xCheck][yCheck].isEmpty) {
                                noSpaceBetween = false;
                            }
                        }
                    } else {
                        for (int xCheck = pickedXCoord + 1; xCheck < xCoord; xCheck++) {
                            if(yCoord < pickedYCoord) {
                                yCheck--;
                            } else if(yCoord > pickedYCoord){
                                yCheck++;
                            }
                            if (!chessBoard[xCheck][yCheck].isEmpty) {
                                noSpaceBetween = false;
                            }
                        }
                    }
                    if(noSpaceBetween) {
                        gameState = "pickPurple";
                        chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                        chessBoard[xCoord][yCoord].isEmpty = false;
                        chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = true;
                        chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                        chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                        chessBoard[pickedXCoord][pickedYCoord].chessPiece.isBlueTeam = false;
                    }
                }
            } else if (pieceName.equals("rook")) {
                  if ((chessBoard[xCoord][yCoord].isEmpty || !chessBoard[xCoord][yCoord].chessPiece.isBlueTeam)) {
                      if(xCoord == pickedXCoord) {
                          if(pickedYCoord < yCoord) {
                              for (int yCheck = pickedYCoord + 1; yCheck < yCoord; yCheck++) {
                                  if (!chessBoard[xCoord][yCheck].isEmpty) {
                                      noSpaceBetween = false;
                                  }
                              }
                          } else {
                              for (int yCheck = pickedYCoord - 1; yCheck > yCoord; yCheck--) {
                                  if (!chessBoard[xCoord][yCheck].isEmpty) {
                                      noSpaceBetween = false;
                                  }
                              }
                          }
                          if(noSpaceBetween) {
                              gameState = "pickPurple";
                              chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                              chessBoard[xCoord][yCoord].isEmpty = false;
                              chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = true;
                              chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                              chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                              chessBoard[pickedXCoord][pickedYCoord].chessPiece.isBlueTeam = false;
                          }
                      } else if (yCoord == pickedYCoord){
                          if(pickedXCoord < xCoord) {
                              for (int xCheck = pickedXCoord + 1; xCheck < xCoord; xCheck++) {
                                  if (!chessBoard[xCheck][yCoord].isEmpty) {
                                      noSpaceBetween = false;
                                  }
                              }
                          } else {
                              for (int xCheck = pickedXCoord - 1; xCheck > xCoord; xCheck--) {
                                  if (!chessBoard[xCheck][yCoord].isEmpty) {
                                      noSpaceBetween = false;
                                  }
                              }
                          }
                          if(noSpaceBetween) {
                              gameState = "pickPurple";
                              chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                              chessBoard[xCoord][yCoord].isEmpty = false;
                              chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = true;
                              chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                              chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                              chessBoard[pickedXCoord][pickedYCoord].chessPiece.isBlueTeam = false;
                          }
                      }
                  }
            } else if(pieceName.contains("Pawn")) {
                      if ((chessBoard[xCoord][yCoord].isEmpty) && xCoord == pickedXCoord) {
                          if (pickedYCoord != 6) {
                              if(pickedYCoord - yCoord == 1) {
                                  gameState = "pickPurple";
                                  chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                                  chessBoard[xCoord][yCoord].isEmpty = false;
                                  chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = true;
                                  chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                                  chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                                  chessBoard[pickedXCoord][pickedYCoord].chessPiece.isBlueTeam = false;
                              }
                          } else {
                              if(pickedYCoord - yCoord  == 2 || pickedYCoord - yCoord == 1) {
                                  gameState = "pickPurple";
                                  chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                                  chessBoard[xCoord][yCoord].isEmpty = false;
                                  chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = true;
                                  chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                                  chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                                  chessBoard[pickedXCoord][pickedYCoord].chessPiece.isBlueTeam = false;

                                  if(pickedYCoord - yCoord  == 2) {
                                      enPassBlue[xCoord][yCoord] = true;
                                  }
                              }
                          }
                      } else if ((pickedYCoord - yCoord == 1 && Math.abs(xCoord - pickedXCoord) == 1) && chessBoard[xCoord][yCoord].isEmpty && !chessBoard[xCoord][yCoord + 1].chessPiece.isBlueTeam && enPassPurple[xCoord][yCoord + 1]) {
                          gameState = "pickPurple";
                          chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                          chessBoard[xCoord][yCoord].isEmpty = false;
                          chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = true;
                          chessBoard[xCoord][yCoord + 1].chessPiece.name = null;
                          chessBoard[xCoord][yCoord + 1].isEmpty = true;
                          chessBoard[xCoord][yCoord + 1].chessPiece.isBlueTeam = false;
                          chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                          chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                          chessBoard[pickedXCoord][pickedYCoord].chessPiece.isBlueTeam = false;
                      } else if (!chessBoard[xCoord][yCoord].isEmpty && !chessBoard[xCoord][yCoord].chessPiece.isBlueTeam) {
                          if(pickedYCoord - yCoord == 1 && Math.abs(xCoord - pickedXCoord) == 1) {
                              gameState = "pickPurple";
                              chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                              chessBoard[xCoord][yCoord].isEmpty = false;
                              chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = true;
                              chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                              chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                              chessBoard[pickedXCoord][pickedYCoord].chessPiece.isBlueTeam = false;
                          }
                      }
              } else if(pieceName.equals("queen")) {
                  if ((chessBoard[xCoord][yCoord].isEmpty || !chessBoard[xCoord][yCoord].chessPiece.isBlueTeam)) {
                      if(xCoord == pickedXCoord) {
                          if(pickedYCoord < yCoord) {
                              for (int yCheck = pickedYCoord + 1; yCheck < yCoord; yCheck++) {
                                  if (!chessBoard[xCoord][yCheck].isEmpty) {
                                      noSpaceBetween = false;
                                  }
                              }
                          } else {
                              for (int yCheck = pickedYCoord - 1; yCheck > yCoord; yCheck--) {
                                  if (!chessBoard[xCoord][yCheck].isEmpty) {
                                      noSpaceBetween = false;
                                  }
                              }
                          }
                          if(noSpaceBetween) {
                              gameState = "pickPurple";
                              chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                              chessBoard[xCoord][yCoord].isEmpty = false;
                              chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = true;
                              chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                              chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                              chessBoard[pickedXCoord][pickedYCoord].chessPiece.isBlueTeam = false;
                          }
                      } else if (yCoord == pickedYCoord){
                          if(pickedXCoord < xCoord) {
                              for (int xCheck = pickedXCoord + 1; xCheck < xCoord; xCheck++) {
                                  if (!chessBoard[xCheck][yCoord].isEmpty) {
                                      noSpaceBetween = false;
                                  }
                              }
                          } else {
                              for (int xCheck = pickedXCoord - 1; xCheck > xCoord; xCheck--) {
                                  if (!chessBoard[xCheck][yCoord].isEmpty) {
                                      noSpaceBetween = false;
                                  }
                              }
                          }
                          if(noSpaceBetween) {
                              gameState = "pickPurple";
                              chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                              chessBoard[xCoord][yCoord].isEmpty = false;
                              chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = true;
                              chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                              chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                              chessBoard[pickedXCoord][pickedYCoord].chessPiece.isBlueTeam = false;
                          }
                      }
                  }

                  if (((Math.abs(xCoord - pickedXCoord) == Math.abs(yCoord - pickedYCoord))) && (chessBoard[xCoord][yCoord].isEmpty || !chessBoard[xCoord][yCoord].chessPiece.isBlueTeam)){
                      int yCheck = pickedYCoord;
                      if(pickedXCoord > xCoord) {
                          for (int xCheck = pickedXCoord - 1; xCheck > xCoord; xCheck--) {
                              if(yCoord < pickedYCoord) {
                                  yCheck--;
                              } else if(yCoord > pickedYCoord) {
                                  yCheck++;
                              }
                              if (!chessBoard[xCheck][yCheck].isEmpty) {
                                  noSpaceBetween = false;
                              }
                          }
                      } else {
                          for (int xCheck = pickedXCoord + 1; xCheck < xCoord; xCheck++) {
                              if(yCoord < pickedYCoord) {
                                  yCheck--;
                              } else if(yCoord > pickedYCoord){
                                  yCheck++;
                              }
                              if (!chessBoard[xCheck][yCheck].isEmpty) {
                                  noSpaceBetween = false;
                              }
                          }
                      }
                      if(noSpaceBetween) {
                          gameState = "pickPurple";
                          chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                          chessBoard[xCoord][yCoord].isEmpty = false;
                          chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = true;
                          chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                          chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                          chessBoard[pickedXCoord][pickedYCoord].chessPiece.isBlueTeam = false;
                      }
                  }
              } else if(pieceName.equals("king")) {
                  if((Math.abs(xCoord - pickedXCoord) <= 1 && Math.abs(yCoord - pickedYCoord) <= 1 && (xCoord - pickedXCoord != 0 || yCoord - pickedYCoord != 0)) && (chessBoard[xCoord][yCoord].isEmpty || !chessBoard[xCoord][yCoord].chessPiece.isBlueTeam)) {
                      gameState = "pickPurple";
                      chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                      chessBoard[xCoord][yCoord].isEmpty = false;
                      chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = true;
                      chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                      chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                      chessBoard[pickedXCoord][pickedYCoord].chessPiece.isBlueTeam = false;
                  }
              } else if(pieceName.equals("horse")) {
                  if(((Math.abs(xCoord - pickedXCoord) == 2 && Math.abs(yCoord - pickedYCoord) == 1) || (Math.abs(yCoord - pickedYCoord) == 2 && Math.abs(xCoord - pickedXCoord) == 1)) && (chessBoard[xCoord][yCoord].isEmpty || !chessBoard[xCoord][yCoord].chessPiece.isBlueTeam)) {
                      gameState = "pickPurple";
                      chessBoard[xCoord][yCoord].chessPiece.name = pieceName;
                      chessBoard[xCoord][yCoord].isEmpty = false;
                      chessBoard[xCoord][yCoord].chessPiece.isBlueTeam = true;
                      chessBoard[pickedXCoord][pickedYCoord].chessPiece.name = null;
                      chessBoard[pickedXCoord][pickedYCoord].isEmpty = true;
                      chessBoard[pickedXCoord][pickedYCoord].chessPiece.isBlueTeam = false;
                  }
            }
        System.out.println(gameState);
    }

    @FXML
    void updateImage() {

        Group pGroup = new Group();
        pGroup.getChildren().add(imageView);
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    String pieceName = chessBoard[i][j].chessPiece.name;
                    if(!(pieceName == null)) {
                        if (pieceName.equals("horse")) {
                            pieceName = "knight";
                        } else if(pieceName.contains("Pawn")) {
                            pieceName = "Pawn";
                        }

                        String team;

                        if(chessBoard[i][j].chessPiece.isBlueTeam) {
                            team = "blue";
                        } else {
                            team = "purple";
                        }
                        try {
                            InputStream pieceStream = new FileInputStream("src/sample/data/" + team + " pieces/" + pieceName + ".png");
                            Image piece = new Image(pieceStream);
                            ImageView pieceView = new ImageView();
                            pieceView.setImage(piece);
                            pieceView.setMouseTransparent(true);
                            double[] cord = arrayCoordToPixel(i, j);
                            pieceView.setX(cord[0]);
                            pieceView.setY(cord[1]);
                            pGroup.getChildren().add(pieceView);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            Parent newRoot = pGroup;
            scene = new Scene(newRoot);
            stage.setScene(scene);
            stage.show();
    }

    public double[] arrayCoordToPixel(int xCoord, int yCoord) {
        double[] coordinates = new double[2];
        coordinates[0] = ((((double) xCoord) / 8) * .93 + .03) * imageView.getFitWidth();
        //System.out.println(coordinates[0]);
        coordinates[1] = ((((double) yCoord) / 8) * .93 + .03) * imageView.getFitHeight();
        return coordinates;
    }

    public Parent setRoot(ImageView imageView) throws IOException {
        //purple rook
        InputStream rookStream = new FileInputStream("src/sample/data/purple pieces/rook.png");
        Image rook = new Image(rookStream);
        ImageView rookView = new ImageView();
        //rookView.setFitHeight(imageView.getFitHeight());
        //rookView.setFitWidth(imageView.getFitWidth());
        rookView.setImage(rook);
        rookView.setMouseTransparent(true);
        double coordinates[] = arrayCoordToPixel(0, 0);
        rookView.setX(coordinates[0]);
        rookView.setY(coordinates[1]);

        //purple knight
        InputStream knightStream = new FileInputStream("src/sample/data/purple pieces/knight.png");
        Image knight = new Image(knightStream);
        ImageView knightView = new ImageView();
        knightView.setImage(knight);
        knightView.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(1, 0);
        knightView.setX(coordinates[0]);
        knightView.setY(coordinates[1]);

        //purple bishop
        InputStream bishopStream = new FileInputStream("src/sample/data/purple pieces/bishop.png");
        Image bishop = new Image(bishopStream);
        ImageView bishopView = new ImageView();
        bishopView.setImage(bishop);
        bishopView.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(2, 0);
        bishopView.setX(coordinates[0]);
        bishopView.setY(coordinates[1]);

        //purple king
        InputStream kingStream = new FileInputStream("src/sample/data/purple pieces/king.png");
        Image king = new Image(kingStream);
        ImageView kingView = new ImageView();
        kingView.setImage(king);
        kingView.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(3, 0);
        kingView.setX(coordinates[0]);
        kingView.setY(coordinates[1]);

        //purple queen
        InputStream queenStream = new FileInputStream("src/sample/data/purple pieces/queen.png");
        Image queen = new Image(queenStream);
        ImageView queenView = new ImageView();
        queenView.setImage(queen);
        queenView.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(4, 0);
        queenView.setX(coordinates[0]);
        queenView.setY(coordinates[1]);

        //purple bishop2
        ImageView bishop2View = new ImageView();
        bishop2View.setImage(bishop);
        bishop2View.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(5, 0);
        bishop2View.setX(coordinates[0]);
        bishop2View.setY(coordinates[1]);

        //purple knight2
        ImageView knight2View = new ImageView();
        knight2View.setImage(knight);
        knight2View.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(6, 0);
        knight2View.setX(coordinates[0]);
        knight2View.setY(coordinates[1]);

        //purple rook2
        ImageView rook2View = new ImageView();
        rook2View.setImage(rook);
        rook2View.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(7, 0);
        rook2View.setX(coordinates[0]);
        rook2View.setY(coordinates[1]);

        //purple pawn
        InputStream pawnStream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn = new Image(pawnStream);
        ImageView pawnView = new ImageView();
        pawnView.setImage(pawn);
        pawnView.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(0, 1);
        pawnView.setX(coordinates[0]);
        pawnView.setY(coordinates[1]);

        //purple pawn2
        InputStream pawn2Stream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn2 = new Image(pawn2Stream);
        ImageView pawn2View = new ImageView();
        pawn2View.setImage(pawn2);
        pawn2View.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(1, 1);
        pawn2View.setX(coordinates[0]);
        pawn2View.setY(coordinates[1]);

        //purple pawn3
        InputStream pawn3Stream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn3 = new Image(pawn3Stream);
        ImageView pawn3View = new ImageView();
        pawn3View.setImage(pawn3);
        pawn3View.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(2, 1);
        pawn3View.setX(coordinates[0]);
        pawn3View.setY(coordinates[1]);

        //purple pawn4
        InputStream pawn4Stream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn4 = new Image(pawn4Stream);
        ImageView pawn4View = new ImageView();
        pawn4View.setImage(pawn4);
        pawn4View.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(3, 1);
        pawn4View.setX(coordinates[0]);
        pawn4View.setY(coordinates[1]);

        //purple pawn5
        InputStream pawn5Stream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn5 = new Image(pawn5Stream);
        ImageView pawn5View = new ImageView();
        pawn5View.setImage(pawn5);
        pawn5View.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(4, 1);
        pawn5View.setX(coordinates[0]);
        pawn5View.setY(coordinates[1]);

        //purple pawn6
        InputStream pawn6Stream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn6 = new Image(pawn6Stream);
        ImageView pawn6View = new ImageView();
        pawn6View.setImage(pawn6);
        pawn6View.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(5, 1);
        pawn6View.setX(coordinates[0]);
        pawn6View.setY(coordinates[1]);

        //purple pawn7
        InputStream pawn7Stream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn7 = new Image(pawn7Stream);
        ImageView pawn7View = new ImageView();
        pawn7View.setImage(pawn7);
        pawn7View.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(6, 1);
        pawn7View.setX(coordinates[0]);
        pawn7View.setY(coordinates[1]);

        //purple pawn8
        InputStream pawn8Stream = new FileInputStream("src/sample/data/purple pieces/pawn.png");
        Image pawn8 = new Image(pawn8Stream);
        ImageView pawn8View = new ImageView();
        pawn8View.setImage(pawn8);
        pawn8View.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(7, 1);
        pawn8View.setX(coordinates[0]);
        pawn8View.setY(coordinates[1]);

        //blue rook
        InputStream blueRookStream = new FileInputStream("src/sample/data/blue pieces/rook.png");
        Image blueRook = new Image(blueRookStream);
        ImageView blueRookView = new ImageView();
        blueRookView.setImage(blueRook);
        blueRookView.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(0, 7);
        blueRookView.setX(coordinates[0]);
        blueRookView.setY(coordinates[1]);

        //blue knight
        InputStream blueKnightStream = new FileInputStream("src/sample/data/blue pieces/knight.png");
        Image blueKnight = new Image(blueKnightStream);
        ImageView blueKnightView = new ImageView();
        blueKnightView.setImage(blueKnight);
        blueKnightView.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(1, 7);
        blueKnightView.setX(coordinates[0]);
        blueKnightView.setY(coordinates[1]);

        //blue bishop
        InputStream blueBishopStream = new FileInputStream("src/sample/data/blue pieces/bishop.png");
        Image blueBishop = new Image(blueBishopStream);
        ImageView blueBishopView = new ImageView();
        blueBishopView.setImage(blueBishop);
        blueBishopView.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(2, 7);
        blueBishopView.setX(coordinates[0]);
        blueBishopView.setY(coordinates[1]);

        //blue queen
        InputStream blueKingStream = new FileInputStream("src/sample/data/blue pieces/king.png");
        Image blueKing = new Image(blueKingStream);
        ImageView blueKingView = new ImageView();
        blueKingView.setImage(blueKing);
        blueKingView.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(3, 7);
        blueKingView.setX(coordinates[0]);
        blueKingView.setY(coordinates[1]);

        //blue queen
        InputStream blueQueenStream = new FileInputStream("src/sample/data/blue pieces/queen.png");
        Image blueQueen = new Image(blueQueenStream);
        ImageView blueQueenView = new ImageView();
        blueQueenView.setImage(blueQueen);
        blueQueenView.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(4, 7);
        blueQueenView.setX(coordinates[0]);
        blueQueenView.setY(coordinates[1]);

        //blue bishop2
        InputStream blueBishop2Stream = new FileInputStream("src/sample/data/blue pieces/bishop.png");
        Image blueBishop2 = new Image(blueBishop2Stream);
        ImageView blueBishopView2 = new ImageView();
        blueBishopView2.setImage(blueBishop2);
        blueBishopView2.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(5, 7);
        blueBishopView2.setX(coordinates[0]);
        blueBishopView2.setY(coordinates[1]);

        //blue knight2
        InputStream blueKnight2Stream = new FileInputStream("src/sample/data/blue pieces/knight.png");
        Image blueKnight2 = new Image(blueKnight2Stream);
        ImageView blueKnightView2 = new ImageView();
        blueKnightView2.setImage(blueKnight2);
        blueKnightView2.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(6, 7);
        blueKnightView2.setX(coordinates[0]);
        blueKnightView2.setY(coordinates[1]);

        //blue rook2
        InputStream blueRook2Stream = new FileInputStream("src/sample/data/blue pieces/rook.png");
        Image blueRook2 = new Image(blueRook2Stream);
        ImageView blueRookView2 = new ImageView();
        blueRookView2.setImage(blueRook2);
        blueRookView2.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(7, 7);
        blueRookView2.setX(coordinates[0]);
        blueRookView2.setY(coordinates[1]);

        //blue pawn1
        InputStream bluePawnStream1 = new FileInputStream("src/sample/data/blue pieces/pawn.png");
        Image bluePawn1 = new Image(bluePawnStream1);
        ImageView bluePawnView1 = new ImageView();
        bluePawnView1.setImage(bluePawn1);
        bluePawnView1.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(0, 6);
        bluePawnView1.setX(coordinates[0]);
        bluePawnView1.setY(coordinates[1]);

        //blue pawn2
        InputStream bluePawnStream2 = new FileInputStream("src/sample/data/blue pieces/pawn.png");
        Image bluePawn2 = new Image(bluePawnStream2);
        ImageView bluePawnView2 = new ImageView();
        bluePawnView2.setImage(bluePawn2);
        bluePawnView2.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(1, 6);
        bluePawnView2.setX(coordinates[0]);
        bluePawnView2.setY(coordinates[1]);

        //blue pawn3
        InputStream bluePawnStream3 = new FileInputStream("src/sample/data/blue pieces/pawn.png");
        Image bluePawn3 = new Image(bluePawnStream3);
        ImageView bluePawnView3 = new ImageView();
        bluePawnView3.setImage(bluePawn3);
        bluePawnView3.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(2, 6);
        bluePawnView3.setX(coordinates[0]);
        bluePawnView3.setY(coordinates[1]);

        //blue pawn4
        InputStream bluePawnStream4 = new FileInputStream("src/sample/data/blue pieces/pawn.png");
        Image bluePawn4 = new Image(bluePawnStream4);
        ImageView bluePawnView4 = new ImageView();
        bluePawnView4.setImage(bluePawn4);
        bluePawnView4.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(3, 6);
        bluePawnView4.setX(coordinates[0]);
        bluePawnView4.setY(coordinates[1]);

        //blue pawn5
        InputStream bluePawnStream5 = new FileInputStream("src/sample/data/blue pieces/pawn.png");
        Image bluePawn5 = new Image(bluePawnStream5);
        ImageView bluePawnView5 = new ImageView();
        bluePawnView5.setImage(bluePawn5);
        bluePawnView5.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(4, 6);
        bluePawnView5.setX(coordinates[0]);
        bluePawnView5.setY(coordinates[1]);

        //blue pawn6
        InputStream bluePawnStream6 = new FileInputStream("src/sample/data/blue pieces/pawn.png");
        Image bluePawn6 = new Image(bluePawnStream6);
        ImageView bluePawnView6 = new ImageView();
        bluePawnView6.setImage(bluePawn6);
        bluePawnView6.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(5, 6);
        bluePawnView6.setX(coordinates[0]);
        bluePawnView6.setY(coordinates[1]);

        //blue pawn7
        InputStream bluePawnStream7 = new FileInputStream("src/sample/data/blue pieces/pawn.png");
        Image bluePawn7 = new Image(bluePawnStream7);
        ImageView bluePawnView7 = new ImageView();
        bluePawnView7.setImage(bluePawn7);
        bluePawnView7.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(6, 6);
        bluePawnView7.setX(coordinates[0]);
        bluePawnView7.setY(coordinates[1]);

        //blue pawn8
        InputStream bluePawnStream8 = new FileInputStream("src/sample/data/blue pieces/pawn.png");
        Image bluePawn8 = new Image(bluePawnStream8);
        ImageView bluePawnView8 = new ImageView();
        bluePawnView8.setImage(bluePawn8);
        bluePawnView8.setMouseTransparent(true);
        coordinates = arrayCoordToPixel(7, 6);
        bluePawnView8.setX(coordinates[0]);
        bluePawnView8.setY(coordinates[1]);

        return new Group(imageView,rookView,knightView,bishopView,kingView,queenView,bishop2View,knight2View,
                rook2View,pawnView,pawn2View,pawn3View,pawn4View,pawn5View,pawn6View,pawn7View,pawn8View,
                blueRookView,blueKnightView,blueBishopView,blueKingView, blueQueenView, blueBishopView2,blueKnightView2,
                blueRookView2,bluePawnView1,bluePawnView2,bluePawnView3,bluePawnView4,bluePawnView5,bluePawnView6,bluePawnView7,bluePawnView8);
    }

}


