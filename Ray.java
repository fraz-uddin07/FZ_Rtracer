
public class Ray extends Vec3 {

    Vec3 origin = new Vec3();
    Vec3 direction = new Vec3();

    Ray () {
        origin.coords[0] = 0;
        origin.coords[1] = 0;
        origin.coords[2] = 0;

        direction.coords[0] = 0;
        direction.coords[1] = 0;
        direction.coords[2] = 0;
    }

    Ray (Vec3 origin, Vec3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vec3 getOrigin () {
        return this.origin;
    }

    public Vec3 getDirection () {
        return this.direction;
    }

    public Vec3 at (double t) {
        return add(origin, multiply(direction, t));
    }

}
