import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Block {
    private int blockType;
    private BufferedImage tileImage;
    private BufferedImage smallTileImage;

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

        tileImage = loadImage("tiles/Blue.png");
        smallTileImage = loadImage("tiles/Blue-Small.png");

    }

    public Point[][] getBlocks() {
        return blocks;
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

    public static BufferedImage loadImage(String fileName) {
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

    public static BufferedImage getTileImageByType(int type) {
        return loadImage("tiles/Blue.png");
    }
}
