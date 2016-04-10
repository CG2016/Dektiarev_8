package by.bsu.dektiarev;

import org.apache.commons.imaging.ImageReadException;

import java.io.IOException;

/**
 * Created by USER on 01.04.2016.
 */
public class Main {

    public static void main(String[] args) {
        ImageProcessor imageProcessor = new ImageProcessor();
        try {
            imageProcessor.processImages();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ImageReadException e) {
            e.printStackTrace();
        }
    }
}
