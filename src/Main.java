import java.awt.Color;
import java.util.*;

import Life.Grass;
import Life.Rabbit;
import itumulator.executable.DisplayInformation;
import itumulator.executable.Program;
import itumulator.world.Location;
import itumulator.world.World;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException{

        Scanner scanner = new Scanner(new File("inputs/week-1/t1-1a.txt"));

        int size = Integer.parseInt(scanner.nextLine().trim());
        int delay = 1000;
        int display_size = 1000;
        Random r = new Random();
        Program p = new Program(size, display_size, delay);
        World w = p.getWorld();
        Placement placement = new Placement(w, size);

        //Indlæsning og placering af objeketer (se evt. Placement.java)

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.trim().split("\s+");
            String type = parts[0];
            String amountText = parts[1];
            int amount;

            if (amountText.contains("-")) {
                String[] bounds = amountText.split("-");
                int min = Integer.parseInt(bounds[0]);
                int max = Integer.parseInt(bounds[1]);

                amount = min + r.nextInt(max - min + 1);
            } else {
                amount = Integer.parseInt(amountText);
            }

            for (int i = 0; i < amount; i++) {
                switch (type) {
                    case "grass":
                        placement.placeNonBlocking(new Grass());
                        break;
                    case "rabbit":
                        placement.placeBlocking(new Rabbit());
                        break;
                    default:
                        throw new IllegalArgumentException("Ukendt type: " + type);
                }}
        }

        DisplayInformation grass = new DisplayInformation(Color.green, "grass");
        p.setDisplayInformation(Grass.class, grass);
        DisplayInformation rabbit = new DisplayInformation(Color.black, "rabbit-small");
        p.setDisplayInformation(Rabbit.class, rabbit);
        p.show();
        for (int i = 0; i < 200; i++) {
            p.simulate();
        }

    }
}