public class Brick {
    private int start;
    private int end;
    private int row;
    private boolean falling;

    public Brick(int start, int end) {
        this.start = start;
        this.end = end;
        this.row = -1;
        falling = true;
    }

    public void moveLeft() {
        start--;
        end--;
    }

    public void moveRight() {
        start++;
        end++;
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
    }

    public int getEnd() {
        return end;
    }

    public String toString() {
        return start + "," + end + " --> Row: " + row;
    }

}