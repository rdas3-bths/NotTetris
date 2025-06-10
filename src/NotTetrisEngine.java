import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class NotTetrisEngine {
    private int rowsCleared;
    private Block[] allBlocks;
    private ArrayList<Integer> speeds = new ArrayList<Integer>(Arrays.asList(1000, 1000, 900, 800, 700, 600, 500, 400));

    private BufferedImage tileImage;
    private BufferedImage smallTileImage;
    private Point pieceOrigin;
    private int currentPiece;
    private int heldPiece;
    private int rotation;
    private ArrayList<Integer> nextPieces = new ArrayList<Integer>();

    private int[][] numberGrid;
    private boolean gameOver;
    private int[][] blockPreview;
    private int[][] heldPiecePreview;
    private SoundHandler soundManager;
    private int highScore;

    public NotTetrisEngine() {
        blockPreview = new int[4][4];
        heldPiecePreview = new int[4][4];
        allBlocks = new Block[7];
        for (int i = 1; i <= 7; i++) {
            allBlocks[i-1] = new Block(i);
        }
        heldPiece = -1;
        loadHighScore();
        tileImage = loadImage("tiles/Blue.png");
        smallTileImage = loadImage("tiles/Blue-Small.png");
        numberGrid = new int[12][24];
        for (int i = 0; i < numberGrid.length; i++) {
            for (int j = 0; j < numberGrid[0].length; j++) {
                numberGrid[i][j] = 9;
            }
        }
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 23; j++) {
                if (i == 0 || i == 11 || j == 22) {
                } else {
                    numberGrid[i][j] = 8;
                }
            }
        }
        newPiece();

        try {
            soundManager = new SoundHandler();
            soundManager.playSound3();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getHighScore() {
        return highScore;
    }

    public void loadHighScore() {
        File f = new File("data/highscore");
        try {
            Scanner scan = new Scanner(f);
            while (scan.hasNextLine()) {
                String score = scan.nextLine();
                highScore = Integer.parseInt(score);
            }
            scan.close();
        }
        catch (FileNotFoundException fn) {
            System.out.println("File not found");
            highScore = 0;
        }
    }

    public void saveHighScore(int newHighScore) {
        try {
            FileWriter f = new FileWriter("data/highscore");
            f.write(newHighScore + "");
            f.close();
        }
        catch (Exception e) {
            System.out.println("Could not save high score");
        }


    }

    public void setNextPiece() {
        blockPreview = new int[4][4];
        Point[] next = this.getNextPiece();
        for (Point p : next) {
            int x = (int)p.getX();
            int y = (int)p.getY();
            blockPreview[y][x] = 1;
        }
    }

    public void setHeldPiece() {
        if (heldPiece != -1) {
            heldPiecePreview = new int[4][4];
            Point[] next = this.getHeldPiecePoint();
            for (Point p : next) {
                int x = (int)p.getX();
                int y = (int)p.getY();
                heldPiecePreview[y][x] = 1;
            }
        }

    }

    public int[][] getHeldPiecePreview() {
        return heldPiecePreview;
    }

    public int[][] getBlockPreview() {
        return blockPreview;
    }

    public int getDropInterval() {

        int level = getLevel();
        if (level < speeds.size()) {
            return speeds.get(level);
        }
        else {
            return 200;
        }

    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getRowsCleared() {
        return rowsCleared;
    }

    public int getLevel() {
        return (rowsCleared / 10) + 1;
    }

    public int[][] getNumberGrid() {
        return numberGrid;
    }

    public Point[] getBlockPoints() {
        return allBlocks[currentPiece].getBlocks()[rotation];
    }

    public int getCurrentPiece() {
        return currentPiece;
    }

    public Point[] getNextPiece() {
        return allBlocks[nextPieces.get(0)].getBlocks()[0];
    }

    public Point[] getHeldPiecePoint() {
        return allBlocks[heldPiece].getBlocks()[0];
    }

    public Point getPieceOrigin() {
        return pieceOrigin;
    }

    public BufferedImage getTileImage() {
        return tileImage;
    }

    public BufferedImage getSmallTileImage() {
        return smallTileImage;
    }

    public void checkGameOver() {
        int checkGrid = 0;
        for (int i = 0; i < numberGrid.length; i++) {
            if (numberGrid[i][checkGrid] >= 0 && numberGrid[i][checkGrid] <= 6) {
                gameOver = true;
            }
        }
    }

    public void newPiece() {
        checkGameOver();
        pieceOrigin = new Point(5, 0);
        rotation = 0;
        if (nextPieces.size() < 2) {
            ArrayList<Integer> newSet = new ArrayList<Integer>();
            Collections.addAll(newSet, 0, 1, 2, 3, 4, 5, 6);
            Collections.shuffle(newSet);
            nextPieces.addAll(newSet);
        }
        currentPiece = nextPieces.get(0);
        nextPieces.remove(0);
    }

    private boolean collidesAt(int x, int y, int rotation) {
        for (Point p : allBlocks[currentPiece].getBlocks()[rotation]) {
            if (numberGrid[p.x + x][p.y + y] != 8) {
                return false;
            }
        }
        return true;
    }

    public void rotate(int i) {
        int newRotation = (rotation + i) % 4;
        if (newRotation < 0) {
            newRotation = 3;
        }
        if (collidesAt(pieceOrigin.x, pieceOrigin.y, newRotation)) {
            rotation = newRotation;
        }
    }

    public void move(int i) {
        if (collidesAt(pieceOrigin.x + i, pieceOrigin.y, rotation)) {
            pieceOrigin.x += i;
        }
    }

    public boolean dropDown() {
        if (collidesAt(pieceOrigin.x, pieceOrigin.y + 1, rotation)) {
            pieceOrigin.y += 1;
            return false;
        }
        else {
            fixToGrid();
            return true;
        }

    }

    public void fullDrop() {
        while (true) {
            boolean check = dropDown();
            if (check)
                break;

        }
    }

    public void displayNumberGrid() {
        for (int i = 0; i < numberGrid[0].length; i++) {
            for (int j = 0; j < numberGrid.length; j++) {
                System.out.print(numberGrid[j][i] + " ");
            }
            System.out.println();
        }
    }

    public void fixToGrid() {
        for (Point p : allBlocks[currentPiece].getBlocks()[rotation]) {
            numberGrid[pieceOrigin.x + p.x][pieceOrigin.y + p.y] = currentPiece;
        }
        boolean check = clearRows();
        soundManager.playSound1();
        if (check)
            soundManager.playSound2();


        newPiece();
    }

    public void holdPiece() {
        if (heldPiece == -1) {
            heldPiece = this.currentPiece;
            newPiece();
        }
        else {
            int swap = heldPiece;
            heldPiece = this.currentPiece;
            this.currentPiece = swap;
        }
    }

    public BufferedImage loadImage(String fileName) {
        try {
            BufferedImage image;
            image = ImageIO.read(new File(fileName));
            return image;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteRow(int row) {
        for (int j = row-1; j > 0; j--) {
            for (int i = 1; i < 11; i++) {
                numberGrid[i][j+1] = numberGrid[i][j];
            }
        }
        rowsCleared++;
    }

    public boolean clearRows() {
        boolean gap;
        boolean cleared = false;

        for (int j = 21; j > 0; j--) {
            gap = false;
            for (int i = 1; i < 11; i++) {
                if (numberGrid[i][j] == 8) {
                    gap = true;
                    break;
                }
            }
            if (!gap) {
                cleared = true;
                deleteRow(j);
                j += 1;
            }
        }
        return cleared;
    }

    public int checkTheoreticalPos(){
        ArrayList<Integer> theor = new ArrayList<>();
        for (Point p : allBlocks[currentPiece].getBlocks()[rotation]) {
            int theorVal = 0;
            for(int j = p.y + pieceOrigin.y + 1; j < 22; j++){
                if(numberGrid[p.x + pieceOrigin.x][j]==8){
                    theorVal++;
                }else break;
            }
            theor.add(theorVal);
        }
        return Collections.min(theor) + pieceOrigin.y;
    }
}
