package edu.school.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Maze {
    private int rows;
    private int cols;
    private int[][] wallAtRight;
    private int[][] wallAtBottom;
    private List<Point> path;
}
