package com.cs240.imageeditor;

public class Manipulator {
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
}
