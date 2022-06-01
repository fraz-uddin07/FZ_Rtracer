
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class WindowFrame extends JFrame {

    BufferedImage bi;
    JLabel label;

    WindowFrame (String title, int width, int height) {

        bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        ImageIcon icon = new ImageIcon(bi);

        label = new JLabel(icon);

        JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.add(label);
        frame.pack();

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
        int color = pixelColor.getRGB();
        bi.setRGB(x, y, color);
        label.repaint();

    }

}
