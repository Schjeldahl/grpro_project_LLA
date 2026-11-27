import Life.Grass;
import itumulator.world.World;

import org.junit.jupiter.api.Test;
import itumulator.world.Location;
import Life.Rabbit;
import static org.junit.jupiter.api.Assertions.*;

class PlacementTest {
    @Test
    void rabbitAndGrassCanShareTheOnlyTile() {
        World w = new World(1);
        Placement placement = new Placement(w, 1);
        Location tile = new Location(0,0);
        Grass grass = new Grass();
        Rabbit rabbit = new Rabbit(0, 10, null);

        placement.placeNonBlocking(grass);
        placement.placeBlocking(rabbit);

        assertSame(rabbit, w.getTile(tile));
        assertSame(grass, w.getNonBlocking(tile));
    }
}
