public class Activity implements Action{

    private final ActiveEntity entity;
    private final WorldModel world;
    private final ImageStore imageStore;

    public Activity(ActiveEntity entity, WorldModel world, ImageStore imageStore) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler)
    {
        this.entity.executeActivity(this.world, this.imageStore, scheduler);
    }
}