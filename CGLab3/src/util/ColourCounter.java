package util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Created by USER on 18.03.2016.
 */
public class ColourCounter {

    private BufferedImage image;
    private int height;
    private int width;

    public ColourCounter(BufferedImage image) {
        this.image = image;
        this.height = image.getHeight();
        this.width = image.getWidth();
    }

    public List<Map<Integer, Integer>> getValues() {
        List<Map<Integer, Integer>> mapList = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            mapList.add(new TreeMap<>());
        }

        int averageRed = 0;
        int averageGreen = 0;
        int averageBlue = 0;

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Color color = new Color(image.getRGB(x, y));
                int rValue = color.getRed();
                int gValue = color.getGreen();
                int bValue = color.getBlue();

                averageRed += rValue;
                averageGreen += gValue;
                averageBlue += bValue;

                if(mapList.get(0).containsKey(rValue)) {
                    mapList.get(0).put(rValue, mapList.get(0).get(rValue) + 1);
                }
                else {
                    mapList.get(0).put(rValue, 1);
                }
                if(mapList.get(1).containsKey(gValue)) {
                    mapList.get(1).put(gValue, mapList.get(1).get(gValue) + 1);
                }
                else {
                    mapList.get(1).put(gValue, 1);
                }
                if(mapList.get(2).containsKey(bValue)) {
                    mapList.get(2).put(bValue, mapList.get(2).get(bValue) + 1);
                }
                else {
                    mapList.get(2).put(bValue, 1);
                }
            }
        }

        int pixelsCount = image.getHeight() * image.getWidth();
        averageRed /= pixelsCount;
        averageGreen /= pixelsCount;
        averageBlue /= pixelsCount;
        mapList.get(3).put(0, averageRed);
        mapList.get(3).put(1, averageGreen);
        mapList.get(3).put(2, averageBlue);

        return mapList;
    }
}
