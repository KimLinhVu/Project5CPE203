import processing.core.PImage;

import java.util.List;

public class Sapling extends Plant{

    private final int healthLimit;

    private static final int TREE_ANIMATION_MAX = 600;
    private static final int TREE_ANIMATION_MIN = 50;
    private static final int TREE_ACTION_MAX = 1400;
    private static final int TREE_ACTION_MIN = 1000;
    private static final int TREE_HEALTH_MAX = 3;
    private static final int TREE_HEALTH_MIN = 1;

    public Sapling(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(health, actionPeriod, images, animationPeriod, 0, id, position);
        this.healthLimit = healthLimit;
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        super.setHealth(1);
        super.executeActivity(world, imageStore, scheduler);
    }

    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (super.getHealth() >= this.healthLimit)
        {
            Tree tree = (Tree) Factory.createTree("tree_" + super.getId(),
                    this.position,
                    Transform.getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN),
                    Transform.getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN),
                    Transform.getNumFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN),
                    imageStore.getImageList(Functions.TREE_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return super.transform(world, scheduler, imageStore);
    }

}
