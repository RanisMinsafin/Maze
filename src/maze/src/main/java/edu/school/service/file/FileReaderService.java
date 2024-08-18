package edu.school.service.file;

import edu.school.model.Maze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderService {
    public static Maze readFromFile(File file) {
        Maze maze = new Maze();

        if (file == null) {
            return maze;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String[] dimensions = reader.readLine().split(" ");
            maze.setRows(Integer.parseInt(dimensions[0]));
            maze.setCols(Integer.parseInt(dimensions[1]));

            if (maze.getRows() > 50 || maze.getCols() > 50) {
                throw new RuntimeException("Недопустимые размеры лабиринта!");
            }

            int[][] rightWalls = readWalls(reader, maze.getRows(), maze.getCols());
            reader.readLine();
            int[][] bottomWalls = readWalls(reader, maze.getRows(), maze.getCols());

            maze.setWallAtRight(rightWalls);
            maze.setWallAtBottom(bottomWalls);
        } catch (IOException e) {
            throw new RuntimeException("Некорректное имя файла");
        }
        return maze;
    }

    private static int[][] readWalls(BufferedReader reader, int rows, int cols) throws IOException {
        int[][] walls = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            String line = reader.readLine().replace(" ", "");
            for (int j = 0; j < cols; j++) {
                walls[i][j] = Character.getNumericValue(line.charAt(j));
            }
        }
        return walls;
    }
}
