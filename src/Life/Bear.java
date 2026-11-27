package Life;
import itumulator.world.World;

public class Bear extends Animals{
    public Bear(){
        super(0, 100, null);
    }

    @Override
    public void act(World w){

        walk(w);
    }



}
