package by.bsu.dektiarev.util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10.04.2016.
 */
public class DDARasterizator implements LineRasterizator {

    @Override
    public List<Point> rasterize(Point pointA, Point pointB) {
        List<Point> pointList = new ArrayList<>();

        double deltaX = pointB.getX() - pointA.getX();
        double deltaY = pointB.getY() - pointA.getY();

        int stepsCount = (int) Math.max(Math.abs(deltaX), Math.abs(deltaY));

        double xInc = deltaX / (double) stepsCount;
        double yInc = deltaY / (double) stepsCount;

        double x = (int) pointA.getX();
        double y = (int) pointA.getY();
        for(int i = 0; i < stepsCount; i++) {
            pointList.add(new Point((int) x , (int) y));
            x += xInc;
            y += yInc;
        }
        pointList.add(pointB);
        return pointList;
    }
}
