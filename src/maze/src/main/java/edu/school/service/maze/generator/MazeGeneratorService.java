package edu.school.service.maze.generator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MazeGeneratorService {
    private int rows;
    private int cols;
    private int[][] wallAtRight;
    private int[][] wallAtBottom;
    private int[][] matrixSet;

    final boolean ROW_BOUND = true;
    final boolean COL_BOUND = false;

    public MazeGeneratorService(int rows, int cols) {
        matrixSet = new int[rows][cols];
        wallAtRight = new int[rows][cols];
        wallAtBottom = new int[rows][cols];
        this.rows = rows;
        this.cols = cols;
        mazeGenerator();
    }

    public void mazeGenerator() {
        fillFirstLine();
        for (int row = 0; row < rows; row++) {
            processLineWithRightWall(row);
            processLineWithBottomWall(row);
            if (row == rows - 1) {
                fillEndLine(row);
            } else {
                fillNewLine(row);
            }
        }
    }

    private void fillFirstLine() {
        for (int col = 0; col < cols; col++) {
            matrixSet[0][col] = col + 1;
        }
    }

    /**
     * Заполнение новой строки
     * 1. Взять за основу текущую строку
     * 2. Удалите все правые границы
     * 3. Удалите ячейки с нижней границей из их множества
     * 4. Удалите все нижние границы
     *
     * @param row номер строки
     */
    private void fillNewLine(int row) {
        for (int col = 0; col < cols; col++) {
            if (checkRightIndexBounds(row, ROW_BOUND) && wallAtBottom[row][col] == 1) {
                matrixSet[row + 1][col] = getUniqueSet(row + 1);
            } else {
                matrixSet[row + 1][col] = matrixSet[row][col];
            }
        }
    }

    /**
     * Заполнить финальную строку
     * 1. Если текущая ячейка и ячейка справа члены разных множеств, то:
     * 1.1 Удалить правую границу
     * 1.2 Объединить множества текущей ячейки и ячейки справа
     *
     * @param row номер строки
     */
    private void fillEndLine(int row) {
        for (int col = 0; col < cols; col++) {
            if (checkRightIndexBounds(col, COL_BOUND) && matrixSet[row][col] != matrixSet[row][col + 1]) {
                wallAtRight[row][col] = 0;
                unionSet(row, col);
            }
            wallAtBottom[row][col] = 1;
        }
    }

    private void processLineWithRightWall(int row) {
        for (int col = 0; col < cols; col++) {
            if (randomGenerateWall()) {
                wallAtRight[row][col] = 1;
            } else {
                if (checkRightIndexBounds(col, COL_BOUND) && matrixSet[row][col] == matrixSet[row][col + 1]) {
                    // Если текущая ячейка и ячейка справа принадлежат одному множеству,
                    // то создаём границу между ними (для предотвращения зацикливаний)
                    wallAtRight[row][col] = 1;
                } else if (checkRightIndexBounds(col, COL_BOUND)) {
                    // Если решили не добавлять границу, то объедините два множества
                    // в которых находится текущая ячейка и ячейка справа.
                    unionSet(row, col);
                }
            }
        }
    }

    private void unionSet(int row, int col) {
        int newSet = matrixSet[row][col];
        int oldSet = matrixSet[row][col + 1];

        for (int i = 0; i < cols; i++) {
            if (matrixSet[row][i] == oldSet) {
                matrixSet[row][i] = newSet;
            }
        }
    }

    /**
     * Рассчёт нижней границы:
     * 1. Если ячейка в своем множестве одна, то не создавайте границу снизу
     * 2. Если ячейка одна в своем множестве без нижней границы, то не создавайте нижнюю границу
     *
     * @param row
     */
    private void processLineWithBottomWall(int row) {
        for (int col = 0; col < cols; col++) {
            if (randomGenerateWall()) {
                // Если ячейка в своем множестве не одна, а также не одна в своем множестве без нижней границы
                wallAtBottom[row][col] = isNotAloneCellWithoutWall(col, row);
            }
        }
    }

    private int isNotAloneCellWithoutWall(int col, int row) {
        for (int i = 0; i < cols; i++) {
            if (i != col && matrixSet[row][i] == matrixSet[row][col] && wallAtBottom[row][i] == 0) {
                return 1;
            }
        }
        return 0;
    }

    private boolean randomGenerateWall() {
        return Math.round(Math.random()) == 1;
    }

    private int getUniqueSet(int row) {
        int uniqueSet = 0;

        for (int i = 0; i <= row; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrixSet[i][j] >= uniqueSet) {
                    uniqueSet = matrixSet[i][j];
                }
            }
        }
        return ++uniqueSet;
    }

    private boolean checkRightIndexBounds(int index, boolean type) {
        if (type) {
            return index < rows - 1;
        } else {
            return index < cols - 1;
        }
    }
}
