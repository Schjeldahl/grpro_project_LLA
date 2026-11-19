package Life;

import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Rabbit implements Actor {

    @Override
    public void act(World w){
        Set<Location> neighbours = w.getSurroundingTiles();
        List<Location> list = new ArrayList<>(neighbours);


    }
}
