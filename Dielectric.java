
public class Dielectric extends Material {

    double r_index = 0.0;


    Dielectric (double index_of_refraction) {
        this.r_index = index_of_refraction;
    }


    @Override
    public boolean scatter(Ray ray_in, Hit_Record rec, Vec3 attenuation, Ray scattered) {
        attenuation.coords[0] = 1.0;
        attenuation.coords[1] = 1.0;
        attenuation.coords[2] = 1.0;

        double refraction_ratio = rec.front_face ? (1.0 / r_index) : r_index;

        Vec3 unit_direction = Vec3.unitVector(ray_in.getDirection());

        double cos_theta  = Math.min(Vec3.dot(Vec3.negative(unit_direction), rec.normal), 1.0);
        double sin_theta = Math.sqrt(1.0 - cos_theta * cos_theta);

        boolean cannot_refract = refraction_ratio * sin_theta > 1.0;
        Vec3 direction;

        if (cannot_refract || reflectance(cos_theta, refraction_ratio) > Util.randomDouble()) {
            direction = Vec3.reflect(unit_direction, rec.normal);
        }
        else {
            direction = Vec3.refract(unit_direction, rec.normal, refraction_ratio);
        }

        Vec3 refracted = Vec3.refract(unit_direction, rec.normal, refraction_ratio);

        scattered.origin = rec.point;
        scattered.direction = direction;

        return true;
    }

    private static double reflectance (double cosine, double ref_idx) {
        //Using Schlick approximation for reflectance
        double r0 = (1 - ref_idx) / (1 + ref_idx);
        r0 *= r0;

        return r0 + (1 - r0) * Math.pow((1 - cosine), 5);
    }




}
