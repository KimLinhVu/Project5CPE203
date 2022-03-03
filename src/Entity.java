import processing.core.PImage;

import java.util.List;

public abstract class Entity{

    private final List<PImage> images;
    private final String id;
    public Point position;

    public Entity(List<PImage> images, String id, Point position){
        this.images = images;
        this.id = id;
        this.position = position;
    }

    public PImage getCurrentImage() {
        return images.get(0);
    }

    public List<PImage> getImages(){
        return images;
    }

    public Point getPosition(){
        return position;
    }

    public void setPosition(Point p){
        position = p;
    }

    public String getId(){
        return id;
    }

}