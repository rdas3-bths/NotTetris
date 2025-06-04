import java.awt.*;
import java.awt.image.BufferedImage;

public class Block {
    private int blockType;
    private BufferedImage imageTile;
    private BufferedImage smallImageTile;

    private Point[][] blocks;

    private int currentBlock;

    public Block(int blockType) {
        this.blockType = blockType;
        currentBlock = 0;
        if (blockType == 1) setIPiece();
        if (blockType == 2) setJPiece();
        if (blockType == 3) setLPiece();
        if (blockType == 4) setSquarePiece();
        if (blockType == 5) setSPiece();
        if (blockType == 6) setTPiece();
        if (blockType == 7) setZPiece();
    }

    private void setIPiece() {
        blocks = new Point[][]{
                {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
                {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)},
                {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
                {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)}
        };
    }

    private void setJPiece() {
        blocks = new Point[][]{
                {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0)},
                {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2)},
                {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2)},
                {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0)}
        };
    }

    private void setLPiece() {
        blocks = new Point[][]{
                { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
                { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
                { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
                { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) }
        };
    }

    private void setSquarePiece() {
        blocks = new Point[][]{
                { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
        };
    }

    private void setSPiece() {
        blocks = new Point[][]{
                { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
                { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
        };
    }

    private void setTPiece() {
        blocks = new Point[][]{
                { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
                { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
                { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
                { new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2) }
        };
    }

    private void setZPiece() {
        blocks = new Point[][]{
            { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
            { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
            { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
            { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }
        };
    }
}
