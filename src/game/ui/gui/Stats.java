package game.ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Stats extends JPanel {
    private Map<String, String> stats = new HashMap<>();
    private List<String> labels = new ArrayList<>();
    private int pixelWidth = 0;
    private int pixelHeight = 0;

    public Stats() {
        this.setBackground(Color.black);
    }

    public void setGraphicalDimensions(int pixelWidth, int pixelHeight) {
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
    }

    public void registerStat(String label) {
        labels.add(label);
    }

    public boolean isRegistered(String label) {
        return labels.contains(label);
    }

    public void setStat(String label, String value) {
        this.stats.put(label, value);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int y = 10;
        g.setColor(Color.white);
        for (String label : labels) {
            if (stats.containsKey(label)) {
                g.drawString(label + ": " + stats.get(label), 10, y);
            }
            y += 40;
        }
    }
}
