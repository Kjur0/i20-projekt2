package edu.tm1.krzysztof.jurkowski.i20.projekt2.pathfind;

import edu.tm1.krzysztof.jurkowski.i20.projekt2.DungeonCrawlerField;
import edu.tm1.krzysztof.jurkowski.i20.projekt2.DungeonCrawlerMap;
import lombok.Setter;

import java.util.*;

import static edu.tm1.krzysztof.jurkowski.i20.projekt2.DungeonCrawlerConsts.Map.HEIGHT;
import static edu.tm1.krzysztof.jurkowski.i20.projekt2.DungeonCrawlerConsts.Map.WIDTH;

public class Algorithm {
	private final Node[][] predecessors;
	private final int[][] distances;
	@Setter
	private DungeonCrawlerMap map;

	public Algorithm(DungeonCrawlerMap map) {
		this.map = map;
		this.predecessors = new Node[WIDTH][HEIGHT];
		this.distances = new int[WIDTH][HEIGHT];
	}

	public void findShortestPath(int startX, int startY) {
		PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getDistance));

		for (int x = 0; x < WIDTH; x++)
			for (int y = 0; y < HEIGHT; y++) {
				distances[x][y] = Integer.MAX_VALUE;
				predecessors[x][y] = null;
			}

		distances[startX][startY] = 0;
		queue.add(new Node(startX, startY, 0));

		while (!queue.isEmpty()) {
			Node current = queue.poll();
			int x = current.getX();
			int y = current.getY();

			for (int[] direction: new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, 1}, {-1, 1}, {1, -1}}) {
				int nx = x + direction[0];
				int ny = y + direction[1];

				if (canEnter(nx, ny)) {
					int newDist = distances[x][y] + 1;

					if (newDist < distances[nx][ny]) {
						distances[nx][ny] = newDist;
						predecessors[nx][ny] = current;
						queue.add(new Node(nx, ny, newDist));
					}
				}
			}
		}
	}

	public List<Node> getPath(int targetX, int targetY) {
		List<Node> path = new LinkedList<>();
		for (Node at = predecessors[targetX][targetY]; at != null; at = predecessors[at.getX()][at.getY()])
			path.add(at);
		Collections.reverse(path);
		return path;
	}

	private boolean canEnter(int x, int y) {
		return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT && map.getFields()[x][y] != DungeonCrawlerField.WALL;
	}
}
