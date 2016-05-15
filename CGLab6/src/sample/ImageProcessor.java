package sample;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by USER on 14.05.2016.
 */
public class ImageProcessor {

    public static BufferedImage filter(BufferedImage image, FilterType filterType, int param) {
        int[][] matrix = new int[3][3];
        int w;
        if(filterType == FilterType.MEDIAN) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if(i == 1 && j == 1) {
                        matrix[i][j] = param;
                    }
                    else {
                        matrix[i][j] = 1;
                    }
                }
            }
            w = 8 + param;
        }
        else {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    matrix[i][j] = 1;
                }
            }
            matrix[1][1] = (int) Math.pow(param, 2);
            matrix[0][1] = param;
            matrix[1][0] = param;
            matrix[2][1] = param;
            matrix[1][2] = param;
            w = (int) Math.pow(2 + param, 2);
        }

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage resultImage = new BufferedImage(image.getColorModel(), image.copyData(null),
                image.isAlphaPremultiplied(), null);

        for(int i = 1; i < width - 1; i++) {
            for(int j = 1; j < height - 1; j++) {
                int color = 0;
                for(int row = 0; row < 3; row++) {
                    for(int col = 0; col < 3; col++) {
                        color += new Color(image.getRGB(i + row - 1, j + col - 1)).getRed() * matrix[row][col];
                    }
                }
                color /= w;
                resultImage.setRGB(i, j, new Color(color, color, color).getRGB());
            }
        }

        return resultImage;
    }

    public static BufferedImage dilate(BufferedImage image) {

        BufferedImage resultImage = new BufferedImage(image.getColorModel(), image.copyData(null),
                image.isAlphaPremultiplied(), null);

        int width = image.getWidth();
        int height = image.getHeight();
        int[][] mask = new int[width][height];
        for(int i = 0; i < mask.length; i++) {
            for(int j = 0; j < mask[i].length; j++) {
                int color = new Color(image.getRGB(i, j)).getRed();
                if(color == 0) {
                    mask[i][j] = 1;
                }
                else if(color == 255) {
                    mask[i][j] = 0;
                }
                else {
                    mask[i][j] = -1;
                }
            }
        }

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(mask[i][j] == 1) {
                    if(i > 0 && mask[i - 1][j] == 0) {
                        mask[i - 1][j] = 2;
                    }
                    if(j > 0 && mask[i][j - 1] == 0) {
                        mask[i][j - 1] = 2;
                    }
                    if(i + 1 < width && mask[i + 1][j] == 0) {
                        mask[i + 1][j] = 2;
                    }
                    if(j + 1 < height && mask[i][j + 1] == 0) {
                        mask[i][j + 1] = 2;
                    }
                }
            }
        }

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(mask[i][j] == 2) {
                    mask[i][j] = 1;
                }
                if(mask[i][j] == 0) {
                    resultImage.setRGB(i, j, new Color(255, 255, 255).getRGB());
                }
                if(mask[i][j] == 1) {
                    resultImage.setRGB(i, j, new Color(0, 0, 0).getRGB());
                }
            }
        }

        return resultImage;
    }

    public static BufferedImage erose(BufferedImage image) {

        BufferedImage resultImage = new BufferedImage(image.getColorModel(), image.copyData(null),
                image.isAlphaPremultiplied(), null);

        int width = image.getWidth();
        int height = image.getHeight();
        int[][] mask = new int[width][height];
        for(int i = 0; i < mask.length; i++) {
            for(int j = 0; j < mask[i].length; j++) {
                int color = new Color(image.getRGB(i, j)).getRed();
                if(color == 0) {
                    mask[i][j] = 1;
                }
                else if(color == 255) {
                    mask[i][j] = 0;
                }
                else {
                    mask[i][j] = -1;
                }
            }
        }

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(mask[i][j] == 0) {
                    if(i > 0 && mask[i - 1][j] == 1) {
                        mask[i - 1][j] = 2;
                    }
                    if(j > 0 && mask[i][j - 1] == 1) {
                        mask[i][j - 1] = 2;
                    }
                    if(i + 1 < width && mask[i + 1][j] == 1) {
                        mask[i + 1][j] = 2;
                    }
                    if(j + 1 < height && mask[i][j + 1] == 1) {
                        mask[i][j + 1] = 2;
                    }
                }
            }
        }

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(mask[i][j] == 2) {
                    mask[i][j] = 0;
                }
                if(mask[i][j] == 0) {
                    resultImage.setRGB(i, j, new Color(255, 255, 255).getRGB());
                }
                if(mask[i][j] == 1) {
                    resultImage.setRGB(i, j, new Color(0, 0, 0).getRGB());
                }
            }
        }

        return resultImage;
    }
}
