package Life;
import itumulator.world.World;

public class Wolf extends Animals{
    public Wolf(){

        super(0, 100, null);
    }

    @Override
    public void act(World w){




        walk(w);
    }



}
