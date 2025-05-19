import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NotTetrisMain {
    public static void main(String[] args) {
        JFrame f = new JFrame("Not Tetris");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(12 * 26 + 10, 26 * 23 + 25);
        f.setVisible(true);

        final NotTetris game = new NotTetris();
        f.add(game);

        // Keyboard controls
        f.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        game.sendRotate(-1);
                        break;
                    case KeyEvent.VK_S:
                        game.sendRotate(+1);
                        break;
                    case KeyEvent.VK_A:
                        game.sendMove(-1);
                        break;
                    case KeyEvent.VK_D:
                        game.sendMove(+1);
                        break;
                    case KeyEvent.VK_SPACE:
                        game.doFullDrop();
                        break;
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        });

        // Make the falling piece drop every second
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        game.singleDrop();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();
    }
}
