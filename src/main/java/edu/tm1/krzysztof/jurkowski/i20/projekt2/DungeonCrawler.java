package edu.tm1.krzysztof.jurkowski.i20.projekt2;

import edu.tm1.krzysztof.jurkowski.i20.projekt2.pathfind.Algorithm;
import edu.tm1.krzysztof.jurkowski.i20.projekt2.pathfind.Node;
import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import static edu.tm1.krzysztof.jurkowski.i20.projekt2.DungeonCrawlerConsts.Map;
import static edu.tm1.krzysztof.jurkowski.i20.projekt2.DungeonCrawlerConsts.Window;

@Log
public class DungeonCrawler extends JFrame {
	private final DungeonCrawlerMap map;
	private final JPanel mapPanel;
	private final JLabel[][] graphicFields;
	private final Algorithm algorithm;
	private int X, Y;
	private boolean isMoving = false;

	public DungeonCrawler() {
		map = new DungeonCrawlerMap();
		algorithm = new Algorithm(map);
		setTitle("Dungeon Crawler");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Window.WIDTH, Window.HEIGHT);

		mapPanel = new JPanel();
		mapPanel.setLayout(new GridLayout(Map.HEIGHT, Map.WIDTH));
		graphicFields = new JLabel[Map.WIDTH][Map.HEIGHT];

		for (int y = Map.HEIGHT - 1; y >= 0; y--)
			for (int x = 0; x < Map.WIDTH; x++) {
				graphicFields[x][y] = new JLabel();
				mapPanel.add(graphicFields[x][y]);
				updateGraphicField(x, y);
			}

		do
		{
			X = (int) (Math.random() * Map.WIDTH);
			Y = (int) (Math.random() * Map.HEIGHT);
		} while (map.getFields()[X][Y] == DungeonCrawlerField.WALL);

		updateMap();

		add(mapPanel, BorderLayout.CENTER);

		mapPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isMoving)
					return;

				int x = e.getX() / (mapPanel.getWidth() / Map.WIDTH);
				int y = e.getY() / (mapPanel.getHeight() / Map.HEIGHT);

				if (map.getFields()[x][y] != DungeonCrawlerField.WALL) {
					movePlayer(x, y);
				}

				log.info(String.format("Clicked (%d, %d)\n", x, y));
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (isMoving)
					return;

				int tmpX = X, tmpY = Y;

				switch (e.getKeyCode()) {
					case KeyEvent.VK_NUMPAD1 -> {
						tmpY--;
						tmpX--;
					}
					case KeyEvent.VK_NUMPAD2 ->
							tmpY--;
					case KeyEvent.VK_NUMPAD3 -> {
						tmpY--;
						tmpX++;
					}
					case KeyEvent.VK_NUMPAD4 ->
							tmpX--;
					case KeyEvent.VK_NUMPAD6 ->
							tmpX++;
					case KeyEvent.VK_NUMPAD7 -> {
						tmpY++;
						tmpX--;
					}
					case KeyEvent.VK_NUMPAD8 ->
							tmpY++;
					case KeyEvent.VK_NUMPAD9 -> {
						tmpY++;
						tmpX++;
					}
				}

				if (canEnter(tmpX, tmpY)) {
					X = tmpX;
					Y = tmpY;
					updateMap();
				}
			}
		});

		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(DungeonCrawler::new);
	}

	private boolean canEnter(int x, int y) {
		return x >= 0 && x < Map.WIDTH && y >= 0 && y < Map.HEIGHT && map.getFields()[x][y] != DungeonCrawlerField.WALL;
	}

	private void updateGraphicField(int x, int y) {
		DungeonCrawlerField field = map.getFields()[x][y];
		Color color;
		switch (field) {
			case CELL ->
					color = Color.WHITE;
			case WALL ->
					color = Color.BLACK;
			case STAIRS ->
					color = Color.GREEN;
			default ->
					throw new IllegalStateException();
		}
		graphicFields[x][y].setOpaque(true);
		graphicFields[x][y].setBackground(color);
	}

	private void movePlayer(int x, int y) {
		algorithm.setMap(map);
		algorithm.findShortestPath(X, Y);
		List<Node> path = algorithm.getPath(x, y);

		if (path == null) {
			log.warning("No path found");
			return;
		}

		isMoving = true;

		SwingWorker<Void, Node> worker = new SwingWorker<>() {
			@Override
			protected Void doInBackground() {
				for (Node node: path) {
					X = node.getX();
					Y = node.getY();
					publish(node);
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						log.severe(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
					}
				}

				return null;
			}

			@Override
			protected void process(List<Node> chunks) {
				updateMap();
			}

			@Override
			protected void done() {
				isMoving = false;
			}
		};

		worker.execute();
	}

	private void updateMap() {
		if (map.getStairsX() == X && map.getStairsY() == Y) {
			log.info("Reached next floor");
			map.generateNewMap();
		}

		for (int x = 0; x < Map.WIDTH; x++)
			for (int y = 0; y < Map.HEIGHT; y++)
				updateGraphicField(x, y);
		graphicFields[X][Y].setBackground(Color.BLUE);

		mapPanel.repaint();
	}
}
