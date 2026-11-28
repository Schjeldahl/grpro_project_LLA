package Life;

import itumulator.world.Location;
import itumulator.world.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalsTest {
    @Test
    void AnimalsDieAfterTheyRunOutOfEnergy() {
        World w = new World(1);
        Animals animal = new Animals(0,1,null) {};
        w.setTile(new Location(0,0), animal);

        animal.act(w);
        assertFalse(w.contains(animal));

    }

    @Test
    void AnimalGetsDeletedSafelyIfTheyHaveNoHomeWhenWakingup() {
        World w = new World(1);
        Animals animal = new Animals(0,10,null) {};
        w.setTile(new Location(0,0), animal);

        w.remove(animal);
        w.setDay();
        animal.act(w);

        assertFalse(w.contains(animal));
    }

}
