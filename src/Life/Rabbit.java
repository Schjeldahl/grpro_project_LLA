package Life;

import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Random;

public class Rabbit implements Actor {
    int energy = 10;
    Random r = new Random();
    @Override
    public void act(World w){
        Set<Location> neighbours = w.getEmptySurroundingTiles();
        List<Location> list = new ArrayList<>(neighbours);
        if(!list.isEmpty()) {
            Location l = list.get(r.nextInt(list.size()));
            w.move(this, l);
        }
        if(w.getNonBlocking(w.getLocation(this)) instanceof Grass) {
            w.delete(w.getNonBlocking(w.getLocation(this)));
            energy =  energy +2;
        }
        /*if(w.getEntities(w.getSurroundingTiles(w.getLocation(this))) instanceof Rabbit) {

        }*/

        energy--;
        if(energy == 0) {
            w.delete(this);
        }

    }
}
