package edu.school.utils;

import edu.school.model.Maze;
import edu.school.model.Point;
import edu.school.service.path.finder.MazePathFinderService;
import edu.school.service.maze.generator.MazeGeneratorService;
import edu.school.service.file.FileReaderService;
import edu.school.service.file.FileWriterService;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class MazeUtils {

    public static final int WALL_THICKNESS = 2;

    public static Maze generateNewMaze(int rows, int cols) {
        MazeGeneratorService mazeGenerator = new MazeGeneratorService(rows, cols);
        return new Maze(rows, cols, mazeGenerator.getWallAtRight(), mazeGenerator.getWallAtBottom(), null);
    }

    public static Maze loadMazeFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл с лабиринтом");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        return FileReaderService.readFromFile(selectedFile);
    }

    public static void writeMazeInFile(Maze maze) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить лабиринт в файл");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        File selectedFile = fileChooser.showSaveDialog(null);
        FileWriterService.writeToFile(maze.getWallAtRight(), maze.getWallAtBottom(), selectedFile);
    }

    public static List<Point> findPath(Maze maze, Point start, Point end){
        MazePathFinderService mazePathFinderService = new MazePathFinderService();
        return mazePathFinderService.findPath(maze, start, end);
    }

    public static void drawMaze(Canvas canvas, Maze maze) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int rows = maze.getRows();
        int cols = maze.getCols();

        double cellSize = (canvas.getWidth() - WALL_THICKNESS) / (double) cols;

        // Минимальный размер ячейки
        if (cellSize < 10) {
            cellSize = 10;
            canvas.setWidth(cols * cellSize + WALL_THICKNESS);
            canvas.setHeight(rows * cellSize + WALL_THICKNESS);
        }


        // Вычисление смещения для центрирования лабиринта
        int xOffset = (int) (canvas.getWidth() - (cols * cellSize + WALL_THICKNESS)) / 2;
        int yOffset = (int) (canvas.getHeight() - (rows * cellSize + WALL_THICKNESS)) / 2;

        gc.setFill(Color.BLACK);

        // Рисуем внешние границы лабиринта с учетом смещения
        gc.fillRect(xOffset, yOffset, cols * cellSize + WALL_THICKNESS, WALL_THICKNESS);
        gc.fillRect(xOffset, yOffset + rows * cellSize, cols * cellSize + WALL_THICKNESS, WALL_THICKNESS);
        gc.fillRect(xOffset, yOffset, WALL_THICKNESS, rows * cellSize + WALL_THICKNESS);
        gc.fillRect(xOffset + cols * cellSize, yOffset, WALL_THICKNESS, rows * cellSize + WALL_THICKNESS);

        // Рисуем внутренние стены
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols - 1; j++) {
                if (maze.getWallAtRight()[i][j] == 1) {
                    gc.fillRect(xOffset + (j + 1) * cellSize, yOffset + i * cellSize, WALL_THICKNESS, cellSize);
                }
            }
        }

        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze.getWallAtBottom()[i][j] == 1) {
                    gc.fillRect(xOffset + j * cellSize, yOffset + (i + 1) * cellSize, cellSize, WALL_THICKNESS);
                }
            }
        }

        if (maze.getPath() != null) {
            drawPath(canvas, maze);
        }
    }


    public static void drawPath(Canvas canvas, Maze maze) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);

        int rows = maze.getRows();
        int cols = maze.getCols();

        double cellSize = (canvas.getWidth() - WALL_THICKNESS) / (double) cols;

        // Минимальный размер ячейки
        if (cellSize < 10) {
            cellSize = 10;
            canvas.setWidth(cols * cellSize + WALL_THICKNESS);
            canvas.setHeight(rows * cellSize + WALL_THICKNESS);
        }

        for (int i = 0; i < maze.getPath().size() - 1; i++) {
            Point from = maze.getPath().get(i);
            Point to = maze.getPath().get(i + 1);

            double x1 = from.getY() * cellSize + cellSize / 2;
            double y1 = from.getX() * cellSize + cellSize / 2;
            double x2 = to.getY() * cellSize + cellSize / 2;
            double y2 = to.getX() * cellSize + cellSize / 2;

            gc.strokeLine(x1, y1, x2, y2);
        }
    }

}