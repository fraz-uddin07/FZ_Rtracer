
import java.io.FileWriter;
import java.io.IOException;

public class tester {

//    public static double hitSphere(Vec3 center, double radius, Ray ray) {
//        Vec3 oc = Vec3.subtract(ray.getOrigin(), center);
//        double a = ray.getDirection().lengthSquared();
//        double half_b = Vec3.dot(ray.getDirection(), oc);
//        double c = oc.lengthSquared() - radius * radius;
//        double discriminant = (half_b * half_b) - (a * c);
//
//        if (discriminant < 0) {
//            return -1.0;
//        }
//        else {
//            return (-half_b - Math.sqrt(discriminant)) / a;
//        }
//    }


    public static Vec3 rayColor (Ray ray, Hittable world, int depth) {

        Hit_Record rec = new Hit_Record();

        if (depth <= 0) {
            return new Vec3(0, 0, 0);
        }

        if (world.hit(ray, 0.001, Util.INFINITY, rec)) {
            Ray scattered = new Ray();
            Vec3 attenuation = new Vec3();

            if (rec.material_ref.scatter(ray, rec, attenuation, scattered)) {
                return Vec3.multiply(attenuation, rayColor(scattered, world, depth - 1));
            }
            return new Vec3(0, 0, 0);

//            Vec3 target = new Vec3(Vec3.add(rec.point, Vec3.random_in_hemisphere(rec.normal)));
////            return Vec3.multiply(Vec3.add(rec.normal, new Vec3(1, 1, 1)), 0.5);
//            return Vec3.multiply(rayColor(new Ray(rec.point, Vec3.subtract(target, rec.point)), world, depth - 1), 0.5);
        }

        Vec3 unitDirection = Vec3.unitVector(ray.getDirection());
        double t = 0.5 * (unitDirection.getY() + 1.0);

        //Lerping form left to right(background)
        return Vec3.add(Vec3.multiply(new Vec3(1.0, 1.0, 1.0), 1.0 - t), Vec3.multiply(new Vec3(0.5, 0.7, 1.0), t));
    }

    public static Hittable_List generateRandomScene () {
        Hittable_List world = new Hittable_List();

        Lambertian ground_material = new Lambertian(new Vec3(0.5, 0.5, 0.5));
        world.add (new Sphere(new Vec3(0, -1000, 0), 1000, ground_material));

        for (int a = -7; a < 5; a++)  {
            for (int b = -9; b < 9; b++) {
                double choose_mat = Util.randomDouble();
                Vec3 center = new Vec3(a + 0.9 * Util.randomDouble(), 0.2, b + 0.9 * Util.randomDouble());

                if (Vec3.subtract(center, new Vec3(4, 0.2, 0)).length() > 0.9) {
                    Material sphere_material; // ?

                    if (choose_mat < 0.8) {
                        //Diffuse Material
                        Vec3 albedo = Vec3.multiply(Vec3.random(), Vec3.random());
                        sphere_material = new Lambertian(albedo);
                        world.add (new Sphere(center, 0.2, sphere_material));
                    }
                    else if (choose_mat < 0.95) {
                        //Metal
                        Vec3 albedo = Vec3.random(0.5, 1);
                        double fuzziness = Util.randomDouble(0, 0.5);
                        sphere_material = new Metal (albedo, fuzziness);
                        world.add (new Sphere(center, 0.2, sphere_material));
                    }
                    else {
                        //Glass
                        sphere_material = new Dielectric (1.5);
                        world.add (new Sphere(center, 0.2, sphere_material));
                    }
                }
            }
        }

        Dielectric material1 = new Dielectric (1.5);
        world.add(new Sphere(new Vec3(0, 1, 0), 1.0, material1));

        Lambertian material2 = new Lambertian (new Vec3(0.4, 0.2, 1));
        world.add (new Sphere (new Vec3(-4, 1, 0), 1.0, material2));

        Metal material3 = new Metal(new Vec3(0.7, 0.6, 0.5), 0.0);
        world.add (new Sphere (new Vec3(4, 1, 0), 1.0, material3));

        return world;

    }




    public static void main (String[] args) throws IOException {

        //Dimensions for the Image
        final double aspect_ratio = 3.0 / 2.0;
        final int image_width = 640;
        final int image_height = (int)(image_width / aspect_ratio);

        //Performance and Image quality parameters
        final int samples_per_pixel = 150;
        final int max_depth = 50;

        //World
        Hittable_List world = generateRandomScene();

//        Lambertian material_ground = new Lambertian(new Vec3 (0.8, 0.8, 0.0));
//        Lambertian material_center = new Lambertian(new Vec3 (0.1, 0.2, 0.5));
////        Metal material_left = new Metal(new Vec3 (0.8, 0.8, 0.8), 0.3);
////        Dielectric material_center = new Dielectric(1.5);
//        Dielectric material_left = new Dielectric(1.5);
//
//        Metal material_right = new Metal(new Vec3 (0.8, 0.6, 0.2), 0.0);
//
//        world.add(new Sphere(new Vec3(0.0, -100.5, -1.0), 100, material_ground));
//        world.add(new Sphere(new Vec3(0.0, 0.0, -1.0), 0.5, material_center));
//        world.add(new Sphere(new Vec3(-1.0, 0.0, -1.0), 0.5, material_left));
//        world.add(new Sphere(new Vec3(-1.0, 0.0, -1.0), -0.4, material_left));
//        world.add(new Sphere(new Vec3(1.0, 0.0, -1.0), 0.5, material_right));

        //Camera
        Vec3 look_from = new Vec3 (13, 2, 3);
        Vec3 look_at = new Vec3 (0, 0, 0);
        Vec3 vup = new Vec3(0, 1, 0);
//        double dist_to_focus = Vec3.subtract(look_from, look_at).length();
        double dist_to_focus = 10.0;
        double aperture = 0.1;
        Camera cam = new Camera(look_from, look_at, vup, 20.0, aspect_ratio, aperture, dist_to_focus);

        //Create a FileWriter Class
        FileWriter fileWriter = null;
        Util util = new Util();
        try {
            fileWriter = new FileWriter("image.ppm");
        } catch (IOException e) {
            System.out.println("Unable to open file...EXITING");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        //Create window/renderer instance
        WindowFrame frame = new WindowFrame("FZ_Rtrace", image_width, image_height);


        //Render
        System.out.println("P3\n" + image_width + " " + image_height + "\n255");
        assert fileWriter != null;
        fileWriter.write("P3\n" + image_width + " " + image_height + "\n255" + '\n');


        long start = System.nanoTime();
        for (int j = image_height - 1; j >= 0; --j) {
            if (j % 10 == 0) { System.out.println("Scanlines Remaining : " + j + '\r'); }
            for (int i = 0; i < image_width; ++i) {

                Vec3 pixel_color = new Vec3(0, 0, 0);

                for (int s = 0; s < samples_per_pixel; s++) {

                    double horizontal_offset = (i + Util.randomDouble()) / (image_width - 1);
                    double vertical_offset = (j + Util.randomDouble()) / (image_height - 1);

                    Ray ray = cam.getRay(horizontal_offset, vertical_offset);
                    pixel_color.add(rayColor(ray, world, max_depth));
                }
                Util.writeColor(frame, fileWriter, pixel_color, samples_per_pixel, i, j, image_height);
            }
        }
        long stop = System.nanoTime();
        System.out.println("Elapsed Time is : " + (stop - start) / 1e6 + " ms");


        //Handle open resources
        fileWriter.close();
    }
}

