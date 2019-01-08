package com.cs240.imageeditor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ImageLoader {
    enum PathType {RELATIVE, DIRECT}

    /**
     * This is the default
     *
     * @param pathToImage - relative path to the image
     * @return - The ppm version of the image
     */
    public static PPMImage loadImage(Path pathToImage) {
        return loadImage(pathToImage, PathType.RELATIVE);
    }

    /**
     * This is the more advanced loading function
     *
     * @param pathToImage - Path to image
     * @param pathType    - whether path is relative or direct
     * @return - returns the PPM image
     */
    private static PPMImage loadImage(Path pathToImage, PathType pathType) {
        System.out.println("Attempting to load image");
        try {
            InputStream inputStream = Files.newInputStream(pathToImage);
            Scanner scanner = new Scanner(inputStream); // Hopefully this will make it easier to read

            String ppmCheck = scanner.next();
            assert ppmCheck.equals("p3");
            System.out.println("Asserted ppmCheck == p3");

            int width = 0, heigth = 0;
            int maxVal = 0;
//            ArrayList<PPMImage.Pixel> pixels = new ArrayList<>();
            PPMImage.Pixel[][] pixels = new PPMImage.Pixel[0][0];

            int state = 0;

            outer:
            while (scanner.hasNext()) {
                String next = scanner.next();
                if (next.startsWith("#")) {
                    next = scanner.nextLine();
                    continue;
                }

                switch (state) {
                    case 0:
                        width = Integer.valueOf(next);
                        System.out.printf("Width: %d \n", width);
                        state = 1;
                        break;
                    case 1:
                        heigth = Integer.valueOf(next);
                        System.out.printf("Height: %d \n", heigth);
                        state = 2;
                        break;
                    case 2:
                        maxVal = Integer.valueOf(next);
                        System.out.printf("Max Val: %d \n", maxVal);
                        state = 3;
                        break;
                    case 3:
                        pixels = new PPMImage.Pixel[width][heigth];
                        int first = Integer.valueOf(next);
                        int second = Integer.valueOf(scanner.next());
                        int third = Integer.valueOf(scanner.next());
                        int count = 0;
                        for (int i = 0; i < width; i++) {
                            for (int j = 0; j < heigth; j++) {
                                // If it is the first one stick in that one we had to create before the loop
                                if (i == 0 && j == 0) {
                                    pixels[i][j] = new PPMImage.Pixel(first, second, third);
                                } else {
                                    if (scanner.hasNext()) { // double check we didnt run out of stuff to read
                                        int pixel_0 = Integer.valueOf(scanner.next());
                                        int pixel_1 = Integer.valueOf(scanner.next());
                                        int pixel_2 = Integer.valueOf(scanner.next());

                                        PPMImage.Pixel pixel = new PPMImage.Pixel(pixel_0, pixel_1, pixel_2);
                                        pixels[i][j] = pixel;
                                    }
                                }
                            }
                        }
                        break outer;
                    default:
                        break outer;
                }
            }

            return new PPMImage(ppmCheck, width, heigth, maxVal, pixels);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void saveImage(Path pathToImage, PPMImage image) {
        saveImage(pathToImage, PathType.RELATIVE, image);
    }

    private static void saveImage(Path pathToImage, PathType type, PPMImage image) {
        System.out.println("Attempting to save image");


        try {
            OutputStream outputStream = Files.newOutputStream(pathToImage);
            Writer writer = new OutputStreamWriter(outputStream);

            writer.write(image.getType() + '\n');
            writer.write(Integer.toString(image.getWidth()));
            writer.write(" ");
            writer.write(Integer.toString(image.getHeight()));
            writer.write("\n");
            writer.write(Integer.toString(image.getMaxVal()));
            writer.write("\n");

            PPMImage.Pixel[][] pixels = image.getPixels();

            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    writer.write(Integer.toString(pixels[i][j].getR()));
                    writer.write("\n");
                    writer.write(Integer.toString(pixels[i][j].getG()));
                    writer.write("\n");
                    writer.write(Integer.toString(pixels[i][j].getB()));
                    writer.write("\n");
                }
            }

            writer.close();
            System.out.println("Saved image :)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
