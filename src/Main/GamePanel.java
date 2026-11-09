package Main;

import Objects.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    public static final int VIRTUAL_WIDTH = 1920;
    public static final int VIRTUAL_HEIGHT = 1080;

    private final int FPS = 60;

    private Thread gameThread;

    int tileRadius = 80;

    int[] layout = {5, 4, 3};
    ArrayList<Tile> tiles = new ArrayList<>();
    ArrayList<String> tileTypes = new ArrayList<>();

    public GamePanel() {
        setPreferredSize(new Dimension(1280, 720));
        setBackground(new Color(92, 214, 255));
        setDoubleBuffered(true);
        setFocusable(true);

        initializeTypes();
        initializeBoard();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Adds all the types of tiles into the lists
    // Must equal 19
    public void initializeTypes() {
        for(int i = 0; i < 1; i++)
            tileTypes.add("Desert");

        for(int i = 0; i < 3; i++)
            tileTypes.add("Wood");

        for(int i = 0; i < 3; i++)
            tileTypes.add("Sheep");

        for(int i = 0; i < 3; i++)
            tileTypes.add("Brick");

        for(int i = 0; i < 3; i++)
            tileTypes.add("Wheat");

        for(int i = 0; i < 3; i++)
            tileTypes.add("Ore");

        for(int i = 0; i < 2; i++)
            tileTypes.add("Silicon");

        for(int i = 0; i < 1; i++)
            tileTypes.add("Copper");
    }

    public void initializeBoard() {
        int currentX = 400;
        int currentY = 500;
        int centerY = 500;
        double apothem = tileRadius * Math.sqrt(3) / 2.0;

        // Draws every tile
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i]; j++) {
                // Gets a random tile type
                int randomIndex = (int)(Math.random() * tileTypes.size());
                String type = tileTypes.get(randomIndex);
                tileTypes.remove(randomIndex);

                // Draws a tile
                tiles.add(new Tile((int) (currentX + j * (apothem * 2)), currentY, tileRadius, type, 1));
                if (layout[i] != 5) {
                    // Gets another random tile type
                    randomIndex = (int)(Math.random() * tileTypes.size());
                    type = tileTypes.get(randomIndex);
                    tileTypes.remove(randomIndex);

                    // Draws the mirrored tile
                    int mirroredY = centerY + (centerY - currentY);
                    tiles.add(new Tile((int) (currentX + j * (apothem * 2)), mirroredY, tileRadius, type, 1));
                }
            }
            currentX += (int) apothem;
            currentY -= (int) (tileRadius * 1.5);
        }
    }

    @Override
    public void run() {
        final double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Scales for different monitors
        double scaleX = getWidth() / (double) VIRTUAL_WIDTH;
        double scaleY = getHeight() / (double) VIRTUAL_HEIGHT;

        double scale = Math.min(scaleX, scaleY);

        double xOffset = (getWidth() - (VIRTUAL_WIDTH * scale)) / 2;
        double yOffset = (getHeight() - (VIRTUAL_HEIGHT * scale)) / 2;

        AffineTransform oldTransform = g2.getTransform();

        // Scales the graphics
        g2.translate(xOffset, yOffset);
        g2.scale(scale, scale);

        /*
         *  ###################
         *  Drawing the objects
         *  ###################
         */

        g2.setColor(Color.WHITE);
        g2.fillRect(1420, 0, VIRTUAL_WIDTH - 1420 + 80, VIRTUAL_HEIGHT);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(1420, 0, 1420, 1080);

        for (Tile tile : tiles) {
            tile.draw(g2);
        }

        g2.setTransform(oldTransform);
        g2.dispose();
    }
}