package com.cs240.imageeditor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Manipulator {
    private String happy;

    public String getHappy() {
        return happy;
    }

    public void setHappy(String happy) {
        this.happy = happy;
    }

    public static PPMImage invertImage(PPMImage imageToInvert) {
        System.out.println("Inverting and creating new image");
        PPMImage.Pixel[][] pixels = new PPMImage.Pixel[imageToInvert.getWidth()][imageToInvert.getHeight()];
        for (int i = 0; i < imageToInvert.getHeight(); i++) {
            for (int j = 0; j < imageToInvert.getWidth(); j++) {
                pixels[j][i] = new PPMImage.Pixel(
                        (imageToInvert.getMaxVal() - imageToInvert.getPixels()[j][i].getR()),
                        (imageToInvert.getMaxVal() - imageToInvert.getPixels()[j][i].getG()),
                        (imageToInvert.getMaxVal() - imageToInvert.getPixels()[j][i].getB())
                );
            }
        }
        return new PPMImage(imageToInvert.getType(), imageToInvert.getWidth(), imageToInvert.getHeight(), imageToInvert.getMaxVal(), pixels);
    }

    public static PPMImage convertToGrayScale(PPMImage imageToInvert) {
        System.out.println("creating new image and converting to gray scale");
        PPMImage.Pixel[][] pixels = new PPMImage.Pixel[imageToInvert.getWidth()][imageToInvert.getHeight()];
        for (int i = 0; i < imageToInvert.getHeight(); i++) {
            for (int j = 0; j < imageToInvert.getWidth(); j++) {
                int rVal = imageToInvert.getPixels()[j][i].getR();
                int gVal = imageToInvert.getPixels()[j][i].getG();
                int bVal = imageToInvert.getPixels()[j][i].getB();

                int gray = (rVal + gVal + bVal) / 3;
                pixels[j][i] = new PPMImage.Pixel(gray, gray, gray);
            }
        }
        return new PPMImage(imageToInvert.getType(), imageToInvert.getWidth(), imageToInvert.getHeight(), imageToInvert.getMaxVal(), pixels);
    }

    static PPMImage embossImage(PPMImage imageToInvert) {
        System.out.println("Embossing and creating new image");
        PPMImage.Pixel[][] pixels = new PPMImage.Pixel[imageToInvert.getWidth()][imageToInvert.getHeight()];
        PPMImage.Pixel[][] imagePixels = imageToInvert.getPixels();
        for (int i = 0; i < imageToInvert.getWidth(); i++) {
            for (int j = 0; j < imageToInvert.getHeight(); j++) {
                int v;
                if (j == 0 || i == 0) {
                    v = 128;
                } else {
                    int compRed = imagePixels[i][j].getR();
                    int compGreen = imagePixels[i][j].getG();
                    int compBlue = imagePixels[i][j].getB();
                    int redDiff = compRed - imagePixels[i - 1][j - 1].getR();
                    int greenDiff = compGreen - imagePixels[i - 1][j - 1].getG();
                    int blueDiff = compBlue - imagePixels[i - 1][j - 1].getB();

                    v = maxIgnoreSign(maxIgnoreSign(redDiff, greenDiff), blueDiff);
                    v += 128;
                }

                // Make sure that it is in the proper place
                if (v < 0) {
                    v = 0;
                } else if (v > 255) {
                    v = 255;
                }

                pixels[i][j] = new PPMImage.Pixel(v, v, v);
            }
        }
        return new PPMImage(imageToInvert.getType(), imageToInvert.getWidth(), imageToInvert.getHeight(), imageToInvert.getMaxVal(), pixels);
    }

    private static int maxIgnoreSign(int a, int b) {
        return (Math.abs(a) >= Math.abs(b)? a : b);
    }
}
