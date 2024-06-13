package edu.tm1.krzysztof.jurkowski.i20.projekt2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DungeonCrawler extends JFrame {
    private final DungeonCrawlerMap map;
    private final JPanel mapPanel;
    private final JLabel[][] graphicFields;

    public DungeonCrawler() {
        map = new DungeonCrawlerMap();
        setTitle("Dungeon Crawler");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);

        mapPanel = new JPanel();
        mapPanel.setLayout(new GridLayout(40, 40));
        graphicFields = new JLabel[40][40];

        for (int x = 0; x < 40; x++)
            for (int y = 0; y < 40; y++) {
                graphicFields[x][y] = new JLabel();
                mapPanel.add(graphicFields[x][y]);
                updateGraphicField(x, y);
            }

        add(mapPanel, BorderLayout.CENTER);

        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / (mapPanel.getWidth() / 40);
                int y = e.getY() / (mapPanel.getHeight() / 40);

                // Dijkstra

                System.out.printf("Clicked (%d, %d)\n", x, y);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println(e.getKeyChar());
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DungeonCrawler::new);
    }

    private void updateGraphicField(int x, int y) {
        DungeonCrawlerField field = map.getFields()[x][y];
        Color color;
        switch (field) {
            case CELL -> color = Color.WHITE;
            case WALL -> color = Color.BLACK;
            case STAIRS -> color = Color.GREEN;
            default -> throw new IllegalStateException();
        }
        graphicFields[x][y].setOpaque(true);
        graphicFields[x][y].setBackground(color);
    }
}
