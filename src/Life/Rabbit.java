package Life;

import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Random;

public class Rabbit implements Actor {
    int  age = 0;
    int energy = 10;
    Random r = new Random();
    @Override
    public void act(World w){
        Set<Location> neighbours = w.getEmptySurroundingTiles();
        List<Location> list = new ArrayList<>(neighbours);
        age++;
        if(!list.isEmpty()) {
            Location l = list.get(r.nextInt(list.size()));
            w.move(this, l);
        }
        if(w.getNonBlocking(w.getLocation(this)) instanceof Grass) {
            w.delete(w.getNonBlocking(w.getLocation(this)));
            if(age <= 3) {
                energy = energy + 3;
            } else if(age <= 6) {
                energy = energy + 2;
            }else {
                energy = energy + 1;
            }
            if(r.nextInt(10) > 8) {
                w.setTile(w.getLocation(this), new Burrow());
            }
        }
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

        energy--;
        if(energy == 0) {
            w.delete(this);
        }

    }
}
