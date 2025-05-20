import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NotTetrisMain {
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
                        if (!game.isPaused())
                            game.sendMove(-1);
                        break;
                    case KeyEvent.VK_D:
                        if (!game.isPaused())
                            game.sendMove(+1);
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
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        });

        new Thread() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                while (true) {
                        long elapsedTime = System.currentTimeMillis() - currentTime;
                        if (elapsedTime > game.getGameDropInterval()) {
                            if (!game.isPaused()) {
                                game.singleDrop();
                            }
                            currentTime = System.currentTimeMillis();
                        }
                }
            }
        }.start();
    }
}
