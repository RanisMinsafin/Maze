package edu.school.service.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterService {

    public static void writeToFile(int[][] rightWalls, int[][] bottomWalls, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            int rows = rightWalls.length;
            int cols = rightWalls[0].length;

            writer.write(rows + " " + cols);
            writer.newLine();

            writeWalls(writer, rightWalls);
            writer.newLine();
            writeWalls(writer, bottomWalls);
        } catch (IOException e) {
            throw new RuntimeException("Некорректное имя файла");
        }
    }

    private static void writeWalls(BufferedWriter writer, int[][] walls) throws IOException {
        for (int[] row : walls) {
            for (int cell : row) {
                writer.write(Integer.toString(cell));
            }
            writer.newLine();
        }
    }
}
