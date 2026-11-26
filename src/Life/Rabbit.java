package Life;

import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Random;

public class Rabbit implements Actor {
    private int  age = 0;
    private int energy = 10;
    private Burrow homeBurrow;
    Random r = new Random();
    @Override
    public void act(World w){

        if (!w.isOnTile(this)) {
            if (w.isNight()) {
                return;
            }

            Location entrance = w.getLocation(homeBurrow);
            Set<Location> exits = w.getEmptySurroundingTiles(entrance);

            if (!exits.isEmpty()) {
                w.setTile(exits.iterator().next(), this);
            }

            return;
        }

        if (homeBurrow == null){
            List<Burrow> burrows = new ArrayList<>();

            for (var entry : w.getEntities().entrySet()) {
                if (entry.getKey() instanceof Burrow burrow && entry.getValue() != null) {
                    burrows.add(burrow);
                }
            }
            if (!burrows.isEmpty()) {
                homeBurrow = burrows.get(r.nextInt(burrows.size()));
            }

        }


        if (w.isNight() && homeBurrow != null) {
            Location entrance = w.getLocation(homeBurrow);

            if (w.getLocation(this).equals(entrance)) {
                w.remove(this);
            } else if (w.getTile(entrance) == homeBurrow) {
                w.move(this, entrance);
                w.remove(this);
            }

            return;
        }

        moveRabbit(w);
        eatGrass(w);
        digBurrow(w);
        reproduction(w);

        energy--;
        die(w);



    }

    /** This method gives the rabbit the ability to eat grass and determines how much
     * energy they get depending on their age
     * @param w
     */
    public void eatGrass(World w) {
        if(w.getNonBlocking(w.getLocation(this)) instanceof Grass) {
            w.delete(w.getNonBlocking(w.getLocation(this)));
            if (age <= 3) {
                energy = energy + 3;
            } else if (age <= 6) {
                energy = energy + 2;
            } else {
                energy = energy + 1;
            }
        }
    }

    /** This method gives the rabbits a 10% chance to dig a burrow everytime it is
     * called
      * @param w
     */
    public void digBurrow(World w) {
        if(r.nextInt(10) > 8) {
            w.setTile(w.getLocation(this), new Burrow());
        }
    }

    /** This method gives the rabbits a 10% chance to reproduce if they are
     * standing next to another rabbit
     * @param w
     */
    public void reproduction(World w) {
        for(Location neighbour : w.getSurroundingTiles(w.getLocation(this))) {
            Set<Location> empty = w.getEmptySurroundingTiles();
            List<Location> list2 = new ArrayList<>(empty);
            if(w.getTile(neighbour) instanceof Rabbit) {
                if(!list2.isEmpty() && r.nextInt(10) > 8) {
                    Location l = list2.get(r.nextInt(list2.size()));
                    w.setTile(l, new Rabbit());
                }
            }
        }
    }

    /** This method makes the rabbit able to move to an unoccupied location
     *
     * @param w
     */
    public void moveRabbit(World w) {
        Set<Location> neighbours = w.getEmptySurroundingTiles();
        List<Location> list = new ArrayList<>(neighbours);
        age++;
        if(!list.isEmpty()) {
            Location l = list.get(r.nextInt(list.size()));
            w.move(this, l);
        }
    }

    /** This method makes a rabbit die if it is out of energy
     *
     * @param w
     */
    public void die(World w) {
        if(energy == 0) {
            w.delete(this);
        }
    }
}
