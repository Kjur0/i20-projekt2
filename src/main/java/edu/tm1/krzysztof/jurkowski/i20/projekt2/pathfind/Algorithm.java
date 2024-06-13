package edu.tm1.krzysztof.jurkowski.i20.projekt2.pathfind;

import edu.tm1.krzysztof.jurkowski.i20.projekt2.DungeonCrawlerMap;

import java.util.PriorityQueue;

import static  edu.tm1.krzysztof.jurkowski.i20.projekt2.DungeonCrawlerMap.WIDTH;
import static  edu.tm1.krzysztof.jurkowski.i20.projekt2.DungeonCrawlerMap.HEIGHT;

public class Algorithm {
	private final DungeonCrawlerMap map;
	private final int [][] costs;
	private Node[][] prevs;
	private PriorityQueue<Node> queue;

	public Algorithm(DungeonCrawlerMap map) {
		this.map = map;
		costs = new int[WIDTH][HEIGHT];
		prevs = new Node[WIDTH][HEIGHT];
		queue = new PriorityQueue<>();

		for (int x= 0; x<WIDTH; x++)
			for (int y= 0; y<HEIGHT;y++) {
				costs[x][y] = Integer.MAX_VALUE;
				prevs[x][y] = null;
			}
	}

	public void findShortestPath(int startX, int startY) {
		costs[startX][startY] = 0;
		queue.add(new Node(startX, startY, 0));

		while (!queue.isEmpty()) {}
	}

}
