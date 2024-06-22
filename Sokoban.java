import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Sokoban extends JFrame implements KeyListener {
    private final int TILE_SIZE = 50;
    private final int GRID_SIZE = 8;
    private final int PLAYER = 1;
    private final int BOX = 2;
    private final int TARGET = 3;
    private final int GROUND = 4;

    private int[][] grid = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 4, 4, 4, 4, 4, 4, 0},
            {0, 4, 3, 0, 0, 0, 4, 0},
            {0, 4, 0, 2, 1, 0, 4, 0},
            {0, 4, 0, 0, 0, 0, 4, 0},
            {0, 4, 4, 4, 0, 0, 4, 0},
            {0, 0, 0, 4, 4, 4, 4, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };

    private int playerX = 3;
    private int playerY = 3;

    public Sokoban() {
        setTitle("Sokoban Game");
        setSize(TILE_SIZE * GRID_SIZE, TILE_SIZE * GRID_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                int tileType = grid[y][x];
                Color color = Color.WHITE;

                switch (tileType) {
                    case PLAYER:
                        color = Color.RED;
                        break;
                    case BOX:
                        color = Color.BLUE;
                        break;
                    case TARGET:
                        color = Color.GREEN;
                        break;
                    case GROUND:
                        color = Color.GRAY;
                        break;
                }

                g.setColor(color);
                g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        int newX = playerX;
        int newY = playerY;

        switch (keyCode) {
            case KeyEvent.VK_UP:
                newY--;
                break;
            case KeyEvent.VK_DOWN:
                newY++;
                break;
            case KeyEvent.VK_LEFT:
                newX--;
                break;
            case KeyEvent.VK_RIGHT:
                newX++;
                break;
        }

        if (isValidMove(newX, newY)) {
            if (grid[newY][newX] == BOX) {
                int boxNewX = newX + (newX - playerX);
                int boxNewY = newY + (newY - playerY);

                if (isValidMove(boxNewX, boxNewY) && grid[boxNewY][boxNewX] == TARGET) {
                    grid[boxNewY][boxNewX] = BOX;
                } else {
                    return;
                }
            }

            grid[playerY][playerX] = GROUND;
            playerX = newX;
            playerY = newY;
            grid[playerY][playerX] = PLAYER;
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE && grid[y][x] != GROUND;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Sokoban sokobanGame = new Sokoban();
            sokobanGame.setVisible(true);
   });;
}
}