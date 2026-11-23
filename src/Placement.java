import itumulator.world.Location;
import itumulator.world.NonBlocking;


import java.util.Map;
import java.util.Random;

import itumulator.world.World;

public class Placement {

    private World w;
    private int size;
    Random r = new Random();

    public Placement(World w, int size){
        this.w = w;
        this.size = size;
    }

    // Generel metode som placerer ét NonBlocking objekt - kræver derfor er for-loop for flere objekter

    public void placeNonBlocking(NonBlocking object) {

        Location l;

        do {
            int x = r.nextInt(size);
            int y = r.nextInt(size);
            l = new Location(x, y);
        } while (w.containsNonBlocking(l));

        this.w.setTile(l, object);
    }

    // Generel metode som placerer ét Blocking objekt - kræver derfor er for-loop for flere objekter

    public void placeBlocking(Object object) {

        Location l;

        do {
            int x = r.nextInt(size);
            int y = r.nextInt(size);
            l = new Location(x, y);
        } while (!w.isTileEmpty(l));

        this.w.setTile(l, object);
    }




}
