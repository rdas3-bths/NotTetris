import java.awt.*;
import javax.swing.JPanel;

public class NotTetris extends JPanel {

    private NotTetrisEngine game = new NotTetrisEngine();
    private boolean paused;
    private int[][] blockPreview;

    public NotTetris() {
        super();
        paused = true;
        game.init();
        blockPreview = new int[4][4];
    }

    public void resetGame() {
        game = new NotTetrisEngine();
        paused = true;
        game.init();
        blockPreview = new int[4][4];
        repaint();
    }

    public void setNextPiece() {
        blockPreview = new int[4][4];
        Point[] next = game.getNextPiece();
        for (Point p : next) {
            int x = (int)p.getX();
            int y = (int)p.getY();
            blockPreview[y][x] = 1;
        }
    }

    public int getGameDropInterval() {
        return game.getDropInterval();
    }

    public void togglePause() {
        paused = !paused;
        repaint();
    }

    public boolean isPaused() {
        return paused;
    }

    public void sendRotate(int i) {
        game.rotate(i);
        repaint();
    }

    public void sendMove(int i) {
        game.move(i);
        repaint();
    }

    public void doFullDrop() {
        game.fullDrop();
        repaint();
    }

    public void singleDrop() {
        game.dropDown();
        repaint();
    }

    // Draw the falling piece
    private void drawPiece(Graphics g) {
        g.setColor(Color.GRAY);
        for (Point p : game.getBlockTypes()[game.getCurrentPiece()][game.getRotation()]) {
            g.fillRect((p.x + game.getPieceOrigin().x) * 26,
                    (p.y + game.checkTheoreticalPos()) * 26,
                    25, 25);
        }

        g.setColor(Color.red);
        for (Point p : game.getBlockTypes()[game.getCurrentPiece()][game.getRotation()]) {

            g.fillRect((p.x + game.getPieceOrigin().x) * 26,
                    (p.y + game.getPieceOrigin().y) * 26,
                    25, 25);
        }

    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (game.isGameOver()) {
            paused = true;
        }
        g.fillRect(0, 0, 26*12, 26*23);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 23; j++) {
                g.setColor(game.getGrid()[i][j]);
                g.fillRect(26*i, 26*j, 25, 25);
            }
        }

        g.setColor(Color.WHITE);

        drawPiece(g);

        if (paused) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(Color.BLACK);
            g.drawRect(100, 270, 100, 50);
            g2.setColor(Color.GRAY);
            g2.fillRect(100, 270, 100, 50);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Courier New", 1, 15));
            if (game.isGameOver()) {
                g.drawString("Game Over", 115, 300);
            }
            else {
                g.drawString("PAUSED", 125, 300);
            }

        }

        Graphics2D g2 = (Graphics2D)g;
        g2.setFont(new Font("Courier New", 1, 15));
        g2.setColor(Color.BLACK);
        g.drawString("Level: " + game.getLevel(), 320, 50);
        g.drawString("Rows cleared: " + game.getRowsCleared(), 320, 70);
        g.drawString("Next piece:", 320, 150);
        g.drawRect(320, 160, 100, 100);

        int y = 175;

        setNextPiece();
        for (int[] row : blockPreview) {
            int x = 330;
            for (int value : row) {
                if (value == 1) {
                    g2.setColor(Color.BLACK);
                    g2.drawRect(x, y, 20, 20);
                    g2.setColor(Color.RED);
                    g2.fillRect(x, y, 19, 19);
                }
                x += 20;
            }
            y += 20;
        }
    }
}