import java.util.ArrayList;

public class Brick {
    private int start;
    private int end;
    private int row;
    private boolean falling;
    private ArrayList<Block> blocks;
    private int blockType;

    public Brick(int start, int end) {
        this.start = start;
        this.end = end;
        blockType = end - start;
        this.row = -1;
        falling = true;
        blocks = new ArrayList<Block>();
        for (int c = start; c <= end; c++) {
            Block b = new Block(row, c);
            blocks.add(b);
        }
    }

    public int getBlockType() {
        return blockType;
    }

    public void moveLeft() {

        for (int i = 0; i < blocks.size(); i++) {
            int currentCol = blocks.get(i).getCol();
            blocks.get(i).setCol(currentCol-1);
        }
    }

    public void moveRight() {

        for (int i = 0; i < blocks.size(); i++) {
            int currentCol = blocks.get(i).getCol();
            blocks.get(i).setCol(currentCol+1);
        }
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public int getStart() {
        return start;
    }

    public boolean isFalling() {
        return falling;
    }

    public void doneFalling() {
        falling = false;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).setRow(row);
        }
    }

    public int getEnd() {
        return end;
    }

    public String toString() {
        return start + "," + end + " --> Row: " + row;
    }

}