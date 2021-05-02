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

    public void addEntity(Entity entity) {
        entities.add(entity);
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
        for(Entity e : entities) {
            e.render(g);
        }
    }
}
