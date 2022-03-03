import processing.core.PImage;

import java.util.*;

public class DudeNotFull extends Move{

    private int resourceCount;

    public DudeNotFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(resourceLimit, actionPeriod, images, animationPeriod, 0, id, position);
        this.resourceCount = resourceCount;

    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> target =
                world.findNearest(this.position, new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (!target.isPresent() || !moveTo(world, target.get(), scheduler)
                || !transform(world, scheduler, imageStore))
        {
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
        if (this.resourceCount >= super.getResourceLimit()) {
            DudeFull miner = (DudeFull) Factory.createDudeFull(super.getId(),
                    this.position, super.getActionPeriod(),
                    super.getAnimationPeriod(),
                    super.getResourceLimit(),
                    super.getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    protected boolean _moveToHelper(Entity target) {
        this.resourceCount += 1;
        Plant p = (Plant) target;
        p.setHealth(-1);
        return true;
    }
}
