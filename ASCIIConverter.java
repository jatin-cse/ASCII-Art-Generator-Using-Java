import java.awt.image.BufferedImage;

public class ASCIIConverter {

    private final char[] symbols = {
            '@', '#', 'S', '%', '?', '*', '+', ';', ':', ',', '.'
    };

    public String convertToASCII(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        int newWidth = 120;
        int newHeight = (height * newWidth) / (width * 2);

        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {

                int px = x * width / newWidth;
                int py = y * height / newHeight;

                int pixel = image.getRGB(px, py);

                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;

                int gray = (r + g + b) / 3;

                int index = gray * (symbols.length - 1) / 255;

                sb.append(symbols[index]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}