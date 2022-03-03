import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Move extends Transform{

    private final int resourceLimit;

    public Move(int resourceLimit, int actionPeriod, List<PImage> images, int animationPeriod, int imageIndex, String id, Point position){
        super(actionPeriod, images, animationPeriod, imageIndex, id, position);
        this.resourceLimit = resourceLimit;
    }

    public boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {

        if (Move.adjacent(this.position, target.getPosition())) {
            _moveToHelper(target);
            return true;
        }

        Point nextPos = this.nextPosition(world, target.getPosition());

        if (!this.position.equals(nextPos)) {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent()) {
                scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(this, nextPos);
        }
        return false;
    }

    protected abstract boolean _moveToHelper(Entity target);

    public Point nextPosition(
            WorldModel world, Point destPos)
    {

        PathingStrategy path = new AStarPathingStrategy();
        Predicate<Point> pred = x -> !world.isOccupied(x) || world.getOccupancyCell(x).getClass() == Stump.class && world.withinBounds(x);

        BiPredicate<Point, Point> biPred = (x, y) -> adjacent(x, y);

        List<Point> points = path.computePath(this.position, destPos, pred, biPred, PathingStrategy.CARDINAL_NEIGHBORS);

        if (points.size() == 0){
            return this.getPosition();
        }

        return points.get(0);

    }

    public static boolean adjacent(Point p1, Point p2) {
        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1) || (p1.getY() == p2.getY()
                && Math.abs(p1.getX() - p2.getX()) == 1);
    }

    public int getResourceLimit(){
        return resourceLimit;
    }
}
