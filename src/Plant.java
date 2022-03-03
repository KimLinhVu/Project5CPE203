import processing.core.PImage;

import java.util.List;

public abstract class Plant extends Transform{

    private int health;

    public Plant(int health, int actionPeriod, List<PImage> images, int animationPeriod, int imageIndex, String id, Point position){
        super(actionPeriod, images, animationPeriod, imageIndex, id, position);
        this.health = health;
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {

        if (!transform(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    super.getActionPeriod());
        }
    }

    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (health <= 0) {
            Entity stump = Factory.createStump(super.getId(),
                    this.position,
                    imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);
            return true;
        }

        return false;
    }

    public void setHealth(int h){
        this.health += h;
    }

    public int getHealth(){
        return health;
    }
}
