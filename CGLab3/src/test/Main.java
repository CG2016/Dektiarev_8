package test;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.lang3.ArrayUtils;
import org.knowm.xchart.ChartBuilder_Category;
import org.knowm.xchart.Chart_Category;
import org.knowm.xchart.SwingWrapper;
import util.ColourCounter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by USER on 18.03.2016.
 */
public class Main {

    public static void main(String[] args) {
        /*FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.png", "*.bmp"));
        fileChooser.setTitle("Open image");
        File imageFile = fileChooser.showOpenDialog(new Stage());*/

        File file = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        chooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "bmp", "pcx", "gif", "png", "tif"));
        int result = chooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }
        else if(result == JFileChooser.CANCEL_OPTION) {
            System.exit(0);
        }

        try {

            BufferedImage image = ImageIO.read(file);

            if (image == null) {
                image = Imaging.getBufferedImage(file);
            }

            ColourCounter colourCounter = new ColourCounter(image);

            List<Map<Integer, Integer>> mapList = colourCounter.getValues();

            /*rMap.keySet().stream().forEach(key -> {
                System.out.println(key + " " + rMap.get(key));
            });*/

            List<Chart_Category> chartList = new ArrayList<>();
            chartList.add(new ChartBuilder_Category().xAxisTitle("Value").yAxisTitle("Pixels Count").build());
            chartList.add(new ChartBuilder_Category().xAxisTitle("Value").yAxisTitle("Pixels Count").build());
            chartList.add(new ChartBuilder_Category().xAxisTitle("Value").yAxisTitle("Pixels Count").build());
            chartList.add(new ChartBuilder_Category().xAxisTitle("Color").yAxisTitle("Average Value").build());

            chartList.get(0).addSeries("RED",
                    ArrayUtils.toPrimitive(mapList.get(0).keySet().toArray(new Integer[0])),
                    ArrayUtils.toPrimitive(mapList.get(0).values().toArray(new Integer[0]))).setFillColor(Color.RED);

            chartList.get(1).addSeries("GREEN",
                    ArrayUtils.toPrimitive(mapList.get(1).keySet().toArray(new Integer[0])),
                    ArrayUtils.toPrimitive(mapList.get(1).values().toArray(new Integer[0]))).setFillColor(Color.GREEN);

            chartList.get(2).addSeries("BLUE",
                    ArrayUtils.toPrimitive(mapList.get(2).keySet().toArray(new Integer[0])),
                    ArrayUtils.toPrimitive(mapList.get(2).values().toArray(new Integer[0]))).setFillColor(Color.BLUE);

            chartList.get(3).addSeries("RED", new double[] {0},
                    new double[] {mapList.get(3).get(0)}).setFillColor(Color.RED);
            chartList.get(3).addSeries("GREEN", new double[] {1},
                    new double[] {mapList.get(3).get(1)}).setFillColor(Color.GREEN);
            chartList.get(3).addSeries("BLUE", new double[] {2},
                    new double[] {mapList.get(3).get(2)}).setFillColor(Color.BLUE);

            chartList.get(0).getStyler().setXAxisMin(0);
            chartList.get(0).getStyler().setXAxisMax(255);
            chartList.get(1).getStyler().setXAxisMin(0);
            chartList.get(1).getStyler().setXAxisMax(255);
            chartList.get(2).getStyler().setXAxisMin(0);
            chartList.get(2).getStyler().setXAxisMax(255);

            chartList.get(3).getStyler().setYAxisMax(255);

            new SwingWrapper(chartList).displayChartMatrix(file.getName());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ImageReadException e) {
            e.printStackTrace();
        }
    }
}
