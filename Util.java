
import java.io.FileWriter;
import java.io.IOException;

public class Util {

    public static final double INFINITY = Double.POSITIVE_INFINITY;
    public static final double pi = 3.1415926535897932385;

    public static double degreesToRadians (double degrees) {
        return degrees * pi / 180.0;
    }

    public static void writeColor (WindowFrame frame, FileWriter fileWriter, Vec3 pixelColor, int samples_per_pixel, int x, int y, int image_height) throws IOException {
        //Writes the translated value [0,255] of each color component
        double red = pixelColor.getX();
        double green = pixelColor.getY();
        double blue = pixelColor.getZ();

        double scale = 1.0 / samples_per_pixel;
        red = Math.sqrt(scale * red);
        green = Math.sqrt(scale * green);
        blue = Math.sqrt(scale * blue);

        int r = (int)(256 * Util.clamp(red, 0.0, 0.999));
        int g = (int)(256 * Util.clamp(green, 0.0, 0.999));
        int b = (int)(256 * Util.clamp(blue, 0.0, 0.999));

        frame.drawPixel(x, image_height - 1 - y, r, g, b);

        fileWriter.write(r + " " + g + " " + b + '\n');
    }

    public static void copy (Hit_Record hit1, Hit_Record hit2) {
        hit1.point = hit2.point;
        hit1.front_face = hit2.front_face;
        hit1.normal = hit2.normal;
        hit1.t = hit2.t;
        hit1.material_ref = hit2.material_ref;
    }

    public static double clamp (double x, double min, double max) {
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }

    public static double randomDouble () {
        return Math.random();
    }

    public static double randomDouble (double min, double max) {
        return min + (max - min) * Math.random();
    }

}