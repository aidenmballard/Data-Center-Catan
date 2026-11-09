package Objects;

import java.awt.*;

public class Tile {
    // Defines the variables for drawing
    public int x;
    public int y;
    public int radius;
    public String type;
    public Color color;

    public int rollNumber;

    // Defines the polygon to draw
    Polygon hexagon;

    public Tile(int x, int y, int radius, String type, int rollNumber) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        this.rollNumber = rollNumber;

        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(i * 60 + 30);
            xPoints[i] = (int) (this.x + radius * Math.cos(angle));
            yPoints[i] = (int) (this.y + radius * Math.sin(angle));
        }
        this.hexagon = new Polygon(xPoints, yPoints,6);

        // Determines the color of the tile
        this.type = type;
        switch (type) {
            case "Wood":
                this.color = new Color(30, 73, 30);
                break;
            case "Sheep":
                this.color = new Color(0, 189, 0);
                break;
            case "Brick":
                this.color = new Color(71, 24, 24);
                break;
            case "Ore":
                this.color = new Color(116, 214, 205);
                break;
            case "Wheat":
                this.color = new Color(255, 236, 62);
                break;
            case "Desert":
                this.color = new Color(250, 230, 152);
                break;
            case "Silicon":
                this.color = new Color(128, 128, 122);
                break;
            case "Copper":
                this.color = new Color(184, 115, 51);
                break;
            default:
                this.color = new Color(255, 173, 173);
                break;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillPolygon(hexagon);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawPolygon(hexagon);
        if (!this.type.equals("Desert")) {
            g2.setColor(Color.WHITE);
            g2.fillOval((int) (x - radius / 3), (int) (y - radius / 3), (int) (radius * 0.667), (int) (radius * 0.667));
        }
    }
}
