package edu.school.controller;

import edu.school.service.main.MazeService;
import edu.school.utils.ControllerUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import static edu.school.utils.ControllerUtils.PATH_DEFAULT_FXML;

public class OptimalPathController {
    @FXML
    private Canvas mazeCanvas;

    @FXML
    private TextField startX;
    @FXML
    private TextField startY;
    @FXML
    private TextField endX;
    @FXML
    private TextField endY;

    private final MazeService mazeService = MazeService.getInstance();

    @FXML
    private void initialize() {
        mazeService.drawMaze(mazeCanvas);
    }

    @FXML
    private void handleFindPath(ActionEvent event) throws IOException {
        mazeService.drawPath(
                Integer.parseInt(startX.getText()),
                Integer.parseInt(startY.getText()),
                Integer.parseInt(endX.getText()),
                Integer.parseInt(endY.getText())
        );
        ControllerUtils.loadNewScene((Button) event.getSource(), PATH_DEFAULT_FXML);
    }
}
