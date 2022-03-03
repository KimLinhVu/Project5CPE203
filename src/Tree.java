import processing.core.PImage;

import java.util.List;

public class Tree extends Plant{

    public Tree(String id, List<PImage> images, Point position, int actionPeriod, int animationPeriod, int health) {
        super(health, actionPeriod, images, animationPeriod, 0, id, position);
    }

}
