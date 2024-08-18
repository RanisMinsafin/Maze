package edu.school.utils;

import edu.school.MazeApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

    public class ControllerUtils {

        public static final String PATH_OPTIMAL_FXML = "optimal_path.fxml";
        public static final String PATH_DEFAULT_FXML = "maze_layout.fxml";

        public static final String DEFAULT_TITLE = "Лабиринт минотавра";
        public static final String DOWNLOAD_FILE_TITLE = "Загрузить лабиринт из файла";
        public static final String OPTIMAL_PATH_TITLE = "Оптимальный путь";

        public static final String GENERATE_NEW_MAZE_BUTTON = "Сгенерировать новый";
        public static final String DOWNLOAD_FILE_BUTTON = "Загрузить из файла";
        public static final String GENERATE_OPTIMAL_PATH_BUTTON = "Показать оптимальный путь";

        public static final int DEFAULT_WINDOW_LENGTH = 650;
        public static final int DEFAULT_WINDOW_HEIGHT = 650;

        public static void loadNewScene(Button button, String fxmlFile) throws IOException {
            changeWindowTitle(button);

            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(MazeApplication.class.getResource(fxmlFile))
            );

            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(new Scene(root, DEFAULT_WINDOW_LENGTH, DEFAULT_WINDOW_HEIGHT));
        }

        private static void changeWindowTitle(Button button) {
            Stage stage = (Stage) button.getScene().getWindow();

            switch(button.getText()) {
                case GENERATE_OPTIMAL_PATH_BUTTON:
                    stage.setTitle(OPTIMAL_PATH_TITLE);
                    break;
                case GENERATE_NEW_MAZE_BUTTON:
                    stage.setTitle(DEFAULT_TITLE);
                    break;
                case DOWNLOAD_FILE_BUTTON:
                    stage.setTitle(DOWNLOAD_FILE_TITLE);
                    break;
            }
        }
    }