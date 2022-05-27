
public class Metal extends Material {

    Vec3 albedo = new Vec3();
    double fuzziness = 0.0;

    Metal (Vec3 color) {
        this.albedo = color;
    }

    Metal (Vec3 color, double f) {
        this.albedo = color;
        fuzziness = f < 1 ? f : 1;
    }


    @Override
    public boolean scatter(Ray ray_in, Hit_Record rec, Vec3 attenuation, Ray scattered) {
        Vec3 reflected = Vec3.reflect(Vec3.unitVector(ray_in.getDirection()), rec.normal);
//        scattered = new Ray(rec.point, reflected);
//        attenuation = albedo;

        scattered.origin = rec.point;
        scattered.direction = Vec3.add(reflected, Vec3.multiply(Vec3.random_in_unit_sphere(), fuzziness));
        attenuation.coords[0] = albedo.coords[0];
        attenuation.coords[1] = albedo.coords[1];
        attenuation.coords[2] = albedo.coords[2];
        return (Vec3.dot(scattered.getDirection(), rec.normal) > 0);

    }
}
