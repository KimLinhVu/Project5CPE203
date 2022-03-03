import processing.core.PImage;

import java.util.List;
import java.util.Random;

public abstract class Transform extends ActiveEntity{

    public Transform(int actionPeriod, List<PImage> images, int animationPeriod, int imageIndex, String id, Point position){
        super(actionPeriod, images, animationPeriod, imageIndex, id, position);
    }

    abstract boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    static int getNumFromRange(int max, int min)
    {
        Random rand = new Random();
        return min + rand.nextInt(
                max
                        - min);
    }
}
