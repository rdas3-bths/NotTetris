import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class NotTetrisEngine {
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

    private Point pieceOrigin;
    private int currentPiece;
    private int rotation;
    private ArrayList<Integer> nextPieces = new ArrayList<Integer>();

    private Color[][] grid;

    public Color[][] getGrid() {
        return grid;
    }

    public Point[][][] getBlockTypes() {
        return blockTypes;
    }

    public int getCurrentPiece() {
        return currentPiece;
    }

    public int getRotation() {
        return rotation;
    }

    public Point getPieceOrigin() {
        return pieceOrigin;
    }

    public void init() {
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
    }

    public void newPiece() {
        pieceOrigin = new Point(5, 2);
        rotation = 0;
        if (nextPieces.isEmpty()) {
            Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6);
            Collections.shuffle(nextPieces);
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

    // Drops the piece one line or fixes it to the well if it can't drop
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
        clearRows();
        newPiece();
    }

    public void deleteRow(int row) {
        for (int j = row-1; j > 0; j--) {
            for (int i = 1; i < 11; i++) {
                grid[i][j+1] = grid[i][j];
            }
        }
    }

    public void clearRows() {
        boolean gap;

        for (int j = 21; j > 0; j--) {
            gap = false;
            for (int i = 1; i < 11; i++) {
                if (grid[i][j] == Color.WHITE) {
                    gap = true;
                    break;
                }
            }
            if (!gap) {
                deleteRow(j);
                j += 1;
            }
        }
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
