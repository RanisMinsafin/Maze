package edu.school.service.main;

import edu.school.model.Maze;
import edu.school.model.Point;
import edu.school.service.path.finder.MazePathFinderService;
import edu.school.utils.MazeUtils;
import javafx.scene.canvas.Canvas;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MazeService {
    private static MazeService instance;
    private Maze maze;
    private MazePathFinderService mazePathFinderService;

    private MazeService() {
    }

    public static MazeService getInstance() {
        if (instance == null) {
            instance = new MazeService();
        }
        return instance;
    }

    public void generateMaze(Integer rows, Integer cols) {
        if (rows <= 50 && cols <= 50) {
            this.maze = MazeUtils.generateNewMaze(rows, cols);
        }
    }

    public void loadMazeFromFile() {
        this.maze = MazeUtils.loadMazeFromFile();
    }

    public void writeMazeInFile() {
        MazeUtils.writeMazeInFile(this.maze);
    }

    public void drawMaze(Canvas canvas) {
        if (this.maze == null) {
            this.maze = MazeUtils.generateNewMaze(5, 5);
        }
        MazeUtils.drawMaze(canvas, this.maze);
    }

    public void drawPath(int startX, int startY, int endX, int endY) {
        if (this.maze != null) {
            List<Point> points = MazeUtils.findPath(
                    this.maze,
                    new Point(startX, startY),
                    new Point(endX, endY));
            this.maze.setPath(points);
        }
    }
}