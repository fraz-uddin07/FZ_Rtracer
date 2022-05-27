
import java.io.FileWriter;
import java.io.IOException;

public class Vec3 {

    public double[] coords = new double[3];

    Vec3 () {
        coords[0] = 0;
        coords[1] = 0;
        coords[2] = 0;
    }

    Vec3 (double x, double y, double z) {
        coords[0] = x;
        coords[1] = y;
        coords[2] = z;
    }

    Vec3 (Vec3 vecInstance) {
        this.coords[0] = vecInstance.coords[0];
        this.coords[1] = vecInstance.coords[1];
        this.coords[2] = vecInstance.coords[2];
    }

    public double getX() {
        return coords[0];
    }

    public double getY() {
        return coords[1];
    }

    public double getZ() {
        return coords[2];
    }

    public static Vec3 negative (Vec3 vecInstance) {
        Vec3 returnInstance = new Vec3();
        for (int i = 0; i < returnInstance.coords.length; i++) {
            returnInstance.coords[i] = -vecInstance.coords[i];
        }
        return returnInstance;
    }

    public Vec3 add (Vec3 vecInstance) {
        for (int i = 0; i < this.coords.length; i++) {
            this.coords[i] += vecInstance.coords[i];
        }
        return this;
    }

    //Perform method nesting or use Variadic functions
    public static Vec3 add (Vec3 vec1, Vec3 vec2) {
        return new Vec3(vec1.coords[0] + vec2.coords[0], vec1.coords[1] + vec2.coords[1], vec1.coords[2] + vec2.coords[2]);
    }

    public static Vec3 add (Vec3 vec1, Vec3 vec2, Vec3 vec3) {
        return new Vec3(vec1.coords[0] + vec2.coords[0] + vec3.coords[0], vec1.coords[1] + vec2.coords[1] + vec3.coords[1], vec1.coords[2] + vec2.coords[2] + vec3.coords[2]);
    }

    public Vec3 subtract (Vec3 vecInstance) {
        for (int i = 0; i < this.coords.length; i++) {
            this.coords[i] -= vecInstance.coords[i];
        }
        return this;
    }

    //Perform method nesting or use Variadic functions
    public static Vec3 subtract (Vec3 vec1, Vec3 vec2) {
        //        for (int i = 0; i < vec3.coords.length; i++) {
//            vec3.coords[i] = vec1.coords[i] - vec2.coords[i];
//        }
        return new Vec3(vec1.coords[0] - vec2.coords[0], vec1.coords[1] - vec2.coords[1], vec1.coords[2] - vec2.coords[2]);
    }

    public static Vec3 subtract (Vec3 vec1, Vec3 vec2, Vec3 vec3) {
        return new Vec3(vec1.coords[0] - vec2.coords[0] - vec3.coords[0], vec1.coords[1] - vec2.coords[1] - vec3.coords[1], vec1.coords[2] - vec2.coords[2] - vec3.coords[2]);
    }

    public Vec3 multiply (double t) {
        for (int i = 0; i < this.coords.length; i++) {
            this.coords[i] *= t;
        }
        return this;
    }

    public static Vec3 multiply (Vec3 vec1, Vec3 vec2) {
        //        for (int i = 0; i < vec3.coords.length; i++) {
//            vec3.coords[i] = vec1.coords[i] * vec2.coords[i];
//        }
        return new Vec3(vec1.coords[0] * vec2.coords[0], vec1.coords[1] * vec2.coords[1], vec1.coords[2] * vec2.coords[2]);
    }

    public static Vec3 multiply (Vec3 vec1, double t) {
        return new Vec3(vec1.coords[0] * t, vec1.coords[1] * t, vec1.coords[2] * t);
    }

    public Vec3 divide (double t) {
        return new Vec3(coords[0] / t, coords[1] / t, coords[2] / t);
    }

    public Vec3 divideBy (double t) {
        for (int i = 0; i < this.coords.length; i++) {
            this.coords[i] /= t;
        }
        return this;
    }

    public static Vec3 divide (Vec3 vec1, double t) {
        return new Vec3(vec1.coords[0] / t, vec1.coords[1] / t,vec1.coords[2] / t);
    }


    public double length () {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared () {
        return (coords[0] * coords[0] + coords[1] * coords[1] + coords[2] * coords[2]);
    }

    public static double dot (Vec3 vec1, Vec3 vec2) {
        return (vec1.coords[0] * vec2.coords[0] + vec1.coords[1] * vec2.coords[1] + vec1.coords[2] * vec2.coords[2]);
    }

    public static Vec3 cross (Vec3 u, Vec3 v) {

        return new Vec3(u.coords[1] * v.coords[2] - u.coords[2] * v.coords[1],
                u.coords[2] * v.coords[0] - u.coords[0] * v.coords[2],
                u.coords[0] * v.coords[1] - u.coords[1] * v.coords[0]);
    }

    public static Vec3 unitVector (Vec3 v) {
        Vec3 unitVec = new Vec3(v);
        return Vec3.divide(unitVec, unitVec.length());
    }

    public static Vec3 randomUnitVector () {
        return Vec3.unitVector(Vec3.random_in_unit_sphere());
    }

    public static Vec3 random_in_hemisphere (Vec3 normal) {
        Vec3 in_unit_sphere = random_in_unit_sphere();

        if (Vec3.dot(in_unit_sphere, normal) > 0.0)  //Same hemisphere as the normal
        return in_unit_sphere;

        return Vec3.negative(in_unit_sphere);


    }

    public static Vec3 random () {
        return new Vec3 (Util.randomDouble(), Util.randomDouble(), Util.randomDouble());
    }

    public static Vec3 random (double min, double max) {
        return new Vec3 (Util.randomDouble(min, max), Util.randomDouble(min, max), Util.randomDouble(min, max));
    }

    public static Vec3 random_in_unit_sphere () {
        while (true) {
            Vec3 p = Vec3.random(-1, 1);
            if (p.lengthSquared() >= 1) continue;
            return p;
        }
    }

    public static Vec3 random_in_unit_disc () {
        while (true) {
            Vec3 p = new Vec3 (Util.randomDouble(-1, 1), Util.randomDouble(-1, 1), 0);
            if (p.lengthSquared() >= 1) continue;;
            return p;
        }
    }

    public boolean nearZero () {
        //Filter out the near Zero vectors
        double s = 1e-8;
        return (Math.abs(coords[0]) < s && Math.abs(coords[1]) < s && Math.abs(coords[2]) < s);
    }

    public static Vec3 reflect (Vec3 v, Vec3 n) {
        return Vec3.subtract(v, Vec3.multiply(n, 2 * Vec3.dot(v, n)));
    }

    public static Vec3 refract (Vec3 uv, Vec3 n, double eta_by_etap) {
        double cos_theta = Math.min(Vec3.dot(Vec3.negative(uv), n), 1.0);
        Vec3 r_out_perp = Vec3.multiply(Vec3.add(uv, Vec3.multiply(n, cos_theta)), eta_by_etap);
        Vec3 r_out_parallel = Vec3.multiply(n, -Math.sqrt(Math.abs(1.0 - r_out_perp.lengthSquared())));
        return Vec3.add(r_out_perp, r_out_parallel);
    }

    public void printVec (FileWriter fileWriter, Vec3 vecInstance) throws IOException {
        fileWriter.write(vecInstance.coords[0] + " " + vecInstance.coords[1] + " " + vecInstance.coords[2]);
    }

    public void printVec (FileWriter fileWriter) throws IOException {
        fileWriter.write(coords[0] + " " + coords[1] + " " + coords[2]);
    }

}
