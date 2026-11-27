package Life;
import itumulator.world.World;
import java.util.Random;

public class Rabbit extends Animals {
    private int age = 0;
    private int energy = 10;
    private Burrow homeBurrow;
    Random r = new Random();

    public Rabbit(int Age, int Energy, Burrow HomeBurrow) {
        super(Age, Energy, HomeBurrow);
    }

    @Override
    public void act(World w) {;
        goToNest(w);
        if (!w.isOnTile(this)) {
            return;
        }
        makeNest(w);
        if (!w.isOnTile(this)) {
            return;
        }

        age();
        walk(w);
        eatGrass(w);
        mate(w);
        die(w);
    }

    public void eatGrass(World w) {
        if (w.getNonBlocking(w.getLocation(this)) instanceof Grass) {
            w.delete(w.getNonBlocking(w.getLocation(this)));
            if (age <= 3) {
                energy = energy + 3;
            } else if (age <= 6) {
                energy = energy + 2;
            } else {
                energy = energy + 1;
            }
            if (r.nextInt(10) > 8) {
                w.setTile(w.getLocation(this), new Burrow());
            }
        }
    }
}
