package by.bsu.dektiarev.util;

import java.awt.*;
import java.util.List;

/**
 * Created by USER on 10.04.2016.
 */
public class CoordConverter {

    public static int[][] convert(List<Point> points) {
        int[][] coords = new int[2][points.size()];
        for(int i = 0; i < points.size(); i++) {
            coords[0][i] = (int) points.get(i).getX();
            coords[1][i] = (int) points.get(i).getY();
        }

        return coords;
    }
}
