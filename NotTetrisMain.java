import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NotTetrisMain {
    public static boolean moveLeft = false;
    public static boolean moveRight = false;

    public static void main(String[] args) {
        JFrame f = new JFrame("Not Tetris");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(12 * 26 + 10 + 200, 26 * 23 + 25);
        f.setLocation(500, 100);

        NotTetris game = new NotTetris();
        f.add(game);
        f.setVisible(true);

        f.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        if (!game.isPaused())
                            game.sendRotate(-1);
                        break;
                    case KeyEvent.VK_S:
                        if (!game.isPaused())
                            game.sendRotate(+1);
                        break;
                    case KeyEvent.VK_A:
                        if (!game.isPaused()) {
                            game.sendMove(-1);
                            moveLeft = true;
                        }
                        break;
                    case KeyEvent.VK_D:
                        if (!game.isPaused()) {
                            game.sendMove(+1);
                            moveRight = true;
                        }

                        break;
                    case KeyEvent.VK_SPACE:
                        if (!game.isPaused())
                            game.doFullDrop();
                        break;
                    case KeyEvent.VK_ALT:
                        if (!game.isPaused())
                            game.singleDrop();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        game.togglePause();
                        break;
                    case KeyEvent.VK_N:
                        game.resetGame();
                    case KeyEvent.VK_SHIFT:
                        if (!game.isPaused())
                            game.holdPiece();
                        break;
                }
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    moveLeft = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    moveRight = false;
                }
            }
        });

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(game.getGameDropInterval());
                    }
                    catch (InterruptedException e) { }
                    if (!game.isPaused()) {
                        game.singleDrop();
                    }
                }
            }
        }.start();
    }
}
