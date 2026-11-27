package Life;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;

public class Rabbit extends Animals {


    public Rabbit(int Age, int Energy, Burrow HomeBurrow) {
        super(Age, Energy, HomeBurrow);
    }

    @Override
    public void act(World w) {
        age();

        if (!w.isOnTile(this)) {
            wakeUp(w);
            return;
        }

        if (w.isNight()) {
            findOrDigHomeBurrow(w);
            goHome(w);

            if (!w.isOnTile(this)) {
                return;
            }
        }

        walk(w);
        eatGrass(w);

        if (w.isDay()) {
            findOrDigHomeBurrow(w);
        }

        mate(w);
        die(w);
    }

    public void eatGrass(World w) {
        if (w.getNonBlocking(w.getLocation(this)) instanceof Grass grass) {
            w.delete(grass);
            if (getAge() <= 3) {
                gainEnergy(3);
            } else if (getAge() <= 6) {
                gainEnergy(2);
            } else {
                gainEnergy(1);
            }
        }
    }

    private void findOrDigHomeBurrow(World w) {
        if (hasHomeBurrow(w)) {
            return;
        }

        setHomeBurrow(null);
        if (w.isNight()) {
            List<Burrow> burrows = new ArrayList<>();

            for (var entry : w.getEntities().entrySet()) {
                if (entry.getKey() instanceof Burrow burrow && entry.getValue() != null) {
                    burrows.add(burrow);
                }
            }
            if (!burrows.isEmpty()) {
                setHomeBurrow(burrows.get(r.nextInt(burrows.size())));
            }
            return;
        }

        Location l = w.getLocation(this);
        Object ground = w.getNonBlocking(l);

        if (ground instanceof Burrow burrow) {
            setHomeBurrow(burrow);
        } else if (ground == null && r.nextInt(10) == 0) {
            Burrow burrow = new Burrow();
            w.setTile(l, burrow);
            setHomeBurrow(burrow);
        }
    }

    private void goHome(World w) {
        if (!hasHomeBurrow(w)) {
            return;
        }

        Location entrance = w.getLocation(getHomeBurrow());
        if (w.getLocation(this).equals(entrance)) {
            w.remove(this);
        } else if (w.isTileEmpty(entrance)) {
            w.move(this, entrance);
            w.remove(this);
        }
    }
    public void mate(World w) {
        Location position = w.getLocation(this);
        List<Location> available = new ArrayList<>(
                w.getEmptySurroundingTiles(position));

        if (available.isEmpty()) {
            return;
        }

        for (Location neighbour : w.getSurroundingTiles(position)) {
            if (w.getTile(neighbour) instanceof Rabbit) {
                if (r.nextInt(10) == 0) {
                    Location birthTile = available.get(r.nextInt(available.size()));
                    w.setTile(birthTile, new Rabbit(0, 10, null));
                }
                return;
            }
        }
    }
}
