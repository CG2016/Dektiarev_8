package by.bsu.dektiarev.util;

import java.awt.*;
import java.util.List;

/**
 * Created by USER on 14.04.2016.
 */
public class FileMaker {

    public int[][] makeFile(List<Point> pointList, int dim) {
        int[][] points = new int[dim][dim];
        for(Point point : pointList) {
            points[dim - (int) point.getY() - 1][(int) point.getX()] = 1;
        }
        return points;
    }
}
