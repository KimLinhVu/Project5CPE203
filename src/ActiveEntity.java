import processing.core.PImage;

import java.util.List;

public abstract class ActiveEntity extends AnimatedEntity{

    private final int actionPeriod;

    public ActiveEntity(int actionPeriod, List<PImage> images, int animationPeriod, int imageIndex, String id, Point position){
        super(images, animationPeriod, imageIndex, id, position);
        this.actionPeriod = actionPeriod;
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this,world, imageStore),
                actionPeriod);
        super.scheduleActions(scheduler, world, imageStore);
    }

    abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public int getActionPeriod(){
        return actionPeriod;
    }
}
