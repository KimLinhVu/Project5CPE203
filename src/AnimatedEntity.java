import processing.core.PImage;

import java.util.List;

public abstract class AnimatedEntity extends Entity{

    private final int animationPeriod;
    private int imageIndex;

    public AnimatedEntity(List<PImage> images, int animationPeriod, int imageIndex, String id, Point position){
        super(images, id, position);
        this.animationPeriod = animationPeriod;
        this.imageIndex = imageIndex;
    }

    public PImage getCurrentImage() {
        return super.getImages().get(imageIndex);
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                Factory.createAnimationAction(this, 0),
                getAnimationPeriod());
    }

    public int getAnimationPeriod() {
        return animationPeriod;
    }

    public void nextImage() {
        this.imageIndex = ((this.imageIndex + 1) % super.getImages().size());
    }

}
