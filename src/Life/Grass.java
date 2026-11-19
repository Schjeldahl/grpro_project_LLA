package Life;
import itumulator.world.NonBlocking;
import itumulator.world.World;
import itumulator.simulator.Actor;
import itumulator.world.Location;

import java.util.*;


public class Grass  implements Actor, NonBlocking {
    //Random r = new Random();

    @Override
    public void act(World w){
        if(w.isNight()) {
            Set<Location> neighbours = w.getSurroundingTiles();
            List<Location> list = new ArrayList<>(neighbours);
            if(!list.isEmpty()) {
                for (Location l : list) {
                    if(!w.containsNonBlocking(l)) {
                        w.setTile(l, new Grass());
                    }

                }
            }
        }

    }

}
