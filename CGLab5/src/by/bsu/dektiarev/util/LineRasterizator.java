package by.bsu.dektiarev.util;

import java.awt.*;
import java.util.List;

/**
 * Created by USER on 10.04.2016.
 */
public interface LineRasterizator {

    List<Point> rasterize(Point pointA, Point pointB);
}
