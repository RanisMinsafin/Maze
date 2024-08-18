package edu.school.service.path.finder;

import edu.school.model.Maze;
import edu.school.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MazePathFinderServiceTest {

    private MazePathFinderService pathFinder;
    private Maze maze;

    @BeforeEach
    void setUp() {
        pathFinder = new MazePathFinderService();
        int rows = 3;
        int cols = 3;

        int[][] wallAtRight = new int[rows][cols];
        int[][] wallAtBottom = new int[rows][cols];

        maze = new Maze();
        maze.setRows(rows);
        maze.setCols(cols);
        maze.setWallAtRight(wallAtRight);
        maze.setWallAtBottom(wallAtBottom);
    }

    @Test
    void testPathInEmptyMaze() {
        Point start = new Point(0, 0);
        Point end = new Point(2, 2);

        List<Point> path = pathFinder.findPath(maze, start, end);

        assertNotNull(path);
        assertEquals(start, path.get(0));
        assertEquals(end, path.get(path.size() - 1));
    }

    @Test
    void testNoPathDueToWalls() {
        maze.getWallAtRight()[0][0] = 1; // Wall to the right of (0,0)
        maze.getWallAtBottom()[0][0] = 1; // Wall below (0,0)

        Point start = new Point(0, 0);
        Point end = new Point(2, 2);

        List<Point> path = pathFinder.findPath(maze, start, end);

        assertTrue(path.isEmpty());
    }

    @Test
    void testStartEqualsEnd() {
        Point start = new Point(1, 1);
        Point end = new Point(1, 1);

        List<Point> path = pathFinder.findPath(maze, start, end);
        assertEquals(0, path.size());
    }

    @Test
    void testPathWithObstacles() {
        maze.getWallAtRight()[0][1] = 1; // Wall to the right of (0,1)
        maze.getWallAtBottom()[1][1] = 1; // Wall below (1,1)

        Point start = new Point(0, 0);
        Point end = new Point(2, 2);

        List<Point> path = pathFinder.findPath(maze, start, end);

        assertNotNull(path);
        assertEquals(5, path.size());
        assertEquals(new Point(2, 2), path.get(path.size() - 1));
    }

    @Test
    void testInvalidStartOrEnd() {
        Point start = new Point(-1, 0); // Invalid point
        Point end = new Point(2, 2);

        List<Point> path = pathFinder.findPath(maze, start, end);
        assertTrue(path.isEmpty());

        start = new Point(0, 0);
        end = new Point(3, 3); // Invalid point
        path = pathFinder.findPath(maze, start, end);
        assertTrue(path.isEmpty());
    }
}
