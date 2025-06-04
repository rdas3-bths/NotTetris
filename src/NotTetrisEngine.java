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
    private ArrayList<Integer> speeds = new ArrayList<Integer>(Arrays.asList(1000, 1000, 900, 800, 700, 600, 500, 400));
    private final Point[][][] blockTypes = {
            // I-Piece
            {
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) }
            },

            // J-Piece
            {
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) }
            },

            // L-Piece
            {
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) }
            },

            // Square-Piece
            {
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
            },

            // S-Piece
            {
                    { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
                    { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
            },

            // T-Piece
            {
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
                    { new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2) }
            },

            // Z-Piece
            {
                    { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
                    { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }
            }
    };

    private BufferedImage tileImage;
    private BufferedImage smallTileImage;
    private Point pieceOrigin;
    private int currentPiece;
    private int heldPiece;
    private int rotation;
    private ArrayList<Integer> nextPieces = new ArrayList<Integer>();

    private Color[][] grid;
    private boolean gameOver;
    private int[][] blockPreview;
    private int[][] heldPiecePreview;
    private SoundHandler soundManager;
    private int highScore;

    public NotTetrisEngine() {
        blockPreview = new int[4][4];
        heldPiecePreview = new int[4][4];
        heldPiece = -1;
        loadHighScore();
        tileImage = loadImage("tiles/Blue.png");
        smallTileImage = loadImage("tiles/Blue-Small.png");
        grid = new Color[12][24];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 23; j++) {
                if (i == 0 || i == 11 || j == 22) {
                    grid[i][j] = Color.BLACK;
                } else {
                    grid[i][j] = Color.WHITE;
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

    public Color[][] getGrid() {
        return grid;
    }

    public Point[][][] getBlockTypes() {
        return blockTypes;
    }

    public int getCurrentPiece() {
        return currentPiece;
    }

    public int getHeldPiece() { return heldPiece; }

    public Point[] getNextPiece() {
        return blockTypes[nextPieces.get(0)][0];
    }

    public Point[] getHeldPiecePoint() {
        return blockTypes[heldPiece][0];
    }

    public int getRotation() {
        return rotation;
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
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][checkGrid] != null) {
                if (grid[i][checkGrid].getRGB() == -65536) {
                    gameOver = true;
                }
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
        for (Point p : blockTypes[currentPiece][rotation]) {
            if (grid[p.x + x][p.y + y] != Color.WHITE) {
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

    public void fixToGrid() {
        for (Point p : blockTypes[currentPiece][rotation]) {
            grid[pieceOrigin.x + p.x][pieceOrigin.y + p.y] = Color.red;
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
                grid[i][j+1] = grid[i][j];
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
                if (grid[i][j] == Color.WHITE) {
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
        for (Point p : blockTypes[currentPiece][rotation]) {
            int theorVal = 0;
            for(int j = p.y + pieceOrigin.y + 1; j < 22; j++){
                if(grid[p.x + pieceOrigin.x][j]==Color.WHITE){
                    theorVal++;
                }else break;
            }
            theor.add(theorVal);
        }
        return Collections.min(theor) + pieceOrigin.y;
    }
}
