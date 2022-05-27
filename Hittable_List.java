
import java.util.ArrayList;

public class Hittable_List extends Hittable {

    ArrayList<Hittable> objects = new ArrayList<>();

    Hittable_List () {}

    Hittable_List (Hittable object) {
        add(object);
    }

    public void clear () {
        objects.clear();
    }

    public void add (Hittable object) {
        objects.add(object);
    }

    @Override
    public boolean hit (Ray ray, double t_min, double t_max, Hit_Record rec) {
//        Hit_Record temp_rec = new Hit_Record();
        boolean hitAnything = false;
        double closestSoFar = t_max;

        for (Hittable object : objects) {
            if (object.hit(ray, t_min, closestSoFar, rec)) {
                hitAnything = true;
//                closestSoFar = temp_rec.t;
//                Util.copy(rec, temp_rec);
                closestSoFar = rec.t;
//                Util.copy(rec, temp_rec);
            }
        }
        return hitAnything;
    }


}
