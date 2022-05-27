
public class Sphere extends Hittable {

    Vec3 center = new Vec3();
    double radius = 0.0;
    Material material_ref = null;

    public Sphere () {};

    public Sphere (Vec3 center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Sphere (Vec3 center, double radius, Material m) {
        this.center = center;
        this.radius = radius;
        this.material_ref = m;
    }

    @Override
    public boolean hit (Ray ray, double t_min, double t_max, Hit_Record rec) {
        Vec3 oc = Vec3.subtract(ray.getOrigin(), center);
        double a = ray.getDirection().lengthSquared();
        double half_b = Vec3.dot(ray.getDirection(), oc);
        double c = oc.lengthSquared() - radius * radius;
        double discriminant = (half_b * half_b) - (a * c);

        if (discriminant < 0) return false;
        double sqrtDis = Math.sqrt(discriminant);

        //Nearest root in acceptable range
        double root = (-half_b - sqrtDis) / a;
        if (root < t_min || root > t_max) {
            root = (-half_b + sqrtDis) / a;
            if (root < t_min || root > t_max)
                return false;
        }

        rec.t = root;
        rec.point = ray.at(rec.t);
//        rec.normal = Vec3.divide(Vec3.subtract(rec.point, center), radius);
//        Vec3 outward_normal = new Vec3();
        Vec3 outward_normal = Vec3.divide(Vec3.subtract(rec.point, center), radius);
//        Vec3 outward_normal = Vec3.unitVector(Vec3.subtract(rec.point, center));

        rec.setFaceNormal(ray, outward_normal);
        rec.material_ref = material_ref;

        return true;
    }
}
