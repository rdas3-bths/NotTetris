import java.util.ArrayList;

public class BrickLayout {

    private ArrayList<Brick> bricks;
    private ArrayList<Brick> bricksOnGrid;
    private int[][] brickLayout;
    private int cols;
    private int score;

    public BrickLayout(int cols) {
        score = 0;
        this.cols = cols;
        bricks = new ArrayList<Brick>();
        bricksOnGrid = new ArrayList<Brick>();
        brickLayout = new int[24][cols];
    }

    public int getScore() {
        return score;
    }

    public int checkFullRow() {
        for (int r = 0; r < brickLayout.length; r++) {
            boolean rowFull = true;
            for (int c = 0; c < brickLayout[0].length; c++) {
                if (brickLayout[r][c] == 0) {
                    rowFull = false;
                }
            }
            if (rowFull) {
                return r;
            }
        }
        return -1;
    }

    public void adjustGrid(int row) {

        for (int i = 0; i < brickLayout[0].length; i++) {
            brickLayout[row][i] = 0;
        }

        for (int i = 0; i < brickLayout[0].length; i++) {
            brickLayout[0][i] = 0;
        }

        for (int r = row-1; r > 0; r--) {
            for (int c = 0; c < brickLayout[0].length; c++) {
                brickLayout[r+1][c] = brickLayout[r][c];
            }
        }
    }

    public void addBrick(Brick b) {
        bricks.add(b);
    }

    public void moveBricksDown() {

        for (Brick b : bricksOnGrid) {

            if (b.getRow() < 29 && canFitOnRow(b.getRow()+1, b, brickLayout)) {
                int blockCounter = 1;
                for (Block block : b.getBlocks()) {
                    brickLayout[block.getRow()][block.getCol()] = 0;
                    brickLayout[block.getRow()+1][block.getCol()] = 1;

                    if (b.getBlockType() == 1) {
                        brickLayout[block.getRow()][block.getCol()] = 1;
                        if (block.getRow() > 0) {
                            brickLayout[block.getRow()-1][block.getCol()] = 0;
                        }
                    }
                    if (b.getBlockType() == 2) {
                        if (blockCounter == 2) {
                            brickLayout[block.getRow()][block.getCol()] = 1;
                            if (block.getRow() > 0) {
                                brickLayout[block.getRow()-1][block.getCol()] = 0;
                            }
                        }
                    }
                    blockCounter++;
                }
                b.setRow(b.getRow()+1);
            }
            else {
                b.doneFalling();
                int fullRow = checkFullRow();
                if (fullRow != -1) {
                    adjustGrid(fullRow);
                    score++;
                }
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

        int blockCounter = 1;
        for (Block block : b.getBlocks()) {
            brickLayout[block.getRow()][block.getCol()] = 0;
            if (b.getBlockType() == 1) {
                if (block.getRow() > 0) {
                    brickLayout[block.getRow()-1][block.getCol()] = 0;
                }
            }
            if (b.getBlockType() == 2) {
                if (block.getRow() > 0) {
                    if (blockCounter == 2) {
                        brickLayout[block.getRow()-1][block.getCol()] = 0;
                    }
                }
            }
            blockCounter++;
        }

        if (direction.equals("left") && !b.checkIfBorderingLeft())
            b.moveLeft();
        if (direction.equals("right") && !b.checkIfBorderingRight(cols))
            b.moveRight();

        blockCounter = 1;
        for (Block block : b.getBlocks()) {
            brickLayout[block.getRow()][block.getCol()] = 1;
            if (b.getBlockType() == 1) {
                if (block.getRow() > 0) {
                    brickLayout[block.getRow()-1][block.getCol()] = 1;
                }
            }
            if (b.getBlockType() == 2) {
                if (block.getRow() > 0) {
                    if (blockCounter == 2) {
                        brickLayout[block.getRow()-1][block.getCol()] = 1;
                    }
                }
            }
            blockCounter++;
        }
    }
}
