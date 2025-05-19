import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

public class NotTetris extends JPanel {

    private NotTetrisEngine game = new NotTetrisEngine();

    public NotTetris() {
        super();
        game.init();
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
        g.fillRect(0, 0, 26*12, 26*23);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 23; j++) {
                g.setColor(game.getGrid()[i][j]);
                g.fillRect(26*i, 26*j, 25, 25);
            }
        }

        g.setColor(Color.WHITE);

        drawPiece(g);
    }
}