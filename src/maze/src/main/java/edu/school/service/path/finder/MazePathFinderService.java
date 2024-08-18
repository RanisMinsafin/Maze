package edu.school.service.path.finder;

import edu.school.model.Maze;
import edu.school.model.Point;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@NoArgsConstructor
@AllArgsConstructor
public class MazePathFinderService {

    private int[][] distances;

    public List<Point> findPath(Maze maze, Point start, Point end) {
        if (!isValidPoint(maze, start) || !isValidPoint(maze, end)) {
            return Collections.emptyList();
        }

        distances = new int[maze.getRows()][maze.getCols()];
        for (int[] row : distances) {
            Arrays.fill(row, -1);
        }

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        distances[start.getX()][start.getY()] = 0;

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            for (int[] direction : directions) {
                int newRow = current.getX() + direction[0];
                int newCol = current.getY() + direction[1];

                if (isValidPoint(maze, new Point(newRow, newCol)) &&
                        !isWall(maze, current, newRow, newCol, direction)) {

                    if (distances[newRow][newCol] == -1) {
                        queue.add(new Point(newRow, newCol));
                        distances[newRow][newCol] =
                                distances[current.getX()][current.getY()] + 1;

                        if (newRow == end.getX() && newCol == end.getY()) {
                            return reconstructPath(maze, end);
                        }
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private List<Point> reconstructPath(Maze maze, Point end) {
        List<Point> path = new ArrayList<>();
        Point current = end;
        path.add(current);

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (distances[current.getX()][current.getY()] != 0) {
            for (int[] direction : directions) {
                int newRow = current.getX() + direction[0];
                int newCol = current.getY() + direction[1];

                if (isValidPoint(maze, new Point(newRow, newCol)) &&
                        distances[newRow][newCol] ==
                                distances[current.getX()][current.getY()] - 1 &&
                        !isWall(maze, current, newRow, newCol, direction)) {

                    current = new Point(newRow, newCol);
                    path.add(current);
                    break;
                }
            }
        }
        Collections.reverse(path);
        return path;
    }

    private boolean isValidPoint(Maze maze, Point point) {
        return point.getX() >= 0 && point.getX() < maze.getRows() &&
                point.getY() >= 0 && point.getY() < maze.getCols();
    }

    private boolean isWall(Maze maze, Point current, int newRow, int newCol, int[] direction) {
        if (direction[0] == 1) {
            return maze.getWallAtBottom()[current.getX()][current.getY()] == 1;
        }
        if (direction[0] == -1) {
            return maze.getWallAtBottom()[newRow][newCol] == 1;
        }
        if (direction[1] == 1) {
            return maze.getWallAtRight()[current.getX()][current.getY()] == 1;
        }
        if (direction[1] == -1) {
            return maze.getWallAtRight()[newRow][newCol] == 1;
        }
        return false;
    }
}
