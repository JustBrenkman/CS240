package com.cs240.imageeditor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageEditor {

    enum ProcessType {INVERT, GRAYSCALE, EMBOSS, MOTION_BLUR}

    private ImageEditor() {}

    public static void main(String[] args) {
        System.out.println("Image Editor Alpha v0.1");

        ImageEditor imageEditor = new ImageEditor(); // Image editor will handle loading, processing and saving of images

        // if we have enough arguments then process an image
        if (args.length >= 3) {
            System.out.printf("Processing image: %s\n", args[0]);
            if (args[2].equals("invert")) {
                imageEditor.processImage(args[0], args[1], ProcessType.INVERT);
            } else if (args[2].equals("grayscale")) {
                imageEditor.processImage(args[0], args[1], ProcessType.GRAYSCALE);
            } else if (args[2].equals("emboss")) {
                imageEditor.processImage(args[0], args[1], ProcessType.EMBOSS);
            } else if (args[2].equals("motionblur")) {
                if (args.length > 3) {
                    imageEditor.processImage(args[0], args[1], ProcessType.MOTION_BLUR, Integer.valueOf(args[3]));
                } else {
                    System.out.println("Unable to perform motion blur, please specify length");
                }
            }
        } else {
            System.out.println("Unable to perform action: to few arguments");
        }
    }

    /**
     * Process image based on processType
     * @param pathToImage - Image path
     * @param pathToResult - Where to save the image
     * @param processType - Type of processing that will be applied to image
     * @param length - focal length for motion blur processing
     */
    private void processImage(String pathToImage, String pathToResult, ProcessType processType, int length) {
        // convert String path to object path
        switch (processType) {
            case INVERT:
                invertImage(Paths.get(pathToImage), Paths.get(pathToResult));
                break;
            case GRAYSCALE:
                grayScaleImage(Paths.get(pathToImage),Paths.get(pathToResult));
                break;
            case EMBOSS:
                embossImage(Paths.get(pathToImage),Paths.get(pathToResult));
                break;
            case MOTION_BLUR:
                motionBlurImage(Paths.get(pathToImage), Paths.get(pathToResult), 10);
                break;
        }
    }

    /**
     * Process the image, redefinition
     * @param pathToImage
     * @param pathToResult
     * @param processType
     */
    private void processImage(String pathToImage, String pathToResult, ProcessType processType) {
        processImage(pathToImage, pathToResult, processType, 10);
    }

    // These next functions will process the images as directed

    /**
     * Apply a motion blur to the image based on the focal length
     * @param pathToImage - Image path
     * @param pathToResult - Saved image path
     * @param length - focal length
     */
    private void motionBlurImage(Path pathToImage, Path pathToResult, int length) {

    }

    /**
     * Apply an emboss to the image
     * @param pathToImage - Image path
     * @param pathToResult - Saved image path
     */
    private void embossImage(Path pathToImage, Path pathToResult) {

    }

    /**
     * Convert image to gray scale
     * @param pathToImage - Image Path
     * @param pathToResult - Saved image path
     */
    private void grayScaleImage(Path pathToImage, Path pathToResult) {

    }

    /**
     * Invert the image colors
     * @param pathToImage - Image path
     * @param pathToSavedVersion - Saved image path
     */
    private void invertImage(Path pathToImage, Path pathToSavedVersion) {
        System.out.println("Process to perform: INVERT");
        PPMImage image = ImageLoader.loadImage(pathToImage);
        PPMImage invertedImage = Manipulator.invertImage(image);
        ImageLoader.saveImage(pathToSavedVersion, invertedImage);
    }

    /**
     * Test function for paths
     * @param args
     */
    private static void testPaths(String[] args) {
        if (args.length > 0) {
            try {
                System.out.println("Opening file.");
                OutputStream out = Files.newOutputStream(Paths.get(args[0]));
                System.out.println("Writing to file...");
                out.write(2);
                out.write(3);
                out.write(4);
                out.write(5);
                System.out.println("Closing file");
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
