package shooter.entities;

import java.awt.*;
import java.util.ArrayList;
//test
public class EntityManager {

    private ArrayList<Entity> entities;
    private ArrayList<Entity> tempentities;
    private ArrayList<Entity> removeEntities;

    public EntityManager() {
        entities = new ArrayList<Entity>();
        tempentities = new ArrayList<Entity>();
        removeEntities = new ArrayList<Entity>();
    }

    public void addEntitytemp(Entity entity) {
        tempentities.add(entity);
    }

    public void removeEntity(Entity entity) {
        removeEntities.add(entity);
    }

    public Entity getClosestItem(float x, float y) {
        ArrayList<Entity> items = new ArrayList<Entity>();
        for(Entity entity : entities){
            if(entity.getClass() == Item.class && entity.active){
                items.add(entity);
            }
        }
        float lowestDistance = 0;
        Entity closestEntity = null;
        for(Entity item : items){
            float dx = x - item.getX();                      //pythagoras
            float dy = y - item.getY();                      //pythagoras
            float distance = ((float) (Math.sqrt((dx * dx) + (dy * dy))));  //pythagoras
            //System.out.println(distance);
            if(distance < lowestDistance || closestEntity == null){
                closestEntity = item;
                lowestDistance = distance;
            }
        }
        if(lowestDistance < 50) {
            //System.out.println("lowestdistance: "+lowestDistance);
            return closestEntity;
        } else {
            return null;
        }
    }

    public void tick(){
        for(Entity e : entities) {
            e.tick();
        }
        entities.addAll(tempentities);
        tempentities.clear();
        entities.removeAll(removeEntities);
        removeEntities.clear();


        //System.out.println(entities.size());
    }
    public void render(Graphics g){
        //TODO add render order (sort entities by Z level and render on after another)
        for(Entity e : entities) {
            e.render(g);
        }
    }
}
