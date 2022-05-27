
public abstract class Hittable {
    abstract boolean hit (Ray r, double t_min, double t_max, Hit_Record rec);
}
