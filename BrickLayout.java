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
        brickLayout = new int[24][cols];
    }

    public void addBrick(Brick b) {
        bricks.add(b);
    }

    public void moveBricksDown() {

        for (Brick b : bricksOnGrid) {

            if (b.getRow() < 29 && canFitOnRow(b.getRow()+1, b, brickLayout)) {
                for (Block block : b.getBlocks()) {
                    brickLayout[block.getRow()][block.getCol()] = 0;
                    brickLayout[block.getRow()+1][block.getCol()] = 1;

                    if (b.getBlockType() == 1) {
                        brickLayout[block.getRow()][block.getCol()] = 1;
                        if (block.getRow() > 0) {
                            brickLayout[block.getRow()-1][block.getCol()] = 0;
                        }
                    }
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

    public boolean canFitOnRow(int row, Brick b, int[][] grid) {
        if (row > grid.length-1) {
            return false;
        }

        for (Block block : b.getBlocks()) {
            if (grid[row][block.getCol()] != 0) {
                return false;
            }
        }
        return true;
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

        for (Block block : b.getBlocks()) {
            brickLayout[block.getRow()][block.getCol()] = 0;
            if (b.getBlockType() == 1) {
                if (block.getRow() > 0) {
                    brickLayout[block.getRow()-1][block.getCol()] = 0;
                }
            }
        }

        if (direction.equals("left") && !b.checkIfBorderingLeft())
            b.moveLeft();
        if (direction.equals("right") && !b.checkIfBorderingRight(cols))
            b.moveRight();

        for (Block block : b.getBlocks()) {
            brickLayout[block.getRow()][block.getCol()] = 1;
            if (b.getBlockType() == 1) {
                if (block.getRow() > 0) {
                    brickLayout[block.getRow()-1][block.getCol()] = 1;
                }
            }
        }
    }
}
