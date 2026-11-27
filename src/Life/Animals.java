package Life;

import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Random;

public abstract class Animals implements Actor {

    Random r = new Random();
    private int age;
    private int energy;
    private Burrow homeBurrow;

    Animals(int Age, int Energy, Burrow homeBurrow) {
        this.age = Age;
        this.energy = Energy;
        this.homeBurrow = homeBurrow;
    }

    @Override
    public void act(World w) {
        wakeUp(w);
        if (!w.contains(this) || !w.isOnTile(this)) {
            return;
        }
        age();
        walk(w);
        die(w);
    }
    public void wakeUp(World w) {
        if (!w.isOnTile(this)) {
            if (w.isNight()) {
                return;
            }

            if(!hasHomeBurrow(w)) {
                w.delete(this);
                return;
            }
            Location entrance = w.getLocation(homeBurrow);
            Set<Location> exits = w.getEmptySurroundingTiles(entrance);

            if (!exits.isEmpty()) {
                w.setTile(exits.iterator().next(), this);
            }
        }
    }



    public void age() {
        age++;
    }


    public void walk(World w) {
        Set<Location> neighbours = w.getEmptySurroundingTiles(w.getLocation(this));
        List<Location> list = new ArrayList<>(neighbours);
        if (!list.isEmpty()) {
            Location l = list.get(r.nextInt(list.size()));
            w.move(this, l);
        }
        energy--;
    }

    public void die(World w) {
        if (energy <= 0) {
            w.delete(this);
        }
    }
    protected int getAge() {
        return age;
    }

    protected void gainEnergy(int amount) {
        energy += amount;
    }

    protected Burrow getHomeBurrow() {
        return homeBurrow;
    }

    protected void setHomeBurrow(Burrow burrow) {
        homeBurrow = burrow;
    }

    protected boolean hasHomeBurrow(World w) {
        return homeBurrow != null && w.contains(homeBurrow) && w.isOnTile(homeBurrow);
    }
}
