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
    private int energy = 100;
    private Burrow homeBurrow;
    Random r = new Random();
    @Override
    public void act(World w){
        age++;

        if(!w.isOnTile(this)) {
            wakeUp(w);
            return;
        }

        goHome(w);
        if(!w.isOnTile(this)) {
            return;
        }

        moveRabbit(w);

        eatGrass(w);

        findOrDigHomeBurrow(w);

        reproduction(w);

        energy--;
        die(w);



    }

    /** This method gives the rabbit the ability to eat grass and determines how much
     * energy they get depending on their age
     * @param w This parameter tells the method which world it's working in
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



    /** This method gives the rabbits a 10% chance to reproduce if they are
     * standing next to another rabbit
     * @param w This parameter tells the method which world it's working in
     */
    public void reproduction(World w) {
        for(Location neighbour : w.getSurroundingTiles(w.getLocation(this))) {
            Set<Location> empty = w.getEmptySurroundingTiles(w.getLocation(this));
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
     * @param w This parameter tells the method which world it's working in
     */
    public void moveRabbit(World w) {
        Set<Location> neighbours = w.getEmptySurroundingTiles();
        List<Location> list = new ArrayList<>(neighbours);
        if(!list.isEmpty()) {
            Location l = list.get(r.nextInt(list.size()));
            w.move(this, l);
        }
    }

    /** This method makes a rabbit die if it is out of energy
     *
     * @param w This parameter tells the method which world it's working in
     */
    public void die(World w) {
        if(energy == 0) {
            w.delete(this);
        }
    }

    /** This method makes a rabbit find or dig a home burrow
     *
     * @param w This parameter tells the method which world it's working in
     */
    private void findOrDigHomeBurrow(World w) {
        if (homeBurrow != null && w.contains(homeBurrow) && w.isOnTile(homeBurrow)) {
            return;
        }

        homeBurrow = null;
        Location l = w.getLocation(this);
        Object ground = w.getNonBlocking(l);

        if (ground instanceof Burrow burrow) {
            homeBurrow = burrow;
        } else if (ground == null && r.nextInt(10) == 0) {
            Burrow burrow = new Burrow();
            w.setTile(l, burrow);
            homeBurrow = burrow;
        }
    }

    /** This method makes the rabbit go to its burrow when it is night
     *
     * @param w This parameter tells the method which world it's working in
     */
    public void goHome(World w) {
        if (w.isNight() && homeBurrow != null) {
            Location entrance = w.getLocation(homeBurrow);

            if (w.getLocation(this).equals(entrance)) {
                w.remove(this);
            } else if (w.getTile(entrance) == homeBurrow) {
                w.move(this, entrance);
                w.remove(this);
            }
        }
    }

    /** This method places the rabbit back on the world when it turns to day
     *
     * @param w This parameter tells the method which world it's working in
     */
    public void wakeUp(World w) {
        if (!w.isOnTile(this)) {
            if (w.isNight()) {
                return;
            }

            Location entrance = w.getLocation(homeBurrow);
            Set<Location> exits = w.getEmptySurroundingTiles(entrance);

            if (!exits.isEmpty()) {
                w.setTile(exits.iterator().next(), this);
            }

        }
    }
}
