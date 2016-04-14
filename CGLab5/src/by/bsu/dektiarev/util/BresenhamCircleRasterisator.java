package by.bsu.dektiarev.util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10.04.2016.
 */
public class BresenhamCircleRasterisator {

    public List<Point> rasterise(int radius, int centerX, int centerY) {
        List<Point> pointList = new ArrayList<>();
        int x = 0;
        int y = radius;
        int delta = 1 - radius * 2;
        int error;
        while( y >= 0) {
            pointList.add(new Point(centerX + x, centerY + y));
            pointList.add(new Point(centerX + x, centerY -y));
            pointList.add(new Point(centerX -x, centerY + y));
            pointList.add(new Point(centerX -x, centerY -y));

            error = 2 * (delta + y) - 1;
            if((delta < 0) && (error <= 0)) {
                delta += 2 * ++x + 1;
                continue;
            }
            error = 2 * (delta - x) - 1;
            if((delta > 0) && (error >= 0)) {
                delta += 1 - 2 * --y;
                continue;
            }
            x++;
            delta += 2 * (x - y);
            y--;
        }
        return pointList;
    }
}
