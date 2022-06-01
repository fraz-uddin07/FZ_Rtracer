import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SceneRenderer extends JPanel {

    BufferedImage bi;
    ImageIcon icon;

    SceneRenderer (int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        ImageIcon icon = new ImageIcon(bi);
        add(new JLabel(icon));

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = Color.cyan;
                int color_value = color.getRGB();
                bi.setRGB(x, y, color_value);
            }
        }
    }

    public void drawPixel (int x, int y, int red, int green, int blue) {
        Color pixelColor = new Color(red, green, blue);
        int color_value = pixelColor.getRGB();

        bi.setRGB(x, y, color_value);

    }

}