package by.bsu.dektiarev.util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10.04.2016.
 */
public class StepByStepRasterizator implements LineRasterizator {

    @Override
    public List<Point> rasterize(Point pointA, Point pointB) {
        List<Point> points = new ArrayList<>();

        double k = (pointB.getY() - pointA.getY()) / (pointB.getX() - pointA.getX());
        double b = pointA.getY() - pointA.getX() * k;

        points.add(pointA);
        for(int x = (int) pointA.getX(); x < pointB.getX(); x++) {
            int y = (int) (k * x + b);
            points.add(new Point(x, y));
        }
        points.add(pointB);

        return points;
    }
}
