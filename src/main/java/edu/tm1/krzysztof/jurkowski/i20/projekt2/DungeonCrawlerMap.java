package edu.tm1.krzysztof.jurkowski.i20.projekt2;

import lombok.Getter;

import java.util.Random;

@Getter
public class DungeonCrawlerMap {
    public static final int WIDTH = 40, HEIGHT = 40;
    private final DungeonCrawlerField[][] fields;

    public DungeonCrawlerMap() {
        fields = new DungeonCrawlerField[WIDTH][HEIGHT];
        generateRandomMap();
    }

    private void generateRandomMap() {
        Random random = new Random();
        boolean stairsPlaced = false;
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++) {
                int randomValue = random.nextInt(10);
                if (randomValue < 7) fields[x][y] = DungeonCrawlerField.CELL;
                else if (randomValue < 9) fields[x][y] = DungeonCrawlerField.WALL;
                else if (!stairsPlaced) {
                    fields[x][y] = DungeonCrawlerField.STAIRS;
                    stairsPlaced = true;
                }
                else fields[x][y] = DungeonCrawlerField.CELL;
            }

    }

}
