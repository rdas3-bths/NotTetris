import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class DrawPanel extends JPanel implements KeyListener {

    private BrickLayout brickGrid;
    private long brickFallCounter;
    private long brickAddCounter;
    private boolean start;
    private final double INTERVAL = .10;
    private final double BRICK_FALL_INTERVAL = .5;
    private boolean brickFalling;

    public DrawPanel() {
        brickGrid = new BrickLayout(40);
        brickFallCounter = System.currentTimeMillis();
        brickAddCounter = System.currentTimeMillis();
        start = false;
        brickFalling = false;
    }

    protected void paintComponent(Graphics g) {
        double brickFallTime = (System.currentTimeMillis() - brickFallCounter) / 1000.0;
        if (brickFallTime > INTERVAL) {
            brickFallCounter = System.currentTimeMillis();
            brickGrid.moveBricksDown();
        }
        double brickAddTime = (System.currentTimeMillis() - brickAddCounter) / 1000.0;
        if (brickAddTime > BRICK_FALL_INTERVAL) {
            brickAddCounter = System.currentTimeMillis();
            int length = (int)(Math.random()*4)+1;
            int start = (int)(Math.random()*35);
            Brick b = new Brick(start, start+length);
            brickGrid.addBrick(b);
        }

        super.paintComponent(g);
        int y = 5;

        for (int r = 0; r < 30; r++) {
            int x = 5;
            for (int c = 0; c < 39; c++) {
                g.drawRect(x, y, 20, 20);
                if (brickGrid.checkBrickSpot(r, c)) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(Color.RED);
                    g2.fillRect(x, y, 20, 20);
                    g2.setColor(Color.BLACK);
                }
                x += 25;
            }
            y += 25;
        }



    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            brickGrid.moveBrickHorizontal("left");
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            brickGrid.moveBrickHorizontal("right");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}