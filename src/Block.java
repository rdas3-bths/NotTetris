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

        if (type == 0)  return loadImage("tiles/Blue.png");
        if (type == 1)  return loadImage("tiles/Cyan.png");
        if (type == 2)  return loadImage("tiles/Green.png");
        if (type == 3)  return loadImage("tiles/Orange.png");
        if (type == 4)  return loadImage("tiles/Purple.png");
        if (type == 5)  return loadImage("tiles/Red.png");
        if (type == 6)  return loadImage("tiles/Yellow.png");

        return null;
    }

    public static BufferedImage getSmallTileImageByType(int type) {
        if (type == 0)  return loadImage("tiles/Blue-Small.png");
        if (type == 1)  return loadImage("tiles/Cyan-Small.png");
        if (type == 2)  return loadImage("tiles/Green-Small.png");
        if (type == 3)  return loadImage("tiles/Orange-Small.png");
        if (type == 4)  return loadImage("tiles/Purple-Small.png");
        if (type == 5)  return loadImage("tiles/Red-Small.png");
        if (type == 6)  return loadImage("tiles/Yellow-Small.png");

        return null;
    }
}
