package by.bsu.dektiarev.util;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10.04.2016.
 */
public class BresenhamRasterizator implements LineRasterizator {

    @Override
    public List<Point> rasterize(Point pointA, Point pointB) {
        List<Point> pointList = new ArrayList<>();
        int deltaX = (int) Math.abs(pointB.getX() - pointA.getX());
        int deltaY = (int) Math.abs(pointB.getY() - pointA.getY());
        int signY = pointA.getY() < pointB.getY() ? 1 : -1;
        int error = deltaX - deltaY;

        int x = (int) pointA.getX();
        int y = (int) pointA.getY();
        while (x < pointB.getX() || y < pointB.getY()) {
            pointList.add(new Point(x, y));
            if(error * 2 > -deltaY) {
                error -= deltaY;
                x++;
            }
            if(error * 2 < deltaX) {
                error += deltaX;
                y += signY;
            }
        }
        pointList.add(pointB);
        return pointList;
    }
}
