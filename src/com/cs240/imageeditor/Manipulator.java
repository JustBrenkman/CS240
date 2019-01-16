package com.cs240.imageeditor;

class Manipulator {

    /**
     * Inverts image colors
     *
     * @param imageToInvert - original
     * @return - new image with effect applied
     */
    static PPMImage invertImage(PPMImage imageToInvert) {
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

    /**
     * Converts image to grayscale
     *
     * @param imageToInvert - original
     * @return - new image with effect applied
     */
    static PPMImage convertToGrayScale(PPMImage imageToInvert) {
        System.out.println("Creating new image and converting to gray scale");
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

    /**
     * Embosses image
     *
     * @param imageToInvert - original
     * @return - new image with effect applied
     */
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

    /**
     * Help with embossing, returns largest value ignoring sign
     *
     * @param a - integer to compare
     * @param b - integer to compare
     * @return - returns largest integer value with its occupying sign
     */
    private static int maxIgnoreSign(int a, int b) {
        return (Math.abs(a) >= Math.abs(b) ? a : b);
    }

    /**
     * Applies a motion blur effect to the image based on the length i.e. range of pixels to average
     *
     * @param imageToConvert - original
     * @param length         - pixel length away to average
     * @return - new image with effect applied
     */
    static PPMImage blurImage(PPMImage imageToConvert, int length) {
        PPMImage.Pixel[][] pixels = new PPMImage.Pixel[imageToConvert.getWidth()][imageToConvert.getHeight()];
        for (int i = 0; i < imageToConvert.getHeight(); i++) {
            for (int j = 0; j < imageToConvert.getWidth(); j++) {
                int averageR = 0, averageG = 0, averageB = 0;
                for (int k = 0; k < ((j <= (imageToConvert.getWidth() - length)) ? length : (imageToConvert.getWidth() - j)); k++) {
                    averageR += imageToConvert.getPixels()[j + k][i].getR();
                    averageG += imageToConvert.getPixels()[j + k][i].getG();
                    averageB += imageToConvert.getPixels()[j + k][i].getB();
                }
                pixels[j][i] = new PPMImage.Pixel(
                        (averageR / ((j <= (imageToConvert.getWidth() - length)) ? length : (imageToConvert.getWidth() - j))),
                        (averageG / ((j <= (imageToConvert.getWidth() - length)) ? length : (imageToConvert.getWidth() - j))),
                        (averageB / ((j <= (imageToConvert.getWidth() - length)) ? length : (imageToConvert.getWidth() - j))));
            }
        }
        return new PPMImage(imageToConvert.getType(), imageToConvert.getWidth(), imageToConvert.getHeight(), imageToConvert.getMaxVal(), pixels);
    }
}
