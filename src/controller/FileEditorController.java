package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.CustomDialog;
import model.FileManagement;
import model.GoL;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static javafx.scene.paint.Color.BLACK;


/**
 * Created by simenperschandersen on 19.04.2017.
 */
public class FileEditorController implements Initializable {
    @FXML
    private TextField previewTitleField;
    @FXML
    private TextField previewOriginField;
    @FXML
    private TextArea previewCommentsArea;
    @FXML
    private TextField previewBornAmountField;
    @FXML
    private TextField previewSurviveAmountField;

    //Initiates main and all preview canvas and GraphicContexts
    @FXML
    private Canvas mainCanvas;
    @FXML
    private Canvas previewOne;
    @FXML
    private Canvas previewTwo;
    @FXML
    private Canvas previewThree;
    @FXML
    private Canvas previewFour;
    @FXML
    private Canvas previewFive;
    @FXML
    private Canvas previewSix;
    @FXML
    private Canvas previewSeven;
    @FXML
    private Canvas previewEight;
    @FXML
    private Canvas previewNine;
    @FXML
    private Canvas previewTen;
    @FXML
    private Canvas previewEleven;
    @FXML
    private Canvas previewTwelve;
    @FXML
    private Canvas previewThirteen;
    @FXML
    private Canvas previewFourteen;
    @FXML
    private Canvas previewFifteen;


    private GraphicsContext mainGc;
    private GraphicsContext gcOne;
    private GraphicsContext gcTwo;
    private GraphicsContext gcThree;
    private GraphicsContext gcFour;
    private GraphicsContext gcFive;
    private GraphicsContext gcSix;
    private GraphicsContext gcSeven;
    private GraphicsContext gcEight;
    private GraphicsContext gcNine;
    private GraphicsContext gcTen;
    private GraphicsContext gcEleven;
    private GraphicsContext gcTwelve;
    private GraphicsContext gcThirteen;
    private GraphicsContext gcFourteen;
    private GraphicsContext gcFifteen;

    private byte[][] staticBoard;
    private ArrayList<ArrayList<Byte>> dynamicBoard;
    private int boardWidth = 0;
    private int boardHeight = 0;
    private String boardType;

    /**
     * Initiliazes the graphic contexts for each canvas represented in the FXML
     */

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        mainGc = mainCanvas.getGraphicsContext2D();
        gcOne = previewOne.getGraphicsContext2D();
        gcTwo = previewTwo.getGraphicsContext2D();
        gcThree = previewThree.getGraphicsContext2D();
        gcFour = previewFour.getGraphicsContext2D();
        gcFive = previewFive.getGraphicsContext2D();
        gcSix = previewSix.getGraphicsContext2D();
        gcSeven = previewSeven.getGraphicsContext2D();
        gcEight = previewEight.getGraphicsContext2D();
        gcNine = previewNine.getGraphicsContext2D();
        gcTen = previewTen.getGraphicsContext2D();
        gcEleven = previewEleven.getGraphicsContext2D();
        gcTwelve = previewTwelve.getGraphicsContext2D();
        gcThirteen = previewThirteen.getGraphicsContext2D();
        gcFourteen = previewFourteen.getGraphicsContext2D();
        gcFifteen = previewFifteen.getGraphicsContext2D();


    }

    /**
     * The following set methods sets the corresponding text field, text area and label FXML elements with String value.
     */

    public void setTitleArea(String title) {
        previewTitleField.setText(title);

    }

    public void setOriginArea(String origin) {
        previewOriginField.setText(origin);

    }

    public void setCommentsArea(String comments) {
        previewCommentsArea.setText(comments);

    }

    public void setBornAmountField(String bornAmount) {
        previewBornAmountField.setText(bornAmount);

    }

    public void setSurviveAmountField(String surviveAmount) {
        previewSurviveAmountField.setText(surviveAmount);

    }

    /**
     * Event actived upon clicking the "Close" button on the scene.
     * Closes the stage.
     */
    public void closeEvent() {

        Stage primaryStage = (Stage) mainCanvas.getScene().getWindow();
        primaryStage.close();
    }

    /**
     * Event actived upon clicking the "Save" button.
     * Opens a save dialog which allows the user to specify the file name and location for the .rle file the user wants to save the active data in.
     * If there is an active file open, specifiec under the "File path" list location, the file and the locaiton of this file exists on the active computer, this file is automatically overwritten
     * allowing the user to quickly save any changes to the active file without going through a save dialog window.
     * If everyone goes right and it returns a valid file, specified in the returnFile parameter, this file is sent to the saveFile(File returnFile) method which handles the writing of the file
     * @throws IOException
     */

    public void saveFileEvent() throws IOException {
        File returnFile;
        if(GoL.getLoadedData() != null) {
            HashMap<String, String> fileData = GoL.getLoadedData();
            String absolutePath = fileData.get("File Path");
            File f = new File(absolutePath);
            if(f.exists() && !f.isDirectory()) {
                returnFile = f;
            }
            else
            {
                FileChooser chooser = new FileChooser();
                File userDirectory = new File(System.getProperty("user.home"));
                if (!userDirectory.canRead()) {
                    userDirectory = new File("c:/");
                }
                chooser.setInitialDirectory(userDirectory);
                chooser.setTitle("Save RLE file");
                chooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("RLE Files (*.rle)", "*.rle"));
                returnFile = chooser.showSaveDialog(null);
            }
        }
        else {
            FileChooser chooser = new FileChooser();
            File userDirectory = new File(System.getProperty("user.home"));
            if (!userDirectory.canRead()) {
                userDirectory = new File("c:/");
            }
            chooser.setInitialDirectory(userDirectory);
            chooser.setTitle("Save RLE file");
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("RLE Files (*.rle)", "*.rle"));
            returnFile = chooser.showSaveDialog(null);
        }
        if (returnFile != null) {
            saveFile(returnFile);

        }

    }

    /**
     * This method works similaryily to the saveEvent() method, only this method will open a save dialog where the user has the option to speicfy the name and the location of file, no matter what active file may already be loaded.
     * The File returnFile is sent to the saveFile(File returnFile) which handles the writing of the file.
     * @throws IOException
     */

    public void saveAsEvent() throws IOException {
        FileChooser chooser = new FileChooser();
        File initDirectory = new File(System.getProperty("user.home"));
        if (!initDirectory.canRead()) {
            initDirectory = new File("c:/");
        }
        chooser.setInitialDirectory(initDirectory);
        chooser.setTitle("Save RLE file");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("RLE Files (*.rle)", "*.rle"));
        if(GoL.getLoadedData() != null) {
            HashMap<String, String> fileData = GoL.getLoadedData();
            String absolutePath = fileData.get("File Path");
            File f = new File(absolutePath);
            chooser.setInitialFileName(f.getName());
            initDirectory = new File(absolutePath.replace(f.getName(), ""));
            chooser.setInitialDirectory(initDirectory);
        }

        File returnFile = chooser.showSaveDialog(null);
        if (returnFile != null) {
            saveFile(returnFile);

        }
    }

    /**
     * This method builds a String based upon what contents may be in the text fields, text areas and labels, representing the specifications of the file.
     * The String is built after standard RLE formatting rules, where values are represented as follows:
     * #N Title String
     * #O Origin String
     * *C Comments String. May be multiple lines.
     * Rules = BXX/SXX
     * Dimensions = x = XX / y = XX
     * The board is counted and represented for each horizontal line where XXo represents the amount of alive cells following each other, XXb representing the amount of dead cells following each other
     * $ represents the end of a row and ! represents the end of the last line on the board.
     * The reading and String result of the reading of the Board is handled separately by the readBoardToString() method.
     * When the writing of the file is complete, the user is notified of its success with the name and location of the file.
     * This file is then read by the readFile method in the FileManagement model class, which sets the saved file as the actively loaded file.
     * @param returnFile
     * @throws IOException
     */

    public void saveFile(File returnFile) throws IOException {
        StringBuilder rleFileStringBuilder = new StringBuilder();
        String title = "";
        String origin = "";
        String comments = "";
        String dimensions = "";
        String rules = "";

        if (previewTitleField.getText() != null) {
            title = "#N " + previewTitleField.getText() + "\n";
        }
        if (previewOriginField != null) {
            origin = "#O " + previewOriginField.getText() + "\n";
        }
        if (previewCommentsArea != null) {
            comments = previewCommentsArea.getText();
            int i = 0;
            StringBuilder commentsString = new StringBuilder();
            for (int index = 0; index < comments.length(); index++) {
                if (comments.charAt(index) == 10) {

                    String subString = "#C " + comments.substring(i, index) + "\n";
                    commentsString.append(subString);
                    i = index + 1;
                }

            }
            String subString = "#C " + comments.substring(i, comments.length()) + "\n";
            commentsString.append(subString);

            comments = new String(commentsString);
        }
        if (boardWidth != 0 && boardHeight != 0) {
            dimensions = "x = " + boardWidth + ", y = " + boardHeight + ", ";
        }
        if (!previewBornAmountField.getText().isEmpty() && !previewSurviveAmountField.getText().isEmpty()) {
            rules = "rules = B" + previewBornAmountField.getText() + "/S" + previewSurviveAmountField.getText() + "\n";
        }

        String board = readBoardToString();

        rleFileStringBuilder.append(title + origin + comments + dimensions + rules + board);
        String rleFileString = new String(rleFileStringBuilder);

        FileWriter fw = new FileWriter(returnFile);
        fw.write(rleFileString);
        fw.close();
        String fileName = returnFile.getName();
        String absolutePath = returnFile.getAbsolutePath();
        String path = returnFile.getPath();
        path = path.replace(fileName, "");


        String message = fileName + " has been saved at " + path;

        Stage primaryStage = (Stage) mainCanvas.getScene().getWindow();
        primaryStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainScreen.fxml"));
        loader.load();

        HashMap<String, String> fileData = FileManagement.readFile(new FileReader(returnFile), absolutePath);

        MainScreenController mainController = loader.getController();
        mainController.applyFileData(fileData);

        new CustomDialog("File saved", true, message);

        loader.setController(mainController);


    }

    /**
     * Reads the board and a builds a String following standard RLE formatting rules.
     * The board is counted and represented for each horizontal line where XXo represents the amount of alive cells following each other, XXb representing the amount of dead cells following each other
     * $ represents the end of a row and ! represents the end of the last line on the board.
     * @return
     */

    public String readBoardToString() {
        int activeValue = 0;
        if (boardType.equals("Dynamic")) {
            activeValue = dynamicBoard.get(0).get(0);
        } else {
            activeValue = staticBoard[0][0];
        }
        String valueIdentifier = "";
        StringBuilder boardStringBuilder = new StringBuilder();
        for (int y = 0; y < boardHeight; y++) {
            int countOfSaidValue = 0;
            if (y != 0) {
                boardStringBuilder.append("$");
            }
            for (int x = 0; x < boardWidth; x++) {
                int thisValue = 0;
                if (boardType.equals("Dynamic")) {
                    thisValue = dynamicBoard.get(y).get(x);
                } else {
                    thisValue = staticBoard[y][x];
                }
                if (thisValue == activeValue) {
                    countOfSaidValue++;
                } else {
                    switch (activeValue) {
                        case 0:
                            valueIdentifier = "b";
                            break;
                        case 1:
                            valueIdentifier = "o";
                            break;
                        default:
                            break;
                    }
                    boardStringBuilder.append(countOfSaidValue + valueIdentifier);
                    activeValue = thisValue;
                    countOfSaidValue = 1;
                }
                if (x == (boardWidth - 1)) {
                    switch (thisValue) {
                        case 0:
                            valueIdentifier = "b";
                            break;
                        case 1:
                            valueIdentifier = "o";
                            break;
                        default:
                            break;
                    }
                    boardStringBuilder.append(countOfSaidValue + valueIdentifier);
                    activeValue = thisValue;
                }

            }
            if (y == (boardHeight - 1)) {
                boardStringBuilder.append("!");
            }

        }

        return new String(boardStringBuilder);

    }

    /**
     * Draws all canvases when a static board is being previewed.
     * The main board represents the active board on the main stage, while each preview canvas represents one generation after another.
     * For each preview canvas, nextBoard is being run one time through the nextGenerationStatic method, which result is drawn onto their respective canvases.
     * @param boardWidth Represents the amount of cells on the height of the board.
     * @param boardHeight Represents the amount of cells on the widt of the board.
     * @param board The active board, copied from the main stage.
     * @param height Represents the amount of cells on the height of the board.
     * @param width Represents the amount of cells on the widt of the board.
     */

    public void drawAllCanvasesStatic(int boardWidth, int boardHeight, byte[][] board, int height, int width) {
        staticBoard = board;
        boardType = "Static";
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        Color aliveCellColor = BLACK;
        Color mainCanvasBackgroundColor = Color.WHITE;
        Color deadCellColor = Color.valueOf("#F4F4F4");
        double cellSizeMainCanvas = calculateCellSize(mainCanvas, height, width);
        double cellSizePreviewCanvas = calculateCellSize(previewOne, height, width);

        drawStatic(mainGc, cellSizeMainCanvas, aliveCellColor, mainCanvasBackgroundColor, board, height, width);

        byte[][] nextBoard = nextGenerationStatic(board, height, width);
        drawStatic(gcOne, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationStatic(nextBoard, height, width);
        drawStatic(gcTwo, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationStatic(nextBoard, height, width);
        drawStatic(gcThree, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationStatic(nextBoard, height, width);
        drawStatic(gcFour, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationStatic(nextBoard, height, width);
        drawStatic(gcFive, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationStatic(nextBoard, height, width);
        drawStatic(gcSix, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationStatic(nextBoard, height, width);
        drawStatic(gcSeven, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationStatic(nextBoard, height, width);
        drawStatic(gcEight, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationStatic(nextBoard, height, width);
        drawStatic(gcNine, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationStatic(nextBoard, height, width);
        drawStatic(gcTen, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationStatic(nextBoard, height, width);
        drawStatic(gcEleven, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationStatic(nextBoard, height, width);
        drawStatic(gcTwelve, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationStatic(nextBoard, height, width);
        drawStatic(gcThirteen, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationStatic(nextBoard, height, width);
        drawStatic(gcFourteen, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationStatic(nextBoard, height, width);
        drawStatic(gcFifteen, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);


    }

    /**
     * Draws all canvases when a dynamic board is being previewed.
     * The main board represents the active board on the main stage, while each preview canvas represents one generation after another.
     * For each preview canvas, nextBoard is being run one time through the nextGenerationDynamic method, which result is drawn onto their respective canvases.
     * @param boardWidth Represents the amount of cells on the height of the board.
     * @param boardHeight Represents the amount of cells on the widt of the board.
     * @param board The active board, copied from the main stage.
     * @param height Represents the amount of cells on the height of the board.
     * @param width Represents the amount of cells on the widt of the board.
     */

    public void drawAllCanvasesDynamic(int boardWidth, int boardHeight, ArrayList<ArrayList<Byte>> board, int height, int width) {
        dynamicBoard = board;
        boardType = "Dynamic";
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        Color aliveCellColor = BLACK;
        Color mainCanvasBackgroundColor = Color.WHITE;
        Color deadCellColor = Color.valueOf("#F4F4F4");
        double cellSizeMainCanvas = calculateCellSize(mainCanvas, height, width);
        double cellSizePreviewCanvas = calculateCellSize(previewOne, height, width);

        drawDynamic(mainGc, mainCanvas, cellSizeMainCanvas, aliveCellColor, mainCanvasBackgroundColor, board, height, width);

        ArrayList<ArrayList<Byte>> nextBoard = nextGenerationDynamic(board, height, width);
        drawDynamic(gcOne, previewOne, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationDynamic(nextBoard, height, width);
        drawDynamic(gcTwo, previewTwo, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationDynamic(nextBoard, height, width);
        drawDynamic(gcThree, previewThree, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationDynamic(nextBoard, height, width);
        drawDynamic(gcFour, previewFour, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationDynamic(nextBoard, height, width);
        drawDynamic(gcFive, previewFive, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationDynamic(nextBoard, height, width);
        drawDynamic(gcSix, previewSix, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationDynamic(nextBoard, height, width);
        drawDynamic(gcSeven, previewSeven, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationDynamic(nextBoard, height, width);
        drawDynamic(gcEight, previewEight, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationDynamic(nextBoard, height, width);
        drawDynamic(gcNine, previewNine, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationDynamic(nextBoard, height, width);
        drawDynamic(gcTen, previewTen, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationDynamic(nextBoard, height, width);
        drawDynamic(gcEleven, previewEleven, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationDynamic(nextBoard, height, width);
        drawDynamic(gcTwelve, previewTwelve, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationDynamic(nextBoard, height, width);
        drawDynamic(gcThirteen, previewThirteen, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationDynamic(nextBoard, height, width);
        drawDynamic(gcFourteen, previewFourteen, cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);

        nextBoard = nextGenerationDynamic(nextBoard, height, width);
        drawDynamic(gcFifteen, previewFifteen,  cellSizePreviewCanvas, aliveCellColor, deadCellColor, nextBoard, height, width);


    }

    /**
     * Calculates the size each cells needs to have in order to fit in the canvas the method gets as input.
     * @param canvas Input parameter representing the canvas to be calculated based on.
     * @param height Input parameter representing the amount of cells on the height of the board that needs to fit in the canvas.
     * @param width Input parameter representing the amount of cells on the width of the board that needs to fit in the canvas.
     * @return Returns the cell size as a double value.
     */

    public double calculateCellSize(Canvas canvas, int height, int width) {
        double canvasHeight = canvas.getHeight();
        double canvasWidth = canvas.getWidth();
        double cellAmountHeight = (double) height;
        double cellAmountWidth = (double) width;

        if (height < width) {
            return canvasWidth / cellAmountWidth;
        }
        else {
            return canvasHeight / cellAmountHeight;
        }



    }

    /**
     * Draws the canvas based on the input parameters. Goes through the board in a loop and fills each cell based on its value and with the corresponding color with a fillRect.
     * @param gc The graphics context of the canvas.
     * @param cellSize The size each cells needs
     * @param aliveCellColor The color each alive cell shall be drawn in.
     * @param deadCellColor The color each dead cell shall be drawn in.
     * @param board The board the draw function is based upon.
     * @param height The height of the board the function is called upon.
     * @param width The width of the board the function is called upon.
     */


    public void drawStatic(GraphicsContext gc, double cellSize, Color aliveCellColor, Color deadCellColor, byte[][] board, int height, int width) {

        gc.strokeRect(0, 0, width, height);

        try {
            for (int y = 0; y < height; y++) {
                try {
                    for (int x = 0; x < width; x++) {
                        if (board[y][x] == 1) {
                            gc.setFill(aliveCellColor);
                            gc.fillRect((x * cellSize), (y * cellSize), cellSize, cellSize);
                        } else {
                            gc.setFill(deadCellColor);
                            gc.fillRect((x * cellSize), (y * cellSize), cellSize, cellSize);
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    /**
     * Draws the canvas based on the input parameters. Goes through the board in a loop and fills each cell based on its value and with the corresponding color with a fillRect.
     * @param gc The graphics context of the canvas.
     * @param cellSize The size each cells needs
     * @param aliveCellColor The color each alive cell shall be drawn in.
     * @param deadCellColor The color each dead cell shall be drawn in.
     * @param board The board the draw function is based upon.
     * @param height The height of the board the function is called upon.
     * @param width The width of the board the function is called upon.
     */

    public void drawDynamic(GraphicsContext gc, Canvas canvas, double cellSize, Color aliveCellColor, Color deadCellColor, ArrayList<ArrayList<Byte>> board, int height, int width) {

        gc.strokeRect(0, 0,     canvas.getWidth(), canvas.getHeight());
        gc.setFill(deadCellColor);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

        try {
            for (int y = 0; y < height; y++) {
                try {
                    for (int x = 0; x < width; x++) {
                        if (board.get(y).get(x) == 1) {
                            gc.setFill(aliveCellColor);
                            gc.fillRect((x * cellSize), (y * cellSize), cellSize, cellSize);
                        } else {
                            gc.setFill(deadCellColor);
                            gc.fillRect((x * cellSize), (y * cellSize), cellSize, cellSize);
                        }
                    }
                } catch (IndexOutOfBoundsException e) {

                }
            }
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Loops through every cell and counts the amount of live neighbor cells in each direction.
     * The next status of each cell is put in the nextBoard array, and what this status should be is based on the rules currently in use in the GoL class.
     * At the end of the loops, the evolvedBoard is set to be the new currentBoard. This is done in order to avoid mix of data between the old, and the new state of the board, which would result in false patterns.
     * For static boards.
     */

    public byte[][] nextGenerationStatic(byte[][] evolvingBoard, int height, int width) {
        byte[][] evolvedBoard = new byte[height][width];

        try {
            for (int y = 0; y < height; y++) {
                try {
                    for (int x = 0; x < width; x++) {

                        int neighbors = 0;
                        int aliveStatus = 0;

                        //Check the status of the cell, whether it is alive or dead.
                        if (evolvingBoard[y][x] == 1) {
                            aliveStatus = 1;
                        }
                        if (evolvingBoard[y][x] == 0) {
                            aliveStatus = 0;
                        }

                        //Count the number of living neighbors of the particular cell
                        if ((y - 1 >= 0 && y - 1 < evolvingBoard.length) && (x - 1 >= 0 && x - 1 < evolvingBoard[y].length)) {
                            if (evolvingBoard[y - 1][x - 1] == 1) {
                                neighbors++;
                            }
                        }

                        if (y - 1 >= 0 && y - 1 < evolvingBoard.length) {
                            if (evolvingBoard[y - 1][x] == 1) {
                                neighbors++;
                            }
                        }

                        if ((y - 1 >= 0 && y - 1 < evolvingBoard.length) && (x + 1 >= 0 && x + 1 < evolvingBoard[y].length)) {
                            if (evolvingBoard[y - 1][x + 1] == 1) {
                                neighbors++;
                            }
                        }

                        if (x - 1 >= 0 && x - 1 < evolvingBoard[y].length) {
                            if (evolvingBoard[y][x - 1] == 1) {
                                neighbors++;
                            }
                        }

                        if (x + 1 >= 0 && x + 1 < evolvingBoard[y].length) {
                            if (evolvingBoard[y][x + 1] == 1) {
                                neighbors++;
                            }
                        }

                        if ((y + 1 >= 0 && y + 1 < evolvingBoard.length) && (x - 1 >= 0 && x - 1 < evolvingBoard[y].length)) {
                            if (evolvingBoard[y + 1][x - 1] == 1) {
                                neighbors++;
                            }
                        }

                        if (y + 1 >= 0 && y + 1 < evolvingBoard.length) {
                            if (evolvingBoard[y + 1][x] == 1) {
                                neighbors++;
                            }
                        }

                        if ((y + 1 >= 0 && y + 1 < evolvingBoard.length) && (x + 1 >= 0 && x + 1 < evolvingBoard[y].length)) {
                            if (evolvingBoard[y + 1][x + 1] == 1) {
                                neighbors++;
                            }
                        }

                        //Returns a value to a temporary array based on the rules method in the GoL class.
                        evolvedBoard[y][x] = GoL.rules(neighbors, aliveStatus);

                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    //
                }

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //
        }
        return evolvedBoard;

    }

    /**
     * Loops through every cell and counts the amount of live neighbor cells in each direction.
     * The next status of each cell is put in the nextBoard array, and what this status should be is based on the rules currently in use in the GoL class.
     * At the end of the loops, the evolvedBoard is set to be the new currentBoard. This is done in order to avoid mix of data between the old, and the new state of the board, which would result in false patterns.
     * For dynamic boards.
     */

    public ArrayList<ArrayList<Byte>> nextGenerationDynamic(ArrayList<ArrayList<Byte>> evolvingBoard, int height, int width) {

        ArrayList<ArrayList<Byte>> evolvedBoard = new ArrayList<>();

        try {
            for (int y = 0; y < height; y++) {
                try {
                    evolvedBoard.add(new ArrayList<>());
                    for (int x = 0; x < width; x++) {

                        int neighbors = 0;
                        int aliveStatus = 0;

                        //Check the status of the cell, whether it is alive or dead.
                        if (evolvingBoard.get(y).get(x) == 1) {
                            aliveStatus = 1;
                        } else if (evolvingBoard.get(y).get(x) == 0) {
                            aliveStatus = 0;
                        }

                        //Count the number of living neighbors of the particular cell
                        if ((y - 1 >= 0 && y - 1 < evolvingBoard.size()) && (x - 1 >= 0 && x - 1 < evolvingBoard.get(y).size())) {
                            if (evolvingBoard.get(y - 1).get(x - 1) == 1) {
                                neighbors++;
                            }
                        }


                        if (y - 1 >= 0 && y - 1 < evolvingBoard.size()) {
                            if (evolvingBoard.get(y - 1).get(x) == 1) {
                                neighbors++;
                            }
                        }

                        if ((y - 1 >= 0 && y - 1 < evolvingBoard.size()) && (x + 1 >= 0 && x + 1 < evolvingBoard.get(y).size())) {
                            if (evolvingBoard.get(y - 1).get(x + 1) == 1) {
                                neighbors++;
                            }
                        }

                        if (x - 1 >= 0 && x - 1 < evolvingBoard.get(y).size()) {
                            if (evolvingBoard.get(y).get(x - 1) == 1) {
                                neighbors++;
                            }
                        }

                        if (x + 1 >= 0 && x + 1 < evolvingBoard.get(y).size()) {
                            if (evolvingBoard.get(y).get(x + 1) == 1) {
                                neighbors++;
                            }
                        }

                        if ((y + 1 >= 0 && y + 1 < evolvingBoard.size()) && (x - 1 >= 0 && x - 1 < evolvingBoard.get(y).size())) {
                            if (evolvingBoard.get(y + 1).get(x - 1) == 1) {
                                neighbors++;
                            }
                        }

                        if (y + 1 >= 0 && y + 1 < evolvingBoard.size()) {
                            if (evolvingBoard.get(y + 1).get(x) == 1) {
                                neighbors++;
                            }
                        }

                        if ((y + 1 >= 0 && y + 1 < evolvingBoard.size()) && (x + 1 >= 0 && x + 1 < evolvingBoard.get(y).size())) {
                            if (evolvingBoard.get(y + 1).get(x + 1) == 1) {
                                neighbors++;
                            }
                        }

                        //Returns a value to a temporary array based on the rules method in the GoL class.
                        byte nextStatus = GoL.rules(neighbors, aliveStatus);


                        evolvedBoard.get(y).add(x, nextStatus);
                    }

                } catch (IndexOutOfBoundsException e) {
                    //
                }
            }

        } catch (IndexOutOfBoundsException e) {
            //
        }
        return evolvedBoard;
    }
}
