import java.util.ArrayList;

public class BrickLayout {

    private ArrayList<Brick> bricks;
    private ArrayList<Brick> bricksOnGrid;
    private int[][] brickLayout;
    private int cols;

    public BrickLayout(int cols) {
        this.cols = cols;
        bricks = new ArrayList<Brick>();
        bricksOnGrid = new ArrayList<Brick>();
        brickLayout = new int[30][cols];
    }

    public ArrayList<Brick> getBricksOnGrid() {
        return bricksOnGrid;
    }

    public void addBrick(Brick b) {
        bricks.add(b);
    }

    public void moveBricksDown() {

        for (Brick b : bricksOnGrid) {
            // brick is falling

            if (b.getRow() < 29 && canFitOnRow(b.getRow()+1, b, brickLayout)) {
                for (int c = b.getStart(); c <= b.getEnd(); c++) {
                    brickLayout[b.getRow()][c] = 0;
                    brickLayout[b.getRow()+1][c] = 1;
                }
                b.setRow(b.getRow()+1);
            }
            else {
                b.doneFalling();
            }

        }

        if (bricks.size() != 0 && bricksOnGridDoneFalling()) {
            Brick b = bricks.remove(0);
            b.setRow(0);
            for (int c = b.getStart(); c <= b.getEnd(); c++) {
                brickLayout[0][c] = 1;
            }
            bricksOnGrid.add(b);
        }

    }

    public boolean bricksOnGridDoneFalling() {
        for (Brick b : bricksOnGrid) {
            if (b.isFalling()) {
                return false;
            }
        }

        return true;
    }

    public void makeNextBrickFall() {
        if (bricks.size() != 0) {
            Brick b = bricks.remove(0);
            for (int r = 0; r < brickLayout.length; r++) {
                boolean currentRow = canFitOnRow(r, b, brickLayout);
                boolean rowBelow = canFitOnRow(r + 1, b, brickLayout);
                if (currentRow && !rowBelow) {
                    for (int c = b.getStart(); c <= b.getEnd(); c++) {
                        b.setRow(r);
                        brickLayout[r][c] = 1;
                    }
                    break;
                }
            }
            bricksOnGrid.add(b);
        }

    }

    public boolean canFitOnRow(int row, Brick b, int[][] grid) {
        if (row > grid.length-1) {
            return false;
        }
        for (int i = b.getStart(); i <= b.getEnd(); i++) {
            if (grid[row][i] != 0) {
                return false;
            }
        }
        return true;
    }

    public void printBrickLayout() {
        for (int r = 0; r < brickLayout.length; r++) {
            for (int c = 0; c < brickLayout[0].length; c++) {
                System.out.print(brickLayout[r][c] + " ");
            }
            System.out.println();
        }
    }

    public boolean checkBrickSpot(int r, int c) {
        if (brickLayout[r][c] == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public Brick getCurrentlyFallingBrick() {
        for (Brick b : bricksOnGrid) {
            if (b.isFalling()) {
                return b;
            }
        }

        return null;
    }

    public void moveBrickHorizontal(String direction) {
        Brick b = getCurrentlyFallingBrick();
        for (int i = b.getStart(); i <= b.getEnd(); i++) {
            brickLayout[b.getRow()][i] = 0;
        }
        if (direction.equals("left") && b.getStart() > 0)
            b.moveLeft();
        if (direction.equals("right") && b.getEnd() < brickLayout[0].length-2)
            b.moveRight();
        for (int i = b.getStart(); i <= b.getEnd(); i++) {
            brickLayout[b.getRow()][i] = 1;
        }
    }
}
