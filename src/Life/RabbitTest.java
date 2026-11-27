package Life;

import itumulator.world.World;
import itumulator.world.Location;
import Life.Rabbit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RabbitTest {
    @Test
    void homelessRabbitFindsBurrowAtNightAndLeavesAtDay() {
        World w = new World(5);
        Rabbit rabbit = new Rabbit(0, 10, null);
        Location entrance = new Location(2,2);

        w.setTile(new Location(0, 0), rabbit);
        w.setTile(entrance, new Burrow());

        w.setNight();
        rabbit.act(w);

        assertTrue(w.contains(rabbit));
        assertFalse(w.isOnTile(rabbit));

        w.setDay();
        rabbit.act(w);

        assertTrue(w.isOnTile(rabbit));
        assertTrue(w.getSurroundingTiles(entrance).contains(w.getLocation(rabbit)));
    }

    @Test
    void rabbitSurvivesByEatingGrass() {
        World w = new World(1);
        Rabbit rabbit = new Rabbit(0, 1, null);
        Grass grass = new Grass();

        w.setTile(new Location(0, 0), rabbit);
        w.setTile(new Location(0,0), grass);
        rabbit.act(w);

        assertFalse(w.contains(grass));
        assertTrue(w.contains(rabbit));

    }
}
