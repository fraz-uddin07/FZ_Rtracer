
public class Hit_Record {
    Vec3 point = new Vec3();
    Vec3 normal = new Vec3();
    Material material_ref;
    double t = 0.0;
    boolean front_face = false;

    public void setFaceNormal (Ray ray, Vec3 outwardNormal) {
        front_face = Vec3.dot(ray.getDirection(), outwardNormal) < 0.0;
        normal = front_face ? outwardNormal : Vec3.negative(outwardNormal);
    }
}
