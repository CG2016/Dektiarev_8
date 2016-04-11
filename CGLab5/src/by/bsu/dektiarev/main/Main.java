package by.bsu.dektiarev.main;

import by.bsu.dektiarev.util.*;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.awt.*;
import java.util.List;
import java.util.Scanner;

/**
 * Created by USER on 10.04.2016.
 */
public class Main {

    public static void main(String[] args) {
        LineRasterizator rasterizator = new DDARasterizator();
        BresenhamCircleRasterisator bresenhamCircleRasterisator = new BresenhamCircleRasterisator();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter point A: ");
        String pointString = scanner.nextLine();
        String[] coord = pointString.split(" ");
        Point pointA = new Point(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));

        System.out.println("Enter point B: ");
        pointString = scanner.nextLine();
        coord = pointString.split(" ");
        Point pointB = new Point(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]));

        List<Point> pointList;
        if(pointA.getX() < pointB.getX()) {
            pointList = rasterizator.rasterize(pointA, pointB);
        }
        else {
            pointList = rasterizator.rasterize(pointB, pointA);
        }

        //pointList = bresenhamCircleRasterisator.rasterise(100);

        int[][] coords = CoordConverter.convert(pointList);

        XYChart chart = new XYChartBuilder().build();
        chart.addSeries("pixels", coords[0], coords[1]);
        new SwingWrapper<XYChart>(chart).displayChart();
    }
}
