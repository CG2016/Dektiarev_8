package by.bsu.dektiarev.main;

import by.bsu.dektiarev.util.*;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.Square;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/**
 * Created by USER on 10.04.2016.
 */
public class Main {

    public static void main(String[] args) {
        LineRasterizator rasterizator = new BresenhamRasterizator();
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

        int radius = 100;
        pointList = bresenhamCircleRasterisator.rasterise(50, radius, radius);

        int[][] coords = CoordConverter.convert(pointList);

        try {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(new java.io.File("res.txt"))));
            FileMaker fileMaker = new FileMaker();
            int[][] points = fileMaker.makeFile(pointList, 200);
            for(int[] pointArr : points) {
                for(int point : pointArr) {
                    printWriter.print(point + " ");
                }
                printWriter.println();
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        XYChart chart = new XYChartBuilder().build();
        chart.addSeries("pixels", coords[0], coords[1]);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setSeriesMarkers(new Marker[] {new Square()});
        new SwingWrapper<XYChart>(chart).displayChart();
    }
}
