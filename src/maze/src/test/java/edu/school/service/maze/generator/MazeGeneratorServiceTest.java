package edu.school.service.maze.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class MazeGeneratorServiceTest {

    @Test
    void testMazeInitialization() {
        MazeGeneratorService generator1 = new MazeGeneratorService(10, 10);
        MazeGeneratorService generator2 = new MazeGeneratorService(5, 10);
        MazeGeneratorService generator3 = new MazeGeneratorService(10, 5);

        Assertions.assertNotNull(generator1.getWallAtRight());
        Assertions.assertNotNull(generator1.getWallAtBottom());

        Assertions.assertNotNull(generator2.getWallAtRight());
        Assertions.assertNotNull(generator2.getWallAtBottom());

        Assertions.assertNotNull(generator3.getWallAtRight());
        Assertions.assertNotNull(generator3.getWallAtBottom());
    }

    @Test
    void testCorrectMazeDimensions() {
        MazeGeneratorService generator = new MazeGeneratorService(5, 10);

        int[][] bottomWalls = generator.getWallAtBottom();
        int[][] rightWalls = generator.getWallAtRight();

        Assertions.assertEquals(5, bottomWalls.length);
        Assertions.assertEquals(10, bottomWalls[0].length);

        Assertions.assertEquals(5, rightWalls.length);
        Assertions.assertEquals(10, rightWalls[0].length);
    }

    @Test
    void testMazeRegeneration() {
        MazeGeneratorService generator = new MazeGeneratorService(10, 10);

        int[][] initialRightWalls = deepCopyArray(generator.getWallAtRight());
        int[][] initialBottomWalls = deepCopyArray(generator.getWallAtBottom());

        generator.mazeGenerator();

        Assertions.assertFalse(Arrays.deepEquals(initialRightWalls, generator.getWallAtRight()));
        Assertions.assertFalse(Arrays.deepEquals(initialBottomWalls, generator.getWallAtBottom()));
    }

    private int[][] deepCopyArray(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

}