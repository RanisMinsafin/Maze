package edu.school.controller;

import edu.school.service.main.MazeService;
import edu.school.utils.ControllerUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import static edu.school.utils.ControllerUtils.PATH_OPTIMAL_FXML;

public class MazeController {
    @FXML
    private Canvas mazeCanvas;

    @FXML
    private TextField widthField;
    @FXML
    private TextField heightField;

    private final MazeService mazeService = MazeService.getInstance();

    @FXML
    private void initialize() {
        mazeService.drawMaze(mazeCanvas);
    }

    @FXML
    private void handleGenerateNew() {
        mazeService.generateMaze(Integer.parseInt(widthField.getText()), Integer.parseInt(heightField.getText()));
        mazeService.drawMaze(mazeCanvas);
    }

    @FXML
    private void handleLoadFromFile() {
        mazeService.loadMazeFromFile();
        mazeService.drawMaze(mazeCanvas);
    }

    @FXML
    private void handleWriteToFile() {
        mazeService.writeMazeInFile();
    }

    @FXML
    private void handleShowOptimalPath(ActionEvent event) throws IOException {
        ControllerUtils.loadNewScene((Button) event.getSource(), PATH_OPTIMAL_FXML);
    }
}