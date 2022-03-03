import processing.core.PImage;

import java.util.List;

public class Obstacle extends AnimatedEntity{

    public Obstacle(String id, Point position, List<PImage> images, int animationPeriod) {
        super(images, animationPeriod,0, id, position);
    }
}
