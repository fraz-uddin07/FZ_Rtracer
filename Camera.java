
public class Camera {

    double v_fov;

    double aspect_ratio = 16.0 / 9.0;
    double viewport_height = 2.0;
    double viewport_width = aspect_ratio * viewport_height;
    double focal_length = 1.0;
    double lens_radius;

    Vec3 origin = null;
    Vec3 horizontal = null;
    Vec3 vertical = null;
    Vec3 lower_left_corner = null;
    Vec3 w = null;
    Vec3 u = null;
    Vec3 v = null;

    public Camera (Vec3 look_from, Vec3 look_at, Vec3 vup, double v_fov, double aspect_ratio, double aperture, double focus_dist) {

        double theta = Util.degreesToRadians(v_fov);
        double h = Math.tan(theta / 2);
        double viewport_height = 2.0 * h;
        double viewport_width = aspect_ratio * viewport_height;

        w = Vec3.unitVector(Vec3.subtract(look_from, look_at));
        u = Vec3.unitVector(Vec3.cross(vup, w));
        v = Vec3.cross(w, u);


//        origin = new Vec3(0, 0, 0);
//        horizontal = new Vec3(viewport_width, 0, 0);
//        vertical = new Vec3(0, viewport_height, 0);

        origin = new Vec3(look_from);
        horizontal = Vec3.multiply(Vec3.multiply(u, viewport_width), focus_dist);
        vertical = Vec3.multiply(Vec3.multiply(v, viewport_height), focus_dist);

        lower_left_corner = new Vec3(origin);
        lower_left_corner.subtract(Vec3.divide(horizontal, 2));
        lower_left_corner.subtract(Vec3.divide(vertical,2));
        lower_left_corner.subtract(Vec3.multiply(w, focus_dist));

        lens_radius = aperture / 2;

    }


    Ray getRay (double s, double t) {

        Vec3 rd = Vec3.multiply(Vec3.random_in_unit_disc(), lens_radius);
        Vec3 offset = Vec3.add(Vec3.multiply(u, rd.getX()) , Vec3.multiply(v, rd.getY()));


        Vec3 direction = new Vec3(lower_left_corner);
        direction.add(Vec3.multiply(horizontal, s));
        direction.add(Vec3.multiply(vertical, t));
        direction.subtract(origin);
        direction.subtract(offset);

        return new Ray(Vec3.add(origin, offset), direction);
    }



}
