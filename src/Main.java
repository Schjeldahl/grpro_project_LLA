import java.awt.Color;
import java.util.*;

import Life.Grass;
import Life.Rabbit;
import itumulator.executable.DisplayInformation;
import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;

public class Main {

    public static void main(String[] args) {
        int size = 5;
        int delay = 1000;
        int display_size = 1000;
        int amountGrass = 5;
        int amountRabbit = 3;

        Random r = new Random();

        Program p = new Program(size, display_size, delay);

        World w = p.getWorld();

        Location rab = new Location(1,1);
        w.setTile(rab, new Rabbit());

        for(int i = 0; i < amountGrass; i++) {
            Location l = randomLocation(size, r, w);
            w.setTile(l, new Grass());
        }
        for(int i = 0; i < amountRabbit; i++) {
            Location l = randomLocation(size, r, w);
            w.setTile(l, new Rabbit());
        }


        // w.setTile(new Location(0, 0), new <MyClass>());

        // p.setDisplayInformation(<MyClass>.class, new DisplayInformation(<Color>, "<ImageName>"));
        DisplayInformation grass = new DisplayInformation(Color.green);
        p.setDisplayInformation(Grass.class, grass);
        DisplayInformation rabbit = new DisplayInformation(Color.black);
        p.setDisplayInformation(Rabbit.class, rabbit);
        p.show();
        for (int i = 0; i < 200; i++) {
            p.simulate();
        }

    }

    private static Location randomLocation(int size, Random r, World w) {
        int x = r.nextInt(size);
        int y = r.nextInt(size);
        Location l = new Location(x,y);

        while(w.containsNonBlocking(l)) {
            x = r.nextInt(size);
            y = r.nextInt(size);
            l = new Location(x,y);
        }
        return l;
    }
    /*public static void placeNonBlocking(int amount, Object object) {
        int size = w.
        for(int i = 0; i < amount; i++) {
            int x = r.nextInt(size);
            int y = r.nextInt(size);
            Location l = new Location(x,y);

            while(w.containsNonBlocking(l)) {
                x = r.nextInt(size);
                y = r.nextInt(size);
                l = new Location(x,y);
            }
            w.setTile(l, new Object());
        }
    }*/
}