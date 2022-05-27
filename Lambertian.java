
public class Lambertian extends Material {

    Vec3 albedo;

    Lambertian (Vec3 color) {
        this.albedo = color;
    }


    @Override
    public boolean scatter(Ray ray_in, Hit_Record rec, Vec3 attenuation, Ray scattered) {
        Vec3 scatterDirection = Vec3.add(rec.normal, Vec3.randomUnitVector());

        //Catching degenerate scatter direction
        if (scatterDirection.nearZero()) {
            scatterDirection = rec.normal;
        }

//        scattered = new Ray(rec.point, scatterDirection);
        scattered.origin = rec.point;
        scattered.direction = scatterDirection;
        attenuation.coords[0] = albedo.coords[0];
        attenuation.coords[1] = albedo.coords[1];
        attenuation.coords[2] = albedo.coords[2];
        return true;

    }
}
