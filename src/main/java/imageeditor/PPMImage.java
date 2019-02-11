package imageeditor;

public class PPMImage {
    private String type;
    private int width, height;
    private int maxVal;
    private Pixel[][] pixels;

    static class Pixel {
        int r, g, b;
        Pixel(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        Pixel() {
            r = 0;
            g = 0;
            b = 0;
        }

        int getR() {
            return r;
        }
        int getG() {
            return g;
        }
        int getB() {
            return b;
        }
    }

    /**
     * New Image
     * @param ppmType - P3
     * @param imageWidth - Image width
     * @param imageHeight - Image height
     * @param maxColorVal - max color value
     * @param imagePixels - pixels to image as 2d array of Pixels
     */
    PPMImage(String ppmType, int imageWidth, int imageHeight, int maxColorVal, Pixel[][] imagePixels) {
        this.type = ppmType;
        this.width = imageWidth;
        this.height = imageHeight;
        this.maxVal = maxColorVal;
        this.pixels = imagePixels;
    }

    PPMImage() {
        this.type = "P3";
        this.width = 0;
        this.height = 0;
        this.maxVal = 0;
        this.pixels = new Pixel[0][0];
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(int maxVal) {
        this.maxVal = maxVal;
    }

    public Pixel[][] getPixels() {
        return pixels;
    }

    public void setPixels(Pixel[][] pixels) {
        this.pixels = pixels;
    }
}
