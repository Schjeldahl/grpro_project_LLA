package Life;
import itumulator.world.Location;
import itumulator.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Bear extends Animals{
    public Bear(){
        super(0, 100, null);
    }

    @Override
    public void act(World w){

        walk(w);
        age();
        mate(w);
        eat(w);
        die(w);
    }

    @Override
    public void mate(World w) {
        for (Location neighbour : w.getSurroundingTiles(w.getLocation(this))) {
            Set<Location> empty = w.getEmptySurroundingTiles(w.getLocation(this));
            List<Location> list2 = new ArrayList<>(empty);
            if (w.getTile(neighbour) instanceof Bear) {
                if (!list2.isEmpty() && super.getAge()>=5) {
                    Location l = list2.get(r.nextInt(list2.size()));
                    w.setTile(l, new Bear(0, 10, null));
                }
            }
        }
    }

    @Override
    public void walk(World w) {
        var home = w.getLocation(this);
        Set<Location> neighbours = w.getEmptySurroundingTiles(home);
        List<Location> list = new ArrayList<>(neighbours);
        if (!list.isEmpty()) {
            Location l = list.get(r.nextInt(list.size()));
            w.move(this, l);
        }
        energy--;
    }

}
